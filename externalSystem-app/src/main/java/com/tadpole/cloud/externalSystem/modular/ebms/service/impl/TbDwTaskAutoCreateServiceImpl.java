package com.tadpole.cloud.externalSystem.modular.ebms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.EbmsShopDataDownTaskParam;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbDwTaskAutoCreate;
import com.tadpole.cloud.externalSystem.modular.ebms.mapper.TbDwTaskAutoCreateMapper;
import com.tadpole.cloud.externalSystem.modular.ebms.model.param.TbDwTaskAutoCreateParam;
import com.tadpole.cloud.externalSystem.modular.ebms.model.result.MarkIdEndPointResult;
import com.tadpole.cloud.externalSystem.modular.ebms.model.result.TbDwTaskAutoCreateResult;
import com.tadpole.cloud.externalSystem.modular.ebms.service.TbDwTaskAutoCreateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * TbDwTaskAutoCreate;(Tb_DW_Task_Auto_Create)--服务实现类
 *
 * @author : LSY
 * @create : 2023-8-14
 */
@Slf4j
@Service
@Transactional
public class TbDwTaskAutoCreateServiceImpl extends ServiceImpl<TbDwTaskAutoCreateMapper, TbDwTaskAutoCreate> implements TbDwTaskAutoCreateService {
    @Resource
    private TbDwTaskAutoCreateMapper tbDwTaskAutoCreateMapper;


    /**
     * 通过ID查询单条数据
     *
     * @param sysDwId 主键
     * @return 实例对象
     */
    @DataSource(name = "EBMS")
    @Override
    public TbDwTaskAutoCreate queryById(BigDecimal sysDwId) {
        return tbDwTaskAutoCreateMapper.selectById(sysDwId);
    }

    /**
     * 分页查询
     *
     * @param queryParam 筛选条件
     * @param current    当前页码
     * @param size       每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "EBMS")
    @Override
    public Page<TbDwTaskAutoCreateResult> paginQuery(TbDwTaskAutoCreateParam queryParam, long current, long size) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<TbDwTaskAutoCreate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataObjNum()), TbDwTaskAutoCreate::getDwDataObjNum, queryParam.getDwDataObjNum());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwTaskName()), TbDwTaskAutoCreate::getDwTaskName, queryParam.getDwTaskName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDataObjEnName()), TbDwTaskAutoCreate::getDwDataObjEnName, queryParam.getDwDataObjEnName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwSellerId()), TbDwTaskAutoCreate::getDwSellerId, queryParam.getDwSellerId());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwShopNameSimple()), TbDwTaskAutoCreate::getDwShopNameSimple, queryParam.getDwShopNameSimple());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwCountryCode()), TbDwTaskAutoCreate::getDwCountryCode, queryParam.getDwCountryCode());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDlPerson()), TbDwTaskAutoCreate::getDwDlPerson, queryParam.getDwDlPerson());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwDlPersonName()), TbDwTaskAutoCreate::getDwDlPersonName, queryParam.getDwDlPersonName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwUseDepart()), TbDwTaskAutoCreate::getDwUseDepart, queryParam.getDwUseDepart());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwAwsKey()), TbDwTaskAutoCreate::getDwAwsKey, queryParam.getDwAwsKey());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwEndPoint()), TbDwTaskAutoCreate::getDwEndPoint, queryParam.getDwEndPoint());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwMarkIdList()), TbDwTaskAutoCreate::getDwMarkIdList, queryParam.getDwMarkIdList());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getDwPlatName()), TbDwTaskAutoCreate::getDwPlatName, queryParam.getDwPlatName());

        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getSysDwId()), TbDwTaskAutoCreate::getSysDwId, queryParam.getSysDwId());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getDwIsAutoDownload()), TbDwTaskAutoCreate::getDwIsAutoDownload, queryParam.getDwIsAutoDownload());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getCreateTime()), TbDwTaskAutoCreate::getCreateTime, queryParam.getCreateTime());
        //2. 执行分页查询
        Page<TbDwTaskAutoCreateResult> pagin = new Page<>(current, size, true);
        IPage<TbDwTaskAutoCreateResult> selectResult = tbDwTaskAutoCreateMapper.selectByPage(pagin, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param tbDwTaskAutoCreate 实例对象
     * @return 实例对象
     */
    @DataSource(name = "EBMS")
    @Override
    public TbDwTaskAutoCreate insert(TbDwTaskAutoCreate tbDwTaskAutoCreate) {
        tbDwTaskAutoCreateMapper.insert(tbDwTaskAutoCreate);
        return tbDwTaskAutoCreate;
    }

    /**
     * 更新数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "EBMS")
    @Override
    public TbDwTaskAutoCreate update(TbDwTaskAutoCreate entityParam) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TbDwTaskAutoCreate> chainWrapper = new LambdaUpdateChainWrapper<>(tbDwTaskAutoCreateMapper);
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDataObjNum()), TbDwTaskAutoCreate::getDwDataObjNum, entityParam.getDwDataObjNum());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwTaskName()), TbDwTaskAutoCreate::getDwTaskName, entityParam.getDwTaskName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDataObjEnName()), TbDwTaskAutoCreate::getDwDataObjEnName, entityParam.getDwDataObjEnName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwSellerId()), TbDwTaskAutoCreate::getDwSellerId, entityParam.getDwSellerId());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwShopNameSimple()), TbDwTaskAutoCreate::getDwShopNameSimple, entityParam.getDwShopNameSimple());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwCountryCode()), TbDwTaskAutoCreate::getDwCountryCode, entityParam.getDwCountryCode());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDlPerson()), TbDwTaskAutoCreate::getDwDlPerson, entityParam.getDwDlPerson());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwDlPersonName()), TbDwTaskAutoCreate::getDwDlPersonName, entityParam.getDwDlPersonName());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwUseDepart()), TbDwTaskAutoCreate::getDwUseDepart, entityParam.getDwUseDepart());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwAwsKey()), TbDwTaskAutoCreate::getDwAwsKey, entityParam.getDwAwsKey());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwEndPoint()), TbDwTaskAutoCreate::getDwEndPoint, entityParam.getDwEndPoint());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwMarkIdList()), TbDwTaskAutoCreate::getDwMarkIdList, entityParam.getDwMarkIdList());
        chainWrapper.set(ObjectUtil.isNotEmpty(entityParam.getDwPlatName()), TbDwTaskAutoCreate::getDwPlatName, entityParam.getDwPlatName());

        chainWrapper.set(ObjectUtil.isNotNull(entityParam.getDwIsAutoDownload()), TbDwTaskAutoCreate::getDwIsAutoDownload, entityParam.getDwIsAutoDownload());
        //2. 设置主键，并更新
        chainWrapper.eq(TbDwTaskAutoCreate::getSysDwId, entityParam.getSysDwId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(entityParam.getSysDwId());
        } else {
            return entityParam;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param sysDwId 主键
     * @return 是否成功
     */
    @DataSource(name = "EBMS")
    @Override
    public boolean deleteById(BigDecimal sysDwId) {
        int total = tbDwTaskAutoCreateMapper.deleteById(sysDwId);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param sysDwIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "EBMS")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> sysDwIdList) {
        int delCount = tbDwTaskAutoCreateMapper.deleteBatchIds(sysDwIdList);
        if (sysDwIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    @DataSource(name = "EBMS")
    @Override
    public ResponseData getMarketplaceIdByPlatformSite(String platform, String site) {
        return ResponseData.success(tbDwTaskAutoCreateMapper.getMarketplaceIdByPlatformSite(platform, site));
    }


    @Override
    @DataSource(name = "EBMS")
    public ResponseData syncShopDwDataTask(EbmsShopDataDownTaskParam dataDownParam) {

        this.convertCountryCode(dataDownParam);

        //判断任务是否已经存在
        TbDwTaskAutoCreate checkTaskExist = this.checkTaskExist(dataDownParam);
        if (ObjectUtil.isNotNull(checkTaskExist)) {
            return ResponseData.success(checkTaskExist.getSysDwId());
        }

        MarkIdEndPointResult markIdEndPoint = tbDwTaskAutoCreateMapper.getMarkIdEndPointByCountryCode(dataDownParam.getCountryCode());

        TbDwTaskAutoCreate autoCreate = new TbDwTaskAutoCreate();
        String taskName = "[" + dataDownParam.getDwDataObjEnName() + "]-[" + dataDownParam.getShopNameSimple() + "]-[" + dataDownParam.getCountryCode() + "]";

        autoCreate.setDwDataObjNum(dataDownParam.getDwDataObjNum());
        autoCreate.setDwTaskName(taskName);
        autoCreate.setDwDataObjEnName(dataDownParam.getDwDataObjEnName());
        autoCreate.setDwSellerId(dataDownParam.getSellerId());
        autoCreate.setDwShopNameSimple(dataDownParam.getShopNameSimple());
        autoCreate.setDwCountryCode(dataDownParam.getCountryCode());
        autoCreate.setDwAwsKey(dataDownParam.getAwsAccessKeyId());
        autoCreate.setDwEndPoint(markIdEndPoint.getEndpoint());
        autoCreate.setDwMarkIdList(markIdEndPoint.getMarketplaceId());
        autoCreate.setDwIsAutoDownload(BigDecimal.ONE);
        autoCreate.setDwPlatName(dataDownParam.getElePlatformName());
        autoCreate.setCreateTime(new Date());
        tbDwTaskAutoCreateMapper.insert(autoCreate);
        return ResponseData.success(autoCreate.getSysDwId());
    }

    /**
     * 站点转换
     *报告支持下载的区域region=ALL+按站点下载dwMerge=0 （站点不变）
     *报告支持下载的区域region=ALL+按区域整体下载dwMerge=1 （站点调整为区域对应的默认站点）
     *报告支持下载的区域region=NA（默认站点US）+按区域整体下载dwMerge=0 （站点不变）
     *报告支持下载的区域region=NA（默认站点US）+按区域整体下载dwMerge=1 （站点调整为区域对应的默认站点）
     *报告支持下载的区域region=EU（默认站点DE）+按区域整体下载dwMerge=0 （站点不变）
     *报告支持下载的区域region=EU（默认站点DE）+按区域整体下载dwMerge=1 （站点调整为区域对应的默认站点）
     *报告支持下载的区域region=FE（默认站点JP）+按区域整体下载dwMerge=0 （站点不变）
     *报告支持下载的区域region=FE（默认站点JP）+按区域整体下载dwMerge=1 （站点调整为区域对应的默认站点）
     * @param dataDownParam
     */
    private void convertCountryCode(EbmsShopDataDownTaskParam dataDownParam) {
        //站点转换 ALL 默认是店铺注册的站点
        String countryCode = dataDownParam.getCountryCode();
        if ("ALL".equals(dataDownParam.getRegion())) {
            //报告支持下载的区域region=ALL+按区域整体下载dwMerge=1 + 根据区域下载匹配出默认站点
            countryCode = Integer.valueOf(1).equals(dataDownParam.getDwMerge()) ? this.getAreaDefCountryCode(dataDownParam.getArea()) : countryCode;
        } else if ("NA".equals(dataDownParam.getRegion())) {
            //报告支持下载的区域region=NA（默认站点US）+按区域整体下载dwMerge=1
            countryCode = Integer.valueOf(1).equals(dataDownParam.getDwMerge()) ? "US" : countryCode;
        } else if ("EU".equals(dataDownParam.getRegion())) {
            //报告支持下载的区域region=EU（默认站点DE）+按区域整体下载dwMerge=1
            countryCode = Integer.valueOf(1).equals(dataDownParam.getDwMerge()) ? "DE" : countryCode;
        } else if ("FE".equals(dataDownParam.getRegion())) {
            //报告支持下载的区域region=FE（默认站点JP）+按区域整体下载dwMerge=1
            countryCode = Integer.valueOf(1).equals(dataDownParam.getDwMerge()) ? "JP" : countryCode;
        }
        dataDownParam.setCountryCode(countryCode);

    }

    /**
     * 区域默认站点
     * NA（默认站点US）
     * EU（默认站点DE）
     * FE（默认站点JP）
     * @param area
     * @return
     */
    private String getAreaDefCountryCode(String area) {
        if (ObjectUtil.isEmpty(area)) {
            return null;
        }
        String result = "";
        switch (area) {
            case "NA":
                result = "US";
                break;
            case "EU":
                result = "DE";
                break;
            case "FE":
                result = "JP";
                break;
            default:
                result = "US";
        }
        return result;
    }

    /**
     * 检查ebms是否已经创建了（平台+账号+站点+报告编码）
     * 站点可能会合并在一个任务中
     * @param dataDownParam
     * @return
     */
    private TbDwTaskAutoCreate checkTaskExist(EbmsShopDataDownTaskParam dataDownParam) {

        LambdaQueryWrapper<TbDwTaskAutoCreate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TbDwTaskAutoCreate::getDwPlatName, dataDownParam.getElePlatformName())
                .eq(TbDwTaskAutoCreate::getDwDataObjNum, dataDownParam.getDwDataObjNum())
                .eq(TbDwTaskAutoCreate::getDwShopNameSimple, dataDownParam.getShopNameSimple())
                .like(TbDwTaskAutoCreate::getDwCountryCode, dataDownParam.getCountryCode());
        List<TbDwTaskAutoCreate> autoCreateList = tbDwTaskAutoCreateMapper.selectList(wrapper);
        if (ObjectUtil.isNotEmpty(autoCreateList)) {
            return autoCreateList.get(0);
        }
        return null;
    }
}