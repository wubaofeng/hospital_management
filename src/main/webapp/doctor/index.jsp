<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getScheme() + "://" + request.getServerName() + ":" +
            request.getServerPort() + request.getContextPath() + "/";
    pageContext.setAttribute("path", path);
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=this.getServletContext().getContextPath() %>/doctor/">
    <title>门诊医生</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../Css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="../Css/bootstrap-responsive.css"/>
    <link rel="stylesheet" type="text/css" href="../Css/style.css"/>
    <script type="text/javascript" src="../Js/jquery.js"></script>
    <script type="text/javascript" src="../Js/jquery.sorted.js"></script>
    <script type="text/javascript" src="../Js/bootstrap.js"></script>
    <script type="text/javascript" src="../Js/ckform.js"></script>
    <script type="text/javascript" src="../Js/common.js"></script>
    <script type="text/javascript" src="../Js/jquery-3.4.1.js"></script>

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

<form action="${path}doctor?method=findByNameOrDepartment" method="get" class="definewidth m20">
    <input type="hidden" name="method" value="findByNameOrDepartment">
    <table class="table table-bordered table-hover definewidth m10">
        <tr>
            <td width="10%" class="tableleft">医生姓名：</td>
            <td><input type="text" id="name" name="nameKeyword" value="${empty nameKeyword ? '' : nameKeyword}"/></td>
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
                    <input id="ret" name="ret" type="reset" class="btn btn-primary" value="清空"/>
                </center>
            </td>
        </tr>
    </table>
</form>

<table class="table table-bordered table-hover definewidth m10">
    <thead>
    <tr>
        <th><input type="checkbox" id="checkall" onChange="checkAll();"></th>
        <th>医生编号</th>
        <th>医生姓名</th>
        <th>联系方式</th>
        <th>所属科室</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="doctor" items="${doctorPage.data}">
        <tr>
            <td><input type="checkbox" value="${doctor.id}" class="checkOne"></td>
            <td>${doctor.id}</td>
            <td>${doctor.name}</td>
            <td>${doctor.phone}</td>
            <td>
                <c:if test="${doctor.department==1}">急诊科</c:if>
                <c:if test="${doctor.department==2}">儿科</c:if>
                <c:if test="${doctor.department==3}">妇科</c:if>
                <c:if test="${doctor.department==4}">皮肤科</c:if>
                <c:if test="${doctor.department==5}">内分泌科</c:if>
                <c:if test="${doctor.department==6}">牙科</c:if>
            </td>
            <td>
                <a href="javascript:;" onclick="deleteDoctorById(${doctor.id})">删除</a>&nbsp
                <a href="${path}doctor?method=updateDoctorView&id=${doctor.id}">编辑</a>&nbsp
                <a href="${path}doctor?method=getDetailById&id=${doctor.id}">详情</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<table class="table table-bordered table-hover definewidth m10">
    <tr>
        <th colspan="5">
            <div class="inline pull-right page">
                <a href='${path}doctor?method=findByNameOrDepartment&page=1&nameKeyword=${nameKeyboard}&departmentKeyword=${departmentKeyword}'>首页</a>

                <a href='${path}doctor?method=findByNameOrDepartment&page=${doctorPage.prePage}&nameKeyword=${nameKeyboard}&departmentKeyword=${departmentKeyword}'>上一页</a>

                <a href='${path}doctor?method=findByNameOrDepartment&page=${doctorPage.nextPage}&nameKeyword=${nameKeyboard}&departmentKeyword=${departmentKeyword}'>下一页</a>

                <a href='${path}doctor?method=findByNameOrDepartment&page=${doctorPage.totalPage}&nameKeyword=${nameKeyboard}&departmentKeyword=${departmentKeyword}'>尾页</a>

                &nbsp;&nbsp;&nbsp;共<span class='current'>${doctorPage.total}</span>条记录
                <span class='current'>${doctorPage.totalPage}</span>页

            </div>
            <div>
                <button type="button" class="btn btn-success" id="newNav" onclick="javascript:location.href='add.jsp'">
                    添加新医生
                </button>
                <button type="button" class="btn btn-success" id="delAll">批量删除</button>
            </div>

        </th>
    </tr>
</table>
<script>
    function deleteDoctorById(id) {
        if (confirm("你确定要删除这条数据吗")) {
            $.get("${path}doctor", {
                method: "deleteDoctorById",
                id: id
            }, function (data) {
                if (data) {
                    location.href = "${path}doctor?method=findAll";
                } else {
                    alert("删除失败，请重试");
                }
            });
        }
    }

    $("#delAll").click(function () {
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
                    method: "deleteDoctorsByIds",
                    ids: ids
                };
                $.post("${path}doctor", data, function (data) {
                    let obj = JSON.parse(data);
                    if (obj.code == 0) {
                        alert(obj.msg);
                        location.href = "${path}doctor?method=findAll";
                    }
                });
            }
        }
    });

    function checkAll() {
        $(".checkOne").prop("checked", $("#checkall").prop("checked"));
    }
</script>
</body>
</html>
