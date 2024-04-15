package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsClearanceDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsClearanceDetailParam;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgCustomsClearanceDetailMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomsClearanceDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 清关单明细 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
@Slf4j
@Service
public class TgCustomsClearanceDetailServiceImpl extends ServiceImpl<TgCustomsClearanceDetailMapper, TgCustomsClearanceDetail> implements ITgCustomsClearanceDetailService {

    @Resource
    private TgCustomsClearanceDetailMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(TgCustomsClearanceDetailParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryList(TgCustomsClearanceDetailParam param) {
        if(param.getPid() == null){
            return ResponseData.error("主表ID参数不能为空！");
        }
        return ResponseData.success(mapper.queryList(param));
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(propagation = Propagation.REQUIRES_NEW )
    public TgCustomsClearanceDetail getOne(Wrapper<TgCustomsClearanceDetail> queryWrapper) {
        return this.getOne(queryWrapper, true);
    }
}
