package me.wbf.hospital.controller;

import me.wbf.hospital.entity.Doctor;
import me.wbf.hospital.entity.Register;
import me.wbf.hospital.service.RegisterService;
import me.wbf.hospital.service.impl.RegisterServiceImpl;
import me.wbf.hospital.util.PageUtil;
import me.wbf.hospital.util.TimeUtil;
import me.wbf.hospital.util.result.Result;
import me.wbf.hospital.util.result.ResultUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;

/**
 * @author Baofeng.Wu
 */
@WebServlet("/register")
public class RegisterServlet extends BaseServlet {
    private RegisterService registerService = new RegisterServiceImpl();

    void findAll(HttpServletRequest request, HttpServletResponse response) {
        PageUtil<Register> registerPage = getPageUtilByHttpServletRequest(request, Register.class);
        registerPage = registerService.findAll(registerPage);
        request.setAttribute("registerPage", registerPage);
        try {
            request.getRequestDispatcher("register/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void checkPhone(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        String phone = request.getParameter("phone");
        if (phone != null) {
//            result = doctorService.existByPhone(phone);
        }
        writeToResponse(response, result);
    }

    void checkIdCard(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        String idCard = request.getParameter("idCard");
        if (idCard != null) {
//            result = doctorService.existByPhone(idCard);
        }
        writeToResponse(response, result);
    }

    void addRegister(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        Register register = getRegisterByHttpServletRequest(request);
        register.setStatus(1);
        register.setRegisterTime(Instant.now().toEpochMilli());
        if (register != null) {
            result = registerService.save(register);
        }
        writeToResponse(response, result);
    }

    void deleteRegisterById(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter("id");
        boolean result = false;
        if (idString != null) {
            int id = Integer.parseInt(idString);
            result = registerService.deleteById(id);
        }
        writeToResponse(response, result);
    }

    void deleteRegistersByIds(HttpServletRequest request, HttpServletResponse response) {
        String[] idsString = request.getParameterValues("ids[]");
        int[] ids = Arrays.stream(idsString).mapToInt(Integer::parseInt).toArray();
        int affectedRows = registerService.deleteByIds(ids);
        String msg = String.format("请求删除%d条,成功删除%d条", ids.length, affectedRows);
        Result result = ResultUtil.success(msg);
        writeJSONToResponse(response, result);
    }

    void updateRegisterById(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        Register register = getRegisterByHttpServletRequest(request);
        if (register != null && register.getId() != null) {
            result = registerService.update(register);
        }
        writeToResponse(response, result);
    }

    void updateRegisterView(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter("id");
        String referer = request.getHeader("referer");
        if (idString != null) {
            int id = Integer.parseInt(idString);
            Register register = registerService.findById(id);
            if (register != null) {
                request.setAttribute("register", register);
                try {
                    request.getRequestDispatcher("/register/edit.jsp").forward(request, response);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            response.sendRedirect(referer);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void findByIdOrNameOrDepartment(HttpServletRequest request, HttpServletResponse response) {
        String idKeywordString = request.getParameter("idKeyword");
        String nameKeyword = request.getParameter("nameKeyword");
        String departmentKeywordString = request.getParameter("departmentKeyword");
        int idKeyword = idKeywordString == null || idKeywordString.isBlank() ? 0 : Integer.parseInt(idKeywordString);
        int departmentKeyword = departmentKeywordString == null || departmentKeywordString.isBlank() ? 0 : Integer.parseInt(departmentKeywordString);
        PageUtil<Register> registerPage = getPageUtilByHttpServletRequest(request, Register.class);
        registerPage = registerService.findByIdOrNameOrType(registerPage, idKeyword, nameKeyword, departmentKeyword);
        request.setAttribute("idKeyword", idKeyword);
        request.setAttribute("nameKeyword", nameKeyword);
        request.setAttribute("departmentKeyword", departmentKeyword);
        request.setAttribute("registerPage", registerPage);
        try {
            request.getRequestDispatcher("register/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getDetailById(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter("id");
        String referer = request.getHeader("referer");
        if (idString != null) {
            int id = Integer.parseInt(idString);
            Register register = registerService.findById(id);
            if (register != null) {
                request.setAttribute("register", register);
                try {
                    request.getRequestDispatcher("/register/look.jsp").forward(request, response);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            response.sendRedirect(referer);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Register getRegisterByHttpServletRequest(HttpServletRequest request) {
        String idString = request.getParameter("id");
        String name = request.getParameter("name");
        String idCard = request.getParameter("idCard");
        String socialSecurityNumber = request.getParameter("socialSecurityNumber");
        String registerMoneyString = request.getParameter("registerMoney");
        String phone = request.getParameter("phone");
        String isPayString = request.getParameter("isPay");
        String sexString = request.getParameter("sex");
        String ageString = request.getParameter("age");
        String consultationString = request.getParameter("consultation");
        String departmentString = request.getParameter("department");
        String doctorIdString = request.getParameter("doctorId");
        String registerTimeString = request.getParameter("registerTime");
        String statusString = request.getParameter("status");
        String remark = request.getParameter("remark");
        Integer id = idString == null ? null : Integer.parseInt(idString);
        Float registerMoney = registerMoneyString == null ? null : Float.parseFloat(registerMoneyString);
        Integer isPay = isPayString == null ? null : Integer.parseInt(isPayString);
        Integer sex = sexString == null ? null : Integer.parseInt(sexString);
        Integer age = ageString == null ? null : Integer.parseInt(ageString);
        Integer consultation = consultationString == null ? null : Integer.parseInt(consultationString);
        Integer department = departmentString == null ? null : Integer.parseInt(departmentString);
        Integer doctorId = doctorIdString == null ? null : Integer.parseInt(doctorIdString);
        Long registerTime = registerTimeString == null ? null : TimeUtil.datetimeLocalOfHtmlToLong(registerTimeString);
        Integer status = statusString == null ? null : Integer.parseInt(statusString);
        return new Register(id, name, idCard, socialSecurityNumber, registerMoney, phone, isPay, sex, age, consultation, department, doctorId, registerTime, status, remark);
    }
}
