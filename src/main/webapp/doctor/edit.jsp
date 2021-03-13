<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getScheme() + "://" + request.getServerName() + ":" +
            request.getServerPort() + request.getContextPath() + "/";
    pageContext.setAttribute("path", path);
%>
<!DOCTYPE html>
<html>
<head>
    <%--	<base href="<%=this.getServletContext().getContextPath() %>/doctor/">--%>
    <title>修改医生</title>
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
    <script type="text/javascript" src="../Js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="Js/jquery.validate.js"></script>
    <script type="text/javascript" src="Js/messages_zh.js"></script>

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
        $(function () {
            $('#backid').click(function () {
                window.location.href = "${path}doctor?method=findAll";
            });

        });
    </script>
</head>
<body>
<form class="definewidth m20">
    <input type="hidden" name="id" value="${doctor.id}"/>
    <input type="hidden" name="method" value="updateDoctorById"/>
    <table class="table table-bordered table-hover definewidth m10">
        <tr>
            <td width="10%" class="tableleft">姓名</td>
            <td><input type="text" name="name" value="${doctor.name}"/></td>
        </tr>

        <tr>
            <td width="10%" class="tableleft">身份证号</td>
            <td><input type="text" name="idCard" value="${doctor.idCard}"/></td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">手机</td>
            <td><input type="text" name="phone" value="${doctor.phone}"/></td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">性别</td>
            <td>
                <input type="radio" name="sex" value="1" ${doctor.sex==1 ? 'checked' : ''}/>男&nbsp;&nbsp;&nbsp;
                <input type="radio" name="sex" value="0" ${doctor.sex==0 ? 'checked' : ''}/>女
            </td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">出生年月</td>
            <td><input type="date" name="birthday" id="birthday" class="Wdate"/></td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">年龄</td>
            <td><input type="text" name="age" value="${doctor.age}"/></td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">电子邮箱</td>
            <td><input type="text" name="email" value="${doctor.email}"/></td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">所属科室</td>
            <td>
                <select name="department">
                    <option value="1" ${doctor.department==1 ? 'checked' : ''}>急诊科</option>
                    <option value="2" ${doctor.department==2 ? 'checked' : ''}>儿科</option>
                    <option value="3" ${doctor.department==3 ? 'checked' : ''}>妇科</option>
                    <option value="4" ${doctor.department==4 ? 'checked' : ''}>皮肤科</option>
                    <option value="5" ${doctor.department==5 ? 'checked' : ''}>内分泌科</option>
                    <option value="6" ${doctor.department==6 ? 'checked' : ''}>牙科</option>
                </select>
            </td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">学历</td>
            <td>
                <select name="education">
                    <option value="1" ${doctor.education==1 ? 'checked' : ''}>专科</option>
                    <option value="2" ${doctor.education==1 ? 'checked' : ''}>本科</option>
                    <option value="3" ${doctor.education==1 ? 'checked' : ''}>研究生</option>
                    <option value="4" ${doctor.education==1 ? 'checked' : ''}>博士</option>
                </select>
            </td>
        </tr>
        <tr>
            <td width="10%" class="tableleft">备注</td>
            <td><textarea name="remark">${doctor.remark}</textarea></td>
        </tr>
        <tr>
            <td colspan="2">
                <center>
                    <input name="save" id="save" type="submit" class="btn btn-primary" value="保存"/> &nbsp;&nbsp;
                    <button type="button" class="btn btn-success" name="backid" id="backid">返回列表</button>
                </center>
            </td>
        </tr>
    </table>
</form>
<script>
    function timeToString(timeStamp) {
        let birthday = new Date(timeStamp);
        return birthday.toJSON().substr(0, 10);
    }

    $("#birthday").val(timeToString(${doctor.birthday}));

    $(function () {
        $("form").validate({
            rules: {
                "name": {
                    required: true
                },
                "idCard": {
                    required: true
                },
                "phone": {
                    required: true
                },
                "email": {
                    email: "email"
                }
            },
            messages: {
                "name": {
                    required: "名字不能为空"
                },
                "idCard": {
                    required: "身份证号不能为空"
                },
                "phone": {
                    required: "手机号不能为空"
                },
                "email": {
                    email: "邮箱格式不正确",
                }
            },
            submitHandler: function () {
                $.post("${path}doctor", $("form").serialize(), function (data) {
                    if (data == 'true') {
                        alert("修改成功");
                    } else {
                        alert("更新失败，请重试");
                    }
                });
            }
        });

    });
</script>
</body>
</html>