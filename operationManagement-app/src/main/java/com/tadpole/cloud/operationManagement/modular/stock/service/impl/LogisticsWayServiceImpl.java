package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.LogisticsWay;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.LogisticsWayMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.LogisticsWayParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.LogisticsWayResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.ILogisticsWayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 设置物流方式 服务实现类
 * </p>
 *
 * @author lsy
 * @since 2022-09-13
 */
@Service
public class LogisticsWayServiceImpl extends ServiceImpl<LogisticsWayMapper, LogisticsWay> implements ILogisticsWayService {

    @Resource
    private LogisticsWayMapper logisticsWayMapper;

    @Override
    @DataSource(name = "stocking")
    public ResponseData queryListPage(LogisticsWayParam param) {
        Page pageContext = param.getPageContext();
        IPage<LogisticsWayResult> pages = logisticsWayMapper.queryListPage(pageContext, param);
        return ResponseData.success(new PageResult<>(pages));
    }

    @Override
    @DataSource(name = "stocking")
    public List<LogisticsWayResult> exportExcel(LogisticsWayParam param) {
        Page pageContext = param.getPageContext();
        return logisticsWayMapper.queryListPage(pageContext, param).getRecords();
    }

    @DataSource(name = "stocking")
    private LogisticsWay validateIsExists(LogisticsWayParam param) {
        LambdaQueryWrapper<LogisticsWay> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(param.getMaterialCode())) {
            wrapper.eq(LogisticsWay::getMaterialCode, param.getMaterialCode());
        }
        if (ObjectUtil.isNotEmpty(param.getArea())) {
            wrapper.eq(LogisticsWay::getArea, param.getArea());
        }
        if (ObjectUtil.isNotEmpty(param.getWay())) {
            wrapper.eq(LogisticsWay::getWay, param.getWay());
        }
        return logisticsWayMapper.selectList(wrapper).stream().findFirst().orElse(null);
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData add(LogisticsWayParam param, String controllerName) {
        LogisticsWay entity = BeanUtil.copyProperties(param, LogisticsWay.class);
        LogisticsWay model = this.validateIsExists(param);
        entity.setCreateBy(this.getUserName());
        entity.setCreateTime(new Date());
        if (model != null) {
            entity.setId(model.getId());
        }
        if (!this.saveOrUpdate(entity)) {
            return ResponseData.error(controllerName + "_" +"新增失败,请稍后重试。");
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData uploadExcel(List<LogisticsWayParam> paramList, String controllerName) {
        List<LogisticsWay> list = new ArrayList<>();
        for (LogisticsWayParam p : paramList) {
            LogisticsWay entity = BeanUtil.copyProperties(p, LogisticsWay.class);
            LogisticsWay model = this.validateIsExists(p);
            if (model != null) {
                entity.setUpdateBy(this.getUserName());
                entity.setUpdateTime(new Date());
                entity.setId(model.getId());
            } else {
                entity.setCreateBy(this.getUserName());
                entity.setCreateTime(new Date());
            }
            list.add(entity);
        }
        if (!this.saveOrUpdateBatch(list)) {
            return ResponseData.error(controllerName + "_" +"修改失败，请稍后重试。");
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData edit(LogisticsWayParam param, String controllerName) {
        LambdaUpdateWrapper<LogisticsWay> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(LogisticsWay::getUpdateBy, this.getUserName());
        updateWrapper.set(LogisticsWay::getUpdateTime, new Date());
        updateWrapper.set(LogisticsWay::getWay, param.getWay());
        updateWrapper.eq(LogisticsWay::getId, param.getId());

        //updateWrapper.set(LogisticsWay::getMaterialCode, param.getMaterialCode());
        //updateWrapper.set(LogisticsWay::getArea, param.getArea());
        if (!this.update(updateWrapper)) {
            return ResponseData.error(controllerName + "_" +"修改失败，请稍后重试。");
        }
        return ResponseData.success();




        //补丁测试
    }


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData delete(List<Integer> idList, String controllerName) {
        if (logisticsWayMapper.deleteBatchIds(idList) > 0) {
            return ResponseData.success();
        }
        return ResponseData.error(controllerName + "_" +"删除失败，未检测到数据。");
    }


    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private String getUserName() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getName();
    }

}
