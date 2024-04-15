package com.tadpole.cloud.product.modular.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.objectLog.model.AttributeModel;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.objectLog.client.service.impl.LogClient;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.tadpole.cloud.product.config.K3CloudWebapiConfig;
import com.tadpole.cloud.product.core.util.GeneratorNoUtil;
import com.tadpole.cloud.product.modular.product.entity.RdManageUpRecord;
import com.tadpole.cloud.product.modular.product.entity.RdPlManage;
import com.tadpole.cloud.product.modular.product.entity.RdPssManage;
import com.tadpole.cloud.product.modular.product.mapper.RdPssManageMapper;
import com.tadpole.cloud.product.modular.product.model.params.RdPlManageParam;
import com.tadpole.cloud.product.modular.product.model.params.RdPssManageParam;
import com.tadpole.cloud.product.modular.product.model.result.RdPlManageResult;
import com.tadpole.cloud.product.modular.product.model.result.RdPssManageResult;
import com.tadpole.cloud.product.modular.product.service.IRdManageUpRecordService;
import com.tadpole.cloud.product.modular.product.service.IRdPlManageService;
import com.tadpole.cloud.product.modular.product.service.IRdPssManageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.product.modular.productplan.enums.RdPreProposalEnum;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;

/**
 * <p>
 * 产品同款管理 服务实现类
 * </p>
 *
 * @author S20210221
 * @since 2023-11-25
 */
@Service
public class RdPssManageServiceImpl extends ServiceImpl<RdPssManageMapper, RdPssManage> implements IRdPssManageService {

    @Resource
    private RdPssManageMapper mapper;

    @Autowired
    private IRdPlManageService plManageService;

    @Autowired
    IRdManageUpRecordService manageUpRecordService;

    @Resource
    private LogClient logClient;

    @Autowired
    K3CloudWebapiConfig k3CloudWebapiConfig;

    @Resource
    private GeneratorNoUtil generatorNoUtil;

    @DataSource(name ="product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<RdPssManageResult> listPage(RdPlManageParam param) {
        return this.baseMapper.listPage(param);
    }

    @DataSource(name ="product")
    @Override
    public void changeStatus(String code, String status) {
        UpdateWrapper<RdPssManage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("SYS_PL_CODE",code).set("SYS_STATUS",status).set("SYS_L_DATE",new Date());
        if(status.equals("关闭")){
            updateWrapper.set("SYS_CLOSE_DATE",new Date());
        }
        this.update(updateWrapper);
    }

    @DataSource(name ="product")
    @Override
    public ResponseData edit(RdPssManageParam param) throws IllegalAccessException {

        QueryWrapper<RdPssManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_PRO_NAME",param.getSysProName())
                .eq("SYS_STYLE",param.getSysStyle())
                .eq("SYS_MAIN_MATERIAL",param.getSysMainMaterial())
                .eq("SYS_BRAND",param.getSysBrand())
                .eq("SYS_MODEL",param.getSysModel())
                .ne("ID",param.getId());

        if(this.list(queryWrapper).size()>0){
            return ResponseData.error("产品线已存在！");
        }

        LoginUser loginUser = LoginContext.me().getLoginUser();

        RdPssManage infoNew = BeanUtil.copyProperties(param,RdPssManage.class);
        RdPssManage infoOld = this.getById(param.getId());

        List<AttributeModel> difList = logClient.getDifAttributeModel(infoOld,infoNew);
        Map codeName = this.getCodeName();
        for (AttributeModel attributeModel:difList) {
            attributeModel.setAttributeName(codeName.get(attributeModel.getAttributeAlias()).toString());
        }
        RdManageUpRecord record = new RdManageUpRecord();
        record.setSysType("SPU_RECORD");
        record.setSysLDate(new Date());
        record.setSysModifyRecordNum(infoOld.getSysSpu());
        record.setSysModifyContent(JSON.toJSONString(difList));
        record.setSysPerCode(loginUser.getAccount());
        record.setSysPerName(loginUser.getName());
        manageUpRecordService.save(record);

        UpdateWrapper<RdPssManage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID",param.getId())
                .set("SYS_PRO_NAME",param.getSysProName())
                .set("SYS_STYLE",param.getSysStyle())
                .set("SYS_MAIN_MATERIAL",param.getSysMainMaterial())
                .set("SYS_BRAND",param.getSysBrand())
                .set("SYS_MODEL",param.getSysModel())
                .set("SYS_L_DATE",new Date());
        this.update(updateWrapper);

        return ResponseData.success();
    }

    @DataSource(name ="product")
    @Override
    public void disable(RdPlManageParam param) {

        LoginUser loginUser = LoginContext.me().getLoginUser();
        List<AttributeModel> difList = new ArrayList<>();
        AttributeModel attributeModel = new AttributeModel();
        attributeModel.setAttributeName("状态");
        attributeModel.setAttributeAlias("sysStatus");
        attributeModel.setOldValue("正常");
        attributeModel.setNewValue("关闭");
        difList.add(attributeModel);
        RdManageUpRecord record = new RdManageUpRecord();
        record.setSysType("SPU_RECORD");
        record.setSysLDate(new Date());
        record.setSysModifyRecordNum(param.getSysPlCode());
        record.setSysModifyContent(JSON.toJSONString(difList));
        record.setSysPerCode(loginUser.getAccount());
        record.setSysPerName(loginUser.getName());
        manageUpRecordService.save(record);

        UpdateWrapper<RdPssManage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID",param.getId())
                .set("SYS_STATUS","关闭").set("SYS_L_DATE",new Date()).set("SYS_CLOSE_DATE",new Date());
        this.update(updateWrapper);
    }

    @DataSource(name ="product")
    @Override
    public void enable(RdPlManageParam param) {

        LoginUser loginUser = LoginContext.me().getLoginUser();
        List<AttributeModel> difList = new ArrayList<>();
        AttributeModel attributeModel = new AttributeModel();
        attributeModel.setAttributeName("状态");
        attributeModel.setAttributeAlias("sysStatus");
        attributeModel.setOldValue("正常");
        attributeModel.setNewValue("关闭");
        difList.add(attributeModel);
        RdManageUpRecord record = new RdManageUpRecord();
        record.setSysType("SPU_RECORD");
        record.setSysLDate(new Date());
        record.setSysModifyRecordNum(param.getSysPlCode());
        record.setSysModifyContent(JSON.toJSONString(difList));
        record.setSysPerCode(loginUser.getAccount());
        record.setSysPerName(loginUser.getName());
        manageUpRecordService.save(record);

        UpdateWrapper<RdPssManage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID",param.getId())
                .set("SYS_STATUS","正常").set("SYS_L_DATE",new Date());
        this.update(updateWrapper);
    }

    @DataSource(name ="product")
    @Override
    public List<RdPssManageResult> listProName(){
        return this.mapper.listProName();
    }

    @DataSource(name ="product")
    @Override
    public List<RdPssManageResult> listStyle(){
        return this.mapper.listStyle();
    }
    @DataSource(name ="product")
    @Override
    public List<RdPssManageResult> listMainMaterial(){
        return this.mapper.listMainMaterial();
    }
    @DataSource(name ="product")
    @Override
    public List<RdPssManageResult> listBrand(){
        return this.mapper.listBrand();
    }
    @DataSource(name ="product")
    @Override
    public List<RdPssManageResult> listModel(){
        return this.mapper.listModel();
    }

    @DataSource(name ="product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData addOrEditVersion(RdPssManageParam param) {
        RdPssManage rdPssManage = new RdPssManage();

        if (ObjectUtil.isNull(param.getSysSpu()) || param.getSysSpu().equals("")){
            rdPssManage = BeanUtil.copyProperties(param,RdPssManage.class);
            rdPssManage.setSysSpu(generatorNoUtil.getSpuBillNoExtents("0000","RD-SPU",4));
            rdPssManage.setSysCurIteVersion("V1.0.0");
            this.saveOrUpdate(rdPssManage);
        }else {
            QueryWrapper<RdPssManage> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("SYS_SPU",param.getSysSpu());
            rdPssManage = this.mapper.selectOne(queryWrapper);
            String[] sysCurAppVersionArr = rdPssManage.getSysCurAppVersion().replace("V","").split("\\.");
            String sysCurIteVersion = "";
            if (param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_PX.getName())){
                sysCurIteVersion = "V" + sysCurAppVersionArr[0] + "." + (Integer.parseInt(sysCurAppVersionArr[1]) + 1) + ".0";
            }else if (param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_PT.getName())){
                sysCurIteVersion = "V" + sysCurAppVersionArr[0] + "." + (Integer.parseInt(sysCurAppVersionArr[1]) + 1) + ".0";
            }else if (param.getSysDevMethond().equals(RdPreProposalEnum.YA_DEV_METHOND_PG.getName())){
                sysCurIteVersion = "V"+ (Integer.parseInt(sysCurAppVersionArr[0]) + 1) + ".0.0";
            }
            rdPssManage.setSysCurIteVersion(sysCurIteVersion);

            this.saveOrUpdate(rdPssManage);
        }
        return ResponseData.success(rdPssManage);

    }

    @DataSource(name ="product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData revokePssVersion(RdPssManageParam param) {

        if (ObjectUtil.isNull(param.getSysSpu()) || param.getSysSpu().equals("")){
            this.mapper.deletePassManage(param);
            generatorNoUtil.getResetSpuBillNoExtents("0000","RD-SPU",4);
        }else {
            QueryWrapper<RdPssManage> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_PL_CODE",param.getSysPlCode());
            queryWrapper.eq("SYS_SPU",param.getSysSpu());
            RdPssManage rdPssManage = this.mapper.selectOne(queryWrapper);
            rdPssManage.setSysCurIteVersion("");
            this.saveOrUpdate(rdPssManage);
        }
        return ResponseData.success();

    }


    public IdentifyInfo getConfigInfo() {

        IdentifyInfo identifyInfoAdb=new IdentifyInfo(){};
        //应用id
        identifyInfoAdb.setAppId(k3CloudWebapiConfig.getAppid());

        identifyInfoAdb.setAppSecret(k3CloudWebapiConfig.getAppsec());
        //账套id
        identifyInfoAdb.setdCID(k3CloudWebapiConfig.getAcctid());

        identifyInfoAdb.setUserName(k3CloudWebapiConfig.getUsername());

        identifyInfoAdb.setServerUrl(k3CloudWebapiConfig.getServerurl());
        return identifyInfoAdb;
    }

    public Map getCodeName(){
        //反射获取类对象
        Class<RdPssManage> invoiceImgExcelClass = RdPssManage.class;
        //获得所有字段
        Field[] declaredFields = invoiceImgExcelClass.getDeclaredFields();
        List<ApiModelProperty> excelHeaderAnnotations = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        for (Field field : declaredFields) {
            ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
            if (annotation != null) {
                map.put(field.getName(), annotation.value());
            }
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("name====>" + entry.getKey()+"value=============>"+entry.getValue() );
        }
        return map;
    }
}
