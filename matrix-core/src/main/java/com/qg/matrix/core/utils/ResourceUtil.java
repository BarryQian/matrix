package com.qg.matrix.core.utils;

import com.alibaba.fastjson.JSON;
import com.qg.matrix.core.model.MatrixJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/****
 * 读取三方包里面的matrix-ext.json配置文件
 */
@Slf4j
public class ResourceUtil {
    public static  <T> T readJsonFile(String fileName, Class<T> clazz) {
        ClassPathResource classPathResource = new ClassPathResource("matrix-ext.json");
        try {
            InputStream inputStream = classPathResource.getInputStream();
        } catch (IOException e) {
            log.error("加载matrix-ext.json文件失败",e);
            throw new RuntimeException("加载matrix-json文件失败");
        }
        return null;
    }

    public static MatrixJson readMatrixExtJson() {
        ClassPathResource classPathResource = new ClassPathResource("matrix-ext.json");
        try {
            InputStream inputStream = classPathResource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line = null ;
            while ( (line = reader.readLine()) != null) {
                builder.append(line);
            }
            String matrixJsonStr = builder.toString();
            MatrixJson matrixJson = JSON.parseObject(matrixJsonStr, MatrixJson.class);
            return matrixJson;
        } catch (IOException e) {
            log.error("加载matrix-ext.json文件失败",e);
            //throw new RuntimeException("加载matrix-json文件失败");
        }
        return new MatrixJson();
    }
}
