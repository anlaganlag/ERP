package com.tadpole.cloud.externalSystem.modular.ebms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.model.web.response.SuccessResponseData;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tadpole.cloud.externalSystem.api.ebms.model.resp.EbmsUserInfo;
import com.tadpole.cloud.externalSystem.config.EbmsInterfaceConfig;
import com.tadpole.cloud.externalSystem.modular.ebms.constants.ProductConstants;
import com.tadpole.cloud.externalSystem.modular.ebms.service.IEbmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class EbmsServiceImpl implements IEbmsService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EbmsInterfaceConfig interfaceConfig;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public ResponseData queryMaterialCategory() {
        String ebmsUrl = interfaceConfig.getServer() + interfaceConfig.getCategory();
        return this.requestEbmsPost(ebmsUrl, null);
    }

    @Override
    public ResponseData queryMaterialOperationCategory(String categoryName) {

        String ebmsUrl = interfaceConfig.getServer() + interfaceConfig.getOperationCategory();

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("categoryName", categoryName);

        return this.requestEbmsPost(ebmsUrl, map);

    }

    @Override
    public ResponseData getDeptList() {

        String ebmsUrl = interfaceConfig.getServer() + interfaceConfig.getGetDeptList();
        return this.requestEbmsPost(ebmsUrl, null);
    }

    @Override
    public ResponseData getSaleBrand() {

        String ebmsUrl = interfaceConfig.getServer() + interfaceConfig.getSaleBrand();
        return this.requestEbmsGet(ebmsUrl, null);
    }

    @Override
    public ResponseData getUserInfo(String perName, String sysComDeptCode) {
        String ebmsUrl = interfaceConfig.getServer() + interfaceConfig.getUserInfo();
        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put("perName", perName);
        parmMap.put("sysComDeptCode", sysComDeptCode);
        return this.requestEbmsGet(ebmsUrl, parmMap);
    }


    @Override
    public EbmsUserInfo getUserInfoByAccount(String account) {

        if (StringUtil.isEmpty(account)) {
            return null;
        }
        EbmsUserInfo userInfo = null;
        //缓存redis读取
        Object user = redisTemplate.opsForValue().get(ProductConstants.EBMS_USER_INFO_PREFIX + account);
        if (ObjectUtil.isNotNull(user)) {
            userInfo= JSONUtil.toBean(JSONUtil.toJsonStr(user),EbmsUserInfo.class);
            return userInfo;
        }

        String ebmsUrl = interfaceConfig.getServer() + interfaceConfig.getUserInfo();
        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put("perName", account);

        ebmsUrl = ebmsUrl + "?perName={perName}";

        JSONObject res = null;
        try {
            res = restTemplate.getForObject(ebmsUrl, JSONObject.class, parmMap);
            if ("0".equals(String.valueOf(res.get("code")))) {//code数字转为字符串 匹配通用
                Object data = res.get("data");
                if (ObjectUtil.isNotEmpty(data)) {
                    userInfo = JSONUtil.parseArray(res.get("data")).get(0, EbmsUserInfo.class);
                }
            }else {
                log.error(">>>>查询EBMS人员[" + account + "]信息失败-->" + JSONUtil.toJsonStr(res));
            }

        } catch (Exception e) {
            String errorStr = JSONUtil.toJsonStr(e);
            log.error(">>>>查询EBMS人员[" + account + "]信息异常-->" + errorStr);
            return null;
        }

        //缓存redis
        if (ObjectUtil.isNotNull(userInfo)) {
            redisTemplate.boundValueOps(ProductConstants.EBMS_USER_INFO_PREFIX + account)
                    .set(userInfo, Duration.ofSeconds(ProductConstants.EBMS_USER_INFO_EXPIRE_SECONDS));
        }

        return userInfo;
    }


    @Override
    public ResponseData getMateriel(String[] matCodes) {
        String ebmsUrl = interfaceConfig.getServer() + interfaceConfig.getMateriel();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity requestEntity = new HttpEntity<>(matCodes, headers);//params 请求体内容, headers请求头内容
            Object result = restTemplate.postForObject(ebmsUrl, requestEntity, Object.class);
            return ResponseData.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error(JSONUtil.toJsonStr(e));
        }
    }

    @Override
    public ResponseData queryMaterialCategoryTree() {
        String ebmsUrl = interfaceConfig.getServer() + interfaceConfig.getCategoryTree();
        return this.requestEbmsPost(ebmsUrl, null);
    }

    @Override
    public ResponseData queryComMatBrand() {
        String ebmsUrl = interfaceConfig.getServer() + interfaceConfig.getComMatBrandClass();
        return this.requestEbmsPost(ebmsUrl, null);
    }

    @Override
    public ResponseData queryComMatModel() {
        String ebmsUrl = interfaceConfig.getServer() + interfaceConfig.getComMatModelClass();
        return this.requestEbmsPost(ebmsUrl, null);
    }

    @Override
    public ResponseData queryComMatColor() {
        String ebmsUrl = interfaceConfig.getServer() + interfaceConfig.getComMatColorClass();
        return this.requestEbmsPost(ebmsUrl, null);
    }

    @Override
    public ResponseData queryComMatSize() {
        String ebmsUrl = interfaceConfig.getServer() + interfaceConfig.getComMatSizeClass();
        return this.requestEbmsPost(ebmsUrl, null);
    }

    @Override
    public ResponseData queryComMatPackQty() {
        String ebmsUrl = interfaceConfig.getServer() + interfaceConfig.getComMatPackQtyClass();
        return this.requestEbmsPost(ebmsUrl, null);
    }

    @Override
    public ResponseData queryComMatCompatibleModel() {
        String ebmsUrl = interfaceConfig.getServer() + interfaceConfig.getComMatCompatibleModelClass();
        return this.requestEbmsPost(ebmsUrl, null);
    }

    @Override
    public ResponseData queryComMatFestLabel() {
        String ebmsUrl = interfaceConfig.getServer() + interfaceConfig.getComMatFestLabelClass();
        return this.requestEbmsPost(ebmsUrl, null);
    }

    @Override
    public ResponseData queryComMaterielTree(String type,String value) {
        String ebmsUrl = interfaceConfig.getServer() + interfaceConfig.getComMaterielTree();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode params = objectMapper.createObjectNode();
            params.put("type",type);
            params.put("value",value);
            params.put("isAuth",false);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity requestEntity = new HttpEntity<>(params.toString(), headers);//params 请求体内容, headers请求头内容
            Object result = restTemplate.postForObject(ebmsUrl, requestEntity, Object.class);
            return ResponseData.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error(JSONUtil.toJsonStr(e));
        }
    }


    /**
     * 统一请求ebms接口
     *
     * @param url 接口地址
     * @param map 请求参数
     * @return
     */
    private ResponseData requestEbmsPost(String url, MultiValueMap<String, String> map) {
        if (ObjectUtil.isNull(map)) {
            map = new LinkedMultiValueMap<String, String>();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        Object body = null;

        try {
            body = restTemplate.postForEntity(url, entity, Object.class).getBody();
        } catch (Exception e) {
            String errorStr = JSONUtil.toJsonStr(e);
            log.error(errorStr);
            return ResponseData.error(errorStr);
        }
        return new SuccessResponseData(body);
    }

    /**
     * 统一请求ebms接口
     *
     * @param url 接口地址
     * @param map 请求参数
     * @return
     */
    private ResponseData requestEbmsGet(String url, Map<String, Object> map) {

        StringBuffer tempStr = new StringBuffer();
        Object res = null;
        try {
            if (ObjectUtil.isNotEmpty(map)) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    tempStr.append(key + "={" + key + "}&");
                }
                url = url + "?" + tempStr.toString();
                url = url.substring(0, url.length() - 1);
                res = restTemplate.getForObject(url, Object.class, map);
            } else {
                res = restTemplate.getForObject(url, Object.class);
            }

        } catch (Exception e) {
            String errorStr = JSONUtil.toJsonStr(e);
            log.error(errorStr);
            return ResponseData.error(errorStr);
        }
        return new SuccessResponseData(res);
    }


}
