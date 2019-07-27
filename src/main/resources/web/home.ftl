<!DOCTYPE html>
<html lang="zh-CN">
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width" />
    <title>海眼看天下 </title>
    <link rel="stylesheet" type="text/css" href="/styles/style.css"/>

</head>
<body class="home blog group-blog">

<div id="main">
    <div id="content">
        <div>
            <article role="article">
                <div id="blog-archives">

                    <#list list as content>
                        <#if curYear!=content.year>
                            <h2>${content.year?c}</h2>
                            <#assign curYear = content.year>
                        </#if>




                    <article>

                        <h1 class="entry-title">
                            <a href="/${content.year?c}/${content.imonth}/${content.day}/${content.smallTitle}/">${content.title}</a>

                        </h1>

                        <time datetime="content.date" pubdate=""><span class="month">${content.month}</span> <span class="day">${content.day}</span> <span class="year">${content.year}</span></time>
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
                        <#if (pageNum > 2)>
                            <a href="/page/${pageNum-1}/" style="float:right;">较新的日志 →</a>
                        <#elseif pageNum ==2>
                            <a href="/" style="float:right;">较新的日志 →</a>
                        </#if>
                    </li>

                </ul>
            </article>

        </div>



        <aside class="sidebar">
            &nbsp;
            &nbsp;  &nbsp;
            <header class="site-header">
            <div class="pug">
                <a href="/">
                    <img style="width:220px;" src="https://guohai.org/assets/wechat.jpg" alt="">
                </a>
            </div>
            <h1 class="site-title"><a href="/">海眼看世界</a></h1>
            <p class="site-intro">
                A blog by <a href="/about/">Guohai</a>.
            </p>

            <p class="site-intro">
                Follow <a href="http://twitter.com/freeguo">@freeguo</a> on Twitter.
            </p>



        </header>



            &nbsp;
        </aside>
    </div>
</div>


<footer class="blog-footer" role="contentinfo"><p>
    © 2014 Guohai.


</p>

</footer>
</body>
</html>