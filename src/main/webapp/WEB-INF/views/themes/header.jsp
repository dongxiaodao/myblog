<%--
  Created by IntelliJ IDEA.
  User: dongxiaodao
  Date: 2019/4/2
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%--导入jstl标签--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <%--必须显示在网页中除 title 元素和其他 meta 元素以外的所有其他元素之前
    文档兼容模式的定义，加强代码对IE的兼容性，强制强制IE使用当前本地最新版标准模式渲染
    或者用chrome内核渲染--%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <%--设置浏览器使用什么内核 webkit（急速核） ie-comp（IE兼容内核） ie-stand（IE标准内核）--%>
    <meta name="renderer" content="webkit">
    <%--可视区域的宽，高，缩放比例（是否支持缩放）--%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <%--指定请求和响应遵循的缓存机制  no-transform：禁止百度转码--%>
    <meta http-equiv="Cache-Control" content="no-transform"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <%--网站的关键字和描述--%>
    <meta name="keywords" content="<%=request.getParameter("keywords")%>"/>
    <meta name="description" content="<%=request.getParameter("description")%>"/>
    <%--为网页的标题加图标--%>
    <link rel="icon" href="${ctx}static/themes/img/book_logo.png"/>
    <%--为移动端网页的标题加图标--%>
    <link rel="apple-touch-icon" href="${ctx}static/themes/img/book_logo.png"/>

    <%--pageTitle由其他页面传入request--%>
    <title><%=request.getParameter("pageTitle")%></title>

    <%--js插件 图标字体，--%>
    <link rel="stylesheet" href="${ctx}static/themes/css/font-awesome.min.css">
    <link href="//cdn.bootcss.com/highlight.js/9.9.0/styles/xcode.min.css" rel="stylesheet">
    <link href="${ctx}static/themes/css/0style.min.css" rel="stylesheet">
    <%--导入jquery--%>
    <script src="//cdn.bootcss.com/jquery/2.2.3/jquery.min.js"></script>

    <%--以下为：博客美化:通用代码高亮插件--%>
    <script type="text/javascript" src="${ctx}static/admin/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
    <link rel="stylesheet" href="${ctx}static/admin/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css">
    <script type="text/javascript">
        SyntaxHighlighter.all();
    </script>

    <!--[if lt IE 9]>
    <!--bootstrap插件-->
    <script src="//cdn.bootcss.com/html5shiv/r29/html5.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="bg-grey" gtools_scp_screen_capture_injected="true">

<!--[if lt IE 8]>
<div class="browsehappy" role="dialog">
    当前网页 <strong>不支持</strong> 你正在使用的浏览器. 为了正常的访问, 请 <a href="http://browsehappy.com/" target="_blank">升级你的浏览器</a>。
</div>
<![endif]-->

<header id="header" class="header bg-white">
    <div class="navbar-container">
        <a href="${ctx}" class="navbar-logo">
            <i class="fa fa-book" aria-hidden="true"></i> DaoBlog
        </a>
        <%--导航条的各个分类--%>
        <div class="navbar-menu">
            <a href="${ctx}archives">文章归档</a>
            <a href="${ctx}tech">技术学习</a>
            <a href="${ctx}life">生活随笔</a>
            <a href="${ctx}diary">开发日志</a>
            <a href="${ctx}about">关于</a>
        </div>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <%--搜索栏样式--%>
        <div class="navbar-search" onclick="">
            <span class="icon-search"></span>
            <form role="search" onsubmit="return false;">
                <span class="search-box" style="width: 430px">
                    <input type="text" id="search-inp" class="input" placeholder="搜索标题..." maxlength="30"
                           autocomplete="off">
                </span>
            </form>
        </div>
        <%--登录页面跳转处--%>
        <div class="navbar-menu top-right">
            <a href="${ctx}login"><i class="fa fa-user fa-x"></i> </a>
        </div>

        <%--以下设置为方便移动端访问--%>
        <div class="navbar-mobile-menu" onclick="">
            <span class="icon-menu cross"><span class="middle"></span></span>
            <ul>
                <li><a href="${ctx}archives">文章归档</a></li>
                <li><a href="${ctx}tech">技术学习</a></li>
                <li><a href="${ctx}life">生活随笔</a></li>
                <li><a href="${ctx}diary">开发日志</a></li>
                <li><a href="${ctx}about">关于</a></li>
            </ul>
        </div>
    </div>
</header>


