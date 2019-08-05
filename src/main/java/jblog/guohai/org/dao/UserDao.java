package jblog.guohai.org.dao;

import jblog.guohai.org.model.UserModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户数据类
 */
public interface UserDao {

    @Select("SELECT * FROM jblog_user WHERE user_name=#{userName};")
    UserModel getUserByName(@Param("userName") String userName);
}
