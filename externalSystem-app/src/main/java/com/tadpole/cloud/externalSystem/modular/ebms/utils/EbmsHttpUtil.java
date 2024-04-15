package com.tadpole.cloud.externalSystem.modular.ebms.utils;

import cn.hutool.http.HttpUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: ty
 * @description: EBMS系统http工具类
 * @date: 2022/7/22
 */
@Slf4j
@Component
public class EbmsHttpUtil {

    @Value("${ebms.server}")
    private String ebmsServer;

    @Value("${ebms.timeout}")
    private Integer ebmsTimeout;

    /**
     * POST请求EBMS接口
     * @param url 接口地址
     * @param body 请求参数
     * @return
     */
    public ResponseData requestEbmsPost(String url, String body) {
        try {
            log.info("EBMS系统请求信息，请求地址[{}]，请求方式[{}]，请求参数[{}]", ebmsServer + url, "POST", body);
            long start = System.currentTimeMillis();
            String respSting = HttpUtil.post(ebmsServer + url, body, ebmsTimeout);
            log.info("EBMS系统响应信息，响应参数[{}]，耗时[{}]", respSting, (System.currentTimeMillis() - start) + "ms");
            BaseEBMSResponse ebmsResponse = JSON.parseObject(respSting, BaseEBMSResponse.class);
            if(BaseEBMSResponse.DEFAULT_SUCCESS_CODE.equals(ebmsResponse.getCode())){
                return ResponseData.success(ebmsResponse.getData());
            }
            return ResponseData.error(ebmsResponse.getMsg());
        } catch (Exception e) {
            log.error("请求EBMS接口异常，异常信息[{}]", e.getMessage());
            return ResponseData.error("请求EBMS接口异常");
        }
    }

    /**
     * GET请求EBMS接口
     * @param url 接口地址
     * @return
     */
    public ResponseData requestEbmsGet(String url) {
        try {
            log.info("EBMS系统请求信息，请求地址[{}]，请求方式[{}]", ebmsServer + url, "GET");
            long start = System.currentTimeMillis();
            String respSting = HttpUtil.get(ebmsServer + url, ebmsTimeout);
            log.info("EBMS系统响应信息，响应参数[{}]，耗时[{}]", respSting, (System.currentTimeMillis() - start) + "ms");
            BaseEBMSResponse ebmsResponse = JSON.parseObject(respSting, BaseEBMSResponse.class);
            if(BaseEBMSResponse.DEFAULT_SUCCESS_CODE.equals(ebmsResponse.getCode())){
                return ResponseData.success(ebmsResponse.getData());
            }
            return ResponseData.error(ebmsResponse.getMsg());
        } catch (Exception e) {
            log.error("请求EBMS接口异常，异常信息[{}]", e.getMessage());
            return ResponseData.error("请求EBMS接口异常");
        }
    }
}
