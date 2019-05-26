<%--
  Created by IntelliJ IDEA.
  User: dongxiaodao
  Date: 2019/4/10
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.Random" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    int rand = new Random().nextInt(4)+1;
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, inital-scale=1">
    <link rel="shortcut icon" href="${ctx}/static/admin/images/book_logo.png"/>
    <title>Dao - 用户登录</title>
    <link href="//cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/admin/css/style.min.css" rel="stylesheet" type="text/css">

    <link href="${ctx}static/admin/plugins/sweetalert/sweetalert2.min.css" rel="stylesheet">
    <style type="text/css">
        body,html {
            background: url("${ctx}/static/admin/images/bg/<%=rand%>.png") no-repeat;
            background-size: cover;
        }
        .panel-shadow {
            -moz-box-shadow: 0px 0px 10px 0px rgba(39, 49, 65, 0.1);
            -webkit-box-shadow: 0px 0px 10px 0px rgba(39, 49, 65, 0.1);
            box-shadow: 0px 0px 10px 0px rgba(39, 49, 65, 0.1);
        }
        .bg-overlay {
            -moz-border-radius: 6px 6px 0 0;
            -webkit-border-radius: 6px 6px 0 0;
            background-color: rgba(47, 51, 62, 0.3);
            border-radius: 6px 6px 0 0;
            height: 100%;
            left: 0;
            position: absolute;
            top: 0;
            width: 100%;
        }
        .input-border {
            font-size: 14px;
            width: 100%;
            height: 40px;
            border-radius: 0;
            border: none;
            border-bottom: 1px solid #dadada;
        }

        .bg-img > h3 {
            text-shadow: 0px 2px 3px #555;
            color: #cac9c8;
        }
    </style>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="//oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

</head>
<body>
<div style="margin: 0 auto; padding-bottom: 0%; padding-top: 7.5%; width: 380px;">
    <div class="panel panel-color panel-danger panel-pages panel-shadow">
        <div class="panel-heading bg-img">
            <div class="bg-overlay"></div>
            <h3 class="text-center m-t-10"> Login DaoBlog</h3>
        </div>

        <div class="panel-body">
            <form class="form-horizontal m-t-20" method="post" id="loginForm" onsubmit="return loginForm()">

                <div class="form-group">
                    <div class="col-xs-12">
                        <%--SpringMvc功能--%>
                        <%--表单中input里面的name的值和实体类里面的值是一样的，可在controller入参中添加实体类参数，即可自动装配并获取该对象--%>
                        <input class=" input-lg input-border" name="username" type="text" required=""
                               placeholder="用户名">
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-xs-12">
                       <input class=" input-lg input-border" name="password" type="password" required=""
                               placeholder="密码">
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-xs-12">
                        <div class="checkbox checkbox-danger">
                            <input id="checkbox-signup" name="remeber_me" type="checkbox">
                            <label for="checkbox-signup">记住我</label>
                        </div>
                    </div>
                </div>

                <div class="form-group text-center m-t-40">
                    <div class="col-xs-12">
                        <button class="btn btn-danger btn-lg btn-rounded btn-block w-lg waves-effect waves-light" style="box-shadow: 0px 0px 4px #868282;" type="submit">登&nbsp;录
                        </button>
                    </div>
                </div>
            </form>
        </div>

    </div>
</div>
<!-- Main  -->
<script src="//cdn.bootcss.com/jquery/2.2.3/jquery.min.js"></script>
<script src="${ctx}static/admin/js/sweetalert2.all.min.js"></script>
<script src="${ctx}static/admin/js/base.js"></script>
<script type="text/javascript">
    <%--提交表单触发该方法--%>
    var tale = new $.tale();
    function loginForm() {
        tale.post({
            url: 'admin/login',
            data: $("#loginForm").serialize(),
            success: function (result) {
                //首先判断是不是空，再判断是不是success
                if (result.success) {
                    //需要有一个Controller来接收这个请求,而不是有一个页面叫admin/index
                    window.location.href = 'admin/console';
                } else {
                    tale.alertError(result.msg || "登录失败");
                }
            }
        });
        return false;
    }
</script>
</body>
</html>