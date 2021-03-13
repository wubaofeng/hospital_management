package me.wbf.hospital.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.wbf.hospital.util.PageUtil;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 根据参数中的method参数调用相应的方法
 *
 * @author Baofeng.Wu
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String method = request.getParameter("method");
		Class<? extends BaseServlet> clazz = this.getClass();
		try {
			Method m = clazz.getDeclaredMethod(method, HttpServletRequest.class,HttpServletResponse.class);
			m.invoke(this, request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void writeJSONToResponse(HttpServletResponse response, Object data) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			writeToResponse(response, objectMapper.writeValueAsString(data));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	protected void writeToResponse(HttpServletResponse response, Object data) {
		try {
			response.getWriter().print(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected  <T> PageUtil<T> getPageUtilByHttpServletRequest(HttpServletRequest request, Class<T> tClass) {
		String pageStr = request.getParameter("page");
		String sizeStr = request.getParameter("size");
		int currentPage = pageStr != null ? Integer.parseInt(pageStr) : 1;
		int size = sizeStr != null ? Integer.parseInt(sizeStr) : 10;
		PageUtil<T> pageUtil = new PageUtil<>(currentPage, size);
		return pageUtil;
	}

}
