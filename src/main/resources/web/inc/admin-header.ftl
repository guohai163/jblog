<header>
        <div class="navbar-header">jBlog</div>
        <ul class="nav navbar-right nav-user">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <span class="thumb-sm avatar pull-left">
                            <img src="${blog_storage}${user_avatar}">
                         </span>${user_name}<b class="caret"></b>
                </a>
                <ul class="dropdown-menu animated fadeInRight">
                    <span class="arrow top"></span>
                    <li><a href="/admin/security">修改密码</a></li>
                    <li><a href="#" id="logout">退出</a></li>
                </ul>
            </li>
        </ul>
    </header>