package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.ReportUploadRecord;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.ReportUploadRecordMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ReportUploadRecordParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ReportUploadRecordResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IReportUploadRecordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.io.Serializable;
import java.util.Date;

/**
* <p>
* AZ报告上传记录 服务实现类
* </p>
*
*
* @author gal
* @since 2021-10-25
*/
@Service
public class ReportUploadRecordServiceImpl extends ServiceImpl<ReportUploadRecordMapper, ReportUploadRecord> implements IReportUploadRecordService {

    @DataSource(name = "finance")
    @Override
    public PageResult<ReportUploadRecordResult> findPageBySpec(ReportUploadRecordParam param) {
        Page pageContext = getPageContext();

        IPage<ReportUploadRecordResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    
    @DataSource(name = "finance")
    @Override
    public void insertSettlementRecord(ReportUploadRecordParam param){

        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        ReportUploadRecord entity = getEntity(param);

        entity.setParseStatus("待解析");
        entity.setCreateBy(currentUser.getName());
        entity.setCreateAt(new Date());
        entity.setPlatformName("Amazon");
        entity.setReportType("Settlement");
        entity.setUploadType("手动上传");
        this.save(entity);
    };

    @DataSource(name = "finance")
    @Override
    public void insertDataRangeRecord(ReportUploadRecordParam param){

        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        ReportUploadRecord entity = getEntity(param);

        entity.setParseStatus("待解析");
        entity.setCreateBy(currentUser.getName());
        entity.setCreateAt(new Date());
        entity.setPlatformName("Amazon");
        entity.setReportType("Data Range");
        entity.setUploadType("手动上传");
        this.save(entity);
    };

    @DataSource(name = "finance")
    @Override
    public void add(ReportUploadRecordParam param) {
        ReportUploadRecord entity = getEntity(param);
        this.save(entity);
    }

    private ReportUploadRecord getEntity(ReportUploadRecordParam param) {
        ReportUploadRecord entity = new ReportUploadRecord();
        BeanUtil.copyProperties(param, entity);
        return entity;
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }


    private ReportUploadRecord getOldEntity(ReportUploadRecordParam param) {
        return this.getById(getKey(param));
    }

    private Serializable getKey(ReportUploadRecordParam param) {
        return param.getId();
    }

}