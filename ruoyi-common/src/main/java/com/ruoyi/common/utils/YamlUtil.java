package com.ruoyi.common.utils;

import cn.hutool.core.util.StrUtil;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * 配置处理工具类
 *
 * @author yml
 */
public class YamlUtil {

    private YamlUtil(){
        throw new IllegalStateException("Utility class");
    }

    public static Map loadYaml(String fileName){
        InputStream in = YamlUtil.class.getClassLoader().getResourceAsStream(fileName);
        return StrUtil.isNotEmpty(fileName) ? new Yaml().load(in) : null;
    }

    public static Object getProperty(Map<?, ?> map, Object qualifiedKey) {
        if (map != null && !map.isEmpty() && qualifiedKey != null) {
            String input = String.valueOf(qualifiedKey);
            if (StrUtil.isNotEmpty(input)) {
                if (input.contains(".")) {
                    int index = input.indexOf('.');
                    String left = input.substring(0, index);
                    String right = input.substring(index + 1);
                    return getProperty((Map<?, ?>) map.get(left), right);
                } else{
                    return map.getOrDefault(input, null);
                }
            }
        }
        return null;
    }

}