<%--
  Created by IntelliJ IDEA.
  User: dongxiaodao
  Date: 2019/4/18
  Time: 8:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageActive" value='<%=request.getParameter("pageActive")%>'/>
<c:set var="has_sub" value='<%=request.getParameter("has_sub")%>'/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title><%=request.getParameter("pageTitle")%> - 东小刀</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta content="Coderthemes" name="author"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="shortcut icon" href="${ctx}/static/admin/images/book_logo.png"/>
    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}static/themes/css/font-awesome.min.css" rel="stylesheet">
    <link href="${ctx}static/admin/css/style.min.css" rel="stylesheet" type="text/css">
    <link href="${ctx}static/admin/plugins/sweetalert/sweetalert2.min.css" rel="stylesheet">

    <script type="text/javascript" src="${ctx}static/admin/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
    <link rel="stylesheet" href="${ctx}static/admin/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css">
    <script type="text/javascript">
        SyntaxHighlighter.all();
    </script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<%--左半边，菜单栏--%>
<body class="fixed-left">
<%--包装--%>
<div id="wrapper">
    <%--最上一层，含以下三块--%>
    <div class="topbar">
        <%--左边的部分--%>
        <div class="topbar-left">
            <div class="text-center p-t-10" style="margin: 0 auto;">
                <div class="pull-left" style="padding-left: 10px;">
                    <%--<a href="${ctx}admin/dashboard">--%>
                        <%--<i class="fa fa-camera-retro fa-2x" ></i>--%>
                    <%--</a>--%>
                </div>
                <div class="pull-left" style="padding-left: 25px;">
                    <span style="font-size: 28px; color: #2f353f; line-height: 50px;">My Blog</span>
                </div>
            </div>
        </div>
        <%--右边的部分--%>
        <div class="navbar navbar-default" role="navigation">
            <div class="container">
                <div class="">
                    <div class="pull-left">
                        <%--点击右边部分扩大--%>
                        <button type="button" class="button-menu-mobile open-left">
                            <i class="fa fa-bars"></i>
                        </button>
                        <span class="clearfix"></span>
                    </div>

                    <ul class="nav navbar-nav navbar-right pull-right">
                        <li class="dropdown">
                            <%--设置按钮--%>
                            <a href=" " class="dropdown-toggle profile" data-toggle="dropdown"
                               aria-expanded="true"><i class="fa fa-gears fa-2x"></i>  </a>
                            <ul class="dropdown-menu">
                                <li><a href="${ctx}" target="_blank"><i class="fa fa-eye" aria-hidden="true"></i> 查看网站</a></li>
                                <%--<li><a href=" "><i class="fa fa-sun-o"></i> 个人设置</a></li>--%>
                                <%--<li><a href=" "><i class="fa fa-circle-o-notch"></i> 重启系统</a></li>--%>
                                <li><a href="${ctx}admin/logout"><i class="fa fa-sign-out"></i> 注销</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
        <%--最上一层结束--%>

    <%--菜单栏--%>
    <div class="left side-menu">
        <div class="sidebar-inner slimscrollleft">
            <div id="sidebar-menu">
                <ul>
                    <%--class="active" 选项卡中当前活动着的选项--%>
                    <li <c:if test="${pageActive == 'dashboard'}">class="active" </c:if>>
                        <a href="${ctx}admin/console" class="waves-effect <c:if test='${pageActive == "dashboard"}'>active </c:if> "><i class="fa fa-dashboard" aria-hidden="true"></i><span> 控制台 </span></a>
                    </li>
                    <li <c:if test="${pageActive == 'publish'}">class="active" </c:if> >
                        <a href="${ctx}admin/article/publish" class="waves-effect <c:if test='${pageActive == "publish"}'>active </c:if> "><i class="fa fa-pencil-square-o" aria-hidden="true"></i><span> 发布文章 </span></a>
                    </li>
                    <li <c:if test="${pageActive == 'article'}"> class="active" </c:if> >
                        <a href="${ctx}admin/article?page=1" class="waves-effect <c:if test='${pageActive == "article"}'>active </c:if> "><i class="fa fa-list" aria-hidden="true"></i><span> 文章管理 </span></a>
                    </li>

                    <%--<li <c:if test="${pageActive == 'attach'}">class="active" </c:if> >--%>
                        <%--<a href="${ctx}admin/attach" class="waves-effect <c:if test='${pageActive == "attach"}'>active </c:if> "><i class="fa fa-cloud-upload" aria-hidden="true"></i><span> 文件管理 </span></a>--%>
                    <%--</li>--%>

                    <%--<li <c:if test="${pageActive == 'links'}"> class="active" </c:if> >
                        <a href="${ctx}admin/links" class="waves-effect <c:if test='${pageActive == "links"}'>active </c:if> "><i class="fa fa-link" aria-hidden="true"></i><span> 友链管理 </span></a>
                    </li>--%>

                        <%--下拉菜单--%>
                    <li class="has_sub">
                        <a href="javascript:void(0)" class="waves-effect <c:if test='${has_sub == "other"}'>active subdrop</c:if> "><i class="fa fa-cubes"></i><span> 其他管理 </span><span class="pull-right"><i class="fa fa-plus"></i></span></a>
                        <ul class="list-unstyled">
                            <%--<li <c:if test="${pageActive == 'page'}">class="active" </c:if> >--%>
                                <%--<a href="${ctx}admin/page" class="waves-effect <c:if test='${pageActive == "page"}'>active </c:if> "><i class="fa fa-file-text" aria-hidden="true"></i><span> 页面管理 </span></a>--%>
                            <%--</li>--%>
                            <li <c:if test="${pageActive eq 'comments'}">class="active" </c:if> >
                                <a href="${ctx}admin/comments" class="waves-effect <c:if test='${pageActive == "comment"}'>active </c:if> "><i class="fa fa-comments" aria-hidden="true"></i><span> 评论管理 </span></a>
                            </li>
                            <li <c:if test="${pageActive eq 'category'}"> class="active" </c:if> >
                                <a href="${ctx}admin/category" class="waves-effect <c:if test='${pageActive == "category"}'>active </c:if> "><i class="fa fa-tags" aria-hidden="true"></i><span> 分类/标签 </span></a>
                            </li>
                            <li <c:if test="${pageActive eq 'logs'}">class="active" </c:if> >
                                <a href="${ctx}admin/logs" class="waves-effect <c:if test='${pageActive == "logs"}'>active </c:if> "><i class="fa fa-globe" aria-hidden="true"></i><span> 查看日志 </span></a>
                            </li>
                        </ul>
                    </li>

                    <%--<li <c:if test="${pageActive eq 'themes'}"> class="active" </c:if> >
                        <a href="${ctx}admin/themes" class="waves-effect <c:if test='${pageActive == "themes"}'>active </c:if> "><i class="fa fa-diamond" aria-hidden="true"></i><span> 主题 </span></a>
                    </li>

                    <li <c:if test="${pageActive eq 'setting'}"> class="active" </c:if> >
                        <a href="${ctx}admin/setting" class="waves-effect <c:if test='${pageActive == "setting"}'>active </c:if> "><i class="fa fa-gear" aria-hidden="true"></i><span> 系统设置 </span></a>
                    </li>--%>

                </ul>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
        <%--以下连接主要内容，即活动着的选中的菜单--%>
    <div class="content-page">
        <div class="content">
            <div class="container">
