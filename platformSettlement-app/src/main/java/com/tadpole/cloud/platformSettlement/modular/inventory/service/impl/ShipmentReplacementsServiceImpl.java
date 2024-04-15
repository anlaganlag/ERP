package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ShipmentReplacements;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.ShipmentReplacementsParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.ShipmentReplacementsResult;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.ShipmentReplacementsTotal;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.ShipmentReplacementsMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IShipmentReplacementsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* <p>
    *  服务实现类
    * </p>
*
* @author S20190161
* @since 2023-06-08
*/
@Service
public class ShipmentReplacementsServiceImpl extends ServiceImpl<ShipmentReplacementsMapper, ShipmentReplacements> implements IShipmentReplacementsService {


    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
    private ShipmentReplacementsTotal total =new ShipmentReplacementsTotal();

    @DataSource(name = "warehouse")
    @Override
    public PageTotalResult<ShipmentReplacementsResult,ShipmentReplacementsTotal> findPageBySpec(ShipmentReplacementsParam param) {
        QueryWrapper<ShipmentReplacements> queryWrapper=queryWrapper(param);
        //查询分页
        IPage<ShipmentReplacementsResult> page =  this.baseMapper.selectPage(getPageContext(),queryWrapper);

        //第一页的时候才查询汇总
        if(getPageContext().getCurrent()==1){
            //查询汇总统计
            queryWrapper.select("sum( quantity) quantity ");
            ShipmentReplacements Fees = this.baseMapper.selectOne(queryWrapper);
            if (Fees != null)
                BeanUtils.copyProperties(Fees,total);
        }
        return new PageTotalResult<>(page,total);
    }
    @DataSource(name = "warehouse")
    @Override
    public List<ShipmentReplacements> export(ShipmentReplacementsParam param) {
        return this.baseMapper.selectList(queryWrapper(param));
    }
    private QueryWrapper<ShipmentReplacements> queryWrapper(ShipmentReplacementsParam param) {
        QueryWrapper<ShipmentReplacements> queryWrapper=new QueryWrapper<>();
queryWrapper.select("decode(replacement_reason_code,0,'其他',1,'丢失',2,'有缺陷',3,'配送损坏',4,'配送错误',5,'配送丢失',6,'发货丢失',7,'买错商品',8,'配送地址错误',9,'配送错误（地址正确）',10,'DC/FC处理中损坏',11,'签收未收到',12,'政策不允许/买家原因')  replacementReasonName"
        ,"id, shipment_date, sku, asin, fulfillment_center_id, original_fulfillment_center_id, quantity, replacement_reason_code, replacement_amazon_order_id, original_amazon_order_id, original_task_name, file_path, sys_seller_id, create_time, sys_site, sys_shops_name");
        if (param.getSource()==1){
            queryWrapper.in("replacement_reason_code",1,3,4,5,8,9,10,11,12);
        }
        if (param.getSysShopsName() !=null && param.getSysShopsName().size()>0){
            queryWrapper.in("sys_shops_name",param.getSysShopsName());
        }
        if (param.getSysSite()!=null && param.getSysSite().size()>0){
            queryWrapper.in("sys_site",param.getSysSite());
        }
        if (param.getSkus()!=null && param.getSkus().size()>0){
            //queryWrapper.in("sku",param.getSkus());
            queryWrapper.in("sku",param.getSkus());
        }
        if (param.getReplacementReasonCode()!=null && param.getReplacementReasonCode().size()>0){
            queryWrapper.in("replacement_reason_code",param.getReplacementReasonCode());
        }

        if (StringUtils.isNotEmpty(param.getReplacementAmazonOrderId())){
            queryWrapper.eq("replacement_amazon_order_id",param.getReplacementAmazonOrderId());
        }
        if (StringUtils.isNotEmpty(param.getOriginalAmazonOrderId())){
            queryWrapper.eq("original_amazon_order_id",param.getOriginalAmazonOrderId());
        }

        if (StringUtils.isNotEmpty(param.getStartDur()) && StringUtils.isNotEmpty(param.getEndDur())){
            Date startDate = DateUtil.parse(param.getStartDur(), "yyyy-MM");
            Date endDate= DateUtil.offsetMonth(DateUtil.parse(param.getEndDur(), "yyyy-MM"),1);
            queryWrapper.lambda().ge(ShipmentReplacements::getShipmentDate,startDate)
                    .lt(ShipmentReplacements::getShipmentDate,endDate);
        }
        queryWrapper.orderByDesc("shipment_date");
        return queryWrapper;
    }

}
