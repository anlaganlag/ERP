package com.tadpole.cloud.product.modular.consumer;

import com.tadpole.cloud.operationManagement.api.shopEntity.AccountMgtPersonalApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 鉴权消费者
 *
 * @author fengshuonan
 * @Date 2019-08-12 18:52
 */
@FeignClient(name = "bsc-cloud-operationManagement")
public interface ProposalServiceConsumer extends AccountMgtPersonalApi {

}
