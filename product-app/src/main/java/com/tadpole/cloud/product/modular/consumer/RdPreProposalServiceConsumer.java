package com.tadpole.cloud.product.modular.consumer;

import com.tadpole.cloud.externalSystem.api.oa.OaHrmApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 鉴权消费者
 *
 * @author fengshuonan
 * @Date 2019-08-12 18:52
 */
@FeignClient(name = "bsc-cloud-externalSystem")
public interface RdPreProposalServiceConsumer extends OaHrmApi {

}
