<%--
  Created by IntelliJ IDEA.
  User: dongxiaodao
  Date: 2019/4/12
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${comments.pageNum != null}">
    <div class="lists-navigator clearfix">
        <ol class="page-navigator">
            <c:if test="${comments.hasPreviousPage}">
                <li class="prev"><a href="?comPage=${comments.prePage}#comments"><i class="fa fa-arrow-left"></i></a></li>
            </c:if>

            <c:forEach items="${comments.navigatepageNums}" var="nav">
                <li <c:if test="${nav == comments.pageNum}"> class="current" </c:if>>
                    <a href="?comPage=${nav}#comments">${nav}</a>
                </li>
            </c:forEach>

            <c:if test="${comments.hasNextPage}">
                <li class="next"><a href="?comPage=${comments.nextPage}#comments"><i class="fa fa-arrow-right fa"></i></a></li>
            </c:if>
        </ol>
    </div>
</c:if>