package com.tadpole.cloud.product.modular.productinterpolate.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.product.api.productinterpolate.entity.RdFoReminder;
import com.tadpole.cloud.product.api.productinterpolate.model.params.RdFoReminderParam;
import com.tadpole.cloud.product.api.productproposal.entity.RdProposal;
import com.tadpole.cloud.product.api.productproposal.model.params.RdProposalParam;
import com.tadpole.cloud.product.modular.productinterpolate.mapper.RdFoReminderMapper;
import com.tadpole.cloud.product.modular.productinterpolate.service.IRdFoReminderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;

import com.tadpole.cloud.product.modular.productplan.enums.RdProposalEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 产品线管理 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-19
 */
@Service
public class RdFoReminderServiceImpl extends ServiceImpl<RdFoReminderMapper, RdFoReminder> implements IRdFoReminderService {

    @Resource
    private RdFoReminderMapper mapper;

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData add(RdFoReminderParam param){

        RdFoReminder rdFoReminder = BeanUtil.copyProperties(param,RdFoReminder.class);

        this.saveOrUpdate(rdFoReminder);

        return ResponseData.success();
    }
}
