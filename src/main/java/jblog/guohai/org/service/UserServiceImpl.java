package jblog.guohai.org.service;

import jblog.guohai.org.dao.UserDao;
import jblog.guohai.org.model.Result;
import jblog.guohai.org.model.UserModel;
import jblog.guohai.org.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    /**
     * UUID缓存
     */
    private static HashMap<String, UserModel> uuidMap = new HashMap<>();

    /**
     * 用户缓存
     */
    private static HashMap<String, String> userMap = new HashMap<>();

    /**
     * 检查用户名与密码是否正常
     *
     * @param user 用户名
     * @param pass 密码
     * @return 结果
     */
    @Override
    public Result<String> checkUserPass(String user, String pass) {

        Result<String> result = new Result<>();
        result.setStatus(false);
        result.setData("未知错误");
        //获取用户实体
        UserModel userModel = userDao.getUserByName(user);
        if (null == userModel) {
            result.setData("请确认用户名密码正确");
            return result;
        }
        //检查密码
        if (!MD5.GetMD5Code(MD5.GetMD5Code(pass) + userModel.getUserKey()).equals(userModel.getUserPass())) {
            result.setData("请确认用户名密码正确");
            return result;
        }
        result.setStatus(true);
        result.setData(saveUserData(userModel));
        return result;
    }

    /**
     * 存储用户数据
     *
     * @param user
     * @return
     */
    private String saveUserData(UserModel user) {
        String uuid = String.valueOf(UUID.randomUUID());
        if (null != userMap.get(user.getUserName())) {
            //清理旧数据
            uuidMap.remove(userMap.get(user.getUserName()));
            userMap.remove(user.getUserName());
        }
        uuidMap.put(uuid, user);
        userMap.put(user.getUserName(), uuid);
        return uuid;
    }

    /**
     * 通过uuid获取用户
     *
     * @param uuid
     * @return
     */
    public static UserModel getUserByUUID(String uuid) {
        return uuidMap.get(uuid);
    }

    /**
     * 通过COOKIE获得用户实体
     * @param request
     * @return
     */
    public static UserModel getUserByCookie(HttpServletRequest request) {
        String uuid = null;
        if (null == request.getCookies()) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("user")) {
                uuid = cookie.getValue();
                break;
            }
        }
        if (null == uuid) {
            return null;
        }
        UserModel user = UserServiceImpl.getUserByUUID(uuid);
        if (null == user) {
            return null;
        }
        return  user;
    }
}
