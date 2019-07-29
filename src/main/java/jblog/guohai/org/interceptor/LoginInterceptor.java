package jblog.guohai.org.interceptor;

import jblog.guohai.org.model.UserModel;
import jblog.guohai.org.service.UserServiceImpl;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws IOException {
        String uuid = null;
        if(null == request.getCookies()) {
            response.sendRedirect("/admin/");
            return false;
        }
        for(Cookie cookie:request.getCookies()) {
            if(cookie.getName().equals("user")) {
                uuid = cookie.getValue();
                break;
            }
        }
        if(null == uuid){
            response.sendRedirect("/admin/");
            return false;
        }
        UserModel user = UserServiceImpl.getUserByUUID(uuid);
        if(null == user) {
            response.sendRedirect("/admin/");
            return false;
        }
        return true;
    }
}
