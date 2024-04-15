package com.tadpole.cloud.product.modular.consumer;

import com.tadpole.cloud.externalSystem.api.k3.ViewBusinessApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 *
 * @author fengshuonan
 * @Date 2019/9/25 18:32
 */
@FeignClient(name = "bsc-cloud-externalSystem")
public interface ExternalConsumer extends ViewBusinessApi {
}
