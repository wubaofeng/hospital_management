package me.wbf.hospital.controller;

import me.wbf.hospital.entity.Medicine;
import me.wbf.hospital.service.MedicineService;
import me.wbf.hospital.service.impl.MedicineServiceImpl;
import me.wbf.hospital.util.PageUtil;
import me.wbf.hospital.util.result.Result;
import me.wbf.hospital.util.result.ResultUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author Baofeng.Wu
 */

@MultipartConfig
@WebServlet("/medicine")
public class MedicineServlet extends BaseServlet {
    private MedicineService medicineService = new MedicineServiceImpl();

    void findAll(HttpServletRequest request, HttpServletResponse response) {
        PageUtil<Medicine> medicinePage = getPageUtilByHttpServletRequest(request, Medicine.class);
        medicinePage = medicineService.findAll(medicinePage);
        request.setAttribute("medicinePage", medicinePage);
        try {
            request.getRequestDispatcher("medicine/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void checkName(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        String name = request.getParameter("name");
        if (name != null) {
            result = medicineService.existByName(name);
        }
        writeToResponse(response, result);
    }

    void addMedicine(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        Medicine medicine = getMedicineByHttpServletRequest(request);
        if (medicine != null) {
            try {
                Part part = request.getPart("picture");
                String msg = part.getHeader("Content-Disposition");
                String fileType = msg.substring(msg.lastIndexOf("."), msg.length() - 1);
                String fileName = UUID.randomUUID() + fileType;
                String path = request.getServletContext().getRealPath("./upload") + File.separator + "medicine";
                ;
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                part.write(path + File.separator + fileName);
                medicine.setPicture("upload" + File.separator + "medicine" + File.separator + fileName);
                result = medicineService.save(medicine);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        writeToResponse(response, result);
    }

    void deleteMedicineById(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter("id");
        boolean result = false;
        if (idString != null) {
            int id = Integer.parseInt(idString);
            result = medicineService.deleteById(id);
        }
        writeToResponse(response, result);
    }

    void deleteMedicinesByIds(HttpServletRequest request, HttpServletResponse response) {
        String[] idsString = request.getParameterValues("ids[]");
        int[] ids = Arrays.stream(idsString).mapToInt(Integer::parseInt).toArray();
        int affectedRows = medicineService.deleteByIds(ids);
        String msg = String.format("请求删除%d条,成功删除%d条", ids.length, affectedRows);
        Result result = ResultUtil.success(msg);
        writeJSONToResponse(response, result);
    }

    void updateMedicineById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean result = false;
        Medicine medicine = getMedicineByHttpServletRequest(request);
        Part part = request.getPart("newPicture");
        String msg = part.getHeader("Content-Disposition");
        String fileName = msg.substring(msg.indexOf("filename")+10, msg.length()-1);

        if (medicine != null && medicine.getId() != null) {
            if (!"".equals(fileName)) {
                String newPicture = UUID.randomUUID() + msg.substring(msg.indexOf("filename")+10, msg.length()-1);
                String path = request.getServletContext().getRealPath("./upload") + File.separator + "medicine";
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                part.write(path + File.separator + newPicture);
                new File(medicine.getPicture()).delete();
                medicine.setPicture("upload" + File.separator + "medicine" + File.separator + newPicture);
            }
            result = medicineService.update(medicine);
        }
        writeToResponse(response, result);
    }

    void updateMedicineView(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter("id");
        String referer = request.getHeader("referer");
        if (idString != null) {
            int id = Integer.parseInt(idString);
            Medicine medicine = medicineService.findById(id);
            if (medicine != null) {
                request.setAttribute("medicine", medicine);
                try {
                    request.getRequestDispatcher("/medicine/edit.jsp").forward(request, response);
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

    void findByNameOrType(HttpServletRequest request, HttpServletResponse response) {
        String nameKeyword = request.getParameter("nameKeyword");
        String typeKeyword = request.getParameter("typeKeyword");
        PageUtil<Medicine> medicinePage = getPageUtilByHttpServletRequest(request, Medicine.class);
        medicinePage = medicineService.findByNameOrType(medicinePage, nameKeyword, typeKeyword);
        request.setAttribute("nameKeyword", nameKeyword);
        request.setAttribute("typeKeyword", typeKeyword);
        request.setAttribute("medicinePage", medicinePage);
        try {
            request.getRequestDispatcher("medicine/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getDetailById(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter("id");
        String referer = request.getHeader("referer");
        if (idString != null) {
            int id = Integer.parseInt(idString);
            Medicine medicine = medicineService.findById(id);
            if (medicine != null) {
                request.setAttribute("medicine", medicine);
                try {
                    request.getRequestDispatcher("/medicine/look.jsp").forward(request, response);
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

    private Medicine getMedicineByHttpServletRequest(HttpServletRequest request) {
        String idString = request.getParameter("id");
        String picture = request.getParameter("picture");
        String inPriceString = request.getParameter("inPrice");
        String salPriceString = request.getParameter("salPrice");
        String name = request.getParameter("name");
        String typeString = request.getParameter("type");
        String simpleDescription = request.getParameter("simpleDescription");
        String qualityDateString = request.getParameter("qualityDate");
        String detailedDescription = request.getParameter("detailedDescription");
        String productFirm = request.getParameter("productFirm");
        String readme = request.getParameter("readme");
        String remark = request.getParameter("remark");
        Integer id = idString == null ? null : Integer.parseInt(idString);
        Float inPrice = inPriceString == null ? null : Float.parseFloat(inPriceString);
        Float salPrice = salPriceString == null ? null : Float.parseFloat(salPriceString);
        Integer type = typeString == null ? null : Integer.parseInt(typeString);
        Integer qualityDate = qualityDateString == null ? null : Integer.parseInt(qualityDateString);
        if (name != null && !name.isBlank()) {
            return new Medicine(id, picture, inPrice, salPrice, name, type, simpleDescription, qualityDate, detailedDescription, productFirm, readme, remark);
        }
        return null;
    }


}
