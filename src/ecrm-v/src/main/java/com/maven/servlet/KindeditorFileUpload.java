package com.maven.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;

import com.maven.util.JSONUnit;

public class KindeditorFileUpload extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final HashMap<String, String> extMap = new HashMap<String, String>();
	
	public KindeditorFileUpload(){
		super();
		//定义允许上传的文件扩展名
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//输出对象
		PrintWriter out = response.getWriter();
		try {
			//文件保存目录路径
			String savePath = request.getSession().getServletContext().getRealPath("/") + "/temp/activity/";
	
			//最大文件大小
			long maxSize = 1000000;
	
			response.setContentType("text/html; charset=UTF-8");
	
			if(!ServletFileUpload.isMultipartContent(request)){
				out.println(getError("请选择文件。"));
				return;
			}
			//检查目录
			File uploadDir = new File(savePath);
			if(!uploadDir.isDirectory()){
				uploadDir.mkdirs();
			}
			//检查目录写权限
			if(!uploadDir.canWrite()){
				out.println(getError("上传目录没有写权限。"));
				return;
			}
	
			String dirName = request.getParameter("dir");
			if (dirName == null) {
				dirName = "image";
			}
			if(!extMap.containsKey(dirName)){
				out.println(getError("目录名不正确。"));
				return;
			}
			//创建文件夹
			savePath += dirName + "/";
			File saveDirFile = new File(savePath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			savePath += ymd + "/";
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
	
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			List<FileItem> items;
			
			items = upload.parseRequest(request);
			
			Iterator<FileItem> itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				String fileName = item.getName();
				if (!item.isFormField()) {
					//检查文件大小
					if(item.getSize() > maxSize){
						out.println(getError("上传文件大小超过限制。"));
						return;
					}
					//检查扩展名
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
						out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
						return;
					}
					String returnData = FileTransfer.upload(item.getInputStream(), fileName, "activity",dirName);
					String url = String.valueOf(JSONUnit.getMapFromJson(returnData).get("url"));
					if(StringUtils.isNotBlank(url)){
						JSONObject obj = new JSONObject();
						obj.put("error", 0);
						obj.put("url", url);
						out.println(obj.toJSONString());
					}else{
						String status = String.valueOf(JSONUnit.getMapFromJson(returnData).get("status"));
						out.println(getError(status));
					}
					return;
				}
			}
		} catch (FileUploadException ex) {
			ex.printStackTrace();
			out.println(getError("上次文件失败。"));
		}catch(Exception ex){
			ex.printStackTrace();
			out.println(getError("上次文件失败。"));
		}finally {
			out.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}

}
