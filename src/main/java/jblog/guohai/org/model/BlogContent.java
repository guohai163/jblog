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
    private int imonth;

    @Getter
    @Setter
    private String sDate;

    @Getter
    @Setter
    private int day;

    /**
     * 完整日期
     */
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

    /**
     * 构造方法
     * @param title 长标题
     * @param intro 内容
     * @param date 日期
     * @param smallTitle 短标题
     */
    public BlogContent(String title, String intro, Date date, String smallTitle) {
        this.title = title;
        this.intro = intro;
        this.date = date;
        this.smallTitle = smallTitle;
    }


    public BlogContent() {

    }
}
