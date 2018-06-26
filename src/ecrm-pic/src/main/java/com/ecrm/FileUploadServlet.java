package com.ecrm;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ecrm.util.DateUtils;

/**
 Servlet implementation class FileUploadServlet
 */
@WebServlet("/FileUploadServlet")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String FAILD = "{'url':'','status':'上传失败'}";
	
	private static final String FILENAME_NOT_NULL = "{'url':'','status':'文件名不能为空'}";
	
	private static final String CANTNOT_WRITE_FILE = "{'url':'','status':'上传目录没有写权限'}";	
	
	private static final String FILETYP_EROOR = "{'url':'','status':'文件类型错误，请上传[0]类型文件'}";
	
	private static final HashMap<String, String> extMap = new HashMap<String, String>();

	/**
	 @see HttpServlet#HttpServlet()
	 */
	public FileUploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init();
		//定义允许上传的文件扩展名
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
	}

	private String getHttpPath(HttpServletRequest request) {
		String contextpath = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ contextpath + "/";
		return basePath;
	}
	private String getFilePath(HttpServletRequest request) {
		String type = request.getParameter("type");
		String filePath = "uploadfiles/";
		// 根据type参数对图片分类存储
		if (type != null) {
			if (type.trim().equals("logo")) {
				filePath += "/logo/";
			} else if (type.trim().equals("deposit")) {
				filePath += "/deposit/"+DateUtils.getYearMonthDay()+"/";
			} else if (type.trim().equals("take")) {
				filePath += "/take/"+DateUtils.getYearMonthDay()+"/";
			}else if(type.trim().equals("activity")){
				filePath += "/activity/";
			}
		}
		return filePath;
	}
	
	public static void main(String[] args) {
			System.out.println(DateUtils.getYearMonthDay());
	}

	/**
	 @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processUpload(request, response);
	}

	public void processUpload(HttpServletRequest request, HttpServletResponse response) {
		String realDir = request.getSession().getServletContext().getRealPath("");
		String contextpath = getHttpPath(request);
		String filePath = getFilePath(request);
		String httpPath = contextpath + filePath;
		String physicalPath = realDir+filePath;
		
		String filename = request.getParameter("filename");
		String dirName  = request.getParameter("dir");
		if (dirName == null) dirName = "image";
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			
			if(filename==null || filename.length()<=0){
				response.getWriter().write(FILENAME_NOT_NULL);
				return;
			}
			
			//检查扩展名
			String fileExt = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
			if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
				response.getWriter().write(FILETYP_EROOR.replace("[0]", extMap.get(dirName)));
				return;
			}
			//检查文件路径是否存在
			File uploadFile = new File(physicalPath);
			if (!uploadFile.exists()) {
				uploadFile.mkdirs();
			}
			//检查目录写权限
			if(!uploadFile.canWrite()){
				response.getWriter().write(CANTNOT_WRITE_FILE);
				return;
			}
			//检查临时转储文件路径是否存在
			File uploadtemp = new File(physicalPath + "\\temp");
			if (!uploadtemp.exists()) {
				uploadtemp.mkdirs();
			}
			
			
			// 检测是不是存在上传文件
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);

			String state = "faild";
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				// 指定在内存中缓存数据大小,单位为byte,这里设为10Mb
				factory.setSizeThreshold(1024*1024*10);
				//设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
				factory.setRepository(uploadtemp);
				// Create a new file upload handler
				ServletFileUpload upload = new ServletFileUpload(factory);
				// 指定单个上传文件的最大尺寸,单位:字节，这里设为50Mb
				upload.setFileSizeMax(50*1024*1024);
				// 指定一次上传多个文件的总尺寸,单位:字节，这里设为50Mb
				upload.setSizeMax(50*1024*1024);
				upload.setHeaderEncoding("UTF-8");
				List<FileItem> items = null;
				try {
					// 解析request请求
					items = upload.parseRequest(request);
				} catch (FileUploadException e) {
					e.printStackTrace();
				}

				if (items != null) {
					// 解析表单项目
					Iterator<FileItem> iter = items.iterator();
					while (iter.hasNext()) {
						FileItem item = iter.next();
						if (item.isFormField()) {
							
						} else {
							String beforeFileName = filename;
							String fileName = new Date().getTime() + beforeFileName.substring(beforeFileName.lastIndexOf("."), beforeFileName.length());
							fileName = fileName.substring(fileName.lastIndexOf("/") + 1);// 获得上传文件的文件名
							httpPath += fileName;
							item.write(new File(physicalPath, fileName));
							state = "success";
						}
					}

				}
			}
			String url = "{'url':'" + httpPath + "','status':'" + state + "'}";
			response.getWriter().write(url);
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response.getWriter().write(FAILD);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
