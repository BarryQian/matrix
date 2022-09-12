package com.qg.matrix.core.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public abstract class BaseResultModel implements Serializable {

    /**
     * 业务身份
     * @return
     */
    private String identity;

    /**
     * 扩展参数
     */
    private Map<String, Object> extMap;

}
