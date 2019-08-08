package jblog.guohai.org.dao;

import jblog.guohai.org.model.BlogContent;
import jblog.guohai.org.model.ClassType;
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
     * 根据分类获取 博客列表分页
     *
     * @param classCode
     * @param pageStart
     * @param pageSize
     * @return
     */
    @Select("select posts.*\n" +
            "   ,date_format(posts.post_date,'%Y') as post_year\n" +
            "   ,date_format(posts.post_date,'%c') as post_month\n" +
            "   ,date_format(posts.post_date,'%e') as post_day\n" +
            "from \n" +
            "   (select post_code from jblog_class_map where class_code=#{classCode}) as map \n" +
            "left join jblog_posts as posts \n" +
            "on map.post_code = posts.post_code\n" +
            "where posts.post_status='publish'\n" +
            "order by posts.post_date desc , posts.post_code desc limit #{pageStart},#{pageSize}")
    List<BlogContent> getPageByClassCode(@Param("classCode") Integer classCode, @Param("pageStart") Integer pageStart, @Param("pageSize") Integer pageSize);

    /**
     * 根据分类获取 返回总数量
     *
     * @return
     */
    @Select("select count(*)" +
            "from \n" +
            "   (select post_code from jblog_class_map where class_code=#{classCode}) as map \n" +
            "left join jblog_posts as posts \n" +
            "on map.post_code = posts.post_code\n" +
            "where posts.post_status='publish'")
    Integer getPostCountByClassCode(@Param("classCode") int classCode);

    /**
     * 返回总数量
     *
     * @return
     */
    @Select("SELECT count(*) FROM jblog_posts WHERE  post_status='publish'")
    Integer getPostCount();

    /**
     * 获得所有类型的总数量
     *
     * @return
     */
    @Select("SELECT count(*) FROM jblog_posts")
    Integer getBackstagePostCount();

    /**
     * 获得不区分类型的列表
     *
     * @param pageStart
     * @param pageSize
     * @return
     */
    @Select("SELECT posts.*,IFNULL(map.class_code,0) as class_code " +
            "FROM `jblog_posts` as posts " +
            "left join jblog_class_map as map on posts.post_code = map.post_code " +
            "ORDER BY post_code DESC limit #{pageStart},#{pageSize};")
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
     *
     * @param postCode
     * @return
     */
    @Delete("DELETE FROM `jblog_posts` WHERE post_code=#{postCode}")
    Boolean delPostBlog(@Param("postCode") Integer postCode);

    /**
     * 编辑一篇BLOG
     *
     * @param blog
     * @return
     */
    @Update("UPDATE `jblog_posts` SET post_content=#{blog.postContent},post_title=#{blog.postTitle}," +
            "post_small_title=#{blog.postSmallTitle},post_date=#{blog.postDate} WHERE post_code=#{blog.postCode}")
    Boolean updatePostBlog(@Param("blog") BlogContent blog);

    /**
     * 获取分类列表
     *
     * @return
     */
    @Select("select class_code,class_name from jblog_class")
    List<ClassType> getClassList();

    /**
     * 获取分类列表(含文章数)
     *
     * @return
     */
    @Select("select blog_map.class_code,class_name,blog_count from \n" +
            "\t(select class_code,count(post_code) as blog_count from jblog_class_map group by class_code)as blog_map\n" +
            "left join jblog_class on jblog_class.class_code = blog_map.class_code")
    List<ClassType> getClassOfBlogCountList();

    /**
     * 添加博客分类
     *
     * @param className 分类名称
     * @return
     */
    @Insert("insert into jblog_class(class_name) values(#{className})")
    @Options(useGeneratedKeys = true, keyProperty = "className.classCode", keyColumn = "class_code")
    Boolean addClass(@Param("className") ClassType className);

    /**
     * 查看分类映射是否提交
     *
     * @param postCode 博客编号
     * @return
     */
    @Select("select count(1) from jblog_class_map where post_code=#{postCode}")
    int getBlogClassCount(@Param("postCode") Integer postCode);

    /**
     * 添加博客分类映射
     *
     * @param postCode  博客编号
     * @param classCode 分类编号
     * @return
     */
    @Insert("insert into jblog_class_map(post_code,class_code)values(#{postCode},#{classCode})")
    Boolean addBlogClassMap(@Param("postCode") Integer postCode, @Param("classCode") Integer classCode);

    /**
     * 更新博客分类
     *
     * @param postCode  博客编号
     * @param classCode 分类编号
     * @return
     */
    @Update("update jblog_class_map set class_code=#{classCode} where post_code=#{postCode}")
    Boolean updateBlogClassMap(@Param("postCode") Integer postCode, @Param("classCode") Integer classCode);

    /**
     * 删除博客分类
     *
     * @param classCode 分类编号
     * @return
     */
    @Delete("delete from jblog_class where class_code=#{classCode}")
    Boolean delClass(@Param("classCode") int classCode);

    /**
     * 删除所有与指定分类相关的博客
     *
     * @param classCode 分类编号
     * @return
     */
    @Delete("delete from jblog_class_map where class_code=#{classCode}")
    Boolean delClassMap(@Param("classCode") int classCode);

    /**
     * 删除置顶的博客分类
     *
     * @param classCode 分类编号
     * @param postCode  博客编号
     * @return
     */
    @Delete("delete from jblog_class_map where class_code=#{classCode} and post_code=#{postCode}")
    Boolean delPostClassMap(@Param("classCode") int classCode, @Param("postCode") int postCode);
}
