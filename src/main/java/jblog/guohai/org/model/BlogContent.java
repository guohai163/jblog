package jblog.guohai.org.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 */
public class BlogContent {

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String intro;

    @Getter
    @Setter
    private int year;

    @Getter
    @Setter
    private String month;

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

    @Getter
    @Setter
    private String smallTitle;
}
