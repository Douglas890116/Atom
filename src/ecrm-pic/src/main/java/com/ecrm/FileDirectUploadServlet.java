package com.ecrm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.util.Streams;


/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet("/FileUploadServlet3")
public class FileDirectUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public FileDirectUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
        super.init();  
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 	response.setContentType("text/html");     
	        response.setCharacterEncoding("UTF-8");  
	        String realDir = request.getSession().getServletContext().getRealPath("");  
	        String contextpath = request.getContextPath();  
	        String basePath = request.getScheme() + "://"  
	        + request.getServerName() + ":" + request.getServerPort()  
	        + contextpath + "/";  
	  
	        try {  
	        String type = request.getParameter("type");
	        String filePath = "uploadfiles";
	        //根据type参数对图片分类存储
	        if(type!=null) {
	        	if(type.trim().equals("logo")){
	        		filePath ="logo";
	        	}else if(type.trim().equals("deposit")){
	        		filePath+="deposit";
	        	}else if(type.trim().equals("draw")){
	        		filePath+="take";
	        	}
	        }
	        String realPath = realDir+"\\"+filePath;  
	        //判断路径是否存在，不存在则创建  
	        File dir = new File(realPath);  
	        if(!dir.isDirectory())  
	            dir.mkdir();  
	  
	        if(ServletFileUpload.isMultipartContent(request)){  
	  
	            DiskFileItemFactory dff = new DiskFileItemFactory();  
	            dff.setRepository(dir);  
	            dff.setSizeThreshold(1024000);  
	            ServletFileUpload sfu = new ServletFileUpload(dff);  
	            FileItemIterator fii = null;  
	            fii = sfu.getItemIterator(request);  
	            String url = "";    //图片地址  
	            String fileName = "";  
	            String state="faild";  
	            String retxt = "";
	            String realFileName="";  
	            while(fii.hasNext()){  
	                FileItemStream fis = fii.next();  
	                try{  
	                    if(!fis.isFormField() && fis.getName().length()>0){  
	                        fileName = fis.getName();  
	                        Pattern reg=Pattern.compile("[.]jpg|png|jpeg|gif$");  
	                        Matcher matcher=reg.matcher(fileName);  
	                        if(!matcher.find()) {  
	                            state = "文件类型不允许！";  
	                            break;  
	                        }  
	                        realFileName = new Date().getTime()+fileName.substring(fileName.lastIndexOf("."),fileName.length());  
	                        url = realPath+"\\"+realFileName;  
	  
	                        BufferedInputStream in = new BufferedInputStream(fis.openStream());//获得文件输入流  
	                        FileOutputStream a = new FileOutputStream(new File(url));  
	                        BufferedOutputStream output = new BufferedOutputStream(a);  
	                        Streams.copy(in, output, true);//开始把文件写到你指定的上传文件夹  
	                        state="success";  
	                    }else{
	                    	state="filename is null"; 
	                    }
	                }catch(Exception e){  
	                    e.printStackTrace();  
	                }  
	            }  
	            response.setStatus(200);  
	            retxt = "{'url':'"+basePath+filePath+"/"+realFileName+"','status':'"+state+"'}";  
	            response.getWriter().write(retxt);  
	        }  
	        }catch(Exception ee) {  
	            ee.printStackTrace();  
	        }  
	          
	}

}
