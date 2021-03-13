<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path=request.getScheme()+"://"+request.getServerName()+":"+
	request.getServerPort()+request.getContextPath()+"/";
	pageContext.setAttribute("path", path);
%>
<!DOCTYPE html>
<html>
<head>
    <title>优就业-线医疗管理系统</title>
	<meta charset="UTF-8">
	<link rel="icon" href="Images/logo_favicon.ico" type="image/x-icon" />
   <link rel="stylesheet" type="text/css" href="Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="Css/style.css" />
    <script type="text/javascript" src="Js/jquery.js"></script>
    <script type="text/javascript" src="Js/jquery.sorted.js"></script>
    <script type="text/javascript" src="Js/bootstrap.js"></script>
    <script type="text/javascript" src="Js/ckform.js"></script>
    <script type="text/javascript" src="Js/common.js"></script>
    <script type="text/javascript" src="Js/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="Js/jquery.validate.js"></script>
    <script type="text/javascript" src="Js/messages_zh.js"></script>
    <style type="text/css">
        body {
            padding-top: 140px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
            font-family: "微软雅黑";
            background: url("Images/yy.jpg");
            background-size: 100%;
            background-repeat: no-repeat;
        }

        .form-signin {
            max-width: 400px;
            padding: 19px 29px 29px;
            margin: 0 auto 20px;
            background-color: #fff;
            border: 1px solid #e5e5e5;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            background: rgba(255,255,255,0.5);
        }

        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 10px;
            font-size: 24px;
            margin-left: 90px;
        }

        .form-signin input[type="text"],
        .form-signin input[type="password"] {
            font-size: 16px;
            height: auto;
            margin-bottom: 15px;
            padding: 7px 9px;
        }
		
		
		#message{
			font-size: 14px;
			color:red;
			margin-left: 40px;
		}
		
		.input-block-level{
			width: 300px;
			margin-left: 40px;
		}
		.input-medium{
			margin-left: 40px;
		}
		.code_images{
			width: 115px;
			height: 35px;
			margin-top: -15px;
			margin-left: 10px;
		}
		.error{
			color: red;
			font-size: 12px;
		}

        #code:hover{
            cursor: pointer;
        }
		
    </style>  
</head>
<body>
<div class="container">
	
    <form class="form-signin" id="form-login">
    	<input type="hidden" name="method" value="login">
        <h2 class="form-signin-heading" >在线医疗管理系统</h2>
        <span id="message" class="message"></span><br>
        <input type="text" name="username" class="input-block-level" value="lisi" placeholder="账号">
        <input type="password" name="password" class="input-block-level" placeholder="密码" value="321">
        <input type="text" id="verify" name="verify" class="input-medium" placeholder="验证码">
        <img id="code" class="code_images"  src="user?method=createVerifyCode" />
		<!--  
			验证码功能参考：
			https://www.cnblogs.com/jianlun/articles/5553452.html
		-->
        <p style="text-align: center;">
        <input id="login" type="submit" value="登录" name="login" class="btn btn-large btn-primary" style="width: 150px;"/>
        <a href="regist.jsp">请先注册</a>
        </p>
    </form>
</div>	
<script type="text/javascript">
    $(function () {
        $("#form-login").validate({
            rules: {
                "username": {
                    required: true,
                    rangelength: [6, 12]
                },
                "password": {
                    required: true,
                    rangelength: [8, 20]
                },
                "verify":{
                    required:true,
                    remote: {
                        url: "user",
                        type: "get",
                        dataType: "json",
                        data:{
                            method:"checkCode",
                            code: function () {
                                return $("#verify").val();
                            }
                        }
                    }
                }
            },
            messages: {
                "username": {
                    required: "用户名不能为空",
                    rangelength: "用户名长度必须在6~12个字符之间"
                },
                "password": {
                    required: "密码不能为空",
                    rangelength: "密码长度必须在8~20个字符之间"
                },
                "verify":{
                    required:"请输入验证码",
                    remote:"验证码有误"
                }
            },
            submitHandler:function () {
                let data = $("#form-login").serialize();
                $.post("user", data, function (data) {
                    let obj = JSON.parse(data);
                    if (obj.code == 0) {
                        alert("登录成功，即将跳转后台管理页面");
                        location.href = "index.jsp";
                    } else {
                        $("#message").text(obj.msg);
                    }
                });
            }
        });

        $("#code").click(function (){
            $("#code").prop("src","user?method=createVerifyCode&date="+new Date());
        });
    });
</script>
</body>
</html>