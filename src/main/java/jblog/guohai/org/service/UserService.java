package jblog.guohai.org.service;


import jblog.guohai.org.model.Result;
import jblog.guohai.org.model.UserModel;

public interface UserService {

    /**
     * 检查用户名与密码是否正常
     * @param user 用户名
     * @param pass 密码
     * @return 结果
     */
    Result<UserModel> checkUserPass(String user, String pass);

    /**
     * 通过COOKIES注销用户
     * @param userCook
     * @return
     */
    Result<String> logoutUser(String userCook);

    /**
     * 设置用户头像,数据有制定格式
     * @param parmBody 格式user={i}&filename={j}
     * @return
     */
    Result<String> setUserAvata(String parmBody);
}
