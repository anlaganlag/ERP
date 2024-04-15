package com.tadpole.cloud.externalSystem.modular.k3.controller;

import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.google.api.client.util.Base64;
import com.tadpole.cloud.externalSystem.config.K3CloudWebapiConfig;
import com.tadpole.cloud.externalSystem.modular.k3.mode.LoginInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

/**
 * @author: ty
 * @description: 领星基础数据Controller类
 * @date: 2022/4/22
 */
@RestController
@ApiResource(name = "erp单点登录", path = "/k3cloud")
@Api(tags = "erp单点登录")
public class K3LoginController {

    @Autowired
    private K3CloudWebapiConfig k3CloudWebapiConfig;

    public static final String LC_ID = "2052";

    @GetResource(name = "单点登录地址", path = "/getLoginUrl",requiredPermission = false, requiredLogin = true)
    @ApiOperation("单点登录地址")
    public ResponseData getLoginUrl() throws Exception {

        LoginUser loginUser = LoginContext.me().getLoginUser();
        LoginInfo info= new LoginInfo();
        info.setDbid(k3CloudWebapiConfig.getAcctid());
        info.setUsername(loginUser.getName());
        info.setAppid(k3CloudWebapiConfig.getAppid());
        info.setLcid(LC_ID);
        info.setOrigintype("SimPas");
        info.setOtherargs("|{'permitcount':'0'}");
        info.setTimestamp(new Date().getTime()/1000 + "");

        String[] strArray ={k3CloudWebapiConfig.getAcctid(),info.getUsername(),k3CloudWebapiConfig.getAppid(),
                k3CloudWebapiConfig.getAppsec(),info.getTimestamp(),"0"};
        info.setSigneddata(getSha1(strArray));

        String urlPara = JSON.toJSONString(info);

        urlPara= Base64.encodeBase64String(urlPara.getBytes("GB2312"));
        urlPara=java.net.URLEncoder.encode(urlPara,"UTF-8");

        return ResponseData.success(k3CloudWebapiConfig.getLoginurl()+ urlPara);
    }


    //SHA-1 加密
    public static String getSha1(String[] arr) {
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        System.out.println(content.toString());
        MessageDigest md = null;
        String tmpStr = null;
        try {
            //闯将 MessageDigest对象，Message Digest 通过getInstance系列静态函数来进行实例化和初始化
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha- 加密
            String s = content.toString();
            byte[] digest = md.digest(s.getBytes("UTF-8"));
            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            tmpStr = hexstr.toString();

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        content = null;

        return tmpStr;
    }


}
