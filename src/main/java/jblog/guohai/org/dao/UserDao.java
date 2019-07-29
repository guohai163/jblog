package jblog.guohai.org.dao;

import jblog.guohai.org.model.UserModel;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * 用户数据类
 */
public interface UserDao {

    @Select("SELECT * FROM gh_user WHERE user_name=#{userName};")
    @Results(id = "userResult", value = {
            @Result(property = "userCode", column = "code"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userPass", column = "pass_word"),
            @Result(property = "userKey", column = "pass_key")
    })
    public UserModel getUserByName(String userName);
}
