package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsApplyDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsApplyDetailParam;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgCustomsApplyDetailMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomsApplyDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 报关单明细 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
@Slf4j
@Service
public class TgCustomsApplyDetailServiceImpl extends ServiceImpl<TgCustomsApplyDetailMapper, TgCustomsApplyDetail> implements ITgCustomsApplyDetailService {

    @Resource
    private TgCustomsApplyDetailMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(TgCustomsApplyDetailParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryList(TgCustomsApplyDetailParam param) {
        return ResponseData.success(mapper.queryList(param));
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(propagation = Propagation.REQUIRES_NEW )
    public TgCustomsApplyDetail getOne(Wrapper<TgCustomsApplyDetail> queryWrapper) {
        return this.getOne(queryWrapper, true);
    }

    @Override
    @DataSource(name = "logistics")
    public List<TgCustomsApplyDetail> getIncompleteDetail(TgCustomsApplyDetail param) {
        return mapper.getIncompleteDetail(param);
    }
}
