<!DOCTYPE html>
<html lang="en">
<head>
	<title>${blog_name}</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/css/jblog.css">
	<link rel="stylesheet" type="text/css" href="/css/jquery-confirm.min.css"/>
</head>
<body>
	
	<div class="limiter">
		<div class="container-login100">
			<div class="admin-login">
				<form class="login100-form validate-form flex-sb flex-w">
					<span class="login100-form-title p-b-32">
						Account Login
					</span>

                    <div class="inputbox">
                        <label>Username</label>
                        <input type="text" name="username"/>
                    </div>
                    <div class="inputbox">
                        <label>Password</label>
                        <input type="password" name="pass"/>
                    </div>


					<div class="container-login100-form-btn">
					    <a class="jblog-a-btn" id="post-login">Login</a>
					</div>

				</form>
			</div>
		</div>
	</div>
	

	<div id="dropDownSelect1"></div>
	
<#include "/inc/foot.ftl"/>

</body>
</html>