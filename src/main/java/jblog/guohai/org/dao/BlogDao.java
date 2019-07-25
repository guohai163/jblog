package jblog.guohai.org.dao;

import jblog.guohai.org.model.BlogContent;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

public interface BlogDao {

    @Select("SELECT * FROM  gh_posts WHERE code=#{code}")
    @Results(id = "content", value = {
            @Result(property = "code", column = "code"),
            @Result(property="title", column="post_title"),
            @Result(property="intro", column="post_content"),
            @Result(property="year", column="date_year"),
            @Result(property="month", column="date_month"),
            @Result(property="day", column="date_day"),
            @Result(property="date", column="post_date"),
            @Result(property="smallTitle", column="post_small_name"),
            @Result(property="iMonth", column="date_imonth")
    })
    public BlogContent getContentById(Integer code);

    @Select("SELECT *,date_format(post_date,'%Y-%c-%e') " +
            "FROM blog.gh_posts " +
            "WHERE post_small_name = #{smallTitle} " +
            "AND date_format(post_date,'%Y-%c-%e') = #{sDate}")
    @ResultMap("content")
    public BlogContent getContentByYMDTitle(String sDate, String smallTitle);
}
