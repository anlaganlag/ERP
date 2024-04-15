package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgBaseProductDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgBaseProductDetailParam;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgBaseProductDetailMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgBaseProductDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 通关产品详细信息 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Service
public class TgBaseProductDetailServiceImpl extends ServiceImpl<TgBaseProductDetailMapper, TgBaseProductDetail> implements ITgBaseProductDetailService {

    @Resource
    private TgBaseProductDetailMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(TgBaseProductDetailParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData delete(TgBaseProductDetailParam param) {
        return this.removeById(param.getId()) ? ResponseData.success() : ResponseData.error("删除失败！");
    }
}
