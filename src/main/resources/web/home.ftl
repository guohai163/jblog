<!DOCTYPE html>
<html lang="zh-CN">
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width" />
    <title>${blog_name}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>

</head>
<body class="home blog group-blog">

<div id="main">
    <div id="content">
        <div>
            <article role="article">
                <div id="blog-archives">
                    <#assign curYear = .now?string.yyyy?number+1>

                    <#list list as content>
                        <#if curYear!=content.postYear>
                            <h2>${content.postYear?c}</h2>
                            <#assign curYear = content.postYear>
                        </#if>




                    <article>

                        <h1 class="entry-title">
                            <a href="/${content.postYear?c}/${content.postMonth}/${content.postDay}/${content.postSmallTitle}/">${content.postTitle}</a>

                        </h1>

                        <time datetime="${content.postDate?string('yyyy-MM-dd')}" pubdate=""><span class="month">${content.postDate?string('MMM')}</span> <span class="day">${content.postDay}</span> <span class="year">${content.postYear}</span></time>
                        <footer>



                        </footer>

                    </article>
                  </#list>
                </div>
                <ul style="list-style:none;text-align:center;margin-top:30px;">
                    <li style="display:inline;">
                        <#if (maxPageNum > pageNum)>
                            <a href="/page/${pageNum+1}/" style="float:left;">← 较旧的日志</a>
                        </#if>
                        <#if (pageNum > 1)>
                            <a href="/page/${pageNum-1}/" style="float:right;">较新的日志 →</a>
                        <#elseif pageNum ==1>
                            <#if (maxPageNum > 1) >
                                <a href="/" style="float:right;">较新的日志 →</a>
                            </#if>
                        </#if>
                    </li>

                </ul>
            </article>

        </div>



<#include "/inc/blog-side.ftl" />
    </div>
</div>


<footer class="blog-footer" role="contentinfo"><p>
    © 2019 jBlog.


</p>

</footer>
</body>
</html>