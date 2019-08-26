package jblog.guohai.org.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户实体类
 */
@Data
public class UserModel {
    /**
     * 用户编号
     */
    private int userCode;

    /**
     * user name
     */
    private String userName;

    /**
     * user password
     */
    private String userPass;

    /**
     * pass key
     * password = md5(md5(origin_pass)+pass_key)
     */
    private String userKey;

    /**
     * user face
     */
    private String userAvatar;

    /**
     * user uuid
     */
    private String userUUID;
}
