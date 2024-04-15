package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.InventoryStorageOverageFees;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.InventoryStorageOverageFeesMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.InventoryStorageOverageFeesResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.InventoryStorageOverageFeesTotal;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IInventoryStorageOverageFeesService;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.InventoryStorageOverageFeesParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
* <p>
* FBA存货存储超额费用报告 服务实现类
* </p>
*
* @author S20190161
* @since 2022-10-12
*/
@Service
public class InventoryStorageOverageFeesServiceImpl extends ServiceImpl<InventoryStorageOverageFeesMapper, InventoryStorageOverageFees> implements IInventoryStorageOverageFeesService {

    InventoryStorageOverageFeesTotal total=new InventoryStorageOverageFeesTotal();

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    @DataSource(name = "finance")
    @Override
    public PageTotalResult<InventoryStorageOverageFeesResult, InventoryStorageOverageFeesTotal> findPageBySpec(InventoryStorageOverageFeesParam param){
        Page pageContext = getPageContext();
        QueryWrapper<InventoryStorageOverageFees> queryWrapper=queryWrapper(param);
        IPage<InventoryStorageOverageFeesResult> page =  this.baseMapper.selectPage(pageContext,queryWrapper);
        //第一页的时候才查询汇总
        if(pageContext.getCurrent()==1){
            //查询汇总统计
            queryWrapper.select("sum(CHARGED_FEE_AMOUNT) chargedFeeAmount","sum(STORAGE_FEE) storageFee","sum(storage_detail_fee) storageDetailFee");
            var fees = this.baseMapper.selectOne(queryWrapper);
            if (fees != null)
            BeanUtils.copyProperties(fees,total);
        }
        return new PageTotalResult<>(page,total);
    }

    @DataSource(name = "finance")
    @Override
    public int deleteBatch(InventoryStorageOverageFeesParam param) {
        //账号 +站点 +期间 必填
        QueryWrapper<InventoryStorageOverageFees> wrapper = new QueryWrapper<>();
        wrapper.in("sys_shops_name", param.getSysShopsNames())
                .in("sys_site", param.getSysSites())
                //只删除未确认的数据
                .eq("status", 1);
        Date startDate = DateUtil.parse(param.getStartDur(), "yyyy-MM");
        Date endDate = DateUtil.offsetMonth(DateUtil.parse(param.getEndDur(), "yyyy-MM"), 1);
        wrapper.lambda().ge(InventoryStorageOverageFees::getChargedDate, startDate)
                .le(InventoryStorageOverageFees::getChargedDate, endDate);

        return  this.baseMapper.delete(wrapper);
    }

    @DataSource(name = "finance")
    @Override
    public int updateBatch(InventoryStorageOverageFeesParam param) {
        UpdateWrapper<InventoryStorageOverageFees> wrapper = new UpdateWrapper<>();
        wrapper.in("sys_shops_name", param.getSysShopsNames())
                //只修改未确认的数据
                .eq("status", 1);
        Date startDate = DateUtil.parse(param.getStartDur(), "yyyy-MM");
        Date endDate = DateUtil.offsetMonth(DateUtil.parse(param.getEndDur(), "yyyy-MM"), 1);
        wrapper.lambda().ge(InventoryStorageOverageFees::getChargedDate, startDate)
                .le(InventoryStorageOverageFees::getChargedDate, endDate);
        if (param.getSysSites()!=null && param.getSysSites().size()>0) {
            wrapper.in("sys_site", param.getSysSites());
        }
        wrapper.set("status", 2).set("update_time", DateUtil.date()).set("update_user", LoginContext.me().getLoginUser().getName());
        return this.baseMapper.update(null, wrapper);
    }

    @DataSource(name = "finance")
    @Override
    public List<InventoryStorageOverageFees> export(InventoryStorageOverageFeesParam param) {
        return this.baseMapper.selectList(queryWrapper(param));
    }

    @DataSource(name = "finance")
    @Override
    public void afreshStorageFee() {
        this.baseMapper.afreshStorageFee();
    }

    /**
     * 查询，汇总 查询条件
     * @author AmteMa
     * @date 2022/10/13
     */
    private QueryWrapper<InventoryStorageOverageFees> queryWrapper(InventoryStorageOverageFeesParam param){
        QueryWrapper<InventoryStorageOverageFees> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("id, charged_date, country_code, storage_type, charge_rate, storage_usage_volume, storage_limit_volume, overage_volume, volume_unit, charged_fee_amount, currency_code, storage_fee, status, original_task_name, file_path, create_time, upload_date, sys_site, sys_shops_name, update_time, update_user",
                "decode(STATUS,1,'未确认',2,'已确认') state,storage_detail_fee"
        );
        if (param.getSysShopsNames() !=null && param.getSysShopsNames().size()>0){
            queryWrapper.in("sys_shops_name",param.getSysShopsNames());
        }
        if (param.getSysSites()!=null && param.getSysSites().size()>0){
            queryWrapper.in("sys_site",param.getSysSites());
        }
        if (param.getStatus()!=null){
            if (param.getStatus() == 2) {
                queryWrapper.in("status",2,3,4);
            } else if(param.getStatus() == 1) {
                queryWrapper.in("status",1);
            }
        }
        if (StringUtils.isNotEmpty(param.getStartDur()) && StringUtils.isNotEmpty(param.getEndDur())){
            Date startDate = DateUtil.parse(param.getStartDur(), "yyyy-MM");
            Date endDate= DateUtil.offsetMonth(DateUtil.parse(param.getEndDur(), "yyyy-MM"),1);
            queryWrapper.lambda().ge(InventoryStorageOverageFees::getChargedDate,startDate)
                    .lt(InventoryStorageOverageFees::getChargedDate,endDate);
        }
        return queryWrapper;
    }

}
