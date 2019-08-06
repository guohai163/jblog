package jblog.guohai.org.dao;

import jblog.guohai.org.model.UserModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 用户数据类
 */
public interface UserDao {

    /**
     * 查找用户根据用户名
     * @param userName 用户名
     * @return 用户实体
     */
    @Select("SELECT * FROM jblog_user WHERE user_name=#{userName};")
    UserModel getUserByName(@Param("userName") String userName);

    /**
     * 设置用户信息根据用户名
     * @param user 用户实体
     * @return 成功与否
     */
    @Update("UPDATE jblog_user SET user_pass=#{user.userPass},user_key=#{user.userKey} WHERE user_name=#{user.userName};")
    Boolean setUserByName(@Param("user") UserModel user);
}
