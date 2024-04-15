package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LongTermStorageFeeCharges;
import com.tadpole.cloud.platformSettlement.api.finance.entity.MonthlyStorageFees;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.LongTermStorageFeeChargesMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.LongTermStorageFeeChargesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LongTermStorageFeeChargesResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LongTermStorageFeeChargesTotal;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.MonthlyStorageFeesMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ILongTermStorageFeeChargesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
* 长期仓储费 服务实现类
* </p>
*
* @author S20190161
* @since 2022-10-12
*/
@Service
public class LongTermStorageFeeChargesServiceImpl extends ServiceImpl<LongTermStorageFeeChargesMapper, LongTermStorageFeeCharges> implements ILongTermStorageFeeChargesService {


    @Autowired
    private LongTermStorageFeeChargesMapper mapper;
    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
    LongTermStorageFeeChargesTotal total=new LongTermStorageFeeChargesTotal();
    @DataSource(name = "finance")
    @Override
    public PageTotalResult<LongTermStorageFeeChargesResult, LongTermStorageFeeChargesTotal> findPageBySpec(LongTermStorageFeeChargesParam param){
        Page pageContext = getPageContext();
        QueryWrapper<LongTermStorageFeeCharges> queryWrapper=queryWrapper(param);


        IPage<LongTermStorageFeeChargesResult> page =  this.baseMapper.selectPage(pageContext,queryWrapper);
        //第一页的时候才查询汇总
        if(pageContext.getCurrent()==1){
        //查询汇总统计
        queryWrapper.select("sum(QTY_CHARGED_6_MO)qtyCharged6Mo",
                "sum(QTY_CHARGED_12_MO)qtyCharged12Mo",
                "sum(MO6_LONG_TERMS_STORAGE_FEE)mo6LongTermsStorageFee",
                "sum(MO12_LONG_TERMS_STORAGE_FEE)mo12LongTermsStorageFee",
                "sum(MO6_LONG_TERMS_STORAGE_FEE+MO12_LONG_TERMS_STORAGE_FEE)longTermsStorageFee",
                "sum(STORAGE_FEE)storageFee",
                "sum(storage_detail_fee) storageDetailFee"
        );
        LongTermStorageFeeCharges fees = this.baseMapper.selectOne(queryWrapper);
        if (fees != null)
        BeanUtils.copyProperties(fees,total);
        }
        return new PageTotalResult<>(page,total);
    }
    @DataSource(name = "finance")
    @Override
    public int deleteBatch(LongTermStorageFeeChargesParam param) {
        //账号 +站点 +期间 必填
        QueryWrapper<LongTermStorageFeeCharges> wrapper = new QueryWrapper<>();
        if (param.getSysShopsNames() !=null && param.getSysShopsNames().size()>0){
            wrapper.in("sys_shops_name",param.getSysShopsNames());
        }
        if (param.getSysSites()!=null && param.getSysSites().size()>0){
            wrapper.in("sys_site",param.getSysSites());
        }
                //只删除未确认的数据
                wrapper.eq("status", 1);
        Date startDate = DateUtil.parse(param.getStartDur(), "yyyy-MM");
        Date endDate = DateUtil.offsetMonth(DateUtil.parse(param.getEndDur(), "yyyy-MM"), 1);
        wrapper.lambda().ge(LongTermStorageFeeCharges::getSnapshotDate, startDate)
                .le(LongTermStorageFeeCharges::getSnapshotDate, endDate);
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
    public int updateBatch(LongTermStorageFeeChargesParam param) {
        //事业部不能为空
        Integer emptyDepartmentCount = new LambdaQueryChainWrapper<>(mapper)
                .in(ObjectUtil.isNotEmpty(param.getSysShopsNames())
                        , LongTermStorageFeeCharges::getSysShopsName, param.getSysShopsNames())
                .in(ObjectUtil.isNotEmpty(param.getSysSites())
                        ,LongTermStorageFeeCharges::getSysSite, param.getSysSites())
                .ge(ObjectUtil.isNotEmpty(param.getStartDur())
                        ,LongTermStorageFeeCharges::getSnapshotDate, DateUtil.parse(param.getStartDur(),"yyyy-MM"))
                .lt(ObjectUtil.isNotEmpty(param.getEndDur())
                        ,LongTermStorageFeeCharges::getSnapshotDate, DateUtil.offsetMonth(DateUtil.parse(param.getEndDur(), "yyyy-MM"), 1))
                .isNull(LongTermStorageFeeCharges::getDepartment)
                .and(wrapper->wrapper.isNull(LongTermStorageFeeCharges::getStatus).or().eq(LongTermStorageFeeCharges::getStatus, BigDecimal.ONE))
                .count();
        if (emptyDepartmentCount > 0 ) {
            return -1;
        }
        UpdateWrapper<LongTermStorageFeeCharges> wrapper = new UpdateWrapper<>();
        if (param.getSysShopsNames() !=null && param.getSysShopsNames().size()>0){
            wrapper.in("sys_shops_name",param.getSysShopsNames());
        }
        if (param.getSysSites()!=null && param.getSysSites().size()>0){
            wrapper.in("sys_site",param.getSysSites());
        }
        //只修改未确认的数据
        wrapper.and(i->i.isNull("status").or().eq("status", 1));

        Date startDate = DateUtil.parse(param.getStartDur(), "yyyy-MM");
        Date endDate = DateUtil.offsetMonth(DateUtil.parse(param.getEndDur(), "yyyy-MM"), 1);
        wrapper.lambda().ge(LongTermStorageFeeCharges::getSnapshotDate, startDate)
                .le(LongTermStorageFeeCharges::getSnapshotDate, endDate);
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
    public List<LongTermStorageFeeCharges> export(LongTermStorageFeeChargesParam param) {
        return this.baseMapper.selectList(queryWrapper(param));
    }
    @DataSource(name = "finance")
    @Override
    public void afreshStorageFee() {
        this.baseMapper.afreshStorageFee();
    }


    @DataSource(name = "finance")
    @Override
    public List<LongTermStorageFeeChargesResult> emailList() {
        List<LongTermStorageFeeChargesResult> list = this.baseMapper.emailList();
        return list;
    }

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateLatestDate(LongTermStorageFeeChargesParam param) throws ParseException {

        List<LongTermStorageFeeChargesResult> resultList = emailList();
        //刷listing最晚时间保存
        for(int i=0;i<resultList.size();i++) {
            UpdateWrapper<LongTermStorageFeeCharges> updateWrapper=new UpdateWrapper<>();
            updateWrapper.eq("id",resultList.get(i).getId())
                    .set("UPDATE_TIME",DateUtil.parse(param.getUpdateTime(),"yyyy-MM-dd"))
                    .set("CREATE_TIME",new Date());

            this.update(updateWrapper);
        }
    }


    @DataSource(name = "finance")
    @Override
    public void fnskuFillLongListing(LongTermStorageFeeChargesParam param) {
        this.baseMapper.fnskuFillLongListing(param);
    }
    /**
     * 查询，汇总 查询条件
     * @author AmteMa
     * @date 2022/10/13
     */
    private QueryWrapper<LongTermStorageFeeCharges> queryWrapper(LongTermStorageFeeChargesParam param){
        QueryWrapper<LongTermStorageFeeCharges> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("id, department,team,sku, fnsku, asin, currency, mo12_long_terms_storage_fee, mo6_long_terms_storage_fee, snapshot_date, title, condition, unit_volume, qty_charged_12_mo, qty_charged_6_mo, volume_unit, country, enrolled_in_small_and_light, material_code, storage_fee, status, original_task_name, file_path, create_time, upload_date, sys_site, sys_shops_name, update_time, update_user",
                "MO6_LONG_TERMS_STORAGE_FEE+MO12_LONG_TERMS_STORAGE_FEE longTermsStorageFee",
                "decode(STATUS,null,'未确认',1,'未确认',5,'SKU异常','已确认') state,storage_detail_fee,surcharge_age_tier,rate_surcharge"
        )
        ;
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
            queryWrapper.eq("material_code",param.getMaterialCode());
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
            Date startDate = DateUtil.parse(param.getStartDur(), "yyyy-MM");
            Date endDate= DateUtil.offsetMonth(DateUtil.parse(param.getEndDur(), "yyyy-MM"),1);
            queryWrapper.lambda().ge(LongTermStorageFeeCharges::getSnapshotDate,startDate)
                    .lt(LongTermStorageFeeCharges::getSnapshotDate,endDate);
        }
        return queryWrapper;
    }

}
