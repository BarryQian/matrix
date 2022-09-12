package com.qg.matrix.core.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MatrixJson implements Serializable {

    /**
     * 垂直业务身份
     */
    private String identity;


    /**
     * 垂直、水平 优先级
     */
    private List<MatrixExt> matrixExtList;
}
