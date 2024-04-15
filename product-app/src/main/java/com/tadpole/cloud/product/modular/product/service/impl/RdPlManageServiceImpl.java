package com.tadpole.cloud.product.modular.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.objectLog.model.AttributeModel;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.objectLog.client.service.impl.LogClient;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.tadpole.cloud.product.config.K3CloudWebapiConfig;
import com.tadpole.cloud.product.core.util.GeneratorNoUtil;
import com.tadpole.cloud.product.modular.product.entity.RdManageUpRecord;
import com.tadpole.cloud.product.modular.product.entity.RdPlManage;
import com.tadpole.cloud.product.modular.product.mapper.RdPlManageMapper;
import com.tadpole.cloud.product.modular.product.model.params.RdPlManageParam;
import com.tadpole.cloud.product.modular.product.model.result.RdPlManageResult;
import com.tadpole.cloud.product.modular.product.service.IRdManageUpRecordService;
import com.tadpole.cloud.product.modular.product.service.IRdPlManageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.product.modular.product.service.IRdPssManageService;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;

/**
 * <p>
 * 产品线管理 服务实现类
 * </p>
 *
 * @author S20210221
 * @since 2023-11-25
 */
@Service
public class RdPlManageServiceImpl extends ServiceImpl<RdPlManageMapper, RdPlManage> implements IRdPlManageService {

    @Resource
    private RdPlManageMapper mapper;

    private static String STATUS_NORMAL ="正常";
    private static String STATUS_CLOSE ="关闭";
    @Resource
    private GeneratorNoUtil generatorNoUtil;

    @Autowired
    IRdPssManageService pssManageService;

    @Autowired
    IRdManageUpRecordService manageUpRecordService;

    @Resource
    private LogClient logClient;

    @Autowired
    K3CloudWebapiConfig k3CloudWebapiConfig;

    @DataSource(name ="product")
    @Override
    public List<RdPlManageResult> list(RdPlManageParam param) {

        return this.baseMapper.list(param);
    }

    @DataSource(name ="product")
    @Override
    public PageResult<RdPlManageResult> listPage(RdPlManageParam param) {
        Page pageContext = param.getPageContext();
        IPage<RdPlManageResult> page = this.baseMapper.listPage(pageContext, param);

        return new PageResult<>(page);
    }

    @DataSource(name ="product")
    @Override
    public ResponseData add(RdPlManageParam param) {

        QueryWrapper<RdPlManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_PL_NAME",param.getSysPlName());

        if(this.list(queryWrapper).size()>0){
            return ResponseData.error("产品线已存在！");
        }

        LoginUser loginUser = LoginContext.me().getLoginUser();

        RdPlManage manage = BeanUtil.copyProperties(param,RdPlManage.class);
        manage.setSysPlCode(generatorNoUtil.getBillNo());
        manage.setSysCDate(new Date());
        manage.setSysLDate(new Date());
        manage.setSysPerCode(loginUser.getAccount());
        manage.setSysPerName(loginUser.getName());
        manage.setSysTaLevel(STATUS_NORMAL);
        this.save(manage);

        return ResponseData.success();
    }

    @DataSource(name ="product")
    @Override
    public ResponseData edit(RdPlManageParam param) throws IllegalAccessException {

        LoginUser loginUser = LoginContext.me().getLoginUser();

        RdPlManage infoNew = BeanUtil.copyProperties(param,RdPlManage.class);
        RdPlManage infoOld = this.getById(param.getId());

        QueryWrapper<RdPlManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_PL_NAME",param.getSysPlName()).ne("ID",param.getId());

        if(this.list(queryWrapper).size()>0){
            return ResponseData.error("产品线已存在！");
        }

        List<AttributeModel> difList = logClient.getDifAttributeModel(infoOld,infoNew);
        Map codeName = this.getCodeName();
        for (AttributeModel attributeModel:difList) {
            attributeModel.setAttributeName(codeName.get(attributeModel.getAttributeAlias()).toString());
        }
        RdManageUpRecord record = new RdManageUpRecord();
        record.setSysType("PL_RECORD");
        record.setSysLDate(new Date());
        record.setSysModifyRecordNum(infoOld.getSysPlCode());
        record.setSysModifyContent(JSON.toJSONString(difList));
        record.setSysPerCode(loginUser.getAccount());
        record.setSysPerName(loginUser.getName());
        manageUpRecordService.save(record);

        UpdateWrapper<RdPlManage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",param.getId())
                .set(StringUtils.isNotEmpty(param.getSysPmPerCode()),"SYS_PM_PER_CODE",param.getSysPmPerCode())
                .set(StringUtils.isNotEmpty(param.getSysPmPerName()),"SYS_PM_PER_NAME",param.getSysPmPerName())
                .set(StringUtils.isNotEmpty(param.getSysDeptExamPerCode()),"SYS_DEPT_EXAM_PER_CODE",param.getSysDeptExamPerCode())
                .set(StringUtils.isNotEmpty(param.getSysDeptExamPerName()),"SYS_DEPT_EXAM_PER_NAME",param.getSysDeptExamPerName())
                .set(StringUtils.isNotEmpty(param.getSysApprPerCode()),"SYS_APPR_PER_CODE",param.getSysApprPerCode())
                .set(StringUtils.isNotEmpty(param.getSysApprPerName()),"SYS_APPR_PER_NAME",param.getSysApprPerName())
                .set(StringUtils.isNotEmpty(param.getSysPlName()),"SYS_PL_NAME",param.getSysPlName())
                .set("SYS_L_DATE",new Date());
        this.update(updateWrapper);

        return ResponseData.success();
    }

    @DataSource(name ="product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enable(RdPlManageParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        List<AttributeModel> difList = new ArrayList<>();
        AttributeModel attributeModel = new AttributeModel();
        attributeModel.setAttributeName("状态");
        attributeModel.setAttributeAlias("sysTaLevel");
        attributeModel.setOldValue(STATUS_CLOSE);
        attributeModel.setNewValue(STATUS_NORMAL);
        difList.add(attributeModel);
        RdManageUpRecord record = new RdManageUpRecord();
        record.setSysType("PL_RECORD");
        record.setSysLDate(new Date());
        record.setSysModifyRecordNum(param.getSysPlCode());
        record.setSysModifyContent(JSON.toJSONString(difList));
        record.setSysPerCode(loginUser.getAccount());
        record.setSysPerName(loginUser.getName());
        manageUpRecordService.save(record);

        UpdateWrapper<RdPlManage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",param.getId()).set("SYS_TA_LEVEL",STATUS_NORMAL)
                .set("SYS_L_DATE",new Date());
        this.update(updateWrapper);

        //开启SPU管理
        pssManageService.changeStatus(param.getSysPlCode(),STATUS_NORMAL);
    }

    @DataSource(name ="product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disable(RdPlManageParam param) {

        LoginUser loginUser = LoginContext.me().getLoginUser();
        List<AttributeModel> difList = new ArrayList<>();
        AttributeModel attributeModel = new AttributeModel();
        attributeModel.setAttributeName("状态");
        attributeModel.setAttributeAlias("sysTaLevel");
        attributeModel.setOldValue(STATUS_NORMAL);
        attributeModel.setNewValue(STATUS_CLOSE);
        difList.add(attributeModel);
        RdManageUpRecord record = new RdManageUpRecord();
        record.setSysType("PL_RECORD");
        record.setSysLDate(new Date());
        record.setSysModifyRecordNum(param.getSysPlCode());
        record.setSysModifyContent(JSON.toJSONString(difList));
        record.setSysPerCode(loginUser.getAccount());
        record.setSysPerName(loginUser.getName());
        manageUpRecordService.save(record);

        UpdateWrapper<RdPlManage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",param.getId()).set("SYS_TA_LEVEL",STATUS_CLOSE)
                .set("SYS_L_DATE",new Date()).set("SYS_PL_CLOSE_DATE",new Date());
        this.update(updateWrapper);

        //关闭SPU管理
        pssManageService.changeStatus(param.getSysPlCode(),STATUS_CLOSE);
    }

    @DataSource(name ="product")
    @Override
    public void enableBatch(List<RdPlManageParam> params) {
        for (RdPlManageParam param:params
             ) {
            this.enable(param);
        }
    }

    @DataSource(name ="product")
    @Override
    public void disableBatch(List<RdPlManageParam> params) {
        for (RdPlManageParam param:params
        ) {
            this.disable(param);
        }
    }

    @DataSource(name ="product")
    @Override
    public void editPersonBatch(List<RdPlManageParam> params) throws IllegalAccessException {
        for (RdPlManageParam param:params
        ) {
            LoginUser loginUser = LoginContext.me().getLoginUser();

            RdPlManage infoNew = BeanUtil.copyProperties(param,RdPlManage.class);
            RdPlManage infoOld = this.getById(param.getId());

            List<AttributeModel> difList = logClient.getDifAttributeModel(infoOld,infoNew);
            Map codeName = this.getCodeName();
            for (AttributeModel attributeModel:difList) {
                attributeModel.setAttributeName(codeName.get(attributeModel.getAttributeAlias()).toString());
            }
            RdManageUpRecord record = new RdManageUpRecord();
            record.setSysType("PL_RECORD");
            record.setSysLDate(new Date());
            record.setSysModifyRecordNum(infoOld.getSysPlCode());
            record.setSysModifyContent(JSON.toJSONString(difList));
            record.setSysPerCode(loginUser.getAccount());
            record.setSysPerName(loginUser.getName());
            manageUpRecordService.save(record);

            UpdateWrapper<RdPlManage> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",param.getId())
                    .set(StringUtils.isNotEmpty(param.getSysPmPerCode()),"SYS_PM_PER_CODE",param.getSysPmPerCode())
                    .set(StringUtils.isNotEmpty(param.getSysPmPerName()),"SYS_PM_PER_NAME",param.getSysPmPerName())
                    .set(StringUtils.isNotEmpty(param.getSysDeptExamPerCode()),"SYS_DEPT_EXAM_PER_CODE",param.getSysDeptExamPerCode())
                    .set(StringUtils.isNotEmpty(param.getSysDeptExamPerName()),"SYS_DEPT_EXAM_PER_NAME",param.getSysDeptExamPerName())
                    .set(StringUtils.isNotEmpty(param.getSysApprPerCode()),"SYS_APPR_PER_CODE",param.getSysApprPerCode())
                    .set(StringUtils.isNotEmpty(param.getSysApprPerName()),"SYS_APPR_PER_NAME",param.getSysApprPerName())
                    .set(StringUtils.isNotEmpty(param.getSysPlName()),"SYS_PL_NAME",param.getSysPlName())
                    .set("SYS_L_DATE",new Date());
            this.update(updateWrapper);

        }
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
        Class<RdPlManage> invoiceImgExcelClass = RdPlManage.class;
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
