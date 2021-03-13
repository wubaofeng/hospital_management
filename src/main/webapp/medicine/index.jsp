<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getScheme() + "://" + request.getServerName() + ":" +
            request.getServerPort() + request.getContextPath() + "/";
    pageContext.setAttribute("path", path);
    String imgPath = request.getScheme() + "://" + request.getServerName() + ":" +
            request.getServerPort() + "/Hospital-pic/";
%>
<!DOCTYPE html>
<html>
<base href="<%=this.getServletContext().getContextPath() %>/medicine/">
<head>
    <title>药品查询</title>
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

<form action="${path }medicine" method="post" class="definewidth m20">
    <input type="hidden" name="method" value="findByNameOrType">
    <table class="table table-bordered table-hover definewidth m10">
        <tr>
            <td width="10%" class="tableleft">药品名称：</td>
            <td><input type="text" id="name" name="nameKeyword" value="${nameKeyword}"/></td>

            <td width="10%" class="tableleft">药品类型：</td>
            <td>
                <select name="typeKeyword" id="type">
                    <option value="0">==请选择==</option>
                    <option value="1" ${typeKeyword == 1 ? 'selected' : ''}>处方药</option>
                    <option value="2" ${typeKeyword == 2 ? 'selected' : ''}>中药</option>
                    <option value="3" ${typeKeyword == 3 ? 'selected' : ''}>西药</option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="4">
                <center>
                    <input id="find" name="find" type="submit" class="btn btn-primary" value="查询"/>
                    <input id="ret" name="ret" type="button" class="btn btn-primary" value="清空"/>
                </center>
            </td>
        </tr>
    </table>
</form>

<table class="table table-bordered table-hover definewidth m10">
    <thead>
    <tr>
        <th><input type="checkbox" id="checkall" onChange="checkAll()"></th>
        <th>药品编号</th>
        <th>药品名称</th>
        <th>图片</th>
        <th>药品类型</th>
        <th>简单描述</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="medicine" items="${medicinePage.data}">
        <tr>
            <td><input type="checkbox" value="${medicine.id}" class="checkOne"></td>
            <td>${medicine.id}</td>
            <td>${medicine.name}</td>
            <td>
                <img width="70" src="${path}/${medicine.picture}">
            <td>
                <c:if test="${medicine.type==1}">处方药</c:if>
                <c:if test="${medicine.type==2}">中药</c:if>
                <c:if test="${medicine.type==3}">西药</c:if>
            </td>
            <td>${medicine.simpleDescription}</td>
            <td>
                <a href="javascript:;" onclick="deleteMedicineById(${medicine.id})">删除</a>&nbsp
                <a href="${path}medicine?method=updateMedicineView&id=${medicine.id}">编辑</a>&nbsp
                <a href="${path}medicine?method=getDetailById&id=${medicine.id}">详情</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<table class="table table-bordered table-hover definewidth m10">
    <tr>
        <th colspan="5">
            <div class="inline pull-right page">
                <a href='${path}medicine?method=findByNameOrType&page=1&nameKeyword=${nameKeyboard}&typeKeyword=${departmentKeyword}'>首页</a>

                <a href='${path}medicine?method=findByNameOrType&page=${medicinePage.prePage}&nameKeyword=${nameKeyboard}&typeKeyword=${typeKeyword}'>上一页</a>

                <a href='${path}medicine?method=findByNameOrType&page=${medicinePage.nextPage}&nameKeyword=${nameKeyboard}&typeKeyword=${typeKeyword}'>下一页</a>

                <a href='${path}medicine?method=findByNameOrType&page=${medicinePage.totalPage}&nameKeyword=${nameKeyboard}&typeKeyword=${typeKeyword}'>尾页</a>

                &nbsp;&nbsp;&nbsp;共<span class='current'>${medicinePage.total}</span>条记录
                <span class='current'>${medicinePage.totalPage}</span>页
            </div>
            <div>
                <button type="button" class="btn btn-success" id="newNav" onclick="javascript:location.href='add.jsp'">
                    添加新药
                </button>
                <button type="button" class="btn btn-success" onclick="delAll()">批量删除</button>
            </div>

        </th>
    </tr>
</table>
<script>
    function deleteMedicineById(id) {
        if (confirm("你确定要删除这条数据吗")) {
            $.get("${path}medicine", {
                method: "deleteMedicineById",
                id: id
            }, function (data) {
                if (data) {
                    location.href = "${path}medicine?method=findAll";
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
                    method: "deleteMedicinesByIds",
                    ids: ids
                };
                $.post("${path}medicine", data, function (data) {
                    let obj = JSON.parse(data);
                    if (obj.code == 0) {
                        alert(obj.msg);
                        location.href = "${path}medicine?method=findAll";
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
