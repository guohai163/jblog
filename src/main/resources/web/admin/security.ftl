<!DOCTYPE html>
<html lang="en">
<head>
	<title>${blog_name}</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/css/jblog.css">
	<link rel="stylesheet" type="text/css" href="/css/jquery-confirm.min.css">
</head>
<body>
<div class="admin-main">

    <#include "/inc/admin-left.ftl"/>
    <div class="admin-body">
        <div class="ul-head"><h2>管理账户</h2></div>
        <div class="inputbox"><label>密码</label><input type="password" name="new_pass" /></div>
        <div class="inputbox"><label>确认</label><input type="password" name="config_new_pass" /></div>
        <div><a class="jblog-a-btn" id="update_pass" >更新密码</a></div>
    </div>
</div>
<#include "/inc/foot.ftl"/>
</body>
</html