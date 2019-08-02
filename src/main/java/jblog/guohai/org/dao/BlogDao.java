package jblog.guohai.org.dao;

import jblog.guohai.org.model.BlogContent;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * BLOG数据操作类
 */
public interface BlogDao {

    @Select("SELECT * FROM  jblog_posts WHERE postCode=#{postCode}")
    public BlogContent getContentById(Integer code);

    /**
     * 活的指定日期指定标题的BLOG
     * @param sDate 日期
     * @param smallTitle 标题
     * @return BLOG实体
     */
    @Select("SELECT *" +
            "FROM jblog_posts " +
            "WHERE post_small_title = #{smallTitle} " +
            "AND date_format(post_date,'%Y-%c-%e') = #{sDate}")
    public BlogContent getContentByYMDTitle(String sDate, String smallTitle);

    /**
     * 活的下一条BLOG
     * @param code 当前BLOG编号
     * @return 返回结果对象
     */
    @Select("SELECT *" +
            ",date_format(post_date,'%Y') as post_year" +
            ",date_format(post_date,'%c') as post_month" +
            ",date_format(post_date,'%e') as post_day" +
            " FROM jblog_posts WHERE post_code > #{postCode} AND" +
            "  post_status='publish'  ORDER BY post_date limit 0,1")
    public BlogContent getNextBlog(Integer code);

    /**
     * 活的上一条BLOG
     * @param code 当前BLOG编号
     * @return 返回结果对象
     */
    @Select("SELECT *" +
            ",date_format(post_date,'%Y') as post_year" +
            ",date_format(post_date,'%c') as post_month" +
            ",date_format(post_date,'%e') as post_day" +
            " FROM jblog_posts WHERE post_code < #{postCode} AND" +
            "  post_status='publish'  ORDER BY post_date DESC limit 0,1")
    public BlogContent getLastBlog(Integer code);

    /**
     * 获取首页数据
     * @return
     */
    @Select("SELECT *" +
            ",date_format(post_date,'%Y') as post_year" +
            ",date_format(post_date,'%c') as post_month" +
            ",date_format(post_date,'%e') as post_day" +
            " FROM jblog_posts " +
            "WHERE post_status='publish' order by post_date desc " +
            "limit 0,10")
    public List<BlogContent> getHomeList();

    /**
     * 获得指定页面
     * @param pageStart 起始记录条数
     * @param pageSize 页大小
     * @return 返回结果数据
     */
    @Select("SELECT *" +
            ",date_format(post_date,'%Y') as post_year" +
            ",date_format(post_date,'%c') as post_month" +
            ",date_format(post_date,'%e') as post_day" +
            " FROM jblog_posts WHERE  post_status='publish' " +
            "order by  post_date desc , post_code desc limit #{pageStart},#{pageSize}")
    public List<BlogContent> getByPage(Integer pageStart, Integer pageSize);

    /**
     * 返回总数量
     * @return
     */
    @Select("SELECT count(*) FROM jblog_posts WHERE  post_status='publish'")
    public Integer getPostCount();

    @Select("SELECT *" +
            "FROM `jblog_posts` ORDER BY post_code DESC limit #{pageStart},#{pageSize};")
    public List<BlogContent> getBackstageList(Integer pageStart, Integer pageSize);

    /**
     * 增加新的BLOG
     * @param blog
     * @return
     */
    @Insert("INSERT INTO `jblog_posts`\n" +
            "(`post_date`,\n" +
            "`post_title`,\n" +
            "`post_content`,\n" +
            "`post_status`,\n" +
            "`post_small_title`)\n" +
            "VALUES\n" +
            "(#{blog.postDate},\n" +
            "#{blog.postTitle},\n" +
            "#{blog.postContent},\n" +
            "'publish',\n" +
            "#{blog.postSmallTitle});\n")
    @Options(useGeneratedKeys = true, keyProperty = "blog.postCode")
    public Boolean addPostBlog(@Param("blog") BlogContent blog);
}
