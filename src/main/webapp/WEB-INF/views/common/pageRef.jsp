<%--
  Created by IntelliJ IDEA.
  User: dongxiaodao
  Date: 2019/4/9
  Time: 12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ol class="page-navigator">
    <c:if test="${article_contents.hasPreviousPage}">
        <li class="prev"><a href="?page=${article_contents.prePage}"><i class="fa fa-arrow-left"></i> </a> </li>
    </c:if>
    <c:forEach items="${article_contents.navigatepageNums}" var="nav">
        <li <c:if test="${nav == article_contents.pageNum}"> class="current" </c:if>>
            <a href="?page=${nav}">${nav}</a>
        </li>
    </c:forEach>

    <c:if test="${article_contents.hasNextPage}">
        <li class="next"><a href="?page=${article_contents.nextPage}"> <i class="fa fa-arrow-right fa"></i> </a> </li>
    </c:if>
</ol>
