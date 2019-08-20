<!DOCTYPE html>
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
                    <p><strong>BLOG列表</strong></p>
${.current_template_name}
                </header>
                <!--表格主体-->
                <section class="scrollable padder">
                    <div class="m-b-md">
                    </div>
                    <section class="panel panel-default">
                        <div class="table-responseive">
                            <table class="table table-striped">
                                <thead><tr><th>标题</th><th>分类</th><th>时间</th><th>操作</th></tr></thead>
                                <tbody>
                                    <#list listContent as content>
                                    <tr><td><#if content.postTitle?length gt 21>${content.postTitle?substring(0,20)}...
                <#else>${content.postTitle}</#if></td>
                                    <td><select class="classify-type">
                        <#if content.classCode == 0><option value="0" selected>文章归类...</option></#if>
                        <#list classTypeList as classItem>
                            <option value="${classItem.classCode}" post_code="${content.postCode}" <#if classItem.classCode == content.classCode> selected</#if> >${classItem.className}</option>
                        </#list>
                    </select></td>
                                    <td><time class="float-right" datetime="${content.postDate?string('yyyy-MM-dd')}">${content.postDate?string('yyyy-MM-dd')}</time></td>

                                    <td><a class="li-btn li-btn-del" id="${content.postCode}">del</a><a class="li-btn" href="/admin/main?postCode=${content.postCode}">edit</a></td>
                                    </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </div>


        


                    </section>
                </section>
        <div>
        <span><#if (maxPageNum > pageNum)><a href="/admin/list?page=${pageNum+1}">上一页</a></#if></span>
        <span class="float-right"><#if (pageNum > 1)><a href="/admin/list?page=${pageNum-1}">下一页</a></#if></span>
        </div>
            </section>        
        </section>
    </section>
    </section>


<#include "/inc/foot.ftl"/>

</body>
</html>