package me.wbf.hospital.controller;

import me.wbf.hospital.entity.Doctor;
import me.wbf.hospital.entity.Register;
import me.wbf.hospital.service.DoctorService;
import me.wbf.hospital.service.impl.DoctorServiceImpl;
import me.wbf.hospital.util.PageUtil;
import me.wbf.hospital.util.TimeUtil;
import me.wbf.hospital.util.result.Result;
import me.wbf.hospital.util.result.ResultUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Baofeng.Wu
 */
@WebServlet("/doctor")
public class DoctorServlet extends BaseServlet {
    private DoctorService doctorService = new DoctorServiceImpl();

    void findAll(HttpServletRequest request, HttpServletResponse response) {
        PageUtil<Doctor> doctorPage = getPageUtilByHttpServletRequest(request, Doctor.class);
        doctorPage = doctorService.findAll(doctorPage);
        request.setAttribute("doctorPage", doctorPage);
        try {
            request.getRequestDispatcher("doctor/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void checkPhone(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        String phone = request.getParameter("phone");
        if (phone != null) {
            result = doctorService.existByPhone(phone);
        }
        writeToResponse(response, result);
    }

    void checkIdCard(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        String idCard = request.getParameter("idCard");
        if (idCard != null) {
            result = doctorService.existByPhone(idCard);
        }
        writeToResponse(response, result);
    }

    void addDoctor(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        Doctor doctor = getDoctorByHttpServletRequest(request);
        if (doctor != null) {
            result = doctorService.save(doctor);
        }
        writeToResponse(response, result);
    }

    void deleteDoctorById(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter("id");
        boolean result = false;
        if (idString != null) {
            int id = Integer.parseInt(idString);
            result = doctorService.deleteById(id);
        }
        writeToResponse(response, result);
    }

    void deleteDoctorsByIds(HttpServletRequest request, HttpServletResponse response) {
        String[] idsString = request.getParameterValues("ids[]");
        int[] ids = Arrays.stream(idsString).mapToInt(Integer::parseInt).toArray();
        int affectedRows = doctorService.deleteByIds(ids);
        String msg = String.format("请求删除%d条,成功删除%d条", ids.length, affectedRows);
        Result result = ResultUtil.success(msg);
        writeJSONToResponse(response, result);
    }

    void updateDoctorById(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        Doctor doctor = getDoctorByHttpServletRequest(request);
        if (doctor != null && doctor.getId() != null) {
            result = doctorService.update(doctor);
        }
        writeToResponse(response, result);
    }

    void updateDoctorView(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter("id");
        String referer = request.getHeader("referer");
        if (idString != null) {
            int id = Integer.parseInt(idString);
            Doctor doctor = doctorService.findById(id);
            if (doctor != null) {
                request.setAttribute("doctor", doctor);
                try {
                    request.getRequestDispatcher("/doctor/edit.jsp").forward(request, response);
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

    void findByNameOrDepartment(HttpServletRequest request, HttpServletResponse response) {
        String nameKeyword = request.getParameter("nameKeyword");
        String departmentKeyword = request.getParameter("departmentKeyword");
        PageUtil<Doctor> doctorPage = getPageUtilByHttpServletRequest(request, Doctor.class);
        doctorPage = doctorService.findByNameOrDepartment(doctorPage, nameKeyword, departmentKeyword);
        request.setAttribute("nameKeyword", nameKeyword);
        request.setAttribute("departmentKeyword", departmentKeyword);
        request.setAttribute("doctorPage", doctorPage);
        try {
            request.getRequestDispatcher("doctor/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getDetailById(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter("id");
        String referer = request.getHeader("referer");
        if (idString != null) {
            int id = Integer.parseInt(idString);
            Doctor doctor = doctorService.findById(id);
            if (doctor != null) {
                request.setAttribute("doctor", doctor);
                try {
                    request.getRequestDispatcher("/doctor/look.jsp").forward(request, response);
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


    void listByDepartment(HttpServletRequest request, HttpServletResponse response) {
        String departmentString = request.getParameter("department");
        if (departmentString != null) {
            int department = Integer.parseInt(departmentString);
            List<Doctor> doctors = doctorService.findByDepartment(department);
            Result result = ResultUtil.success(doctors);
            writeJSONToResponse(response, result);
        }
    }

    private Doctor getDoctorByHttpServletRequest(HttpServletRequest request) {
        String idString = request.getParameter("id");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String idCard = request.getParameter("idCard");
        String ageString = request.getParameter("age");
        String sexString = request.getParameter("sex");
        String birthdayString = request.getParameter("birthday");
        String email = request.getParameter("email");
        String departmentString = request.getParameter("department");
        String educationString = request.getParameter("education");
        String remark = request.getParameter("remark");
        Integer id = idString == null ? null : Integer.parseInt(idString);
        Integer age = ageString == null ? null : Integer.parseInt(ageString);
        Integer sex = sexString == null ? null : Integer.parseInt(sexString);
        Integer department = departmentString == null ? null : Integer.parseInt(departmentString);
        Integer education = educationString == null ? null : Integer.parseInt(educationString);
        Long birthday = birthdayString == null ? null : TimeUtil.dateOfHtmlToLong(birthdayString);
        if (name != null && !name.isBlank() && phone != null && !phone.isBlank()
                && idCard != null && !idCard.isBlank()) {
            return new Doctor(id, name, idCard, phone, sex, age, birthday, email, department, education, remark);
        }
        return null;
    }
}
