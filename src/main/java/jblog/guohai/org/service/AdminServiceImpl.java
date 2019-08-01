package jblog.guohai.org.service;

import jblog.guohai.org.dao.BlogDao;
import jblog.guohai.org.model.BlogContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    BlogDao blogDao;

    private final Integer adminPageSize = 20;
    /**
     * 获得管理后台的BLOG列表
     *
     * @param pageNumber 想获取的页号
     * @return
     */
    @Override
    public List<BlogContent> getBackstageList(Integer pageNumber) {

        return blogDao.getBackstageList((pageNumber-1)*adminPageSize, adminPageSize);
    }
}
