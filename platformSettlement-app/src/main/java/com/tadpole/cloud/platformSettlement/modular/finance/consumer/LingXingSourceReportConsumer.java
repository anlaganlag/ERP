package com.tadpole.cloud.platformSettlement.modular.finance.consumer;

import com.tadpole.cloud.externalSystem.api.lingxing.LingXingSourceReportApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: ty
 * @description: 领星亚马逊源表数据消费者
 * @date: 2023/4/17
 */
@FeignClient(name = "bsc-cloud-externalSystem")
public interface LingXingSourceReportConsumer extends LingXingSourceReportApi {

}
