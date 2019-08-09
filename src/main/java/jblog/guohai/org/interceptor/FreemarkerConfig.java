package jblog.guohai.org.interceptor;

import freemarker.template.TemplateModelException;
import jblog.guohai.org.model.ClassType;
import jblog.guohai.org.service.BlogService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 模板引擎配置类
 */
@Configuration
@ConfigurationProperties
public class FreemarkerConfig {

    @Autowired
    private freemarker.template.Configuration configuration;

    /**
     * BLOG名称
     */
    @Setter
    @Getter
    @Value("${my-data.blog-name}")
    private String blogName;

    /**
     * 作者名
     */
    @Setter
    @Getter
    @Value("${my-data.blog-author}")
    private String blogAuthor;

    /**
     * twttier账号
     */
    @Setter
    @Getter
    @Value("${my-data.blog-twitter}")
    private String blogTwitter;

    /**
     * 博客二维码
     */
    @Setter
    @Getter
    @Value("${my-data.blog-qrcode}")
    private String blogQRCode;

    @PostConstruct
    public void setSharedVariable() throws TemplateModelException {
        configuration.setSharedVariable("blog_name", blogName);
        configuration.setSharedVariable("blog_author", blogAuthor);
        configuration.setSharedVariable("blog_twitter", blogTwitter);
        configuration.setSharedVariable("blog_qrcode", blogQRCode);
    }
}
