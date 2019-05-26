<%--
  Created by IntelliJ IDEA.
  User: dongxiaodao
  Date: 2019/4/2
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>

<%--尾部页面--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="dao" uri="http://www.daoblog.cn/jsp/mytag/core"%>

<footer id="footer" class="footer bg-white">
    <%--左边--%>
    <div class="footer-meta">
        <div class="footer-container">
            <div class="meta-item meta-copyright">
                <%--版权信息--%>
                <div class="meta-copyright-info">
                    <%--点击logo回到首页--%>
                    <a href="${ctx}" class="info-logo">
                        <%--alt标签实际上是网站上图片的文字提示--%>
                        <img src="${ctx}static/themes/img/book_logo.png" alt="DaoBlog">
                    </a>
                    <div class="info-text">
                        <p></p>
                        <%--本网站所用主题的作者--%>
                        <p>Theme <a href="https://github.com/chakhsu/pinghsu" target="_blank" rel="nofollow">pinghsu</a> & <a href="https://github.com/otale/tale" target="_blank" rel="nofollow">Tale</a></p>
                        <%--去关于页面--%>
                        <p>&copy; 2019 <a href="${ctx}about">Dao</a></p>
                    </div>
                </div>
            </div>
            <%--中间：热门文章区域--%>
            <div class="meta-item meta-posts">
                <h3 class="meta-title"><b>热门文章</b></h3>
                <c:forEach items="${dao:showHotArticle(8)}" var="article">
                    <li>
                        <a href="<c:choose>
                                           <c:when test="${article.slug != null}">${ctx}article/${article.slug}</c:when>
                                           <c:otherwise>${ctx}article/${article.cid}</c:otherwise>
                                </c:choose>">

                                ${article.title}</a>
                    </li>
                </c:forEach>
            </div>
            <%--右边：热门评论区域--%>
            <div class="meta-item meta-comments">
                <h3 class="meta-title"><b>最新评论</b></h3>
                <c:forEach items="${dao:recentComments(8)}" var="comment">
                    <li>
                        <%-- 多加#comment-${comment.coid}: 方便点击评论跳转到评论所在位置--%>
                        <a href="${ctx}article/${comment.cid}#comment-${comment.coid}"><b>${comment.author}</b>：${comment.content}</a>
                    </li>
                </c:forEach>
            </div>
        </div>
    </div>
</footer>
<script>
    $('#search-inp').keypress(function (e) {
        var key = e.which;//e.which是按键的值(键盘回车键对应的ASC码是13。)
        if(key == 13){
            var q = $(this).val();
            if(q && q != ''){
                window.location.href = '${ctx}search/' + q;
            }
        }
    });
</script>
</body>
</html>
