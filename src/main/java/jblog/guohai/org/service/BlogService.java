package jblog.guohai.org.service;

import jblog.guohai.org.model.BlogContent;
import jblog.guohai.org.model.ClassType;
import jblog.guohai.org.model.Result;

import java.util.List;

public interface BlogService {
    /**
     * 获得指定编号BLOG
     * @param code
     * @return
     */
    BlogContent getByID(Integer code);

    /**
     * 获得指定日期BLOG
     * @param sDate
     * @param smallTitle
     * @return
     */
    BlogContent getByYMDTitle(String sDate, String smallTitle);

    /**
     * 活的下一条BLOG
     * @param code
     * @return
     */
    BlogContent getNextBlog(Integer code);

    /**
     * 获得上一条BLOG
     * @param code
     * @return
     */
    BlogContent getLastBlog(Integer code);


    /**
     * 获得指定页号数据
     * @param pageNumber
     * @return
     */
    List<BlogContent> getByPage(Integer pageNumber);

    /**
     * 返回 总数量
     * @return
     */
    Integer getMaxPageNum();

    /**
     * 增加一篇BLOG
     * @param blog BLOG实体
     * @return 返回结果情况
     */
    Result<String> addPostBlog(BlogContent blog);

    /**
     * 添加博客分类映射
     *
     * @param postCode  博客编号
     * @param classCode 分类编号
     * @return
     */
    Result<String> addUpdateBlogClass(Integer postCode, Integer classCode);

    /**
     * 获取分类列表
     * @return
     */
    List<ClassType> getClassList();

    /**
     * 获取
     *
     * @param classCode 分类编号
     * @param pageNum 分页页码
     * @return
     */
    List<BlogContent> getPageByClassCode(Integer classCode, int pageNum);

    /**
     * 返回 总数量
     *
     * @return
     */
    Integer getMaxClassPageNum(Integer classCode);
}
