package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.AmazonInventoryList;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.AmazonInventoryListMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.AmazonInventoryTotal;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IAmazonInventoryListService;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.AmazonInventoryListParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
* <p>
    * 庫存列表3.0 服务实现类
    * </p>
*
* @author S20190161
* @since 2022-12-20
*/
@Service
public class AmazonInventoryListServiceImpl extends ServiceImpl<AmazonInventoryListMapper, AmazonInventoryList> implements IAmazonInventoryListService {

    @Autowired
    AmazonInventoryListOrgServiceImpl inventoryListOrgService;

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
    private AmazonInventoryTotal total =new AmazonInventoryTotal();

    @DataSource(name = "warehouse")
    @Override
    public void afreshCount() {

        this.baseMapper.afreshCount();
    }

    @Transactional
    @DataSource(name = "warehouse")
    @Override
    public PageTotalResult<AmazonInventoryList, AmazonInventoryTotal> findPageBySpec(AmazonInventoryListParam param) {
        getPageContext().setSize(param.getPageSize());
        //按仓库维度排序，lv倒序
        QueryWrapper<AmazonInventoryList> queryWrapper=queryWrapper(param,1);
        queryWrapper.orderByDesc("warehouse_name","lv");
        //查询分页
        IPage<AmazonInventoryList> page =  this.baseMapper.selectPage(getPageContext(),queryWrapper);
        List<AmazonInventoryList> list=page.getRecords();
        list.parallelStream().filter(a->StringUtils.isNotEmpty(a.getMaterialCode())).forEach(a->{
           var orgs=inventoryListOrgService.getListOrg(a);
           a.setOrgDetails(orgs);
        });
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLv() ==1){
                String  warehouseName= list.get(i).getWarehouseName();
                //新加进去仓库维度汇总行的会挤占索引，直接跳过
                // if (StringUtils.isNotEmpty(warehouseName)){
                QueryWrapper<AmazonInventoryList> xjWrapper= queryWrapper(param,2);
                //查询汇总统计
                xjWrapper.eq("warehouse_name",warehouseName);
                AmazonInventoryList item = this.baseMapper.selectOne(xjWrapper);
                item.setLv(9999L);
                list.add(i+1,item);
            }

        }

        //第一页的时候才查询汇总
        if(getPageContext().getCurrent()==1){
            //查询汇总统计
            var totalWrapper=queryWrapper(param,2);
            AmazonInventoryList querySum = this.baseMapper.selectOne(totalWrapper);
            if (querySum != null)
                BeanUtils.copyProperties(querySum,total);
        }
        page.setRecords(list);
        return new PageTotalResult<>(page,total);
    }

    @DataSource(name = "warehouse")
    @Override
    public List<AmazonInventoryList> export(AmazonInventoryListParam param) {
        return this.baseMapper.selectList(queryWrapper(param,1));
    }

    private QueryWrapper<AmazonInventoryList> queryWrapper(AmazonInventoryListParam param,int type) {
        QueryWrapper<AmazonInventoryList> queryWrapper=new QueryWrapper<>();
        if (type==1)
        {
            queryWrapper.select("row_number() over (partition by warehouse_name order by shop_name,site ) lv",
                    "id, year, month, shop_name, site, platform, warehouse_name, inventory_status, sku, material_code, department, team, sales_org, category, product_type, product_name, style, main_material, design, company_brand, fit_brand, model, color, sizes, packing, version, type, specmodel, first_order_date, opening_total, opening_in_transit, opening_logistics, opening_in_stock, inbound_total, inbound_domestic_fba, inbound_eu_transfer, inbound_ow_to_fba, outgoing_total, outgoing_monthly_sales, outgoing_monthly_returned, outgoing_monthly_destoryed, outgoing_monthly_removal, outgoing_inventory_increase, outgoing_inventory_reduction, closing_quantity_book, closing_store_inventory_total, closing_store_inventory_in_transit, closing_store_inventory_logistics, closing_store_inventory_in_stock, inventory_surplus, inventory_loss, create_time"
            );
        }else  if (type==2){
            queryWrapper.select(" sum(opening_total)openingtotal, sum(opening_in_transit)openingintransit, sum(opening_logistics)openinglogistics, sum(opening_in_stock)openinginstock, sum(inbound_total)inboundtotal, sum(inbound_domestic_fba)inbounddomesticfba, sum(inbound_eu_transfer)inboundeutransfer, sum(inbound_ow_to_fba)inboundowtofba, sum(outgoing_total)outgoingtotal, sum(outgoing_monthly_sales)outgoingmonthlysales, sum(outgoing_monthly_returned)outgoingmonthlyreturned, sum(outgoing_monthly_destoryed)outgoingmonthlydestoryed, sum(outgoing_monthly_removal)outgoingmonthlyremoval, sum(outgoing_inventory_increase)outgoinginventoryincrease, sum(outgoing_inventory_reduction)outgoinginventoryreduction, sum(closing_quantity_book)closingquantitybook, sum(closing_store_inventory_total)closingstoreinventorytotal, sum(closing_store_inventory_in_transit)closingstoreinventoryintransit, sum(closing_store_inventory_logistics)closingstoreinventorylogistics, sum(closing_store_inventory_in_stock)closingstoreinventoryinstock, sum(inventory_surplus)inventorysurplus, sum(inventory_loss)inventoryloss ");
        }
        if (param.getSysShopsNames() !=null && param.getSysShopsNames().size()>0){
            queryWrapper.in("shop_name",param.getSysShopsNames());
        }
        if (param.getSysSites()!=null && param.getSysSites().size()>0){
            queryWrapper.in("site",param.getSysSites());
        }
        if (StringUtils.isNotEmpty(param.getInventoryStatus())){
            queryWrapper.in("inventory_status",param.getInventoryStatus());
        }
        if (StringUtils.isNotEmpty(param.getWarehouseName())){
            queryWrapper.eq("warehouse_name",param.getWarehouseName());
        }
        if (StringUtils.isNotEmpty(param.getYear())){
            queryWrapper.eq("year",param.getYear());
        }
        if (StringUtils.isNotEmpty(param.getMonth())){
            queryWrapper.eq("month",param.getMonth());
        }
        if (StringUtils.isNotEmpty(param.getSalesOrg())){
            queryWrapper.eq("sales_org",param.getSalesOrg());
        }
        if (StringUtils.isNotEmpty(param.getSku())){
            queryWrapper.eq("sku",param.getSku());
        }
        if (StringUtils.isNotEmpty(param.getMaterialCode())){
            queryWrapper.eq("material_code",param.getMaterialCode());
        }
        if (StringUtils.isNotEmpty(param.getProductType())){
            queryWrapper.eq("product_type",param.getProductType());
        }
        if (StringUtils.isNotEmpty(param.getProductName())){
            queryWrapper.eq("product_name",param.getProductName());
        }
        if (StringUtils.isNotEmpty(param.getStyle())){
            queryWrapper.eq("style",param.getStyle());
        }
        if (StringUtils.isNotEmpty(param.getCompanyBrand())){
            queryWrapper.eq("company_brand",param.getCompanyBrand());
        }
        if (StringUtils.isNotEmpty(param.getModel())){
            queryWrapper.eq("model",param.getModel());
        }
        if (StringUtils.isNotEmpty(param.getSpecmodel())){
            queryWrapper.like("specmodel",param.getSpecmodel());
        }
        if (param.getDepartments() !=null && param.getDepartments().size()>0){
            queryWrapper.in("department",param.getDepartments());
        }
        if (param.getTeams()!=null && param.getTeams().size()>0){
            queryWrapper.in("team",param.getTeams());
        }
        if (param.getSurplus()!=null){
            if (param.getSurplus()==1)
            queryWrapper.gt("inventory_surplus",0);
            else if (param.getSurplus()==0)
                queryWrapper.gt("inventory_loss",0);
        }

        return queryWrapper;
    }
}
