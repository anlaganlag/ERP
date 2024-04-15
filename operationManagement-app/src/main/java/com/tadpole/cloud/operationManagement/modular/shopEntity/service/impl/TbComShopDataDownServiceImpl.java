package com.tadpole.cloud.operationManagement.modular.shopEntity.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.entity.JcSite;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.api.ebms.entity.TbDwSourceData;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.EbmsShopDataDownTaskParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopDataDown;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopDataDownParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopDataDownResult;
import com.tadpole.cloud.operationManagement.modular.config.ShopDataDwTaskEnum;
import com.tadpole.cloud.operationManagement.modular.shopEntity.consumer.ComShopApiConsumer;
import com.tadpole.cloud.operationManagement.modular.shopEntity.consumer.JcSiteApiConsumer;
import com.tadpole.cloud.operationManagement.modular.shopEntity.mapper.TbComShopDataDownMapper;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopApplyService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopDataDownService;
import com.tadpole.cloud.operationManagement.modular.shopEntity.service.TbComShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 资源-店铺数据下载管理;(Tb_Com_Shop_Data_Down)--服务实现类
 *
 * @author : LSY
 * @create : 2023-8-11
 */
@Slf4j
@Service
@Transactional
public class TbComShopDataDownServiceImpl extends ServiceImpl<TbComShopDataDownMapper, TbComShopDataDown> implements TbComShopDataDownService {
    @Resource
    private TbComShopDataDownMapper tbComShopDataDownMapper;

    @Resource
    private TbComShopApplyService tbComShopApplyService;
    @Resource
    private ComShopApiConsumer comShopApiConsumer;

    @Resource
    private JcSiteApiConsumer jcSiteApiConsumer;

    @Resource
    private TbComShopService tbComShopService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopDataDown queryById(BigDecimal id) {
        return tbComShopDataDownMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param queryParam 筛选条件
     * @param current    当前页码
     * @param size       每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "stocking")
    @Override
    public Page<TbComShopDataDownResult> paginQuery(TbComShopDataDownParam queryParam, long current, long size) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbComShopDataDown> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getElePlatformName()), TbComShopDataDown::getElePlatformName, queryParam.getElePlatformName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getShopName()), TbComShopDataDown::getShopName, queryParam.getShopName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getSellerId()), TbComShopDataDown::getSellerId, queryParam.getSellerId());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getShopNameSimple()), TbComShopDataDown::getShopNameSimple, queryParam.getShopNameSimple());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getArea()), TbComShopDataDown::getArea, queryParam.getArea());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getCountryCode()), TbComShopDataDown::getCountryCode, queryParam.getCountryCode());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataObjNum()), TbComShopDataDown::getDwDataObjNum, queryParam.getDwDataObjNum());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataObjEnName()), TbComShopDataDown::getDwDataObjEnName, queryParam.getDwDataObjEnName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataObjCnName()), TbComShopDataDown::getDwDataObjCnName, queryParam.getDwDataObjCnName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataTableObj()), TbComShopDataDown::getDwDataTableObj, queryParam.getDwDataTableObj());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getRegion()), TbComShopDataDown::getRegion, queryParam.getRegion());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwMerge()), TbComShopDataDown::getDwMerge, queryParam.getDwMerge());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getTaskState()), TbComShopDataDown::getTaskState, queryParam.getTaskState());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getTaskFrequency()), TbComShopDataDown::getTaskFrequency, queryParam.getTaskFrequency());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getRemark()), TbComShopDataDown::getRemark, queryParam.getRemark());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getCreateBy()), TbComShopDataDown::getCreateBy, queryParam.getCreateBy());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getUpdateBy()), TbComShopDataDown::getUpdateBy, queryParam.getUpdateBy());

        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getId()), TbComShopDataDown::getId, queryParam.getId());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getAuthStatus()), TbComShopDataDown::getAuthStatus, queryParam.getAuthStatus());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getCreateTask()), TbComShopDataDown::getCreateTask, queryParam.getCreateTask());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getCreateTaskTime()), TbComShopDataDown::getCreateTaskTime, queryParam.getCreateTaskTime());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getTaskId()), TbComShopDataDown::getTaskId, queryParam.getTaskId());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getSyncTime()), TbComShopDataDown::getSyncTime, queryParam.getSyncTime());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getCreateTime()), TbComShopDataDown::getCreateTime, queryParam.getCreateTime());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getUpdateTime()), TbComShopDataDown::getUpdateTime, queryParam.getUpdateTime());
        //2. 执行分页查询
        Page<TbComShopDataDownResult> pagin = new Page<>(current, size, true);
        IPage<TbComShopDataDownResult> selectResult = tbComShopDataDownMapper.selectByPage(pagin, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param tbComShopDataDown 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopDataDown insert(TbComShopDataDown tbComShopDataDown) {
        tbComShopDataDownMapper.insert(tbComShopDataDown);
        return tbComShopDataDown;
    }

    /**
     * 更新数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "stocking")
    @Override
    public TbComShopDataDown update(TbComShopDataDown entityParam) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbComShopDataDown> chainWrapper = new LambdaUpdateChainWrapper<>(tbComShopDataDownMapper);
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getElePlatformName()), TbComShopDataDown::getElePlatformName, entityParam.getElePlatformName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopName()), TbComShopDataDown::getShopName, entityParam.getShopName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getSellerId()), TbComShopDataDown::getSellerId, entityParam.getSellerId());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getShopNameSimple()), TbComShopDataDown::getShopNameSimple, entityParam.getShopNameSimple());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getArea()), TbComShopDataDown::getArea, entityParam.getArea());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getCountryCode()), TbComShopDataDown::getCountryCode, entityParam.getCountryCode());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDataObjNum()), TbComShopDataDown::getDwDataObjNum, entityParam.getDwDataObjNum());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDataObjEnName()), TbComShopDataDown::getDwDataObjEnName, entityParam.getDwDataObjEnName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDataObjCnName()), TbComShopDataDown::getDwDataObjCnName, entityParam.getDwDataObjCnName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDataTableObj()), TbComShopDataDown::getDwDataTableObj, entityParam.getDwDataTableObj());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getRegion()), TbComShopDataDown::getRegion, entityParam.getRegion());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwMerge()), TbComShopDataDown::getDwMerge, entityParam.getDwMerge());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaskState()), TbComShopDataDown::getTaskState, entityParam.getTaskState());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getTaskFrequency()), TbComShopDataDown::getTaskFrequency, entityParam.getTaskFrequency());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getRemark()), TbComShopDataDown::getRemark, entityParam.getRemark());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getCreateBy()), TbComShopDataDown::getCreateBy, entityParam.getCreateBy());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getUpdateBy()), TbComShopDataDown::getUpdateBy, entityParam.getUpdateBy());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getAuthStatus()), TbComShopDataDown::getAuthStatus, entityParam.getAuthStatus());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getCreateTask()), TbComShopDataDown::getCreateTask, entityParam.getCreateTask());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getCreateTaskTime()), TbComShopDataDown::getCreateTaskTime, entityParam.getCreateTaskTime());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getTaskId()), TbComShopDataDown::getTaskId, entityParam.getTaskId());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getSyncTime()), TbComShopDataDown::getSyncTime, entityParam.getSyncTime());
        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getUpdateTime()), TbComShopDataDown::getUpdateTime, entityParam.getUpdateTime());
        //2. 设置主键，并更新
        chainWrapper.eq(TbComShopDataDown::getId, entityParam.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(entityParam.getId());
        } else {
            return entityParam;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteById(BigDecimal id) {
        int total = tbComShopDataDownMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param idList 主键List
     * @return 是否成功
     */
    @DataSource(name = "stocking")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> idList) {
        int delCount = tbComShopDataDownMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 店铺数据自动下载任务创建
     *
     */
    @Override
    @DataSource(name = "stocking")
    public ResponseData createTask(TbComShop comShop) {
        //根据平台，账号，站点，生成需要创建的任务信息
        TbComShopDataDown dataDown = new TbComShopDataDown();
        BeanUtil.copyProperties(comShop, dataDown);

        List<TbDwSourceData> sourceDataObjList = comShopApiConsumer.getSourceDataObjList();
        if (ObjectUtil.isEmpty(sourceDataObjList)) {
            log.error("店铺【{}】未找到需要配置的店铺下载数据对象信息",comShop.getShopName());
            return ResponseData.error("未找到需要配置的店铺下载数据对象信息");
        }
        //过滤出指定平台的数据下载对象
        sourceDataObjList = sourceDataObjList.stream().filter(d -> d.getDwPlatName().equals(comShop.getElePlatformName())).collect(Collectors.toList());
        if (ObjectUtil.isEmpty(sourceDataObjList)) {
            log.error("店铺【{}】按平台过滤后，未找到需要配置的店铺下载数据对象信息",comShop.getShopName());
            return ResponseData.error("按平台过滤后，未找到需要配置的店铺下载数据对象信息");
        }

        List<JcSite> siteList = jcSiteApiConsumer.getAll();
        if (ObjectUtil.isEmpty(siteList)) {
            log.error("店铺【{}】获取站点信息失败",comShop.getShopName());
            return ResponseData.error("获取站点信息失败");
        }
        Map<String, String> siteAreaMap = siteList.stream().filter(s->ObjectUtil.isNotEmpty(s.getReportArea())).collect(Collectors.toMap(JcSite::getSite, JcSite::getReportArea, (k1, k2) -> k2));
        //去重校验
        List<TbDwSourceData>   filterSourcDataList  = this.checkRepeat(comShop, sourceDataObjList);

        if (ObjectUtil.isEmpty(filterSourcDataList)) {
            //没有需要创建的店铺数据下载任务，直接返回
            log.info("店铺【{}】去重校验后，没有需要创建的店铺数据下载任务,直接返回",comShop.getShopName());
            return ResponseData.success();
        }

        List<TbComShopDataDown> shopDataDownList = new ArrayList<>();
        try {
            for (TbDwSourceData dwSourceData : filterSourcDataList) {

                TbComShopDataDown waitCopy = new TbComShopDataDown();
                BeanUtil.copyProperties(dataDown, waitCopy);
                BeanUtil.copyProperties(dwSourceData, waitCopy);
                waitCopy.setArea(siteAreaMap.get(waitCopy.getCountryCode()));
                waitCopy.setId(BigDecimal.valueOf(IdWorker.getId()));
                waitCopy.setCreateTask(ShopDataDwTaskEnum.NOT_CREATE.getCode());
                if (BigDecimal.ONE.equals(comShop.getShopDataDownTask())) {
                    waitCopy.setCreateTask(ShopDataDwTaskEnum.NEED_CREATED.getCode());
                }

                //判断当前报告对象是否适用当前店铺所处的站点
                if (!this.checkNeedCreateTask(waitCopy, dwSourceData)) {
                    //该报告对象不适用改店铺
                    continue;
                }

                waitCopy = this.requestEbmsCreateTask(waitCopy);
                waitCopy.setCreateBy("System");
                waitCopy.setCreateTime(new Date());
                shopDataDownList.add(waitCopy);
            }
        } catch (Exception e) {
            log.error("店铺【{}】创建店铺数据下载任务出现异常:{}",comShop.getShopName(),JSON.toJSONString(e));
            throw new RuntimeException(e);
        }
        if (shopDataDownList.size() > 0) {
            this.saveBatch(shopDataDownList);
        }

        return ResponseData.success();
    }

    private TbComShopDataDown requestEbmsCreateTask(TbComShopDataDown waitCopy) {
        EbmsShopDataDownTaskParam dataDownParam = BeanUtil.copyProperties(waitCopy, EbmsShopDataDownTaskParam.class);
        //创建店铺是勾选了自动下载数据
        if (! ShopDataDwTaskEnum.NOT_CREATE.getCode().equals(waitCopy.getCreateTask())) {
            waitCopy.setCreateTask(ShopDataDwTaskEnum.NEED_CREATED.getCode());
            //sellerId有值 +  已经授权
            if ( ObjectUtil.isNotEmpty(waitCopy.getSellerId()) && BigDecimal.ONE.equals(waitCopy.getAuthStatus())) {
                waitCopy.setSyncTime(new Date());
                ResponseData responseData = comShopApiConsumer.syncShopDwDataTask(dataDownParam);
                if (responseData.getSuccess() && ObjectUtil.isNotNull(responseData.getData())) {
                    BigDecimal taskId = new BigDecimal(String.valueOf(responseData.getData()));
                    waitCopy.setTaskId(taskId);
                    waitCopy.setCreateTask(ShopDataDwTaskEnum.CREATED.getCode());
                    waitCopy.setTaskState("已配置");
                    waitCopy.setRemark("EBMS已经创建任务成功");
                } else {
                    //调用EBMS 创建下载任务失败
                    waitCopy.setCreateTask(ShopDataDwTaskEnum.CREATE_FAIL.getCode());
                    waitCopy.setRemark("EBMS创建下载任务失败");
                }
            } else {
                waitCopy.setRemark("sellerId必须有值，并且authStatus已授权（1）");
                waitCopy.setCreateTask(ShopDataDwTaskEnum.MISSING_CONDITIONS.getCode());
            }
        } else {
            waitCopy.setRemark("创建店铺时运营确认不需要拉取数据");
            waitCopy.setCreateTask(ShopDataDwTaskEnum.NOT_CREATE.getCode());
        }
        return waitCopy;
    }

    @Override
    @DataSource(name = "stocking")
    public ResponseData checkRestartTask(String shopNameSimple) {
        //创建失败，货缺失条件的，以及后面开启下载数据任务的店铺
        //1，刷新TbcomShopDataDown数据
        tbComShopDataDownMapper.updateShopDataDown();
        //2,再次创建任务
        LambdaQueryWrapper<TbComShopDataDown> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(TbComShopDataDown::getCreateTask, ShopDataDwTaskEnum.NOT_CREATE.getCode());
        wrapper.ne(TbComShopDataDown::getCreateTask, ShopDataDwTaskEnum.CREATED.getCode());
        wrapper.eq(ObjectUtil.isNotEmpty(shopNameSimple),TbComShopDataDown::getShopNameSimple, shopNameSimple);
        List<TbComShopDataDown> dataDownList = tbComShopDataDownMapper.selectList(wrapper);
        if (ObjectUtil.isEmpty(dataDownList)) {
            log.info("未找到需要重新创建店铺数据下载的任务数据");
            return ResponseData.success("未找到需要重新创建店铺数据下载的任务数据");
        }
        for (TbComShopDataDown shopDataDown : dataDownList) {
            try {
                TbComShopDataDown dataDown = requestEbmsCreateTask(shopDataDown);
                log.info("重新创建店铺【{}】数据下载的任务成功",shopDataDown.getShopName());
            } catch (Exception e) {
                log.error("重新创建店铺【{}】数据下载的任务异常:{}",shopDataDown.getShopName(), JSON.toJSONString(e));
            }
        }
        this.updateBatchById(dataDownList);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "stocking")
    public ResponseData shopHistoryDataTask(String shopNameSimple,Integer isAllShop) {
        //创建失败，货缺失条件的，以及后面开启下载数据任务的店铺
        //1，刷新TbcomShopDataDown数据
        List<TbComShop> shopList = new ArrayList<>();
        if (Integer.valueOf(1).equals(isAllShop)) {
            shopList = tbComShopDataDownMapper.getAllShopList();
        } else {
           shopList= tbComShopDataDownMapper.getShopHistoryDataTask();
        }
        if (ObjectUtil.isNotEmpty(shopList) &&  ObjectUtil.isNotEmpty(shopNameSimple)) {
            shopList=  shopList.stream().filter(s -> shopNameSimple.equals(s.getShopNameSimple())).collect(Collectors.toList());
        }
        if (ObjectUtil.isEmpty(shopList)) {
            log.info("未找到历史店铺需要创建下载任务的数据");
            return ResponseData.success();
        }

        log.info("找到历史店铺需要创建下载任务的数据【{}】条",shopList.size());
        for (TbComShop comShop : shopList) {
            try {
                if (ObjectUtil.isNotEmpty(comShop.getElePlatformName()) &&
                        ("Amazon".equals(comShop.getElePlatformName()) || "Walmart".equals(comShop.getElePlatformName()))) {
                    createTask(comShop);
                    log.info("历史店铺【{}】数据下载新增任务成功", comShop.getShopName());
                } else {
                    log.info("历史店铺【{}】不是Amazon，Walmart平台不需要下载店铺数据", comShop.getShopName());
                }

            } catch (Exception e) {
                log.error("历史店铺【{}】数据下载新增任务异常:{}",comShop.getShopName(), JSON.toJSONString(e));
            }
        }
        return ResponseData.success();
    }

    /**
     * 创建数据对象下载任务重复校验
     * @param comShop 店鋪
     * @param sourceDataObjList
     * @return List<TbDwSourceData>
     */
    private List<TbDwSourceData> checkRepeat(TbComShop comShop, List<TbDwSourceData> sourceDataObjList) {

            LambdaQueryWrapper<TbComShopDataDown> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TbComShopDataDown::getElePlatformName, comShop.getElePlatformName())
//                    .eq(TbComShopDataDown::getSellerId, comShop.getSellerId())
                    .eq(TbComShopDataDown::getShopName, comShop.getShopNameSimple())
//                    .eq(TbComShopDataDown::getCountryCode,comShop.getCountryCode())
                    .like(TbComShopDataDown::getCountryCode, comShop.getCountryCode());
            List<TbComShopDataDown> dataDownList = tbComShopDataDownMapper.selectList(wrapper);
            if (ObjectUtil.isNotEmpty(dataDownList)) {
              //有已经创建过的任务存在，排除已创建的
                List<String> objNumList = dataDownList.stream().map(d -> d.getDwDataObjNum()).collect(Collectors.toSet()).stream().collect(Collectors.toList());
                List<TbDwSourceData> filterSourcDataList = sourceDataObjList.stream().filter(o -> ! objNumList.contains(o.getDwDataObjNum())).collect(Collectors.toList());
                return filterSourcDataList;
            }
            return sourceDataObjList;
    }

    /**
     * 判断当前报告对象是否适用当前店铺所处的站点,最后给出是否需要创建任务
     *
     * @param waitCopy
     * @param dwSourceData
     * @return
     */
    private boolean checkNeedCreateTask(TbComShopDataDown waitCopy, TbDwSourceData dwSourceData) {
        if ("ALL".equals(dwSourceData.getRegion())) {
            return Boolean.TRUE;
        }
        boolean result = false;
        String area = waitCopy.getArea();
        switch (area) {
            case "NA":
                result = Boolean.TRUE;
                break;
            case "EU":
                result = Boolean.TRUE;
                break;
            case "FE":
                result = Boolean.TRUE;
                break;
            default:
                result = Boolean.FALSE;
        }
        return result;
    }
}