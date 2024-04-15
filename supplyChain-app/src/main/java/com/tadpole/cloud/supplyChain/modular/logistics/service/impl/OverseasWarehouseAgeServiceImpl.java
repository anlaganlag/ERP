package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasWarehouseAge;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.OverseasWarehouseAgeMapper;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasWarehouseAgeParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasWarehouseAgeResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasWarehouseAgeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 海外仓库龄报表服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-02-03
 */
@Service
@Slf4j
public class OverseasWarehouseAgeServiceImpl extends ServiceImpl<OverseasWarehouseAgeMapper, OverseasWarehouseAge> implements IOverseasWarehouseAgeService {

    @Autowired
    private OverseasWarehouseAgeMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(OverseasWarehouseAgeParam param) {
        return ResponseData.success(mapper.queryPage(PageFactory.defaultPage(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public List<OverseasWarehouseAgeResult> export(OverseasWarehouseAgeParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        return mapper.queryPage(pageContext, param).getRecords();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData batchAgeIn(List<OverseasWarehouseAge> param) {
        if(CollectionUtil.isEmpty(param)){
            return ResponseData.error("海外仓库龄入库参数为空");
        }
//        this.saveBatch(param);
        for (OverseasWarehouseAge age : param) {
            this.save(age);
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData batchAgeOut(List<OverseasWarehouseAge> param) {
        if(CollectionUtil.isEmpty(param)){
            return ResponseData.error("海外仓库龄出库参数为空");
        }
        ageFor1:for (OverseasWarehouseAge overseasWarehouseAge : param) {
            //出库数量
            BigDecimal outAccount = overseasWarehouseAge.getInventoryQuantity();
            LambdaQueryWrapper<OverseasWarehouseAge> ageWrapper = new LambdaQueryWrapper<>();
            ageWrapper.eq(OverseasWarehouseAge :: getPlatform, overseasWarehouseAge.getPlatform())
                    .eq(OverseasWarehouseAge :: getSysShopsName, overseasWarehouseAge.getSysShopsName())
                    .eq(OverseasWarehouseAge :: getSysSite, overseasWarehouseAge.getSysSite())
                    .eq(OverseasWarehouseAge :: getWarehouseName, overseasWarehouseAge.getWarehouseName())
                    .eq(OverseasWarehouseAge :: getSku, overseasWarehouseAge.getSku())
                    .eq(StringUtils.isNotBlank(overseasWarehouseAge.getMaterialCode()), OverseasWarehouseAge :: getMaterialCode, overseasWarehouseAge.getMaterialCode())
                    .likeLeft(OverseasWarehouseAge :: getFnSku, overseasWarehouseAge.getFnSku())
                    .orderByDesc(OverseasWarehouseAge :: getSignDate);
            List<OverseasWarehouseAge> ageList = this.baseMapper.selectList(ageWrapper);
            //出库扣减
            if(CollectionUtil.isNotEmpty(ageList)){
                ageFor2:for (OverseasWarehouseAge ageResult : ageList) {
                    /**
                     * 1、当出库数量大于等于账存数量时，出库数量扣除账存数量，剩下的出库数量根据签收时间倒序依次扣除，并更新当前账存数量为：0，数据状态为出库：0，直至扣除完出库数量为止；
                     * 2、当出库数量小于账存数量时，当前账存数量 = 账存数量 - 出库数量
                     */
                    BigDecimal inventoryQuantity = ageResult.getInventoryQuantity();
                    if(outAccount.compareTo(inventoryQuantity) >= 0){
                        outAccount = outAccount.subtract(inventoryQuantity);
                        ageResult.setInventoryQuantity(BigDecimal.ZERO);
                        ageResult.setDataStatus("0");//0：出库
                    } else {
                        ageResult.setInventoryQuantity(inventoryQuantity.subtract(outAccount));
                        outAccount = BigDecimal.ZERO;
                    }
                    /*if(StringUtils.isNotBlank(ageResult.getRemark())){
                        ageResult.setRemark(ageResult.getRemark() + "," + overseasWarehouseAge.getRemark());
                    } else {
                        ageResult.setRemark(overseasWarehouseAge.getRemark());
                    }*/
                    ageResult.setUpdateTime(DateUtil.date());
                    ageResult.setUpdateUser(overseasWarehouseAge.getUpdateUser());
                    this.baseMapper.updateById(ageResult);

                    //出库数量全部出库完成，停止继续出库
                    if(BigDecimal.ZERO.compareTo(outAccount) == 0){
                        break ageFor2;
                    }
                }
            }
        }
        return ResponseData.success();
    }
}
