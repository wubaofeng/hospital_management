package me.wbf.hospital.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.wbf.hospital.entity.User;
import me.wbf.hospital.service.UserService;
import me.wbf.hospital.service.impl.UserServiceImpl;
import me.wbf.hospital.util.VerifyCodeUtils;
import me.wbf.hospital.util.result.Result;
import me.wbf.hospital.util.result.ResultEnum;
import me.wbf.hospital.util.result.ResultUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Baofeng.Wu
 * @since 1.0.0
 */

@WebServlet(value = "/user")
public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();

    void checkUsername(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        String username = request.getParameter("username");
        if (username != null) {
            result = userService.existsByEmail(username);
        }
        writeToResponse(response, result);
    }

    void checkEmail(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        String email = request.getParameter("email");
        if (email != null) {
            result = userService.existsByEmail(email);
        }
        writeToResponse(response, result);
    }

    void register(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username"),
                password = request.getParameter("password"),
                name = request.getParameter("name"),
                email = request.getParameter("email");
        boolean result = false;
        if (username != null && password != null && name != null && email != null
                && !username.isBlank() && !password.isBlank() && !name.isBlank() && !email.isBlank()) {
            User user = new User(null, username, password, name, email);
            result = userService.save(user);
        }
        writeToResponse(response, result);
    }

    void login(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username"),
                password = request.getParameter("password");
        Result result = new Result();
        if (username != null && password != null
                && !username.isBlank() && !password.isBlank()) {
            User user = userService.findByUsername(username);
            if (user == null) {
                result = ResultUtil.error("该用户名不存在，请重试");
            } else if (password.equals(user.getPassword())) {
                request.getSession().setAttribute("username", user.getUsername());
                request.getSession().setAttribute("name", user.getName());
                result = ResultUtil.success();
            } else {
                result = ResultUtil.error("用户名或密码错误，请重试");
            }
        } else {
            result = ResultUtil.error("用户名和密码不能为空，请重试");
        }
        writeJSONToResponse(response, result);
    }

    void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        try {
            response.sendRedirect("login.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void createVerifyCode(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        response.setContentType("image/jpeg");
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        HttpSession session = request.getSession();
        session.removeAttribute("verifyCode");
        session.setAttribute("verifyCode",verifyCode.toLowerCase());
        int w = 100, h = 30;
        try {
            VerifyCodeUtils.outputImage(w,h,response.getOutputStream(),verifyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void checkCode(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
        boolean result = code.equalsIgnoreCase(verifyCode);
        try {
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
