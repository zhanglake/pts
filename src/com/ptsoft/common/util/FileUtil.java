package com.ptsoft.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ptsoft.common.Constants;

public class FileUtil
{
	/**
	 * 上传文件
	 */
	@SuppressWarnings("unused")
	public static void UpLoadFile(MultipartFile file) throws IOException
	{
		File uploadDir = new File(Constants.UPLOAD_PATH);
		if (!uploadDir.exists() && !uploadDir.mkdirs())
		{
			uploadDir.mkdirs();
		}

		FileOutputStream fs = new FileOutputStream(Constants.UPLOAD_PATH + file.getOriginalFilename());
		byte[] buffer = new byte[1024 * 1024];
		int bytesum = 0;
		int byteread = 0;
		InputStream stream = file.getInputStream();
		while ((byteread = stream.read(buffer)) != -1)
		{
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}

	/**
	 * 下载文件
	 */
	public static void DownloadFile(HttpServletResponse response, HttpServletRequest request, String filePath, String fileName) throws IOException
	{
		response.reset();// 可以加也可以不加
		response.setContentType("application/x-download");// 设置为下载application/x-download
		String filenamedownload = filePath;
		String filenamedisplay = fileName;// 系统解决方案.txt
		
		//判断文件名编码格式是否是UTF-8,否则转换编码
		String encode = "UTF-8";  
		if(StringUtils.isNotEmpty(request.getHeader("User-Agent")))
		{
			if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) 
			{  
				//ie
				filenamedisplay = URLEncoder.encode(filenamedisplay, encode);  
			} 
			else 
			{  
				//firefox chrome
			    filenamedisplay = new String(filenamedisplay.getBytes(encode), "ISO8859-1");  
			}
		}
		else 
		{
			filenamedisplay = URLEncoder.encode(filenamedisplay, encode);  
		}
		
		response.addHeader("Content-Disposition", "attachment;filename=" + filenamedisplay);

		OutputStream output = null;
		FileInputStream fis = null;
		try
		{
			output = response.getOutputStream();
			fis = new FileInputStream(filenamedownload);

			byte[] b = new byte[1024];
			int i = 0;

			while ((i = fis.read(b)) > 0)
			{
				output.write(b, 0, i);
			}
			output.flush();
		}
		catch (Exception e)
		{
			System.out.println("Error!");
			e.printStackTrace();
		}
		finally
		{
			if (fis != null)
			{
				fis.close();
				fis = null;
			}
			if (output != null)
			{
				output.close();
				output = null;
			}
		}

	}

	/**
	 * 复制文件夹内容
	 * @param fromPath 来源
	 * @param toPath 目标
	 * @throws Exception 
	 */
	public static void CopyFolder(String fromPath, String toPath) throws Exception
	{
		try
		{
			File oFiles = new File(fromPath);
			String[] file = oFiles.list();
			File temp = null;
			for (int i = 0; i < file.length; i++)
			{
				if (fromPath.endsWith(File.separator))
				{
					temp = new File(fromPath + file[i]);
				}
				else
				{
					temp = new File(fromPath + File.separator + file[i]);
				}

				if (temp.isFile())
				{
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(toPath + File.separator + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1)
					{
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory())
				{
					//如果是子文件夹 
					CopyFolder(fromPath + File.separator + file[i], toPath + File.separator + file[i]);
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("复制整个文件夹内容操作出错:" + fromPath);
			throw e;
		}
	}
	
	/**
	 * 删除某个文件夹下的所有文件夹和文件
	 * 
	 * @param delpath 文件路径
	 */
	public static boolean DeleteFile(String delpath) throws Exception
	{
		try
		{
			File file = new File(delpath);
			// 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true  
			if (!file.isDirectory())
			{
				file.delete();
			}
			else if (file.isDirectory())
			{
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++)
				{
					File delfile = new File(delpath + File.separator + filelist[i]);
					if (!delfile.isDirectory())
					{
						delfile.delete();
					}
					else if (delfile.isDirectory())
					{
						DeleteFile(delpath + File.separator + filelist[i]);
					}
				}
				file.delete();
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("deletefile() Exception:" + e.getMessage());
		}
		return true;
	}

	/**
	 * 取得文件的创建时间
	 */
	public static Date GetFileCreateDate(File _file)
	{
		File file = _file;
		BufferedReader br = null;
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		try
		{
			Process ls_proc = Runtime.getRuntime().exec("cmd.exe /c dir " + file.getAbsolutePath() + " /tc");
			br = new BufferedReader(new InputStreamReader(ls_proc.getInputStream()));
			for (int i = 0; i < 5; i++)
			{
				br.readLine();
			}
			String stuff = br.readLine();
			StringTokenizer st = new StringTokenizer(stuff);
			String dateC = st.nextToken();
			String time = st.nextToken();
			String datetime = dateC.concat(" " + time);

			return formatter1.parse(datetime);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 文件上传
	 * @author jqi.can
	 * @param type 1-图片文件 2-excel文件
	 * @date 2016-1-28上午10:35:02
	 */
	public static String fileUpload(HttpServletRequest request, int type) 
	{
		try 
		{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Iterator<String> fileNames = multipartRequest.getFileNames();
			if(fileNames.hasNext())
			{
				CommonsMultipartFile mpartFile = (CommonsMultipartFile) multipartRequest.getFile(fileNames.next());
				String filename = mpartFile.getOriginalFilename();
				String extendname = filename.substring(filename.lastIndexOf(".") + 1);
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				ServletContext sc = request.getSession().getServletContext();
				String wbPath = sc.getRealPath("");
				if(type == 1)
				{
					wbPath += Constants.UPLOAD_LOGO_PATH;
				}
				else if(type == 2)
				{
					wbPath += Constants.UPLOAD_XLS_PATH;
				}
				StringBuilder destFileName = new StringBuilder(wbPath);
				destFileName.append(uuid).append(".").append(extendname);
				File dir = new File(destFileName.toString());
				if (!dir.exists())
					dir.mkdirs();
				File destFile = new File(destFileName.toString());
				mpartFile.transferTo(destFile);
				if(type == 2)
				{
					return filename + uuid + "." + extendname;
				}
				else
				{
					return uuid + "." + extendname;
				}
			}
			else
			{
				return null;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}

}
