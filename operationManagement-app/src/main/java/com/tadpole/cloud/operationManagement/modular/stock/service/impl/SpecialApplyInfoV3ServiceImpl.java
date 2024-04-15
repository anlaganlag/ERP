package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant;
import com.tadpole.cloud.operationManagement.modular.stock.entity.PurchaseOrders;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SpecialApplyInfoV3;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SysBizConfig;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.SpecialApplyInfoV3Mapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SpecialApplyInfoV3ComitDto;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SpecialApplyInfoV3Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SpecialApplyInfoV3Result;
import com.tadpole.cloud.operationManagement.modular.stock.service.*;
import com.tadpole.cloud.platformSettlement.api.finance.entity.Material;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
* <p>
    * 特殊备货申请V3 服务实现类
    * </p>
*
* @author lsy
* @since 2022-08-31
*/
@Slf4j
@Service
public class SpecialApplyInfoV3ServiceImpl extends ServiceImpl<SpecialApplyInfoV3Mapper, SpecialApplyInfoV3> implements ISpecialApplyInfoV3Service {

    @Resource
    private SpecialApplyInfoV3Mapper specialApplyInfoV3Mapper;

    @Autowired
    IBackPrepareRecomBiService backPrepareRecomBiService;

    @Autowired
    private IPurchaseOrdersService purchaseOrdersService;

    @Autowired
    private ITeamVerifService teamVerifService;

    @Resource
    private IProductLine2Service productLine2Service;

    @Autowired
    private IVerifInfoRecordService verifInfoRecordService;

    @Autowired
    private ISysBizConfigService sysBizConfigService;

    @Autowired
    private IStockAreaBlacklistService stockAreaBlacklistService;

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    @Override
    @DataSource(name = "stocking")
    public ResponseData queryListPage(SpecialApplyInfoV3Param param) {
        Page pageContext = param.getPageContext();
        IPage pages = this.baseMapper.queryListPage(pageContext, param);
        return ResponseData.success(new PageResult<>(pages));
    }

    @Override
    @DataSource(name = "stocking")
    public List<SpecialApplyInfoV3Result> uploadValidateData(List<String> mergeFields){
        return this.baseMapper.uploadValidateData(mergeFields);
    }

    @Override
    @DataSource(name = "stocking")
    public ResponseData queryList(SpecialApplyInfoV3Param param) {
//        param.setOperator(loginUserInfo.getUserAccount());
        return ResponseData.success(this.baseMapper.queryList2(param));
    }

    @DataSource(name = "stocking")
    private  ResponseData validateIsExists(SpecialApplyInfoV3Param param,String controllerName){
        LoginUser userInfo = LoginContext.me().getLoginUser();
        //验证唯一
        LambdaQueryWrapper<SpecialApplyInfoV3> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SpecialApplyInfoV3::getStatus,0);
        wrapper.eq(SpecialApplyInfoV3::getPlatform,param.getPlatform());
        wrapper.eq(SpecialApplyInfoV3::getArea,param.getArea());
        wrapper.eq(SpecialApplyInfoV3::getDepartment,userInfo.getDepartment());
        wrapper.eq(SpecialApplyInfoV3::getTeam,userInfo.getTeam());
        wrapper.eq(SpecialApplyInfoV3::getMaterialCode,param.getMaterialCode());
        wrapper.eq(SpecialApplyInfoV3::getBillType,param.getBillType());
        if(ObjectUtil.isNotEmpty(param.getId())){
            wrapper.ne(SpecialApplyInfoV3::getId,param.getId());
        }
        if(specialApplyInfoV3Mapper.selectCount(wrapper)>0){
            return ResponseData.error(controllerName+"新增失败，原因：平台+区域+事业部+Team+物料编码+备货类型+备货状态 只允许存在一条[未提交]记录.");
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData add(SpecialApplyInfoV3Param param, Material material, String controllerName) {

        ResponseData blacklistResponseData = this.validateIsBlacklist(param,controllerName);//验证黑名单
        if (!blacklistResponseData.getSuccess()) {
            return blacklistResponseData;
        }

        ResponseData responseData = this.validateIsExists(param,controllerName);//验证唯一
        if (!responseData.getSuccess()) {
            return responseData;
        }

        LoginUser userInfo = LoginContext.me().getLoginUser();
        //ProductLine2 pl2 = productLine2Service.getUserDeptTeam(loginUserInfo.getUserAccount());

        SpecialApplyInfoV3 entity = BeanUtil.copyProperties(param, SpecialApplyInfoV3.class);

        //entity.setDepartment(pl2.getDepartment());  //entity.setDepartment(userInfo.getDepartment());
        //entity.setTeam(pl2.getTeam());              //entity.setTeam(userInfo.getTeam());

        entity.setStatus(BigDecimal.valueOf(0));
        entity.setApplyPersonNo(userInfo.getAccount());
        entity.setApplyPersonName(userInfo.getName());
        entity.setApplyDate(new Date());
        entity.setProductType(material.getProductType());
        entity.setProductName(material.getProductName());
        entity.setInitType(BigDecimal.valueOf(1));
        entity.setCreateTime(new Date());

        entity.setMergeField(entity.getPlatform()+entity.getDepartment()+ entity.getTeam()+entity.getMaterialCode()+entity.getBillType());
        if(!this.save(entity)){
            return ResponseData.error(controllerName+"新增失败，原因：存储异常。");
        }
        return ResponseData.success();
    }

    @DataSource(name = "stocking")
    private ResponseData validateIsBlacklist(SpecialApplyInfoV3Param param, String controllerName) {

//        LoginUser userInfo = loginUserInfo.getUserInfo();
//        param.setDepartment(userInfo.getDepartment());
//        param.setTeam(userInfo.getTeam());
        List<SpecialApplyInfoV3Param> paramList = new ArrayList<>();
        paramList.add(param);
        return  stockAreaBlacklistService.checkAreaBlacklist(paramList);

    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData upload(List<SpecialApplyInfoV3> entityList, String controllerName) {

        if(!this.saveBatch(entityList,500)){
            return  ResponseData.error(controllerName+"导入失败，数据存储异常，请稍后重试。");
        }
        return ResponseData.success();
    }

    @DataSource(name = "stocking")
    @Override
    public ResponseData edit(SpecialApplyInfoV3Param param, String controllerName) {
        //验证唯一

        ResponseData responseData = this.validateIsExists(param,controllerName);
        if (!responseData.getSuccess()) {
            return responseData;
        }

        LambdaUpdateWrapper<SpecialApplyInfoV3> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SpecialApplyInfoV3::getId, param.getId());
        updateWrapper.set(SpecialApplyInfoV3::getSalesStockDays, param.getSalesStockDays());
        updateWrapper.set(SpecialApplyInfoV3::getSalesDemand, param.getSalesDemand());
        updateWrapper.set(SpecialApplyInfoV3::getOperExpectedDate, param.getOperExpectedDate());
        updateWrapper.set(SpecialApplyInfoV3::getApplyQty, param.getApplyQty());
        updateWrapper.set(SpecialApplyInfoV3::getStockReason, param.getStockReason());
        updateWrapper.set(SpecialApplyInfoV3::getDeliveryType, param.getDeliveryType());
        updateWrapper.set(SpecialApplyInfoV3::getUpdateTime,new Date());
        if(!this.update(updateWrapper)) {
            return ResponseData.error(controllerName+"修改失败，请稍后重试！");
        }
        return ResponseData.success();
    }

    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData delete(List<String> ids, String controllerName) {
        if(specialApplyInfoV3Mapper.deleteBatchIds(ids)<=0){
            return ResponseData.error(controllerName+"删除失败，原因：未获取到待删除数据。");
        }
        return ResponseData.success(controllerName+"删除成功。");
    }


    @DataSource(name = "stocking")
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchSubmit(SpecialApplyInfoV3ComitDto param) {
        List<Integer> idList = param.getIdList();

        Map<String, List<SpecialApplyInfoV3>> unsubmitDataMap = new HashMap<>();

        List<SpecialApplyInfoV3> applyInfoList = this.baseMapper.selectBatchIds(idList);
        Map<String, List<SpecialApplyInfoV3>> groupApplyInfo = applyInfoList.stream().collect(Collectors.groupingBy(SpecialApplyInfoV3::getMergeField));

        if (param.getIgnoreWarn()!=1) {//不忽略警告,取消提交
            LambdaQueryWrapper<SpecialApplyInfoV3> wrapper = new LambdaQueryWrapper<>();
            for (String mergeField : groupApplyInfo.keySet()) {
                wrapper.eq(SpecialApplyInfoV3::getMergeField, mergeField);
                List<SpecialApplyInfoV3> allAreaData = this.baseMapper.selectList(wrapper);
                if ( allAreaData.size() != groupApplyInfo.get(mergeField).size() ) {
                    //本次勾选还有其他区域的数据没有一起提交，过滤出还没提交的数据
                    List<SpecialApplyInfoV3> unsubmitData = allAreaData.stream().filter(ad -> !idList.contains(ad.getId().intValue())).collect(Collectors.toList());
                    unsubmitDataMap.put(mergeField, unsubmitData);
                }
            }

            if ( unsubmitDataMap.size() !=0 ) {
                if (ObjectUtil.isNull(param.getIgnoreWarn()) || param.getIgnoreWarn()==0) {
                    return ResponseData.success(200, "本次批量提交按以下维度分析：平台+事业部+Team+物料编码+备货类型，还有其他区域的数据没能一起提交，是否忽略？", unsubmitDataMap);
                }
            }
        }

        //都勾选了提交，或直接忽略提示

        for (Map.Entry<String, List<SpecialApplyInfoV3>> entry : groupApplyInfo.entrySet()) {
            try {
                String mergeField = entry.getKey();
                List<SpecialApplyInfoV3> infoList = entry.getValue();
                this.submit(mergeField,infoList);
//                this.updateBatchById(infoList);

            } catch (Exception e) {
                log.error("提交错误:{}", JSON.toJSONString(e));
                return ResponseData.error("提交异常"+e.toString());
            }
        }

        return ResponseData.success();
    }


    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void submit(String mergeField, List<SpecialApplyInfoV3> infoList) throws Exception{

        ArrayList<TeamVerif> teamVerifList = new ArrayList<>();

        for (SpecialApplyInfoV3 applyInfo : infoList) {
//            TeamVerif teamVerif = backPrepareRecomBiService.creatTeamVerif(applyInfo);
            TeamVerif teamVerif = backPrepareRecomBiService.initTeamVerif(applyInfo);
          /*  if (ObjectUtil.isNull(teamVerif)) {
                throw new Exception("特殊备货申请提交时，按照("
                        +"<平台:"+applyInfo.getPlatform()
                        +">|<区域:"+applyInfo.getArea()
                        +">|<部门:"+applyInfo.getDepartment()
                        +">|<Team:"+applyInfo.getTeam()
                        +">|<物料编码:"+applyInfo.getMaterialCode()+
                        ")维度，没有关联上每日备货推荐的数据");
            }*/

            if (ObjectUtil.isNotNull(teamVerif)) {
                teamVerifService.saveTeamverif(teamVerif);
//            this.saveTeamverif(teamVerif);
                applyInfo.setTeamVerifNo(teamVerif.getId().intValue());
            }

            teamVerifList.add(teamVerif);
            applyInfo.setTeamVerifNo(teamVerif.getId().intValue());
        }

        PurchaseOrders order = backPrepareRecomBiService.createPurchaseOrder(teamVerifList,infoList);
       /* if (ObjectUtil.isNull(order)) {
            throw new Exception("特殊备货申请提交时创建采购申请单为null");
        }

        for (SpecialApplyInfoV3 applyInfo : infoList) {
            applyInfo.setPurchaseApplyNo(order.getId());
            applyInfo.setStatus(BigDecimal.valueOf(2));
            applyInfo.setUpdateTime(new Date());
            applyInfo.setApplyDate(new Date());
//            this.baseMapper.updateById(applyInfo);
        }

        this.updateBatchByIdList(infoList);*/



    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchByIdList(List<SpecialApplyInfoV3> infoList) {
      return   this.updateBatchById(infoList);
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTeamverif(TeamVerif teamVerif) {
        teamVerifService.getBaseMapper().insert(teamVerif);
    }


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData flashSpecialApplyData(SysBizConfig sysBizConfig) {

        LambdaQueryWrapper<PurchaseOrders> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(PurchaseOrders::getBillType, "RCBH")
                .eq(PurchaseOrders::getOrderStatus,StockConstant.ORDER_STATUS_PLAN_WAIT);
        List<PurchaseOrders> ordersList = purchaseOrdersService.list(wrapper);

        if (ObjectUtil.isEmpty(ordersList)) {
            return ResponseData.success("没有找到需要更新的特殊备货申请单");
        }

        for (PurchaseOrders purchaseOrders : ordersList) {

            try {
                ResponseData result=  this.flashApplyData(purchaseOrders);
            } catch (Exception e) {
               log.error("刷新特殊备货申请的每日推荐数据出现异常订单编号:{},异常信息:{}",purchaseOrders.getId(),JSON.toJSONString(e));
            }
        }

        sysBizConfigService.updateActionResult(sysBizConfig.getId(),"1");

        return ResponseData.success();
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData flashApplyData(PurchaseOrders order) {

        LambdaQueryWrapper<SpecialApplyInfoV3> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SpecialApplyInfoV3::getPurchaseApplyNo, order.getId());
        List<SpecialApplyInfoV3> applyInfoList = this.baseMapper.selectList(wrapper);


        LambdaQueryWrapper<TeamVerif> teamWrapper = new LambdaQueryWrapper<>();
        teamWrapper.eq(TeamVerif::getPurchaseApplyNo, order.getId());
        List<TeamVerif> teamVerifList = teamVerifService.list(teamWrapper);

        for (SpecialApplyInfoV3 applyInfo : applyInfoList) {
            TeamVerif needTeamVerif = teamVerifList.stream().filter(t -> t.getId().intValue() == applyInfo.getTeamVerifNo().intValue()).findFirst().get();
//            backPrepareRecomBiService.updateTeamVerif(applyInfo,needTeamVerif);  ok

            TeamVerif initTeamVerif = backPrepareRecomBiService.initTeamVerif(applyInfo);
            BeanUtil.copyProperties(initTeamVerif, needTeamVerif, CopyOptions.create().setIgnoreNullValue(true).setIgnoreProperties("createTime"));
            needTeamVerif.setId(BigDecimal.valueOf(applyInfo.getTeamVerifNo()));
            teamVerifService.updateById(needTeamVerif);
        }
        //tes
            backPrepareRecomBiService.updatePurchaseOrder(order,teamVerifList);


        return  ResponseData.success();
    }
}
