package com.qg.matrix.core.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MatrixExt implements Serializable {
    /**
     * 扩展点编码
     */
    private String extCode;

    /**
     * X/Y
     */
    private List<String> typeList;
}
