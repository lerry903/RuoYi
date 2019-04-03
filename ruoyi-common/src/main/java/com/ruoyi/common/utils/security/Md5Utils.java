package com.ruoyi.common.utils.security;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Md5加密方法
 *
 * @author ruoyi
 */
@Slf4j
public class Md5Utils {

    private Md5Utils(){
        throw new IllegalStateException("Utility class");
    }

    private static byte[] md5(String s) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes(StandardCharsets.UTF_8));
            return algorithm.digest();
        } catch (Exception e) {
            log.error("MD5 Error..." , e);
        }
        return new byte[0];
    }

    private static String toHex(byte[] hash) {
        if (hash == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                builder.append("0");
            }
            builder.append(Long.toString(hash[i] & 0xff, 16));
        }
        return builder.toString();
    }

    public static String hash(String s) {
        try {
            return new String(toHex(md5(s)).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("not supported charset...{}" , e);
            return s;
        }
    }
}
