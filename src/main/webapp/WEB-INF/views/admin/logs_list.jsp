<%--
  Create by ghost 2018/4/9 16:11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="dao" uri="http://www.daoblog.cn/jsp/mytag/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="查看日志"/>
    <jsp:param name="pageActive" value="logs"/>
    <jsp:param name="has_sub" value="other"/>
</jsp:include>

<div class="row">
    <div class="col-sm-12">
        <h4 class="page-title">查看日志</h4>
    </div>
    <div class="row">
        <form class="form-inline" style="padding-left: 22px" action="${ctx}admin/logs/search" method="post">
            <div class="form-group">
                <label for="ip">Ip</label>
                <input type="text" class="form-control" id="ip" name="ip" value="${log_ip}" placeholder="ip">
            </div>
            <div class="form-group">
                <label for="data">data</label>
                <input type="text" class="form-control" id="data" name="data" value="${log_data}" placeholder="data">
            </div>
            <div class="form-group">
                <label for="created">date</label>
                <input type="text" class="form-control" id="created" name="created" value="${log_created}" placeholder="created time">
            </div>
            <div class="form-group">
                <label for="authorId">sort</label>
                <input type="text" class="form-control" id="authorId" name="authorId" value="${log_authorId}" placeholder="0(user) or 1(admin)">
            </div>
            <div class="form-group" >
                <label for="authorId">page</label>
                <input type="text" class="form-control" id="page" name="page" value="${page}">
            </div>
            <button type="submit" class="btn btn-primary"> 查询 </button>
        </form>
        <br/>
    </div>
    <div class="col-md-12">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>id</th>
                <th>action</th>
                <th>data</th>
                <th>ip</th>
                <th>sort</th>
                <th>time</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${articles.list}" var="log">
                <tr>
                    <td>${log.id}</td>
                    <td>${log.action}</td>
                    <td width="35%">${log.data}</td>
                    <td>${log.ip}</td>
                    <td>${log.authorId}</td>
                    <td><fmt:formatDate value="${log.created}" pattern="HH:mm:ss yyyy-MM-dd"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="../common/pageAdminNav.jsp"/>
    </div>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>