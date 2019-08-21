package jblog.guohai.org.service;


import jblog.guohai.org.model.Result;

public interface UserService {

    /**
     * 检查用户名与密码是否正常
     * @param user 用户名
     * @param pass 密码
     * @return 结果
     */
    Result<String> checkUserPass(String user, String pass);

    /**
     * 通过COOKIES注销用户
     * @param userCook
     * @return
     */
    Result<String> logoutUser(String userCook);
}
