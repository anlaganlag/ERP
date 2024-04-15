package com.tadpole.cloud.product.modular.product.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.product.modular.product.entity.RdPssManageVersion;
import com.tadpole.cloud.product.modular.product.mapper.RdPssManageVersionMapper;
import com.tadpole.cloud.product.modular.product.service.IRdPssManageVersionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 产品同款版本 服务实现类
 * </p>
 *
 * @author S20210221
 * @since 2024-04-03
 */
@Service
public class RdPssManageVersionServiceImpl extends ServiceImpl<RdPssManageVersionMapper, RdPssManageVersion> implements IRdPssManageVersionService {

    @Resource
    private RdPssManageVersionMapper mapper;


    @DataSource(name ="product")
    @Override
    public List<RdPssManageVersion> getBySysSpu(String sysSpu) {
        LambdaQueryWrapper<RdPssManageVersion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RdPssManageVersion::getSysSpu, sysSpu);
        queryWrapper.orderByDesc(RdPssManageVersion::getId);
       return mapper.selectList(queryWrapper);

    }

    @DataSource(name ="product")
    @Override
    public ResponseData assertVersion(String sysSpu) {
        LambdaQueryWrapper<RdPssManageVersion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RdPssManageVersion::getSysSpu, sysSpu);
        queryWrapper.eq(RdPssManageVersion::getSysVersionStatus, "进行中");
        List<RdPssManageVersion> rdPssManageVersions = mapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(rdPssManageVersions)) {
            return ResponseData.success("版本落地成功");
        }


        for (RdPssManageVersion version : rdPssManageVersions) {
            version.setSysVersionStatus("已落地");
            version.setSysCurAppVersion(version.getSysCurIteVersion());
            version.setSysCurIteVersion("");
            mapper.updateById(version);
        }
            return ResponseData.success("版本落地成功");
    }
}
