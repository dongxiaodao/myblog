<%--
  Created by IntelliJ IDEA.
  User: dongxiaodao
  Date: 2019/4/17
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="dao" uri="http://www.daoblog.cn/jsp/mytag/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="${keyword}"/>
</jsp:include>

<div class="main-content common-page clearfix">
    <div class="categorys-item">
        <div class="common-title">
            <b>${type}: ${keyword}</b>
        </div>
        <c:choose>
            <c:when test="${articles.list == null || articles.list.size() < 1} ">
                <div>
                    <p>抱歉，没有相关文章！！！</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="post-lists">
                    <div class="post-lists-body">
                        <c:forEach items="${articles.list}" var="article">
                            <div class="post-list-item">
                                <div class="post-list-item-container">
                                    <div class="item-label">
                                        <div class="item-title">
                                            <a href="${ctx}article/<c:choose><c:when test="${article.slug!=null}">${article.slug}</c:when><c:otherwise>${article.cid}</c:otherwise></c:choose>">${article.title}</a>
                                        </div>
                                        <div class="item clearfix">
                                            <div class="item-meta-ico ${dao:showIcon(article.cid)}" style="background: url(${ctx}static/themes/img/bg-ico.png) no-repeat;background-size: 40px auto;"></div>
                                            <div class="item-meta-date">发布于 <fmt:formatDate value="${article.created}" pattern="yyyy-MM-dd"/></div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                </div>

            </c:otherwise>

        </c:choose>
    </div>
</div>

    <jsp:include page="footer.jsp"/>
