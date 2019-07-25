
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>${content.title}</title>
	
	<link rel="stylesheet" type="text/css" href="/styles/style.css"/>
	
	<link rel="stylesheet" type="text/css" href="/styles/markdown.css"/>
</head>
<body>
<div id="main">
	<div id="content">
		<div>
			<article class="hentry " role="article">
			  <header>
    			<h1 class="entry-title">

${content.title}
</h1>
      <p class="meta">
        
			Written <time datetime="${content.title}" pubdate data-updated="true">${(content.date?string("yyyy-MM-dd"))!}</time>.
        



      </p>
    
  </header>
			<div class="entry-content">
			${content.intro}
			</div>
			
			</article>
			<ul style="list-style:none;text-align:center;margin:20px;">
				<li style="display:inline;">
          <#if contactLast??>
            <a href="/${contactLast.year?c}/${contactLastMonth}/${contactLast.day}/${contactLast.smallTitle}/" style="float:left;">← ${contactLast.title}</a>
          </#if>
				</li>
				<li style="display:inline;">
          <#if contactNext??>
            <a href="/${contactNext.year?c}/${contactNextMonth}/${contactNext.day}/${contactNext.smallTitle}/" style="float:right;">${contactNext.title} →</a>
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
		<aside class="sidebar">
&nbsp; 
&nbsp;  &nbsp;


<header class="site-header">
  <div class="pug">
    <a href="/">
      <img src="http://guohai.org/images/beaver.png" alt="">
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
