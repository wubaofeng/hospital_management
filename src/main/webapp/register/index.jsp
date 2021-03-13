<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getScheme() + "://" + request.getServerName() + ":" +
            request.getServerPort() + request.getContextPath() + "/";
    pageContext.setAttribute("path", path);
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=this.getServletContext().getContextPath() %>/register/">
    <title>门诊查询</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../Css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="../Css/bootstrap-responsive.css"/>
    <link rel="stylesheet" type="text/css" href="../Css/style.css"/>
    <script type="text/javascript" src="../Js/jquery.js"></script>
    <script type="text/javascript" src="../Js/jquery.sorted.js"></script>
    <script type="text/javascript" src="../Js/bootstrap.js"></script>
    <script type="text/javascript" src="../Js/ckform.js"></script>
    <script type="text/javascript" src="../Js/common.js"></script>

    <style type="text/css">
        body {
            padding-bottom: 40px;
        }

        .sidebar-nav {
            padding: 9px 0;
        }

        @media (max-width: 980px) {
            /* Enable use of floated navbar text */
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }


    </style>
    <script type="text/javascript">


    </script>
</head>
<body>

<form action="${path }register" method="post" class="definewidth m20">
    <input name="method" value="findByIdOrNameOrDepartment" type="hidden"/>
    <table class="table table-bordered table-hover definewidth m10">
        <tr>
            <td width="10%" class="tableleft">病历号：</td>
            <td><input type="text" id="rid" name="idKeyword" value="${idKeyword}"/></td>

            <td width="10%" class="tableleft">姓名：</td>
            <td><input type="text" id="name" name="nameKeyword" value="${nameKeyword}"/></td>

            <td width="10%" class="tableleft">科室：</td>
            <td>
                <select name="departmentKeyword" id="department">
                    <option value="0">==请选择==</option>
                    <option value="1" ${departmentKeyword == 1 ? 'selected' : ''}>急诊科</option>
                    <option value="2" ${departmentKeyword == 2 ? 'selected' : ''}>儿科</option>
                    <option value="3" ${departmentKeyword == 3 ? 'selected' : ''}>妇科</option>
                    <option value="4" ${departmentKeyword == 4 ? 'selected' : ''}>皮肤科</option>
                    <option value="5" ${departmentKeyword == 5 ? 'selected' : ''}>内分泌科</option>
                    <option value="6" ${departmentKeyword == 6 ? 'selected' : ''}>牙科</option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="6">
                <center>
                    <input id="find" name="find" type="submit" class="btn btn-primary" value="查询"/>
                    <input name="ret" id="ret" type="reset" class="btn btn-primary" value="清空"/>
                </center>
            </td>
        </tr>
    </table>
</form>

<table class="table table-bordered table-hover definewidth m10">
    <thead>
    <tr>
        <th><input type="checkbox" id="checkall" onChange="checkAll();"></th>
        <th>病例号</th>
        <th>病人姓名</th>
        <th>主治医生</th>
        <th>挂号时间</th>
        <th>挂号科室</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="dateValue" class="java.util.Date"/>
    <c:forEach var="register" items="${registerPage.data}">
        <tr>
            <td><input type="checkbox" value="${register.id}" class="checkOne"></td>
            <td>${register.id}</td>
            <td>${register.name}</td>
            <td>${register.doctor.name}</td>
            <td>
                <c:if test="${!empty register.registerTime}">
                    <jsp:setProperty name="dateValue" property="time"
                                     value="${register.registerTime}"/>
                    <fmt:formatDate value="${dateValue}" pattern="yyyy-MM-dd hh:mm"/>
                </c:if>
            </td>
            <td>
                <c:if test="${register.department==1}">急诊科</c:if>
                <c:if test="${register.department==2}">儿科</c:if>
                <c:if test="${register.department==3}">妇科</c:if>
                <c:if test="${register.department==4}">皮肤科</c:if>
                <c:if test="${register.department==5}">内分泌科</c:if>
                <c:if test="${register.department==6}">牙科</c:if>
            </td>
            <td>
                <c:if test="${register.status==1}">已挂号</c:if>
                <c:if test="${register.status==2}">已就诊</c:if>
                <c:if test="${register.status==3}">已住院</c:if>
                <c:if test="${register.status==4}">已出院</c:if>
            </td>
            <td>
                <a href="javascript:;" onclick="deleteMedicineById(${register.id})">删除</a>&nbsp
                <a href="${path}register?method=updateRegisterView&id=${register.id}">编辑</a>&nbsp
                <a href="${path}register?method=getDetailById&id=${register.id}">详情</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<table class="table table-bordered table-hover definewidth m10">
    <tr>
        <th colspan="5">
            <div class="inline pull-right page">
                <a href='${path}register?method=findByIdOrNameOrDepartment&page=1&idKeyword=${idKeyboard}&nameKeyword=${nameKeyword}&typeKeyword=${departmentKeyword}'>首页</a>

                <a href='${path}register?method=findByIdOrNameOrDepartment&page=${registerPage.prePage}&idKeyword=${idKeyboard}&nameKeyword=${nameKeyword}&typeKeyword=${typeKeyword}'>上一页</a>

                <a href='${path}register?method=findByIdOrNameOrDepartment&page=${registerPage.nextPage}&idKeyword=${idKeyboard}&nameKeyword=${nameKeyword}&typeKeyword=${typeKeyword}'>下一页</a>

                <a href='${path}register?method=findByIdOrNameOrDepartment&page=${registerPage.totalPage}&idKeyword=${idKeyboard}&nameKeyword=${nameKeyword}&typeKeyword=${typeKeyword}'>尾页</a>

                &nbsp;&nbsp;&nbsp;共<span class='current'>${registerPage.total}</span>条记录
                <span class='current'>${registerPage.totalPage}</span>页
            </div>
            <div>
                <button type="button" class="btn btn-success" id="newNav" onclick="javascript:location.href='add.jsp'">
                    门诊挂号
                </button>&nbsp;&nbsp;&nbsp;
                <button type="button" class="btn btn-success" id="delRegister" onclick="delAll()">批量删除</button>
            </div>
        </th>
    </tr>
</table>
<script>
    function deleteMedicineById(id) {
        if (confirm("你确定要删除这条数据吗")) {
            $.get("${path}register", {
                method: "deleteRegisterById",
                id: id
            }, function (data) {
                if (data) {
                    location.href = "${path}register?method=findAll";
                } else {
                    alert("删除失败，请重试");
                }
            });
        }
    }

    function delAll() {
        let ids = new Array();
        $(".checkOne:checked").each(function () {
            let id = $(this).val();
            ids.push(id);
        });
        if (ids.length == 0) {
            alert("请选择你要删除的数据");
        } else {
            if (confirm("你确定要删除这些数据吗")) {
                let data = {
                    method: "deleteRegistersByIds",
                    ids: ids
                };
                $.post("${path}register", data, function (data) {
                    let obj = JSON.parse(data);
                    if (obj.code == 0) {
                        alert(obj.msg);
                        location.href = "${path}register?method=findAll";
                    }
                });
            }
        }
    }

    function checkAll() {
        $(".checkOne").prop("checked", $("#checkall").prop("checked"));
    }
</script>
</body>
</html>
