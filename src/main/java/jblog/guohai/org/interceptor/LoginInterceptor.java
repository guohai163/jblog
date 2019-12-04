package jblog.guohai.org.interceptor;

import freemarker.template.TemplateModelException;
import jblog.guohai.org.model.UserModel;
import jblog.guohai.org.service.UserService;
import jblog.guohai.org.service.UserServiceImpl;
import jblog.guohai.org.util.JsonTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class LoginInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private freemarker.template.Configuration configuration;

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws IOException, TemplateModelException {
        logger.info("登陆验证拦截开始");
        String uuid = null;
        if (null == request.getCookies()) {
            response.sendRedirect("/admin/");
            return false;
        }
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("user")) {
                uuid = cookie.getValue();
                break;
            }
        }
        if (null == uuid) {
            response.sendRedirect("/admin/");
            return false;
        }
        UserModel user = UserServiceImpl.getUserByUUID(uuid);
        logger.info("获取user："+ JsonTool.toStrFormBean(user));
        if (null == user) {
            logger.info("获取user为null,登陆验证失败");
            userService.logoutUser(uuid);
            response.sendRedirect("/admin/");
            return false;
        }
        logger.info("登陆验证成功");
        logger.info("登陆验证拦截结束");
        return true;
    }
}
