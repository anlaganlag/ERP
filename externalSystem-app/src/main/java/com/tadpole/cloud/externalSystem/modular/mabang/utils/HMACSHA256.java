package com.tadpole.cloud.externalSystem.modular.mabang.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 生成 HMACSHA256
 * @return 加密结果
 * @throws Exception
 */
public class HMACSHA256 {

    public static String encrypt (String data, String key) throws Exception {

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

        sha256_HMAC.init(secret_key);

        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();

        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toLowerCase();
    }

    public static String encrypt2 (String data, String secretKey) throws Exception {
        byte[] hmacSha256 = null;
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secretKeySpec);
        hmacSha256 = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        return  DatatypeConverter.printHexBinary(hmacSha256).toLowerCase();
    }

    public static String hmacSha256(String data, String key) {
        byte[] array = null;
        try {
            array = hmacSha256(data, key.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toLowerCase();
    }

    public static byte[] hmacSha256(String data, byte[] key) {
        String algorithm = "HmacSHA256";
        Mac sha256_HMAC;
        byte[] array = null;
        try {
            sha256_HMAC = Mac.getInstance(algorithm);
            sha256_HMAC.init(new SecretKeySpec(key, algorithm));
            array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return array;
    }







}
