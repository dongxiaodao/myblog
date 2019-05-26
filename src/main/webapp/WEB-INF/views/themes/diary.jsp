<%--
  Created by IntelliJ IDEA.
  User: dongxiaodao
  Date: 2019/4/16
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="${article.title}"/>
</jsp:include>

<article class="main-content page-page">
    <div class="post-header">
        <h1 class="post-title" itemprop="name headline">${article.title}</h1>
        <div class="post-data">
            <time datetime="<fmt:formatDate value="${article.created}" pattern="yyyy-MM-dd"/>" itemprop="datePublished">发布于 <fmt:formatDate value="${article.created}" pattern="yyyy-MM-dd"/></time>
            / <a href="#comments">
                <c:if test="${article.commentsNum == 0}">还没有评论</c:if>
                <c:if test="${article.commentsNum > 0}">${article.commentsNum}条评论</c:if>
              </a>
            / ${article.hits}浏览
        </div>
    </div>
    <div id="post-content" class="post-content">
        <p class="post-tags"></p>
        ${article.content}
    </div>
</article>

<%--评论模块--%>
<jsp:include page="comments.jsp"/>
<jsp:include page="footer.jsp"/>
