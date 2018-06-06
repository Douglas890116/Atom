package com.maven.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.maven.logger.LoggerManager;
import com.maven.logger.SwithObject;
import com.maven.util.VerifyCodeUtils;

public class ValidateCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static LoggerManager log = LoggerManager.getLogger("MAIN", new SwithObject(true));
	
	private static final String[] domains = {
			"http://www.hyzonghe.net", 
			"https://www.hyzonghe.net", 
			"http://www.168jinta.com", 
			"https://www.168jinta.com", 
			"http://www.168egg.com",
			"https://www.168egg.com",
			"http://127.0.0.1:8080/ecrm-v/",//本地测试
	};

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			boolean flag = isValidationDomain(request.getHeader("Referer"));
			flag = true;
			response.setContentType("image/jpeg");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			HttpSession session = request.getSession();
			
//			session.removeAttribute("verifyCode");
//			ValidateCode vCode = new ValidateCode(90,30,4,50);
//			session.setAttribute("verifyCode", vCode.getCode());
//			log.Debug(vCode.getCode());
//			vCode.write(response.getOutputStream());
			
			String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
			long timestamp = System.currentTimeMillis()/1000;
			session.setAttribute("verifyCode", verifyCode.toUpperCase() + "_" + timestamp);
			log.Debug(verifyCode.toLowerCase());
			int w = 250, h = 70;  
			OutputStream os = response.getOutputStream();
			VerifyCodeUtils.outputImage(w, h, os, flag?verifyCode:"ERROR!");
			os.close();  
			os.flush();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.Error(e.getMessage(),e);
		}
	}
	
	private boolean isValidationDomain(String domain) {
		for (String sample : domains) {
			if (domain.startsWith(sample))
				return true;
		}
		return false;
	}
}