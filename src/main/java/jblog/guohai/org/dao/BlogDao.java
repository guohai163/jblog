package jblog.guohai.org.dao;

import jblog.guohai.org.model.BlogContent;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * BLOG数据操作类
 */
public interface BlogDao {

    @Select("SELECT * FROM  jblog_posts WHERE post_code=#{postCode}")
    BlogContent getContentById(@Param("postCode") Integer postCode);

    /**
     * 活的指定日期指定标题的BLOG
     *
     * @param sDate      日期
     * @param smallTitle 标题
     * @return BLOG实体
     */
    @Select("SELECT *" +
            "FROM jblog_posts " +
            "WHERE post_small_title = #{smallTitle} " +
            "AND date_format(post_date,'%Y-%c-%e') = #{sDate}")
    BlogContent getContentByYMDTitle(@Param("sDate") String sDate, @Param("smallTitle") String smallTitle);

    /**
     * 活的下一条BLOG
     *
     * @param code 当前BLOG编号
     * @return 返回结果对象
     */
    @Select("SELECT *" +
            ",date_format(post_date,'%Y') as post_year" +
            ",date_format(post_date,'%c') as post_month" +
            ",date_format(post_date,'%e') as post_day" +
            " FROM jblog_posts WHERE post_code > #{postCode} AND" +
            "  post_status='publish'  ORDER BY post_date limit 0,1")
    BlogContent getNextBlog(@Param("postCode") Integer code);

    /**
     * 活的上一条BLOG
     *
     * @param code 当前BLOG编号
     * @return 返回结果对象
     */
    @Select("SELECT *" +
            ",date_format(post_date,'%Y') as post_year" +
            ",date_format(post_date,'%c') as post_month" +
            ",date_format(post_date,'%e') as post_day" +
            " FROM jblog_posts WHERE post_code < #{postCode} AND" +
            "  post_status='publish'  ORDER BY post_date DESC limit 0,1")
    BlogContent getLastBlog(@Param("postCode") Integer code);


    /**
     * 获得指定页面
     *
     * @param pageStart 起始记录条数
     * @param pageSize  页大小
     * @return 返回结果数据
     */
    @Select("SELECT *" +
            ",date_format(post_date,'%Y') as post_year" +
            ",date_format(post_date,'%c') as post_month" +
            ",date_format(post_date,'%e') as post_day" +
            " FROM jblog_posts WHERE  post_status='publish' " +
            "order by  post_date desc , post_code desc limit #{pageStart},#{pageSize}")
    List<BlogContent> getByPage(@Param("pageStart") Integer pageStart, @Param("pageSize") Integer pageSize);

    /**
     * 返回总数量
     *
     * @return
     */
    @Select("SELECT count(*) FROM jblog_posts WHERE  post_status='publish'")
    Integer getPostCount();

    /**
     * 获得所有类型的总数量
     * @return
     */
    @Select("SELECT count(*) FROM jblog_posts")
    Integer getBackstagePostCount();

    /**
     * 获得不区分类型的列表
     * @param pageStart
     * @param pageSize
     * @return
     */
    @Select("SELECT *" +
            "FROM `jblog_posts` ORDER BY post_code DESC limit #{pageStart},#{pageSize};")
    List<BlogContent> getBackstageList(@Param("pageStart") Integer pageStart, @Param("pageSize") Integer pageSize);

    /**
     * 增加新的BLOG
     *
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
    Boolean addPostBlog(@Param("blog") BlogContent blog);

    /**
     * 删除一篇BLOG
     * @param postCode
     * @return
     */
    @Delete("DELETE FROM `jblog_posts` WHERE post_code=#{postCode}")
    Boolean delPostBlog(@Param("postCode") Integer postCode);

    /**
     * 编辑一篇BLOG
     * @param blog
     * @return
     */
    @Update("UPDATE `jblog_posts` SET post_content=#{blog.postContent},post_title=#{blog.postTitle}," +
            "post_small_title=#{blog.postSmallTitle},post_date=#{blog.postDate} WHERE post_code=#{blog.postCode}")
    Boolean updatePostBlog(@Param("blog") BlogContent blog);
}
