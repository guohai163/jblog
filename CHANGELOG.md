## Version 0.6

*增加微信扫码登陆
*增加deploy目录，用于构建不同的站点
*登陆状态拦截器逻辑调整，如找不到用户信息，则清空用户登陆

~~~sql
#创建微信登陆关系表
CREATE TABLE `jblog_oauth` (
  `oauth_code` int(11) NOT NULL,
  `oauth_openid` varchar(50) NOT NULL,
  `oauth_unionid` varchar(50) NOT NULL,
  `oauth_appid` varchar(50) NOT NULL,
  `user_code` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
#绑定自己的openId
INSERT INTO `jblog_oauth` (`oauth_code`, `oauth_openid`, `oauth_unionid`, `oauth_appid`, `user_code`) VALUES
(1, 'your_open_id', 'your_union_id', 'your_appid', 1);
~~~

## Version 0.5

* 增加了对象存储功能，项目中的涉及用户上传的文件都会放到第三方OSS中。目前使用阿里OSS服务。
* 增加了用户头像上传功能
* 增加了回调开关

~~~sql
ALTER TABLE `jblog_user` ADD user_avatar varchar(100) NOT NULL DEFAULT 'avatar.png';
~~~

## Version 0.4

* 重构了后台界面