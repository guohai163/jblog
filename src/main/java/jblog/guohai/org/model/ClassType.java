package jblog.guohai.org.model;

import lombok.Data;

@Data
public class ClassType {
    /**
     * 分类编号
     */
    private Integer classCode;
    /**
     * 分类名称
     */
    private String className;
    /**
     * 博客数量
     */
    private Integer blogCount;
}
