package com.tadpole.cloud.externalSystem.modular.mabang.task;

import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.SkuStockQtyParam;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangOrdersService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpdateSkuStockTask implements Runnable {

    private SkuStockQtyParam skuStockQtyParam;



    private static final IMabangOrdersService service = SpringContext.getBean(IMabangOrdersService.class);
    public UpdateSkuStockTask(SkuStockQtyParam skuStockQtyParam) {
        this.skuStockQtyParam = skuStockQtyParam;
    }

    @Override
    public void run() {
        try {
            long stime = System.currentTimeMillis();
//            log.info(DateUtil.now()+"SKU库存开始时间"+Thread.currentThread().getName());
            service.getMatStockQty(skuStockQtyParam);
            long etime = System.currentTimeMillis();
//            log.info(StrUtil.format("时间:{} 线程:[{}] SKU库存数量更新结束[执行时长：{} 毫秒.]",DateUtil.now(),Thread.currentThread().getName(),etime - stime));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }




}
