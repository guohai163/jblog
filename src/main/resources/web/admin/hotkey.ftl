<html>
<head>
    <#include "/inc/admin-head.ftl" />
</head>
<body>
    <section class="main-section">
    <header>
        <div class="navbar-header">jBlog</div>
    </header>
    <section class="main-down">
        <#include "/inc/admin-left.ftl" />
        <!--右侧区域-->
        <section class="right-content">
            <section class="vbox">
                <header class="panel-heading">
                    <p><strong>热词列表</strong></p>
${.current_template_name}
                    <button class="btn-default" type="button" id="renew_hotkey">重新计算热词</button>
                </header>
                <!--表格主体-->
                <section class="scrollable padder">
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
                            </table>
                        </div>
                    </section>
                </section>
            </section>        
        </section>
    </section>
    </section>
<#include "/inc/foot.ftl"/>
</body>
</html>