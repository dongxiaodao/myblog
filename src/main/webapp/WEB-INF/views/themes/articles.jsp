<%--
  Created by IntelliJ IDEA.
  User: dongxiaodao
  Date: 2019/4/8
  Time: 8:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="dao" uri="http://www.daoblog.cn/jsp/mytag/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="${article.title}"/>
    <jsp:param name="keywords" value="${article.tags}"/>
    <jsp:param name="description" value="${dao:showIntro(article, 80)}"/>
</jsp:include>

<article class="main-content page-page" itemscope itemtype="http://schema.org/Article">
    <%--打开文章后，显示于文章标题底下的内容（发布时间，所属分类，评论数量，多少浏览）--%>
        <div class="post-header">
            <h1 class="post-title" itemprop="name headline">
                <a href="/article/<c:choose>
                                           <c:when test="${article.slug} != null">${article.slug}</c:when>
                                           <c:otherwise>${article.cid}</c:otherwise>
                                </c:choose>">
                ${article.title}</a>
            </h1>
            <div class="post-data">
                <time datetime="<fmt:formatDate value='${article.created}'/>" itemprop="datePublished">发布于 <fmt:formatDate value="${article.created}" type="date" dateStyle="full"/></time>
                / ${article.categories}
                / <a href="#comments"><c:if test="${article.commentsNum == 0}">还没有评论</c:if>
                <c:if test="${article.commentsNum > 0}">${article.commentsNum}条评论</c:if></a>
                / ${article.hits}浏览
            </div>
        </div>
        <%--文章主体内容--%>
        <div id="post-content" class="post-content" itemprop="articleBody">
                        <%--显示文章标签--%>
            <p class="post-tags"><c:if test="${dao:showTags(article.tags) != null}">
                 <c:forEach items="${dao:showTags(article.tags)}" var="tag" varStatus="status">
                     <a href="/tag/${tag}">${tag}</a>
                 </c:forEach></c:if>
            </p>
            <%--文章内容--%>

            ${article.content}
        </div>
</article>
<jsp:include page="comments.jsp"/>
<jsp:include page="footer.jsp"/>