<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="发表文章"/>
    <jsp:param name="pageActive" value="publish"/>
</jsp:include>
<html>
<head>
    <link href="${ctx}static/admin/plugins/tagsinput/jquery.tagsinput.css" rel="stylesheet">
    <link href="${ctx}static/admin/plugins/select2/dist/css/select2-bootstrap.css" rel="stylesheet">
    <link href="${ctx}static/admin/plugins/toggles/toggles.css" rel="stylesheet">

    <link href="//cdn.bootcss.com/multi-select/0.9.12/css/multi-select.css" rel="stylesheet"/>
    <link href="//cdn.bootcss.com/select2/3.4.8/select2.min.css" rel="stylesheet"/>
    <link href="//unpkg.com/mditor@1.0.8/dist/css/mditor.min.css" rel="stylesheet"/>

    <link href="//cdn.bootcss.com/summernote/0.8.2/summernote.css" rel="stylesheet">
    <link href="//cdn.bootcss.com/dropzone/4.3.0/min/dropzone.min.css" rel="stylesheet">
    <style rel="stylesheet">
        #tags_tagsinput {
            background-color: #fafafa;
            border: 1px solid #eeeeee;
        }

        #tags_addTag input {
            width: 100%;
        }

        #tags_addTag {
            margin-top: -5px;
        }

        .mditor .editor {
            font-size: 14px;
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
        }

        .note-toolbar {
            text-align: center;
        }

        .note-editor.note-frame {
            border: none;
        }

        .note-editor .note-toolbar {
            background-color: #f5f5f5;
            padding-bottom: 10px;
        }

        .note-toolbar .note-btn-group {
            margin: 0;
        }

        .note-toolbar .note-btn {
            border: none;
        }

        #articleForm #dropzone {
            min-height: 200px;
            background-color: #dbdde0;
            line-height: 200px;
            margin-bottom: 10px;
        }

        #articleForm .dropzone {
            border: 1px dashed #8662c6;
            border-radius: 5px;
            background: white;
        }

        #articleForm .dropzone .dz-message {
            font-weight: 400;
        }

        #articleForm .dropzone .dz-message .note {
            font-size: 0.8em;
            font-weight: 200;
            display: block;
            margin-top: 1.4rem;
        }



    </style>

    <!-- 配置文件 -->
    <script type="text/javascript" src="${ctx}static/admin/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="${ctx}static/admin/ueditor/ueditor.all.js"></script>
    <!-- 实例化编辑器 -->
    <script type="text/javascript" charset="utf-8" src="${ctx}static/admin/ueditor/lang/zh-cn/zh-cn.js"></script>

    <%--调用UEditor编辑器--%>
    <script type="text/javascript">
        var ue = UE.getEditor('ue_container',{
            //'enterTag':''
            initialFrameHeight:300
        });
    </script>

</head>
<body>
<div class="row">
    <div class="col-sm-12">
        <h4 class="page-title">
            <%--分为编辑文章和新建文章--%>
            <c:if test="${null != contents.cid }">编辑文章</c:if>
            <c:if test="${null == contents.cid }">发布文章</c:if>
        </h4>
    </div>
    <div class="col-md-12">

        <input type="hidden" id="attach_url" value="${ctx}"/>

        <%--编辑文章的表单--%>
        <form id="articleForm" role="form" novalidate="novalidate">
            <input type="hidden" name="categories" id="categories"/>
            <%--上传表单后 直接封装为一个contents对象 --%>
            <input type="hidden" name="cid" value="${contents.cid}" id="cid"/>
            <input type="hidden" name="status" value="${contents.status}" id="status"/>
            <input type="hidden" name="allowComment" value="${contents.allowComment}" id="allowComment"/>
            <input type="hidden" name="allowPing" value="${contents.allowPing}" id="allowPing"/>
            <input type="hidden" name="allowFeed" value="${contents.allowFeed}" id="allowFeed"/>
            <input type="hidden" name="content" id="content-editor"/>
            <input type="hidden" name="fmtType" id="fmtType" value="${contents.fmtType}"/>
            <input type="hidden" name="thumbImg" id="thumbImg" value="${contents.thumbImg}"/>

            <div class="form-group col-md-6" style="padding: 0 10px 0 0;">
                <input type="text" class="form-control" placeholder="请输入文章标题（必须）" name="title" required
                       aria-required="true" value="${contents.title}"/>
            </div>

            <div class="form-group col-md-6" style="padding: 0 0 0 10px;">
                <input type="text" class="form-control" placeholder="自定义访问路径，如：my-first-article  默认为文章id" name="slug"
                       value="${contents.slug}"/>
            </div>

            <div class="form-group col-md-6" style="padding: 0 10px 0 0;">
                <input name="tags" id="tags" type="text" class="form-control" placeholder="请填写文章标签"
                       value="${contents.tags}"/>
            </div>

            <div class="form-group col-md-6">
                <select id="multiple-sel" class="select2 form-control" multiple="multiple" data-placeholder="请选择分类...">
                    <c:if test="${null == categories}">
                        <option value="默认分类" selected>默认分类</option>
                    </c:if>
                    <c:forEach items="${categories}" var="c">
                        <%--分类选项  值为分类的名字，--%>
                        <option value="${c.name}"

                                <c:if test="${null != contents && (c.name == contents.categories)}">selected</c:if>>
                                ${c.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="clearfix"></div>
            <%--两种编辑器的切换--%>
            <div class="form-group col-xs-12">
                <div style="float: left">
                    <label class="radio-inline">
                        <input type="radio" name="sorts" id="inlineRadio1" value="true" <c:if test="${sorts == 'true'}"> checked="checked"</c:if>> 置顶
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="sorts" id="inlineRadio2" value="false" <c:if test="${sorts != 'true'}"> checked="checked"</c:if>> 不置顶
                    </label>
                </div>
                <div class="pull-right">
                    <%--由js去做编辑器的切换--%>
                    <a id="switch-btn" href="javascript:void(0)"
                       class="btn btn-purple btn-sm waves-effect waves-light switch-editor">
                        <c:choose>
                            <c:when test="${null != contents && (contents.fmtType == 'html')}">切换为Markdown编辑器</c:when>
                            <c:otherwise>切换为富文本编辑器</c:otherwise>
                        </c:choose>
                    </a>
                </div>
            </div>

            <!--编辑文章的编辑器-->
            <div id="md-container" class="form-group col-md-12">
                <textarea id="md-editor" name="md_content" class="<c:if test="${null != contents && contents.fmtType == 'html'}"> hide </c:if>">${contents.content}</textarea>
            </div>

            <div id="html-container" class="form-group col-md-12">
                <!-- 加载编辑器的容器 -->
                <script id="ue_container" name="ue_content" type="text/plain" >${contents.content}</script>

            </div>
            <!--editor-->

            <div class="form-group col-md-3 col-sm-4">
                <label class="col-sm-4">开启评论</label>
                <div class="col-sm-8">
                    <div class="toggle toggle-success
                <c:choose>
                <c:when test="${contents.allowComment==1}">allow-true</c:when>
                <c:otherwise>allow-false</c:otherwise>
                </c:choose>

                "onclick="allow_comment(this);" on="${contents.allowComment}"></div>
                </div>
            </div>

            <div class="form-group col-md-3 col-sm-4">
                <label class="col-sm-4">标为随笔</label>
                <div class="col-sm-8">
                    <div class="toggle toggle-success
                <c:choose>
                <c:when test="${contents.allowPing==1}">allow-true</c:when>
                <c:otherwise>allow-false</c:otherwise>
                </c:choose> "
                         onclick="allow_ping(this);" on="${contents.allowPing}"></div>
                </div>
            </div>
            <div class="form-group col-md-3 col-sm-4">
                <label class="col-sm-4">私密</label>
                <div class="col-sm-8">
                    <div class="toggle toggle-success
                <c:choose>
                <c:when test="${contents.allowFeed==1}">allow-true</c:when>
                <c:otherwise>allow-false</c:otherwise>
                </c:choose>
                "onclick="allow_feed(this);" on="${contents.allowFeed}"></div>
                </div>
            </div>

            <div class="form-group col-md-3">
                <label class="col-sm-5">添加缩略图</label>
                <div class="col-sm-7">
                    <div id="thumb-toggle" class="toggle toggle-success" on="false" thumb_url="${contents.thumbImg}"
                         onclick="add_thumbimg(this);"></div>
                </div>
            </div>

            <div id="dropzone-container" class="form-group col-md-12 hide">
                <div class="dropzone dropzone-previews" id="dropzone" style="background-size: 50px">
                    <div class="dz-message">
                        <p>可以为你的文章添加一张缩略图 :)</p>
                    </div>
                </div>
                <input type="hidden" name="thumb_img" id="thumb_img" style="width: 50px;"/>
            </div>

            <div class="clearfix"></div>

            <div class="text-right">
                <a class="btn btn-default waves-effect waves-light" href="${ctx}admin/article">返回列表</a>
                <%--js去做发表文章--%>
                <button type="button" class="btn btn-primary waves-effect waves-light" onclick="subArticle('publish');">
                    保存文章
                </button>
                <button type="button" class="btn btn-warning waves-effect waves-light" onclick="subArticle('draft');">
                    存为草稿
                </button>
            </div>
        </form>
        <%--表单结束--%>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<script src="${ctx}static/admin/plugins/tagsinput/jquery.tagsinput.min.js"></script>
<script src="${ctx}static/admin/plugins/jquery-multi-select/jquery.quicksearch.js"></script>

<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>

<script src="//unpkg.com/mditor@1.0.8/dist/js/mditor.min.js"></script>
<script src="//cdn.bootcss.com/wysihtml5/0.3.0/wysihtml5.min.js"></script>
<script src="//cdn.bootcss.com/summernote/0.8.2/summernote.min.js"></script>
<script src="//cdn.bootcss.com/summernote/0.8.2/lang/summernote-zh-CN.min.js"></script>

<script src="//cdn.bootcss.com/multi-select/0.9.12/js/jquery.multi-select.min.js"></script>
<script src="//cdn.bootcss.com/select2/3.4.8/select2.min.js"></script>
<script src="//cdn.bootcss.com/jquery-toggles/2.0.4/toggles.min.js"></script>
<script src="//cdn.bootcss.com/dropzone/4.3.0/min/dropzone.min.js"></script>

<%--<jsp:include page="articlejs.jsp"/>--%>
<script src="${ctx}static/admin/js/article1.js" type="text/javascript"></script>
</body>