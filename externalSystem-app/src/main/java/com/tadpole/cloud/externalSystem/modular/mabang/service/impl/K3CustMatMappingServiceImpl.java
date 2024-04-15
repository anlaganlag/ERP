package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3CustMatMapping;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.K3CustMatMappingMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3CustMatMappingParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3CustMatMappingResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3CustMatMappingService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
* <p>
    * K3客户物料对应表 服务实现类
    * </p>
*
* @author lsy
* @since 2022-06-20
*/
@Service
@Slf4j
public class K3CustMatMappingServiceImpl extends ServiceImpl<K3CustMatMappingMapper, K3CustMatMapping> implements IK3CustMatMappingService {

    @Resource
    private K3CustMatMappingMapper mapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @DataSource(name = "external")
    @Override
    public PageResult<K3CustMatMappingResult> list(K3CustMatMappingParam param) {
        Page pageContext = param.getPageContext();
        IPage<K3CustMatMappingResult> page = mapper.list(pageContext, param);
        return new PageResult<>(page);
    }


    @DataSource(name = "external")
    @Override
    public List<K3CustMatMappingResult> exportList(K3CustMatMappingParam param) {
        return  mapper.exportList(param);
    }



    @DataSource(name = "external")
    @Override
    public ResponseData createMat() {
        //用代码床架
        List<K3CustMatMapping> createMatList =  mapper.getCreateMatList();
        for (K3CustMatMapping createItem : createMatList) {
            LambdaQueryWrapper<K3CustMatMapping> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(K3CustMatMapping::getPlatformId, createItem.getPlatformId())
                    .eq(K3CustMatMapping::getShopId, createItem.getShopId())
                    .eq(K3CustMatMapping::getMaterialId, createItem.getMaterialId())
                    .eq(K3CustMatMapping::getCustMatNo, createItem.getCustMatNo())
                    .eq(K3CustMatMapping::getFinanceCode, createItem.getFinanceCode());
            List<K3CustMatMapping> warehouseList = this.mapper.selectList(queryWrapper);

            if (ObjectUtil.isEmpty(warehouseList)) {
                createItem.setUpdateTime(DateUtil.date());
                this.save(createItem);
            }

            }
        //原用merge into创建k3物料
        //        mapper.createMat();

        return ResponseData.success();

        }




    @DataSource(name = "external")
    @Override
    public ResponseData updateOrdersStatus() {
        mapper.updateOrdersStatus();
        return ResponseData.success();
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData updateSeaOrdersStatus() {
        mapper.updateSeaOrdersStatus();
        return ResponseData.success();
    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData updatePlatFormSku() {
        mapper.updatePlatFormSku();
        return ResponseData.success();
    }




//    @DataSource(name = "external")
//    @Override
//    public void refreshCustomerId(K3CustMatMappingParam param) {
//        mapper.refreshCustomerId(param);
//    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<K3CustMatMappingResult> getSyncList() {
        return mapper.getSyncList();
    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<K3CustMatMappingResult> getPushK3List() {
        return mapper.getPushK3List();
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> getFinCodeList() {
        return mapper.getFinCodeList();
    }


    @DataSource(name = "erpcloud")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getCustId(String fincode) {
        return mapper.getCustId(fincode);
    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCustId(String finCode, String custId) {
        mapper.updateCustId(finCode, custId);
    }


    @DataSource(name = "erpcloud")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public K3CustMatMappingResult getInfo(String custId) {
        return mapper.getInfo(custId);
    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refreshInfo(K3CustMatMappingResult res) {
        mapper.refreshInfo(res);
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refreshNoCustId(String finCode) {
        mapper.refreshNoCustId(finCode);
    }



    @DataSource(name = "erpcloud")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void beginProcedureSync(K3CustMatMappingResult param) {
        mapper.beginProcedureSync(param);
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncFailedStatus(K3CustMatMappingResult param) {
        new LambdaUpdateChainWrapper<>(mapper)
        .eq(K3CustMatMapping::getId, param.getId())
        .set(K3CustMatMapping::getUpdateTime, DateUtil.date())
        .set(K3CustMatMapping::getSyncTime, DateUtil.date())
        .set(K3CustMatMapping::getSyncStatus, "0")
        .set(K3CustMatMapping::getSyncFailTimes, param.getSyncFailTimes().add(BigDecimal.ONE))
        .set(K3CustMatMapping::getSyncResultMsg, param.getSyncResultMsg())
        .update();
        //String str = "存储过程同步失败!||\n\n................参数:................||\n\n"+ JSONUtil.toJsonStr(param);
        //itemUpdateWrapper.set(K3CustMatMapping::getSyncResultMsg, str);
        //mapper.update(null, itemUpdateWrapper);
    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncSuccessStatus(K3CustMatMappingResult param) {

        LambdaUpdateWrapper<K3CustMatMapping> itemUpdateWrapper = new LambdaUpdateWrapper<>();
        itemUpdateWrapper.eq(K3CustMatMapping::getId, param.getId());
        itemUpdateWrapper.set(K3CustMatMapping::getUpdateTime, DateUtil.date());
        itemUpdateWrapper.set(K3CustMatMapping::getSyncTime, DateUtil.date());
        itemUpdateWrapper.set(K3CustMatMapping::getSyncStatus, "1");
        String str = "存储过程同步成功!||\n\n................参数:................||\n\n"+ JSONUtil.toJsonStr(param);
        itemUpdateWrapper.set(K3CustMatMapping::getSyncResultMsg, str);
        itemUpdateWrapper.set(K3CustMatMapping::getSyncSuccessTimes, param.getSyncSuccessTimes().add(BigDecimal.ONE));

        mapper.update(null, itemUpdateWrapper);

    }
    @DataSource(name = "erpcloud")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getK3One(K3CustMatMappingResult param) {
       return mapper.getK3One(param);
    }


    @DataSource(name = "external")
    @Override
    public ResponseData queryNames(){
        return ResponseData.success(mapper.queryNames());
    }








}
