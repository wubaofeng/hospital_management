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
    <base href="<%=this.getServletContext().getContextPath() %>/register/">
    <title>挂号</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../Css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="../Css/bootstrap-responsive.css"/>
    <link rel="stylesheet" type="text/css" href="../Css/style.css"/>
    <script type="text/javascript" src="../Js/jquery.js"></script>
    <script type="text/javascript" src="../Js/jquery.sorted.js"></script>
    <script type="text/javascript" src="../Js/bootstrap.js"></script>
    <script type="text/javascript" src="../Js/ckform.js"></script>
    <script type="text/javascript" src="../Js/common.js"></script>
    <script type="text/javascript" src="../Js/ckeditor/ckeditor.js"></script>
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
<form action="" method="post" class="definewidth m20">

    <input name="method" value="updateRegisterById" type="hidden">
    <input name="id" value="${register.id}" type="hidden">

    <table class="table table-bordered table-hover definewidth m10">
        <tr>
            <td width="10%" class="tableleft">姓名</td>
            <td><input type="text" name="name" value="${register.name}"/></td>
        </tr>

        <tr>
            <td width="10%" class="tableleft">身份证号</td>
            <td><input type="text" name="idCard" value="${register.idCard}"/></td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">社保号</td>
            <td><input type="text" name="socialSecurityNumber" value="${register.socialSecurityNumber}"/></td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">挂号费</td>
            <td><input type="text" name="registerMoney" value="${register.registerMoney}"/>元</td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">联系电话</td>
            <td><input type="text" name="phone" value="${register.phone}"/></td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">是否自费</td>
            <td>
                <input type="radio" name="isPay" value="0" ${register.isPay==0 ? 'checked' : ''}/>否&nbsp;&nbsp;&nbsp;
                <input type="radio" name="isPay" value="1" ${register.isPay==1 ? 'checked' : ''}/>是
            </td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">性别</td>
            <td><input type="radio" name="sex" value="1" ${register.age==0 ? 'checked' : ''}/>男&nbsp;&nbsp;&nbsp;
                <input type="radio" name="sex" value="0" ${register.age==0 ? 'checked' : ''}/>女
            </td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">年龄</td>
            <td><input type="text" name="age" value="${register.age}"/></td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">初复诊</td>
            <td>
                <input type="radio" name="consultation" value="0" ${register.consultation==0 ? 'checked' : ''}/>初诊&nbsp;&nbsp;&nbsp;
                <input type="radio" name="consultation" value="1" ${register.consultation==1 ? 'checked' : ''}/>复诊
            </td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">所挂科室</td>
            <td>
                <select name="department" id="department">
                    <option value="1" ${register.department==1 ? 'selected' : ''}>急诊科</option>
                    <option value="2" ${register.department==2 ? 'selected' : ''}>儿科</option>
                    <option value="3" ${register.department==3 ? 'selected' : ''}>妇科</option>
                    <option value="4" ${register.department==4 ? 'selected' : ''}>皮肤科</option>
                    <option value="5" ${register.department==5 ? 'selected' : ''}>内分泌科</option>
                    <option value="6" ${register.department==6 ? 'selected' : ''}>牙科</option>
                </select>
            </td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">指定医生</td>
            <td>
                <select name="doctorId" id="doctor">
                    <option value="${register.doctorId}">${register.doctor.name}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">备注</td>
            <td><textarea name="remark">${register.remark}</textarea></td>
        </tr>
        <tr>
            <td colspan="2">
                <center>
                    <button type="button" class="btn btn-primary" onclick="update()">保存</button> &nbsp;&nbsp;
                    <button type="button" class="btn btn-success" name="backid" id="backid">返回列表</button>
                </center>
            </td>
        </tr>
    </table>
</form>
<script>
    function update() {
        $.post("${path}register", $("form").serialize(), function(obj) {
            if (obj) {
                alert("修改成功");
            } else {
                alert("更新失败，请重试");
            }
        });
    }

    function loadDoctorByDepartment(department) {
        let sendData = {
            method: "listByDepartment",
            department: department
        };
        $.get("${path}doctor", sendData, function (data) {
            data = JSON.parse(data);
            if (data.code == 0) {
                let doctors = data.data;
                for (let doctor of doctors) {
                    let option = "<option value='" + doctor.id + "'>" + doctor.name + "</option>";
                    $("#doctor").append(option);
                }
            }
        });
    }

    $("#department").change(function () {
        let department = $(this).val();
        $("#doctor").html("");
        loadDoctorByDepartment(department);
    });
</script>
</body>
</html>