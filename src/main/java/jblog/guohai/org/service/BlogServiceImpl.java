package jblog.guohai.org.service;

import jblog.guohai.org.dao.BlogDao;
import jblog.guohai.org.model.BlogContent;
import jblog.guohai.org.model.ClassType;
import jblog.guohai.org.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    /**
     * 前台用的页大小
     */
    private final Integer pageSize = 10;

    /**
     * 获得指定ID的
     *
     * @param code
     * @return
     */
    @Override
    public BlogContent getByID(Integer code) {
        return blogDao.getContentById(code);
    }

    /**
     * 活的指定日期的
     *
     * @param sDate
     * @param smallTitle
     * @return
     */
    @Override
    public BlogContent getByYMDTitle(String sDate, String smallTitle) {
        return blogDao.getContentByYMDTitle(sDate, smallTitle);
    }

    /**
     * 活的下一条BLOG
     *
     * @param code
     * @return
     */
    @Override
    public BlogContent getNextBlog(Integer code) {
        return blogDao.getNextBlog(code);
    }

    /**
     * 获得上一条BLOG
     *
     * @param code
     * @return
     */
    @Override
    public BlogContent getLastBlog(Integer code) {
        return blogDao.getLastBlog(code);
    }


    /**
     * 获得指定页号数据
     *
     * @param pageNumber
     * @return
     */
    @Override
    public List<BlogContent> getByPage(Integer pageNumber) {
        return blogDao.getByPage((pageNumber - 1) * pageSize, pageSize);
    }

    /**
     * 返回 总数量
     *
     * @return
     */
    @Override
    public Integer getMaxPageNum() {
        int postCount = blogDao.getPostCount();
        return postCount % pageSize == 0 ? postCount / pageSize : postCount / pageSize + 1;
    }

    /**
     * 增加一篇BLOG
     *
     * @param blog BLOG实体
     * @return 返回结果情况
     */
    @Override
    public Result<String> addPostBlog(BlogContent blog) {
        Result<String> result = new Result<>();
        blogDao.addPostBlog(blog);
        if (blog.getPostCode() > 0) {
            result.setStatus(true);
            result.setData("Success:" + blog.getPostCode());
        } else {
            result.setStatus(false);
            result.setData("Error");
        }
        return result;
    }

    /**
     * 添加博客分类映射
     *
     * @param postCode  博客编号
     * @param classCode 分类编号
     * @return
     */
    public Result<String> addUpdateBlogClass(Integer postCode, Integer classCode) {
        if (blogDao.getBlogClassCount(postCode) > 0) {
            blogDao.updateBlogClassMap(postCode, classCode);
            return new Result<>(true, "设置成功");
        }

        blogDao.addBlogClassMap(postCode, classCode);
        return new Result<>(true, "设置成功");
    }

    /**
     * 获取文章分类列表
     *
     * @return
     */
    @Override
    public List<ClassType> getClassList() {
        List<ClassType> list = blogDao.getClassList();
        return list == null ? new ArrayList<>() : list;
    }

    /**
     * 获取分类列表(含文章数)
     *
     * @return
     */
    @Override
    public List<ClassType> getClassOfBlogCountList() {
        List<ClassType> list = blogDao.getClassOfBlogCountList();
        return list == null ? new ArrayList<>() : list;
    }

    /**
     * 获取
     *
     * @param classCode 分类编号
     * @param pageNum   分页页码
     * @return
     */
    @Override
    public List<BlogContent> getPageByClassCode(Integer classCode, int pageNum) {
        return blogDao.getPageByClassCode(classCode, (pageNum - 1) * pageSize, pageSize);
    }

    /**
     * 返回 总数量
     *
     * @return
     */
    @Override
    public Integer getMaxClassPageNum(Integer classCode) {
        int postCount = blogDao.getPostCountByClassCode(classCode);
        return postCount % pageSize == 0 ? postCount / pageSize : postCount / pageSize + 1;
    }

    /**
     * 编辑分类名称
     *
     * @param classCode 分类编号
     * @param className 分类名称
     * @return
     */
    @Override
    public Result<String> updateClassName(Integer classCode, String className) {
        blogDao.updateClass(classCode, className);
        return new Result<>(true, "更新成功");
    }

    /**
     * 删除分类
     *
     * @param classCode 分类编号
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> delClass(Integer classCode) {
        //删除分类
        blogDao.delClass(classCode);
        //删除关联的分类映射
        blogDao.delClassMap(classCode);
        return new Result<>(true, "删除成功");
    }

    /**
     * 添加分类
     *
     * @param className 分类名称
     * @return
     */
    @Override
    public Result<String> addClass(String className) {
        if (blogDao.getClassCountByClassName(className) > 0) {
            return new Result<>(false, "分类已存在");
        }
        ClassType classType = new ClassType();
        classType.setClassName(className);
        blogDao.addClass(classType);
        return new Result<>(true, classType.getClassCode() + "");
    }
}
