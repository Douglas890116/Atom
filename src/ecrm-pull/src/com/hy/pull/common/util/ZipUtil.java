package com.hy.pull.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ZipUtil {
	/**
	 * 压缩文件/文件夹 为zip格式
	 * 
	 * @param path
	 *            待压缩的文件/文件夹
	 * @param folder
	 *            压缩后的文件完成路径
	 * @return 压缩成功返回true
	 */
	public static boolean zip(String[] filePaths, String file) {
		boolean isSuccessful = false;

		String[] fileNames = new String[filePaths.length - 1];
		for (int i = 0; i < filePaths.length - 1; i++) {
			fileNames[i] = parse(filePaths[i]);
		}

		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			ZipOutputStream zos = new ZipOutputStream(bos);
			String entryName = null;
			entryName = fileNames[0];

			for (int i = 0; i < fileNames.length; i++) {
				entryName = fileNames[i];

				// 创建Zip条目
				ZipEntry entry = new ZipEntry(entryName);
				zos.putNextEntry(entry);

				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePaths[i]));

				byte[] b = new byte[1024];

				while (bis.read(b, 0, 1024) != -1) {
					zos.write(b, 0, 1024);
				}
				bis.close();
				zos.closeEntry();
			}

			zos.flush();
			zos.close();
			isSuccessful = true;
		} catch (IOException e) {
		}

		return isSuccessful;
	}

	/**
	 * 解压zip文件
	 * 
	 * @param file
	 *            待解压zip完整路径
	 * @param folder
	 *            解压后存放路径
	 * @return 解压成功返回true
	 */
	public static boolean unzip(String file, String folder) {
		boolean isSuccessful = true;
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			ZipInputStream zis = new ZipInputStream(bis);

			BufferedOutputStream bos = null;

			ZipEntry entry = null;
			new File(folder).mkdirs();
			while ((entry = zis.getNextEntry()) != null) {
				String entryName = entry.getName();
				bos = new BufferedOutputStream(new FileOutputStream(folder + "/" + entryName));
				int b = 0;
				while ((b = zis.read()) != -1) {
					bos.write(b);
				}
				bos.flush();
				bos.close();
			}
			zis.close();
		} catch (IOException e) {
			isSuccessful = false;
		}
		return isSuccessful;
	}
	/**
	 * 直接读取zip文件里的文件内容
	 * @param path
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String readZipFile(String path) {
		StringBuffer str = new StringBuffer();
		try {
			ZipFile zf = new ZipFile(path);
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
			ZipInputStream zis = new ZipInputStream(bis);
			ZipEntry ze;
			while ((ze = zis.getNextEntry()) != null) {
				if (ze.isDirectory()) {
					// 可以继续处理文件夹
				} else {
					BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));
					String line;
					while ((line = br.readLine()) != null) {
						str.append(line);
					}
					br.close();
				}
			}
			zis.closeEntry();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
	}

	// 解析文件名
	private static String parse(String srcFile) {
		int location = srcFile.lastIndexOf("/");
		String fileName = srcFile.substring(location + 1);
		return fileName;
	}

	public static void main(String[] args) {
		String file = "D:/jdb168/20171027/Bar Game/201710271345_201710271350.zip";
		String content = readZipFile(file);
		System.out.println(content);
		JSONArray array = JSONArray.fromObject(content);
		if (array != null && array.size() > 0) {
			JSONObject object = null;
			for (int i = 0; i < array.size(); i++) {
				object = array.getJSONObject(i);
				System.err.println(object.getInt("seqNo"));
				
			}
		}
	}
}