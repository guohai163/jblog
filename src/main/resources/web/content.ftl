
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>${blog_name} - ${content.postTitle}</title>
	
	<link rel="stylesheet" type="text/css" href="/css/style.css"/>
	
	<link rel="stylesheet" type="text/css" href="/css/markdown.css"/>
</head>
<body>
<div id="main">
	<div id="content">
		<div>
			<article class="hentry " role="article">
			  <header>
    			<h1 class="entry-title">${content.postTitle}</h1>
      <p class="meta">
        
			Written <time datetime="${content.postTitle}" pubdate data-updated="true">${(content.postDate?string("yyyy-MM-dd"))!}</time>.
        



      </p>
    
  </header>
			<div class="entry-content">
			${content.postContent}
			</div>
			
			</article>
			<ul style="list-style:none;text-align:center;margin:20px;">
				<li style="display:inline;">
          <#if contactLast??>
            <a href="/${contactLast.postYear?c}/${contactLast.postMonth}/${contactLast.postDay}/${contactLast.postSmallTitle}/" style="float:left;">← ${contactLast.postTitle}</a>
          </#if>
				</li>
				<li style="display:inline;">
          <#if contactNext??>
            <a href="/${contactNext.postYear?c}/${contactNext.postMonth}/${contactNext.postDay}/${contactNext.postSmallTitle}/" style="float:right;">${contactNext.postTitle} →</a>
          </#if>
				</li>
			</ul>
			<br/>
			<!-- commin -->
  <div id="disqus_thread"></div>
    <script type="text/javascript">
        /* * * CONFIGURATION VARIABLES: EDIT BEFORE PASTING INTO YOUR WEBPAGE * * */
        var disqus_shortname = 'haiblog'; // required: replace example with your forum shortname

        /* * * DON'T EDIT BELOW THIS LINE * * */
        (function() {
            var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
            dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
            (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
        })();
    </script>
    <noscript>Please enable JavaScript to view the <a href="http://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
    <a href="http://disqus.com" class="dsq-brlink">comments powered by <span class="logo-disqus">Disqus</span></a>
    
    
    
    
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
