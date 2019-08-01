package jblog.guohai.org.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import jblog.guohai.org.model.BlogContent;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * BLOG数据操作类
 */
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
            @Result(property="imonth", column="date_imonth"),
            @Result(property = "state", column = "post_status")
    })
    public BlogContent getContentById(Integer code);

    /**
     * 活的指定日期指定标题的BLOG
     * @param sDate 日期
     * @param smallTitle 标题
     * @return BLOG实体
     */
    @Select("SELECT *,date_format(post_date,'%Y-%c-%e') " +
            "FROM blog.gh_posts " +
            "WHERE post_small_name = #{smallTitle} " +
            "AND date_format(post_date,'%Y-%c-%e') = #{sDate}")
    @ResultMap("content")
    public BlogContent getContentByYMDTitle(String sDate, String smallTitle);

    /**
     * 活的下一条BLOG
     * @param code 当前BLOG编号
     * @return 返回结果对象
     */
    @Select("SELECT code,post_date,post_content,post_title" +
            ",date_format(post_date,'%Y') as date_year" +
            " ,date_format(post_date,'%b') as date_month" +
            ",date_format(post_date,'%e') as date_day" +
            ",date_format(post_date,'%c') as date_imonth" +
            ",post_small_name FROM gh_posts WHERE code > #{code} AND" +
            "  post_status='publish'  ORDER BY code limit 0,1")
    @ResultMap("content")
    public BlogContent getNextBlog(Integer code);

    /**
     * 活的上一条BLOG
     * @param code 当前BLOG编号
     * @return 返回结果对象
     */
    @Select("SELECT code,post_date,post_content,post_title" +
            ",date_format(post_date,'%Y') as date_year" +
            " ,date_format(post_date,'%b') as date_month" +
            ",date_format(post_date,'%e') as date_day" +
            ",date_format(post_date,'%c') as date_imonth" +
            ",post_small_name FROM gh_posts WHERE code < #{code} AND" +
            "  post_status='publish'  ORDER BY code DESC limit 0,1")
    @ResultMap("content")
    public BlogContent getLastBlog(Integer code);

    /**
     * 获取首页数据
     * @return
     */
    @Select("SELECT code,post_date,post_content,post_title" +
            ",date_format(post_date,'%Y') as date_year" +
            ",date_format(post_date,'%b') as date_month" +
            ",date_format(post_date,'%e') as date_day" +
            ",date_format(post_date,'%c') as date_imonth" +
            ",post_small_name " +
            "FROM gh_posts " +
            "WHERE post_status='publish' order by post_date desc " +
            "limit 0,10")
    @ResultMap("content")
    public List<BlogContent> getHomeList();

    /**
     * 获得指定页面
     * @param pageStart 起始记录条数
     * @param pageSize 页大小
     * @return 返回结果数据
     */
    @Select("SELECT code,post_date,post_content,post_title" +
            ",date_format(post_date,'%Y') as date_year" +
            ",date_format(post_date,'%b') as date_month" +
            ",date_format(post_date,'%e') as date_day" +
            ",date_format(post_date,'%c') as date_imonth" +
            ",post_small_name FROM gh_posts WHERE  post_status='publish' " +
            "order by  post_date desc , code desc limit #{pageStart},#{pageSize}")
    @ResultMap("content")
    public List<BlogContent> getByPage(Integer pageStart, Integer pageSize);

    /**
     * 返回总数量
     * @return
     */
    @Select("SELECT count(*) FROM gh_posts WHERE  post_status='publish'")
    public Integer getPostCount();

    @Select("SELECT `code`,`post_date`,`post_title`,`post_content`,`post_status`," +
            "`post_small_name`" +
            "FROM `gh_posts` ORDER BY code DESC limit #{pageStart},#{pageSize};")
    @ResultMap("content")
    public List<BlogContent> getBackstageList(Integer pageStart, Integer pageSize);

    /**
     * 增加新的BLOG
     * @param blog
     * @return
     */
    @Insert("INSERT INTO `gh_posts`\n" +
            "(`post_date`,\n" +
            "`post_title`,\n" +
            "`post_content`,\n" +
            "`post_status`,\n" +
            "`post_small_name`)\n" +
            "VALUES\n" +
            "(#{blog.date},\n" +
            "#{blog.title},\n" +
            "#{blog.intro},\n" +
            "'publish',\n" +
            "#{blog.smallTitle});\n")
    @Options(useGeneratedKeys = true, keyProperty = "blog.code")
    public Boolean addPostBlog(@Param("blog") BlogContent blog);
}
