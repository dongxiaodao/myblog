<%--
  Created by IntelliJ IDEA.
  User: dongxiaodao
  Date: 2019/4/10
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="dao" uri="http://www.daoblog.cn/jsp/mytag/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="博客后台"/>
    <jsp:param name="pageActive" value="dashboard"/>
</jsp:include>

<html>
<body>
<%--控制台显示页面--%>
<div class="row">
    <%----%>
    <div class="col-sm12">
        <h4 class="page-title">控制台</h4>
    </div>

    <div class="row">
        <div class="col-sm-6 col-lg-4">
            <div class="mini-stat clearfix bx-shadow bg-info">
                <span class="mini-stat-icon"><i class="fa fa-quote-right" aria-hidden="true"></i></span>
                <div class="mini-stat-info text-right">
                    发表了<span class="counter">${statistics.articleNum}</span>篇文章
                </div>
            </div>
        </div>

        <div class="col-sm-6 col-lg-4">
            <div class="mini-stat clearfix bg-purple bx-shadow">
                <span class="mini-stat-icon"><i class="fa fa-comment-o" aria-hidden="true"></i></span>
                <div class="mini-stat-info text-right">
                    收到了<span class="counter">${statistics.commentNum}</span>条留言
                </div>
            </div>
        </div>
        <%--上传的附件的个数--%>
        <div class="col-sm-6 col-lg-4">
            <div class="mini-stat clearfix bx-shadow bg-success">
                <span class="mini-stat-icon"><i class="fa fa-cloud-upload" aria-hidden="true"></i></span>
                <div class="mini-stat-info text-right">
                    上传了<span class="counter">${statistics.attacheNum}</span>条附件
                </div>
            </div>
        </div>

        <%--<div class="col-sm-6 col-lg-3">--%>
            <%--<div class="mini-stat clearfix bx-shadow bg-primary">--%>
                <%--<span class="mini-stat-icon"><i class="fa fa-link" aria-hidden="true"></i></span>--%>
                <%--<div class="mini-stat-info text-right">--%>
                    <%--友链了<span class="counter">${statistics.linkNum}</span>个好友--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    </div>

    <div class="row">
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">最新文章</h4>
                </div>
                <div class="panel-body">
                    <ul class="list-group">
                        <c:forEach items="${d_articles}" var="article">
                            <li class="list-group-item">
                                <span class="badge badge-primary" title="${article.commentsNum}条评论">${article.commentsNum}</span>
                                <a target="_blank" href="${ctx}article/${article.cid}">${article.title}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">最新评论</h4>
                </div>
                <div class="panel-body">
                    <c:choose>
                        <c:when test="${d_comments==null||d_comments.size() == 0}">
                            <div class="alert alert-warning">
                                还没有收到留言.
                            </div>
                        </c:when>
                        <c:otherwise>
                            <ul class="list-group">
                                <c:forEach items="${d_comments}" var="comment">
                                    <li class="list-group-item">
                                        <c:choose>
                                            <c:when test="${comment.url==null||comment.url==''}">
                                                ${comment.author}
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${comment.url}" target="_blank">${comment.author}</a>
                                            </c:otherwise>
                                        </c:choose>
                                        于 <fmt:formatDate value="${comment.created}" pattern="yyyy-MM-dd HH:mm"/>
                                        <a href="${ctx}article/${comment.cid}#comments" target="_blank">${dao:showIntro_Com(comment, 18)}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">系统日志</h4>
                </div>
                <div class="panel-body">
                    <ul class="list-group">
                        <c:forEach items="${d_logs.list}" var="log">
                            <li class="list-group-item">
                                <span><fmt:formatDate value="${log.created}" pattern="yyyy-MM-dd HH:mm:ss"/> => ${log.action} => ${log.ip}</span>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>