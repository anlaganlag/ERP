package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.platformSettlement.api.finance.entity.MonthlyStorageFees;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.MonthlyStorageFeesMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.MonthlyStorageFeesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.MonthlyStorageFeesResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.MonthlyStorageFeesTotal;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IMonthlyStorageFeesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
* <p>
* 月储存费用 服务实现类
* </p>
*
* @author S20190161
* @since 2022-10-12
*/
@Service
public class MonthlyStorageFeesServiceImpl extends ServiceImpl<MonthlyStorageFeesMapper, MonthlyStorageFees> implements IMonthlyStorageFeesService {


    @Autowired
    private MonthlyStorageFeesMapper mapper;

    private MonthlyStorageFeesTotal total = new MonthlyStorageFeesTotal();
    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    @DataSource(name = "finance")
    @Override
    public PageTotalResult<MonthlyStorageFeesResult,MonthlyStorageFeesTotal> findPageBySpec(MonthlyStorageFeesParam param) {
        Page pageContext = getPageContext();
        var queryWrapper=queryWrapper(param);
        //查询分页
        IPage<MonthlyStorageFeesResult> page =  this.baseMapper.selectPage(pageContext,queryWrapper);
        //第一页的时候才查询汇总
        if(pageContext.getCurrent()==1) {
            //查询汇总统计
            queryWrapper.select("round(sum(estimated_monthly_storage_fee),2) estimatedMonthlyStorageFee", "sum(storage_fee) storageFee"
            ,"round(sum(decode(SYS_SITE,'JP',estimated_monthly_storage_fee*1.1,estimated_monthly_storage_fee)),2) estimatedMonthlyStorageDetailFee,sum(STORAGE_DETAIL_FEE ) storage_detail_fee"
            );
            MonthlyStorageFees fees = this.baseMapper.selectOne(queryWrapper);
            if (fees != null)
            BeanUtils.copyProperties(fees, total);
        }
        return new PageTotalResult<>(page,total);

    }
    @DataSource(name = "finance")
    @Override
    public int deleteBatch(MonthlyStorageFeesParam param) {
        //账号 +站点 +期间 必填
        QueryWrapper<MonthlyStorageFees> wrapper = new QueryWrapper<>();
        wrapper.in("sys_shops_name", param.getSysShopsNames())
                .in("sys_site", param.getSysSites())
                //只删除未确认的数据
                .eq("status", 1);
        Date startDate = DateUtil.offsetMonth(DateUtil.parse(param.getStartDur(), "yyyy-MM"), 1);
        Date endDate = DateUtil.offsetMonth(DateUtil.parse(param.getEndDur(), "yyyy-MM"), 2);
        wrapper.lambda().ge(MonthlyStorageFees::getUploadDate, startDate)
                .le(MonthlyStorageFees::getUploadDate, endDate);
        if (StringUtils.isNotEmpty(param.getFnsku())){
            wrapper.eq("fnsku",param.getFnsku());
        }
        if (StringUtils.isNotEmpty(param.getSku())){
            wrapper.eq("sku",param.getSku());
        }
        if (StringUtils.isNotEmpty(param.getMaterialCode())){
            wrapper.eq("MATERIAL_CODE",param.getMaterialCode());
        }
      return  this.baseMapper.delete(wrapper);
    }
    @DataSource(name = "finance")
    @Override
    public int updateBatch(MonthlyStorageFeesParam param) {
        //事业部不能为空
        Integer emptyDepartmentCount = new LambdaQueryChainWrapper<>(mapper)
                .in(ObjectUtil.isNotEmpty(param.getSysShopsNames())
                        ,MonthlyStorageFees::getSysShopsName, param.getSysShopsNames())
                .in(ObjectUtil.isNotEmpty(param.getSysSites())
                        ,MonthlyStorageFees::getSysSite, param.getSysSites())
                .ge(ObjectUtil.isNotEmpty(param.getStartDur())
                        ,MonthlyStorageFees::getMonthOfCharge, param.getStartDur())
                .le(ObjectUtil.isNotEmpty(param.getEndDur())
                        ,MonthlyStorageFees::getMonthOfCharge, param.getEndDur())
//                .eq(MonthlyStorageFees::getStatus, BigDecimal.ONE)
                .isNull(MonthlyStorageFees::getDepartment)
                .and(wrapper->wrapper.isNull(MonthlyStorageFees::getStatus).or().eq(MonthlyStorageFees::getStatus, BigDecimal.ONE))
                .count();
        if (emptyDepartmentCount > 0 ) {
            return -1;
        }
        UpdateWrapper<MonthlyStorageFees> wrapper = new UpdateWrapper<>();
        wrapper
                .in("sys_shops_name", param.getSysShopsNames())
                //只修改未确认的数据
                .and(i->i.isNull("status").or().eq("status", 1))
                ;

        Date startDate = DateUtil.offsetMonth(DateUtil.parse(param.getStartDur(), "yyyy-MM"), 1);
        Date endDate = DateUtil.offsetMonth(DateUtil.parse(param.getEndDur(), "yyyy-MM"), 2);
        wrapper.lambda().ge(MonthlyStorageFees::getUploadDate, startDate)
                .le(MonthlyStorageFees::getUploadDate, endDate);
        if (param.getSysSites()!=null && param.getSysSites().size()>0) {
            wrapper.in("sys_site", param.getSysSites());
        }
        if (StringUtils.isNotEmpty(param.getFnsku())){
            wrapper.eq("fnsku",param.getFnsku());
        }
        if (StringUtils.isNotEmpty(param.getSku())){
            wrapper.eq("sku",param.getSku());
        }
        if (StringUtils.isNotEmpty(param.getMaterialCode())){
            wrapper.eq("MATERIAL_CODE",param.getMaterialCode());
        }
        wrapper.set("status", 2).set("update_time", DateUtil.date()).set("update_user", LoginContext.me().getLoginUser().getName());
        return this.baseMapper.update(null, wrapper);
    }
    @DataSource(name = "finance")
    @Override
    public List<MonthlyStorageFees> export(MonthlyStorageFeesParam param) {
        return this.baseMapper.selectList(queryWrapper(param));

    }
    @DataSource(name = "finance")
    @Override
    public void afreshStorageFee() {
        this.baseMapper.afreshStorageFee();
    }


    @DataSource(name = "finance")
    @Override
    public void fnskuFillMonListing(MonthlyStorageFeesParam param) {
        this.baseMapper.fnskuFillMonListing(param);
    }


    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateLatestDate(MonthlyStorageFeesParam param) throws ParseException {

        List<MonthlyStorageFeesResult> resultList = emailList();
        //刷listing最晚时间保存
        for(int i=0;i<resultList.size();i++) {
            UpdateWrapper<MonthlyStorageFees> updateWrapper=new UpdateWrapper<>();
            updateWrapper.eq("id",resultList.get(i).getId())
                    .set("UPDATE_TIME",DateUtil.parse(param.getUpdateTime(),"yyyy-MM-dd"))
                    .set("CREATE_TIME",new Date());

            this.update(updateWrapper);
        }
    }

    @DataSource(name = "finance")
    @Override
    public List<MonthlyStorageFeesResult> emailList() {
        List<MonthlyStorageFeesResult> list = this.baseMapper.emailList();
        return list;
    }


    /**
     * 查询，汇总 查询条件
     * @author AmteMa
     * @date 2022/10/13
     */
    private QueryWrapper<MonthlyStorageFees> queryWrapper(MonthlyStorageFeesParam param){
        QueryWrapper<MonthlyStorageFees> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("id, department,team,asin, fnsku, longest_side, median_side, shortest_side, measurement_units, weight, weight_units, currency, item_volume, volume_units, product_size_tier, average_quantity_on_hand, average_quantity_pending_removal, total_item_volume, month_of_charge, storage_rate, fulfillment_center, country_code, estimated_monthly_storage_fee, sku, material_code, storage_fee, status, original_task_name, file_path, create_time, upload_date, sys_site, sys_shops_name, estimated_total_item_volume, update_time, update_user",
                "decode(STATUS,null,'未确认',1,'未确认',5,'SKU异常','已确认') state",
                "decode(SYS_SITE,'JP',estimated_monthly_storage_fee*1.1,estimated_monthly_storage_fee) estimatedMonthlyStorageDetailFee,storage_detail_fee "
        );
        if (param.getSysShopsNames() !=null && param.getSysShopsNames().size()>0){
            queryWrapper.in("sys_shops_name",param.getSysShopsNames());
        }
        if (param.getSysSites()!=null && param.getSysSites().size()>0){
            queryWrapper.in("sys_site",param.getSysSites());
        }
        if (StringUtils.isNotEmpty(param.getFnsku())){
            queryWrapper.eq("fnsku",param.getFnsku());
        }
        if (StringUtils.isNotEmpty(param.getSku())){
            queryWrapper.eq("sku",param.getSku());
        }
        if (StringUtils.isNotEmpty(param.getMaterialCode())){
            queryWrapper.eq("MATERIAL_CODE",param.getMaterialCode());
        }
        if (param.getStatus()!=null){
            if (param.getStatus() == 2) {
                queryWrapper.in("status",2,3,4);
            } else if(param.getStatus() == 1) {
                queryWrapper.in("status",1);
            } else if(param.getStatus() == 5) {
                queryWrapper.in("status",5);
            }
        }
        if (StringUtils.isNotEmpty(param.getStartDur()) && StringUtils.isNotEmpty(param.getEndDur())){
            queryWrapper.lambda().eq(MonthlyStorageFees::getMonthOfCharge,param.getStartDur());
        }
        return queryWrapper;
    }
}
