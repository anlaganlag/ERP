package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.entity.SysDict;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.LogisticsDay;
import com.tadpole.cloud.operationManagement.modular.stock.enums.StockExceptionEnum;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.LogisticsDayMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.LogisticsDayParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.LogisticsDayResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.ILogisticsDayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 企业信息 服务实现类
 *
 * @author gal
 * @since 2019-10-10
 */
@Service
public class LogisticsDayServiceImpl extends ServiceImpl<LogisticsDayMapper, LogisticsDay>
        implements ILogisticsDayService {


    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public PageResult<LogisticsDayResult> findPageBySpec(LogisticsDayParam param) {
        if (ObjectUtil.isNull(param.getPageSize())|| param.getPageSize()<0) {
            param.setPageSize(Long.MAX_VALUE);
        }
        if (ObjectUtil.isNull(param.getPageNo())|| param.getPageNo()<0) {
            param.setPageNo(1L);
        }
        Page pageContext = param.getPageContext();
        List<String> areaList = param.getAreaList();
        if (ObjectUtil.isNotEmpty(areaList)) {
            //areaList过滤掉空字符串
            areaList.removeIf(area -> area.equals(""));
            param.setAreaList(areaList);
        }


        IPage<LogisticsDayResult> page = this.baseMapper.customPageList(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "stocking")
    @Override

    public List<LogisticsDayResult> exportExcel(LogisticsDayParam param) {
        List<LogisticsDayResult> page = this.baseMapper.exportExcel(param);
        return page;

    }

    @DataSource(name = "stocking")
    @Override
    public List<LogisticsDayResult> readySet(List<String> areaList , List<SysDict> dictList, LogisticsDayParam param){
        List<String> setResult = this.baseMapper.repeatAreaLog(param);
        HashSet<String> hashSet = new HashSet<>(setResult);

        List<LogisticsDayResult> resList = new ArrayList<>();
        for (String area : areaList) {
            String curArea = area;
            for (SysDict dict : dictList) {
               String curLog =  dict.getDictName();
                if (!hashSet.contains(curArea+curLog)) {
                    LogisticsDayResult res =  new LogisticsDayResult();
                    res.setArea(curArea);
                    res.setLogisticsMode(curLog);
                    res.setLogisticsDays("0");
                    resList.add(res);
                }

            }

        }
        return resList;
    }





    @DataSource(name = "stocking")
    @Override
    public LogisticsDay detail(String id) {
        return this.getById(id);
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(LogisticsDayParam param) throws ParseException {

        Page pageContext = param.getPageContext();
        IPage<LogisticsDayResult> page = this.baseMapper.customPageList(pageContext, param);

        if (page.getTotal() > 0) {
            // 返回提示已存在
        } else {

            LogisticsDay entity = getEntity(param);
            entity.setId(IdWorker.getIdStr());

            this.save(entity);
        }
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData addBatch(List<LogisticsDayParam> param) throws ParseException {
        List<LogisticsDay> logList = new ArrayList<>();
        for (LogisticsDayParam logisticsDayParam : param) {
            LogisticsDay log = BeanUtil.copyProperties(logisticsDayParam,LogisticsDay.class);
            logList.add(log);
        }
            this.saveBatch(logList);
        return ResponseData.success();
    }



    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBatch(List<LogisticsDay> LogisticsDayList) {
        this.saveBatch(LogisticsDayList);
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(LogisticsDayParam param) {
        LogisticsDay oldEntity = getOldEntity(param);
        LogisticsDay newEntity = getEntity(param);
        BeanUtil.copyProperties(newEntity, oldEntity);
        this.updateById(oldEntity);
    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(LogisticsDayParam param) {
        this.removeById(getKey(param));
    }


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData deleteBatch(List<String> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            try {
                this.baseMapper.deleteBatchIds(list);
            } catch (Exception e) {
                return ResponseData.error(StockExceptionEnum.BATCH_DELETE_FAIL.getCode(), StockExceptionEnum.BATCH_DELETE_FAIL.getMessage());
            }
        }
        return ResponseData.success();
    }


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdateBatch(List<LogisticsDay> dataList) {
        return this.saveOrUpdateBatch(dataList, 1000);
    }


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public void updateBatch(HashSet<LogisticsDay> dataList) {
        this.baseMapper.updateBatch(dataList);
    }

    private LogisticsDay getOldEntity(LogisticsDayParam param) {
        return this.getById(getKey(param));
    }

    private Serializable getKey(LogisticsDayParam param) {
        return param.getId();
    }

    private LogisticsDay getEntity(LogisticsDayParam param) {
        LogisticsDay entity = new LogisticsDay();
        BeanUtil.copyProperties(param, entity);
        return entity;
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
}
