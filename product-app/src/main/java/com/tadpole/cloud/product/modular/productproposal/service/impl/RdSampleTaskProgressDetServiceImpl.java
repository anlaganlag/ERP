package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleTask;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleTaskProgressDet;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleTaskProgressDetParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleTaskProgressDetResult;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdSampleTaskMapper;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdSampleTaskProgressDetMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSampleTaskProgressDetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 提案-开发样任务进度明细 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2024-02-29
 */
@Service
public class RdSampleTaskProgressDetServiceImpl extends ServiceImpl<RdSampleTaskProgressDetMapper, RdSampleTaskProgressDet> implements IRdSampleTaskProgressDetService {

    @Resource
    private RdSampleTaskProgressDetMapper mapper;

    @Resource
    private RdSampleTaskMapper sampleTaskMapper;

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdSampleTaskProgressDetResult> listTaskProgressDet(RdSampleTaskProgressDetParam param) {

        LoginUser loginUser = LoginContext.me().getLoginUser();

        this.sampleTaskMapper.updateTsRead(loginUser.getAccount(),param.getSysTsTaskCode());

        return this.mapper.listTaskProgressDet(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData add(RdSampleTaskProgressDetParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        Date oprDate = new Date();
        RdSampleTaskProgressDet rdSampleTaskProgressDet = BeanUtil.copyProperties(param,RdSampleTaskProgressDet.class);
        rdSampleTaskProgressDet.setId(UUID.randomUUID().toString().replace("-", ""));
        rdSampleTaskProgressDet.setSysCDate(oprDate);
        rdSampleTaskProgressDet.setSysPerCode(loginUser.getAccount());
        rdSampleTaskProgressDet.setSysPerName(loginUser.getName());

        UpdateWrapper<RdSampleTask> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("SYS_TS_READ",loginUser.getAccount());
        updateWrapper.eq("SYS_TS_TASK_CODE",param.getSysTsTaskCode());

        if (this.mapper.insert(rdSampleTaskProgressDet) > 0 && this.sampleTaskMapper.update(null,updateWrapper)>0){
            return ResponseData.success("拿样任务明细保存成功.");
        }else {
            return ResponseData.error("拿样任务明细保存失败.");
        }
    }
}
