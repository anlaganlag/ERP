package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockPurchaseOrders;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.StockPurchaseOrdersMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockPurchaseOrdersParam;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockPurchaseOrdersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* <p>
    * 采购订单 服务实现类
    * </p>
*
* @author cyt
* @since 2022-06-30
*/
@Service
public class StockPurchaseOrdersServiceImpl extends ServiceImpl<StockPurchaseOrdersMapper, StockPurchaseOrders> implements IStockPurchaseOrdersService {

    @Resource
    private StockPurchaseOrdersMapper mapper;

    @Override
    @DataSource(name = "stocking")
    public void exportExcel(HttpServletResponse response, StockPurchaseOrdersParam param) {
        LambdaQueryWrapper<StockPurchaseOrders> queryWrapper = new LambdaQueryWrapper<>();
        //queryWrapper.eq(StockPurchaseOrders::getBillType, param.getBillType());

        //采购订单状态:值域{"0:待审核"/"1:不备货"/"2:待计划部审批"/"3:计划未通过"/"4:待PMC审批"/"5:PMC未通过"/"6:已通过"}默认值：待审核
        if(!ObjectUtil.isNull(param.getOrderStatus()))
            queryWrapper.eq(StockPurchaseOrders::getOrderStatus, param.getOrderStatus());

        //备货类型:正常备货：0，特殊紧急备货：1
        if(!ObjectUtil.isNull(param.getBillType()))
            queryWrapper.eq(StockPurchaseOrders::getBillType, param.getBillType());

        //物料编码
        if(!ObjectUtil.isNull(param.getMaterialCode()))
            queryWrapper.eq(StockPurchaseOrders::getMaterialCode, param.getMaterialCode());

        List<StockPurchaseOrders> results = this.baseMapper.selectList(queryWrapper);
        try {
            ExcelUtils.exportExcel(results, "PMC审批导出数据", "sheet1", StockPurchaseOrders.class, "PMC审批导出数据New", response);
        } catch (IOException ex) {
            log.error("PMC审批导出数据异常", ex);
        }
    }
}
