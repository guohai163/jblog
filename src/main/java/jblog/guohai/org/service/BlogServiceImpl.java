package jblog.guohai.org.service;

import jblog.guohai.org.dao.BlogDao;
import jblog.guohai.org.model.BlogContent;
import jblog.guohai.org.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    /**
     * 获得指定ID的
     * @param code
     * @return
     */
    @Override
    public BlogContent getByID(Integer code) {
        return blogDao.getContentById(code);
    }

    /**
     * 活的指定日期的
     * @param sDate
     * @param smallTitle
     * @return
     */
    @Override
    public  BlogContent getByYMDTitle(String sDate, String smallTitle) {
        return blogDao.getContentByYMDTitle(sDate,smallTitle);
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
     * 获得首页数据
     *
     * @return
     */
    @Override
    public List<BlogContent> getHomeList() {
        return blogDao.getHomeList();
    }

    /**
     * 获得指定页号数据
     *
     * @param pageNumber
     * @return
     */
    @Override
    public List<BlogContent> getByPage(Integer pageNumber) {
        return blogDao.getByPage((pageNumber-1)*10, 10);
    }

    /**
     * 返回 总数量
     *
     * @return
     */
    @Override
    public Integer getMaxPageNum() {
        return blogDao.getPostCount()/10;
    }

    /**
     * 增加一篇BLOG
     *
     * @param blog BLOG实体
     * @return 返回结果情况
     */
    @Override
    public Result<String> addPostBlog(BlogContent blog) {
        Result<String> result = new Result<String>();
        blogDao.addPostBlog(blog);
        if(blog.getCode()>0) {
            result.setState(true);
            result.setData("Success:" + blog.getCode());
        }
        else {
            result.setState(false);
            result.setData("Error");
        }
        return result;
    }
}
