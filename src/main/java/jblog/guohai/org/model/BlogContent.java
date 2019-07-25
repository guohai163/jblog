package jblog.guohai.org.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * BLOG实体
 */
public class BlogContent {

    /**
     * 编号
     */
    @Getter
    @Setter
    private int code;

    /**
     * 中文标题
     */
    @Getter
    @Setter
    private String title;

    /**
     * 内容
     */
    @Getter
    @Setter
    private String intro;

    /**
     * 年
     */
    @Getter
    @Setter
    private int year;

    /**
     * 月
     */
    @Getter
    @Setter
    private String month;

    /**
     * int 月
     */
    @Getter
    @Setter
    private int iMonth;

    @Getter
    @Setter
    private String sDate;

    @Getter
    @Setter
    private int day;

    @Getter
    @Setter
    private Date date;

    @Getter
    @Setter
    private String term;

    /**
     * 短标题，URL使用
     */
    @Getter
    @Setter
    private String smallTitle;
}
