<%--
  Created by IntelliJ IDEA.
  User: dongxiaodao
  Date: 2019/4/16
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--文章归档页面，按照发表时间年月排序
包含
    头部页面--%>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="文章归档"/>
</jsp:include>
<div class="main-content archive-page clearfix">
    <div class="categorys-item">
        <c:forEach items="${archives}" var="archive" varStatus="status">
            <div class="categorys-title"><b>${archive.dateTimeStr}</b></div>
            <div class="post-lists">
                <div class="post-lists-body">
                    <c:forEach items="${archive.articles}" var="article" varStatus="status">
                        <div class="post-list-item">
                            <div class="post-list-item-container">
                                <div class="item-label">
                                    <div class="item-title">
                                        <a href="<c:choose>
                                           <c:when test="${article.slug != null}">${ctx}article/${article.slug}</c:when>
                                           <c:otherwise>${ctx}article/${article.cid}</c:otherwise>
                                                </c:choose>">

                                        ${article.title}</a>
                                    </div>
                                    <div class="item-meta clearfix">
                                        <div class="item-meta-date">发布于 <fmt:formatDate value="${article.created}" pattern="yyyy-MM-dd"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<jsp:include page="footer.jsp"/>