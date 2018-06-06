package com.maven.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.maven.logger.TLogger;
import com.maven.utils.FileHelper;

/**
 * Servlet implementation class FileUpload
 */
public class FileUpload extends HttpServlet {
	
	@SuppressWarnings("unused")
	private static final String FILENAME_NOT_NULL = "{'url':'','status':'文件名不能为空'}";	
	
	private static final String FILETYP_EROOR = "{'url':'','status':'文件类型错误，请上传[0]类型文件'}";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileUpload() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter  out = response.getWriter();
		String realDir = request.getSession().getServletContext().getRealPath("");
		String type = request.getParameter("type");

		try {
			String realPath = realDir + "\\temp";
			File dir = new File(realPath);
			if (!dir.isDirectory())
				dir.mkdir();

			if (ServletFileUpload.isMultipartContent(request)) {

				DiskFileItemFactory dff = new DiskFileItemFactory();
				dff.setRepository(dir);
				dff.setSizeThreshold(1024000);
				ServletFileUpload sfu = new ServletFileUpload(dff);
				FileItemIterator fii = null;
				fii = sfu.getItemIterator(request);
				String fileName = "";
				while (fii.hasNext()) {
					FileItemStream fis = fii.next();
					try {
						if (!fis.isFormField() && fis.getName().length() > 0) {
							fileName = fis.getName();
							
//							不能直接使用上传过来的图片名称，否则乱码及路径难找
//							if(fileName==null || fileName.length()<=0){
//								out.println(FILENAME_NOT_NULL);
//								return;
//							}
							//使用此种方式
							fileName = UUID.randomUUID().toString().replaceAll("-", "") + FileHelper.getFileType(fileName);
							
							Pattern reg = Pattern.compile("[.]jpg|png|jpeg|gif$");
							Matcher matcher = reg.matcher(fileName);
							if (!matcher.find()) {
								out.println(FILETYP_EROOR);
								return;
							}
							out.println(FileTransfer.upload(fis.openStream(), fileName, type,null));
							return;
						}
					} catch (Exception e) {
						TLogger.getLogger().Error(e.getMessage(), e);
					}
				}
			}
		} catch (Exception ee) {
			TLogger.getLogger().Error(ee.getMessage(), ee);
			out.println(FileTransfer.FAILD);
		}finally {
			out.close();
		}
		return;
	}

}
