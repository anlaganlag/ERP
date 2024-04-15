package com.tadpole.cloud.externalSystem.modular.ebms.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbComMateriel;
import com.tadpole.cloud.externalSystem.modular.ebms.mapper.TbcommaterielMapper;
import com.tadpole.cloud.externalSystem.modular.ebms.model.params.TbcommaterielParam;
import com.tadpole.cloud.externalSystem.modular.ebms.service.ITbcommaterielService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author gal
 * @since 2022-10-25
 */
@Service
public class TbcommaterielServiceImpl extends ServiceImpl<TbcommaterielMapper, TbComMateriel> implements ITbcommaterielService {

    @Resource
    private TbcommaterielMapper mapper;


    @DataSource(name = "EBMS")
    @Override
    public List<TbComMateriel> getWaitMatList(List<String> matList) {
        LambdaQueryWrapper<TbComMateriel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TbComMateriel::getMatCode, matList);
        List<TbComMateriel> list = mapper.selectList(queryWrapper);
        return list;
    }


    @DataSource(name = "EBMS")
    @Override
    public List<TbComMateriel> getAllMatList() {
        LambdaQueryWrapper<TbComMateriel> queryWrapper = new LambdaQueryWrapper<>();
        List<TbComMateriel> list = mapper.selectList(queryWrapper);
        return list;
    }

    @DataSource(name = "EBMS")
    @Override
    public List<TbComMateriel> getWaitMatList(Date createTimeStart, Date createTimeEnd, List<String> matList) {


        LambdaQueryWrapper<TbComMateriel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(ObjectUtil.isNotNull(createTimeStart), TbComMateriel::getMatLastUpdateDate, createTimeStart);
        queryWrapper.le(ObjectUtil.isNotNull(createTimeEnd), TbComMateriel::getMatLastUpdateDate, DateUtil.endOfDay(createTimeEnd));
        queryWrapper.in(ObjectUtil.isNotEmpty(matList), TbComMateriel::getMatCode, matList);
        List<TbComMateriel> list = mapper.selectList(queryWrapper);
        return list;
    }

    @DataSource(name = "EBMS")
    @Override
    public List<TbComMateriel> getWaitAddMatList(Date createTimeStart, Date createTimeEnd, List<String> matList) {

        LambdaQueryWrapper<TbComMateriel> queryWrapper = new LambdaQueryWrapper<>();

        if (ObjectUtil.isNotNull(createTimeStart)) {
            queryWrapper.ge(TbComMateriel::getMatCreatDate, createTimeStart);
        }else {
            queryWrapper.ge(TbComMateriel::getMatCreatDate, DateUtil.parse("2022-12-01"));//2022-9-30之后使用api调用新增
        }

        if (ObjectUtil.isNotNull(createTimeEnd)) {
            queryWrapper.le(TbComMateriel::getMatCreatDate, DateUtil.endOfDay(createTimeEnd));
        }
        if (ObjectUtil.isNotEmpty(matList)) {
            queryWrapper.in(TbComMateriel::getMatCode, matList);
        }

        List<TbComMateriel> list = mapper.selectList(queryWrapper);
        return list;
    }

    @DataSource(name = "EBMS")
    @Override
    public List<TbComMateriel> getWaitUpdateMatList(Date updateTimeStart, Date updateTimeEnd, List<String> matList) {
        if (ObjectUtil.isNotNull(updateTimeEnd)) {
            updateTimeEnd = DateUtil.endOfDay(updateTimeEnd);
        }
        return mapper.getWaitUpdateMatList(updateTimeStart, updateTimeEnd, matList);
    }

    @DataSource(name = "EBMS")
    @Override
    public PageResult<TbComMateriel> listBySpec(TbcommaterielParam param) {
        Page pageContext = param.getPageContext();
        try {

            IPage<TbComMateriel> page = mapper.listBySpec(pageContext, param);
            return new PageResult<>(page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return new PageResult<>(pageContext);
        }
    }

    @DataSource(name = "EBMS")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData disableProdMateriel(List<String> matCodes) {
        LambdaUpdateChainWrapper<TbComMateriel> lambdaUpdateChainWrapper = new LambdaUpdateChainWrapper<>(mapper);
        lambdaUpdateChainWrapper.set(TbComMateriel::getMatStatus, "true");
        lambdaUpdateChainWrapper.set(TbComMateriel::getMatDisableTime, DateUtil.date());
        lambdaUpdateChainWrapper.in(TbComMateriel::getMatCode, matCodes);
        if (lambdaUpdateChainWrapper.update()) {
            return ResponseData.success();
        } else {
            return ResponseData.error("EBMS系统物料禁用状态操作失败");
        }
    }

}
