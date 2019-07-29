package jblog.guohai.org.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户实体类
 */
public class UserModel {
    /**
     * 用户编号
     */
    @Getter
    @Setter
    private int userCode;

    /**
     * user name
     */
    @Getter
    @Setter
    private String userName;

    /**
     * user password
     */
    @Getter
    @Setter
    private String userPass;

    /**
     * pass key
     * password = md5(md5(origin_pass)+pass_key)
     */
    @Getter
    @Setter
    private String userKey;
}
