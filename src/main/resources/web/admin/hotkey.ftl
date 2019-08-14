<html>
<head>
    <title>${blog_name}</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="/css/jblog-admin.css" />
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
    <section class="main-section">
    <header>
        <div class="navbar-header">jBlog</div>
    </header>
    <section class="main-down">
        <aside class="left-aside">
            <section class="jbox">
                <div class="slimScrolldiv">
                    <ul class="nav">
                        <li><a href="/admin/main"><i class="fa fa-pencil fa-fw"></i><span>增加文章</span></a></li>
                        <li><a href="/admin/list"><i class="fa fa-book fa-fw"></i><span>文章列表</span></a></li>
                        <li><a href="/admin/security"><i class="fa fa-cog fa-fw"></i><span>管理密码</span></a></li>
                        <li><a href="/admin/hotkey"><i class="fa fa-line-chart fa-fw"></i><span>查看热词</span></a></li>
                    </ul>
                </div>
            </section>
        </aside>
        <!--右侧区域-->
        <section class="right-content">
            <section class="vbox">
                <header class="panel-heading">
                    <p><strong>热词列表</strong></p>

                    <button class="btn-default" type="button">重新计算热词</button>
                </header>
                <!--表格主体-->
                <section class="scrollable">
                    <div class="m-b-md">
                    </div>
                    <section class="panel panel-default">
                        <div class="table-responseive">
                            <table class="table table-striped">
                                <thead><tr><th>热词</th><th>次数</th></tr></thead>
                                <tbody>
                                    <#list hotkey_list as hotkey>
                                    <tr><td>${hotkey.hotkey}</td><td>${hotkey.hotkeyCount}</td></tr>
                                    </#list>
                                </tbody>
                            </table
                        </div>
                    </section>
                </section>
            </section>        
        </section>
    </section>
    </section>
</body>
</html>