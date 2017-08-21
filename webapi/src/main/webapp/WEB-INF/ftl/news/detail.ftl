<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no,email=no">
    <meta name="author" content="mayue0328@autohome.com.cn">
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <meta charset='gb2312'/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="renderer" content="webkit">

    <title>文章详情</title>
    <link rel="stylesheet" href="../css/style.min.css">
    <style type="text/css">
    </style>
</head>
<body ontouchstart>
<!-- 文章部分 -->
<section class="article-main">
    <h1>${news.title}</h1>
    <p class="article-info">
        <span>${news.source}</span>
        <span class="article-info-date">${news.publishTime?datetime}</span>
    </p>
    <div class="article-content">${news.content}</div>
    <p style="text-align: center; font-size: 12px; margin-bottom: 20px; font-size: 12px; color: #ccc;">
        本文来自互联网，不代本站的观点和立场
    </p>
</section>
<script
</body>
</html>
