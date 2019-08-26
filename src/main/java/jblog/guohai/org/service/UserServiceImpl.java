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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public Result<UserModel> checkUserPass(String user, String pass) {

        Result<UserModel> result = new Result<>();
        result.setStatus(false);
        //获取用户实体
        UserModel userModel = userDao.getUserByName(user);
        if (null == userModel) {
            result.setData(null);
            return result;
        }
        //检查密码
        if (!MD5.GetMD5Code(MD5.GetMD5Code(pass) + userModel.getUserKey()).equals(userModel.getUserPass())) {
            result.setData(null);
            return result;
        }
        userModel.setUserUUID(saveUserData(userModel));
        result.setStatus(true);
        result.setData(userModel);
        return result;
    }

    /**
     * 通过COOKIES注销用户
     *
     * @param userCook
     * @return
     */
    @Override
    public Result<String> logoutUser(String userCook) {
        UserModel user = uuidMap.get(userCook);
        if (null != user) {
            userMap.remove(user.getUserName());
            uuidMap.remove(userCook);
        }
        return new Result<>(true,"删除成功");
    }

    /**
     * 设置用户头像,数据有制定格式
     *
     * @param parmBody 格式user={i}&filename={j}
     * @return
     */
    @Override
    public Result<String> setUserAvata(String parmBody) {
        Pattern pattern = Pattern.compile("user=([^&]+)&filename=([^&]+)");
        Matcher matcher = pattern.matcher(parmBody);
        if(matcher.find()) {
            //查找到匹
            UserModel user = new UserModel();
            user.setUserCode(Integer.valueOf(matcher.group(1)));
            user.setUserAvatar(matcher.group(2));
            return userDao.setUserAvataByCode(user)?new Result<>(true,"success"):new Result<>(false,"fialure");
        } else {
            return new Result<>(false,"parm error");
        }
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
