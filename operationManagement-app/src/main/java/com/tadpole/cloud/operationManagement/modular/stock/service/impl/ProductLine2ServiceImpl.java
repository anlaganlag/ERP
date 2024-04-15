package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.ProductLine2;
import com.tadpole.cloud.operationManagement.modular.stock.enums.StockExceptionEnum;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.ProductLine2Mapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.ProductLine2Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.ProductLineReplaceUpdateParam;
import com.tadpole.cloud.operationManagement.modular.stock.service.IProductLine2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 产品线 服务实现类
 * </p>
 *
 * @author lsy
 * @since 2022-06-16
 */
@Service
@Slf4j
public class ProductLine2ServiceImpl extends ServiceImpl<ProductLine2Mapper, ProductLine2> implements IProductLine2Service {



    @Resource
    private ProductLine2Mapper mapper;

    @Override
    @DataSource(name = "stocking")
    public ResponseData queryListPage(ProductLine2Param param) {

        Page pageContext = param.getPageContext();
        return ResponseData.success(this.baseMapper.queryListPage(pageContext, param));
    }

    @DataSource(name = "stocking")
    @Override
    public  List<ProductLine2> exportExcel(HttpServletResponse response, ProductLine2Param param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        List<ProductLine2> records = this.baseMapper.queryListPage(pageContext, param).getRecords();
//            ExcelUtils.exportExcel(records, "产品线分配数据", "sheet1", ProductLine2.class, "产品线导出数据New", response);
        return records;
    }


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData update(ProductLine2Param param) {

        if (!this.validateProductLien2BindingRepeat(param, true)) {
            return ResponseData.error(StockExceptionEnum.PRODUCT_LINE_IS_BINDING.getCode(), StockExceptionEnum.PRODUCT_LINE_IS_BINDING.getMessage());
        }

        ProductLine2 productLine2 = new ProductLine2();
        BeanUtils.copyProperties(param, productLine2);
        productLine2.setUpdateBy(this.getUserAccount());
        this.baseMapper.updateById(productLine2);
        return ResponseData.success();
    }


//    @Override
//    @DataSource(name = "stocking")
//    @Transactional(rollbackFor = Exception.class)
//    public ResponseData updateBatch(List<ProductLine2Param> paramList) {
//        if (CollectionUtil.isNotEmpty(paramList)) {
//            List<ProductLine2> productLine2List = new ArrayList<>();
//            for (ProductLine2Param param : paramList) {
////                if (! validateProductLien2BindingRepeat(param, true))
////                    return ResponseData.error(StockExceptionEnum.INSERT_PRODUCT_LINE_ERROR.getCode(), StockExceptionEnum.INSERT_PRODUCT_LINE_ERROR.getMessage());
//
//                LambdaQueryWrapper<ProductLine2> wrapper = new LambdaQueryWrapper<>();
//                wrapper.eq(ProductLine2::getId, param.getId());
//                ProductLine2 model = this.baseMapper.selectOne(wrapper);
//
//                model.setTeam(param.getTeam());
//                model.setDepartment(param.getDepartment());
//                model.setDeptMgr(param.getDeptMgr());
//                model.setDeptMgrName(param.getDeptMgrName());
//                model.setOperator(param.getOperator());
//                model.setOperatorName(param.getOperatorName());
//                model.setTeamSupervise(param.getTeamSupervise());
//                model.setTeamSuperviseName(param.getTeamSuperviseName());
//
//                model.setUpdateAt(new Date());
//                model.setUpdateBy(this.getUserAccount());
//
//                //BeanUtils.copyProperties(param, productLine2);
//                productLine2List.add(model);
//            }
//            this.updateBatchById(productLine2List);
//        }
//        return ResponseData.success();
//    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData replaceUpdateBatch(ProductLineReplaceUpdateParam param) {
        List<String> idList = param.getIdList();
        if (CollectionUtil.isEmpty(idList)) {
            return ResponseData.error("无勾选数据");
        }
        String deptMgrOld="",deptMgrNew="",teamSupvOld="",teamSupvNew="",operatorOld="",operatorNew="",deptMgrNameOld="",deptMgrNameNew="",teamSupvNameOld="",teamSupvNameNew="",operatorNameOld="",operatorNameNew="";
        List<String> deptMgr = param.getDeptMgr() == null? new ArrayList<>():Arrays.asList(param.getDeptMgr().split("\\|"));
        List<String> deptMgrName = param.getDeptMgrName() == null? new ArrayList<>(): Arrays.asList(param.getDeptMgrName().split("\\|"));
        if (deptMgr.size() == 2 && deptMgrName.size()==2){
             deptMgrOld = deptMgr.get(0);
             deptMgrNew = deptMgr.get(1);
             deptMgrNameOld = deptMgrName.get(0);
             deptMgrNameNew = deptMgrName.get(1);
        }else if (deptMgr.size() == 1 && deptMgrName.size()==1){
            deptMgrOld = deptMgr.get(0);
            deptMgrNameOld = deptMgrName.get(0);
        }

        List<String> teamSupv = param.getTeamSupv() == null? new ArrayList<>():Arrays.asList(param.getTeamSupv().split("\\|"));
        List<String> teamSupvName = param.getTeamSupvName() == null? new ArrayList<>():Arrays.asList(param.getTeamSupvName().split("\\|"));
        if (teamSupv.size() == 2 && teamSupvName.size()==2){
            teamSupvOld = teamSupv.get(0);
            teamSupvNew = teamSupv.get(1);
            teamSupvNameOld = teamSupvName.get(0);
            teamSupvNameNew = teamSupvName.get(1);
        }else if (teamSupv.size() == 1 && teamSupvName.size()==1){
            teamSupvOld = teamSupv.get(0);
            teamSupvNameOld = teamSupvName.get(0);
        }
        List<String> operator = param.getOperator() == null? new ArrayList<>(): Arrays.asList(param.getOperator().split("\\|"));
        List<String> operatorName = param.getOperatorName() == null? new ArrayList<>():Arrays.asList(param.getOperatorName().split("\\|"));
        if (operator.size() == 2 && operatorName.size()==2){
            operatorOld = operator.get(0);
            operatorNew = operator.get(1);
            operatorNameOld = operatorName.get(0);
            operatorNameNew = operatorName.get(1);
        } else if (operator.size() == 1 && operatorName.size()==1){
            operatorOld = operator.get(0);
            operatorNameOld = operatorName.get(0);
        }

            LambdaQueryWrapper<ProductLine2> wrapper = new LambdaQueryWrapper<>();

            List<ProductLine2> productLine2List = new ArrayList<>();
            for (String id : idList) {

                wrapper.eq(ProductLine2::getId, id);
                ProductLine2 pl = this.baseMapper.selectOne(wrapper);
                wrapper.clear();
                if (ObjectUtil.isEmpty(pl)){
                    continue;
                }
                //事业部
                // a -> b
                if (StrUtil.isNotEmpty(deptMgrOld)&&StrUtil.isNotEmpty(deptMgrNew)&&StrUtil.isNotEmpty(deptMgrNameOld)&&StrUtil.isNotEmpty(deptMgrNameNew)) {
                    pl.setDeptMgrName(pl.getDeptMgrName().replace(deptMgrNameOld, deptMgrNameNew));
                    pl.setDeptMgr(pl.getDeptMgr().replace(deptMgrOld, deptMgrNew));
                    // -> b
                } else if (StrUtil.isEmpty(deptMgrOld)&&StrUtil.isNotEmpty(deptMgrNew)&&StrUtil.isEmpty(deptMgrNameOld)&&StrUtil.isNotEmpty(deptMgrNameNew)){
                    if (pl.getDeptMgrName()!=null){
                        ArrayList<String> list1= new ArrayList<>(Arrays.asList(pl.getDeptMgrName().split(",")));
                        ArrayList<String> list2= new ArrayList<>(Arrays.asList(deptMgrNameNew.split(",")));
                        list1.retainAll(list2);
                        if (CollectionUtil.isNotEmpty(list1)){
                            return ResponseData.error(list1+"事业部经理已存在!");
                        }
                    }
                    pl.setDeptMgrName(pl.getDeptMgrName()==null?deptMgrNameNew:pl.getDeptMgrName()+","+deptMgrNameNew);
                    pl.setDeptMgr(pl.getDeptMgr()==null?deptMgrNew:pl.getDeptMgr()+","+deptMgrNew);
                    // a ->
                } else if (StrUtil.isNotEmpty(deptMgrOld)&&StrUtil.isEmpty(deptMgrNew)&&StrUtil.isNotEmpty(deptMgrNameOld)&&StrUtil.isEmpty(deptMgrNameNew)) {
                    pl.setDeptMgrName(StrUtil.strip(pl.getDeptMgrName().replace(deptMgrNameOld,"").replace(",,",","),","));
                    pl.setDeptMgr(StrUtil.strip(pl.getDeptMgr().replace(deptMgrOld,"").replace(",,",","),","));
                }

                //Team主管


                if (StrUtil.isNotEmpty(teamSupvOld)&&StrUtil.isNotEmpty(teamSupvNew)&&StrUtil.isNotEmpty(teamSupvNameOld)&&StrUtil.isNotEmpty(teamSupvNameNew)){
                    pl.setTeamSuperviseName(pl.getTeamSuperviseName().replace(teamSupvNameOld, teamSupvNameNew));
                    pl.setTeamSupervise(pl.getTeamSupervise().replace(teamSupvOld, teamSupvNew));
                    // -> b
                } else if (StrUtil.isEmpty(teamSupvOld)&&StrUtil.isNotEmpty(teamSupvNew)&&StrUtil.isEmpty(teamSupvNameOld)&&StrUtil.isNotEmpty(teamSupvNameNew)){
                    if (pl.getTeamSuperviseName()!=null){
                        ArrayList<String> list1= new ArrayList<>(Arrays.asList(pl.getTeamSuperviseName().split(",")));
                        ArrayList<String> list2= new ArrayList<>(Arrays.asList(teamSupvNameNew.split(",")));
                        list1.retainAll(list2);
                        if (CollectionUtil.isNotEmpty(list1)){
                            return ResponseData.error(list1+"Team主管已存在!");
                        }
                    }
                    pl.setTeamSuperviseName(pl.getTeamSuperviseName()==null?teamSupvNameNew:pl.getTeamSuperviseName()+","+teamSupvNameNew);
                    pl.setTeamSupervise(pl.getTeamSupervise()==null?teamSupvNew:pl.getTeamSupervise()+","+teamSupvNew);
                    // a ->
                } else if (StrUtil.isNotEmpty(teamSupvOld)&&StrUtil.isEmpty(teamSupvNew)&&StrUtil.isNotEmpty(teamSupvNameOld)&&StrUtil.isEmpty(teamSupvNameNew)) {
                    pl.setTeamSuperviseName(StrUtil.strip(pl.getTeamSuperviseName().replace(teamSupvNameOld,"").replace(",,",","),","));
                    pl.setTeamSupervise(StrUtil.strip(pl.getTeamSupervise().replace(teamSupvOld,"").replace(",,",","),","));
                }

                //运营人员
                if (StrUtil.isNotEmpty(operatorOld)&&StrUtil.isNotEmpty(operatorNew)&&StrUtil.isNotEmpty(operatorNameOld)&&StrUtil.isNotEmpty(operatorNameNew)) {
                    pl.setOperatorName(pl.getOperatorName().replace(operatorNameOld, operatorNameNew));
                    pl.setOperator(pl.getOperator().replace(operatorOld, operatorNew));
                    // -> b
                } else if (StrUtil.isEmpty(operatorOld)&&StrUtil.isNotEmpty(operatorNew)&&StrUtil.isEmpty(operatorNameOld)&&StrUtil.isNotEmpty(operatorNameNew)){
                    if (pl.getOperatorName()!=null){
                        ArrayList<String> list1= new ArrayList<>(Arrays.asList(pl.getOperatorName().split(",")));
                        ArrayList<String> list2= new ArrayList<>(Arrays.asList(operatorNameNew.split(",")));
                        list1.retainAll(list2);
                        if (CollectionUtil.isNotEmpty(list1)){
                            return ResponseData.error(list1+"运营人员已存在!");
                        }
                    }
                    pl.setOperatorName(pl.getOperatorName()==null?operatorNameNew:pl.getOperatorName()+","+operatorNameNew);
                    pl.setOperator(pl.getOperator()==null?operatorNew:pl.getOperator()+","+operatorNew);
                    // a ->
                } else if (StrUtil.isNotEmpty(operatorOld)&&StrUtil.isEmpty(operatorNew)&&StrUtil.isNotEmpty(operatorNameOld)&&StrUtil.isEmpty(operatorNameNew)) {
                    pl.setOperatorName(StrUtil.strip(pl.getOperatorName().replace(operatorNameOld,"").replace(",,",","),","));
                    pl.setOperator(StrUtil.strip(pl.getOperator().replace(operatorOld,"").replace(",,",","),","));
                }
//                pl.setTeamSupervise(pl.getTeamSupervise().replace(teamSupvOld,teamSupvNew));
//
//
//                pl.setOperatorName(pl.getOperatorName().replace(operatorNameOld,operatorNameNew));
//                pl.setOperator(pl.getOperator().replace(operatorOld,operatorNew));
                pl.setUpdateAt(new Date());
                pl.setUpdateBy(this.getUserAccount());
                productLine2List.add(pl);
            }
            this.updateBatchById(productLine2List);
             return ResponseData.success();

    }



    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData insertBatch(List<ProductLine2Param> paramList) {
        if (CollectionUtil.isNotEmpty(paramList)) {
            List<ProductLine2> productLineList = new ArrayList<>();
            for (ProductLine2Param param : paramList) {
                if (validateProductLien2BindingRepeat(param, false)) {

                    return ResponseData.error(StockExceptionEnum.INSERT_PRODUCT_LINE_ERROR.getCode(), StockExceptionEnum.INSERT_PRODUCT_LINE_ERROR.getMessage());
                }

                ProductLine2 insertProductLine2 = new ProductLine2();
                insertProductLine2.setProductType(param.getProductType());
                insertProductLine2.setPlatform(param.getPlatform());
                insertProductLine2.setArea(param.getArea());
                insertProductLine2.setDepartment(param.getDepartment());
                insertProductLine2.setTeam(param.getTeam());
                insertProductLine2.setOperator(param.getOperator());
                insertProductLine2.setOperatorName(param.getOperatorName());
                insertProductLine2.setTeamSupervise(param.getTeamSupervise());
                insertProductLine2.setTeamSuperviseName(param.getTeamSuperviseName());
                insertProductLine2.setDeptMgr(param.getDeptMgr());
                insertProductLine2.setDeptMgrName(param.getDeptMgrName());
                insertProductLine2.setCreateBy(this.getUserAccount());
                insertProductLine2.setCreateAt(DateTime.now());
                productLineList.add(insertProductLine2);
            }
            if (this.saveBatch(productLineList)) {

                return ResponseData.success();
            }
        }
        return ResponseData.error(StockExceptionEnum.INSERT_PRODUCT_LINE_ERROR.getCode(), StockExceptionEnum.INSERT_PRODUCT_LINE_ERROR.getMessage());
    }

    @Override
    @DataSource(name = "stocking")
    public ResponseData delete(Integer id) {
        if (this.baseMapper.deleteById(id) > 0) {
            return ResponseData.success();
        }
        return ResponseData.error(StockExceptionEnum.DELETE_FAIL.getCode(), StockExceptionEnum.DELETE_FAIL.getMessage());
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData deleteBatch(List<Integer> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            try {
                this.baseMapper.deleteBatchIds(list);
            } catch (Exception e) {
                return ResponseData.error(StockExceptionEnum.BATCH_DELETE_FAIL.getCode(), StockExceptionEnum.BATCH_DELETE_FAIL.getMessage());
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "replenish")
    public ResponseData lineAnalysis(ProductLine2Param param) {
        Page pageContext = param.getPageContext();
        return ResponseData.success(this.baseMapper.lineAnalysis(pageContext, param));
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdateBatch(List<ProductLine2> dataList) {
        return this.saveOrUpdateBatch(dataList, 1000);
    }

    /**
     * @param param      ProductLine2Param对象
     * @param validateID 是否验证ID，确保Param.ID已存在
     * @return
     */
    @DataSource(name = "stocking")
    private Boolean validateProductLien2BindingRepeat(ProductLine2Param param, Boolean validateID) {
        ProductLine2 result = null;
        boolean isBindinqRepeat = false;
        //        QueryWrapper<ProductLine2> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<ProductLine2> wrapper = new LambdaQueryWrapper<>();
        if (!ObjectUtil.isNull(param.getProductType())) {

            wrapper.eq(ProductLine2::getProductType, param.getProductType());
        }
        if (!ObjectUtil.isNull(param.getPlatform())) {

            wrapper.eq(ProductLine2::getPlatform, param.getPlatform());
        }
        if (!ObjectUtil.isNull(param.getArea())) {

            wrapper.eq(ProductLine2::getArea, param.getArea());
        }
        if (!ObjectUtil.isNull(param.getDepartment())) {

            wrapper.eq(ProductLine2::getDepartment, param.getDepartment());
        }
        if (!ObjectUtil.isNull(param.getTeam())) {

            wrapper.eq(ProductLine2::getTeam, param.getTeam());
        }
        //.eq(ProductLine2::getOperator, param.getOperator())
        //.eq(ProductLine2::getOperatorName,param.getOperatorName())
        //.eq(ProductLine2::getTeamSupervise, param.getTeamSupervise())
        //.eq(ProductLine2::getTeamSuperviseName, param.getTeamSuperviseName())
        //.eq(ProductLine2::getDeptMgr, param.getDeptMgr())
        //.eq(ProductLine2::getDeptMgrName, param.getDeptMgr())
        ;
        if (validateID) {
            wrapper.eq(ProductLine2::getId, param.getId());
            result = this.baseMapper.selectOne(wrapper);
        } else {
            List<ProductLine2> results = this.baseMapper.selectList(wrapper);
            if (param.getId() != null) {
                //TODO：写法【集合对象属性是否包含某一个值】
                if (results.stream().map(ProductLine2::getId).collect(Collectors.toList()).contains(param.getId())) {
                    //TODO：写法【集合中获取符合条件的对象】
                    result = results.stream().filter(f -> f.getId() == param.getId()).findAny().orElse(null);
                }
            }
        }
        if (result != null) {
            isBindinqRepeat = true;
            log.info(StockExceptionEnum.PRODUCT_LINE_IS_BINDING + "详细信息：运营大类[{}],平台[{}],区域[{}],部门[{}],Team[{}],事业部经理[{}],Team主管[{}],运营人员[{}]",
                    result.getProductType(),
                    result.getPlatform(),
                    result.getArea(),
                    result.getDepartment(),
                    result.getTeam(),
                    result.getOperator(),
                    result.getOperatorName(),
                    result.getDeptMgr(),
                    result.getDeptMgrName(),
                    result.getTeamSupervise(),
                    result.getTeamSuperviseName());
        }
        return isBindinqRepeat;
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    @Override
    @DataSource(name = "stocking")
    public ProductLine2 getUserDeptTeam(String userNo){
        return new LambdaQueryChainWrapper<>(mapper).like(ProductLine2::getTeamSupervise,userNo).list().stream().findFirst().orElse(null);
    }

    private String getUserName() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getName();
    }

    private String getUserAccount() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getAccount();
    }

}
