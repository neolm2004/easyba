package org.neolm.batools.core.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author neolm
 */
public class FileUtil {

	public static String getFileEncoding() {
		String str = System.getProperty("file.encoding");
		if (null == str) {
			return "";
		}
		str = str.trim().toUpperCase();
		if (str.equals("GBK")) {
			str = "GB2312";
		} else if (str.equals("ISO8859_1")) {
			str = "ISO-8859-1";
		}
		return str;
	}

	public static String[] getFileList(String sDir, final String filterName) {
		File fileDir = new File(sDir);
		return fileDir.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String rr = new File(name).toString();
				return rr.endsWith(filterName);
			}
		});
	}


	public static void newDirectory(String folderPath) throws IOException {
		ensurePathValid(folderPath);
		File myFilePath = new File(folderPath);
		if (!myFilePath.exists()) {
			myFilePath.mkdir();
		}
	}

	
	public static File newFile(String filePathAndName) throws IOException {
		ensurePathValid(filePathAndName);
		File file = new File(filePathAndName);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	
	public static void newFile(String filePathAndName, String fileContent) throws IOException {
		FileWriter resultFile = null;
		PrintWriter myFile = null;
		try {
			File file = newFile(filePathAndName);
			resultFile = new FileWriter(file);
			myFile = new PrintWriter(resultFile);
			myFile.print(fileContent);
			resultFile.close();
		} finally {
			try {
				if (resultFile != null)
					resultFile.close();
			} catch (Exception e) {
			}
			try {
				if (myFile != null)
					myFile.close();
			} catch (Exception e) {
			}
		}
	}

	
	public static void newFile(String filePathAndName, byte[] fileContent) throws IOException {
		FileOutputStream resultFile = null;
		try {
			File file = newFile(filePathAndName);
			resultFile = new FileOutputStream(file);
			resultFile.write(fileContent);
			resultFile.close();
		} finally {
			try {
				resultFile.close();
			} catch (Exception ex) {
				// do nothing
			}
		}
	}

	
	public static void ensurePathValid(String path) throws IOException {
		int i = path.lastIndexOf(File.separator);
		if (i < 0) {
			throw new IllegalArgumentException( path);
		}
		String parent = path.substring(0, i);
		if (!isDirectory(parent)) {
			newDirectory(parent);
		}
	}

	
  public static boolean delFile(String filePathAndName) {
    java.io.File myDelFile = new java.io.File(filePathAndName);
    if (!myDelFile.exists()) {
      return false;
    } else if (myDelFile.isFile()) {
      return myDelFile.delete();
    } else {
      return false;
    }
  }


	public static void delFolder(String folderPath) {
		delAllFile(folderPath); // 删锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
		String filePath = folderPath;
		filePath = filePath.toString();
		java.io.File myFilePath = new java.io.File(filePath);
		myFilePath.delete(); 
	}

	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);			
				delFolder(path + "/" + tempList[i]);
				}
		}
	}


	public static void copyFile(String oldPath, String newPath) throws IOException {
		// int bytesum = 0;
		int byteread = 0;
		File oldfile = new File(oldPath);
		if (oldfile.exists()) { // 锟侥硷拷锟斤拷锟斤拷时
			InputStream inStream = new FileInputStream(oldPath); // 锟斤拷锟斤拷原锟侥硷拷
			FileOutputStream fs = new FileOutputStream(newPath);
			byte[] buffer = new byte[1444];
			while ((byteread = inStream.read(buffer)) != -1) {
				// bytesum += byteread; // 锟街斤拷锟斤拷 锟侥硷拷锟斤拷小
				// System.out.println(bytesum);
				fs.write(buffer, 0, byteread);
			}
			inStream.close();
		}
	}


	public static void copyFolder(String oldPath, String newPath) throws IOException {
		ensurePathValid(newPath); // 锟斤拷锟斤拷募锟斤拷胁锟斤拷锟斤拷锟�锟斤拷b锟斤拷锟侥硷拷锟斤拷
		File a = new File(oldPath);
		String[] file = a.list();
		File temp = null;
		for (int i = 0; i < file.length; i++) {
			if (oldPath.endsWith(File.separator)) {
				temp = new File(oldPath + file[i]);
			} else {
				temp = new File(oldPath + File.separator + file[i]);
			}
			if (temp.isFile()) {
				FileInputStream input = new FileInputStream(temp);
				FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
				byte[] b = new byte[1024 * 5];
				int len;
				while ((len = input.read(b)) != -1) {
					output.write(b, 0, len);
				}
				output.flush();
				output.close();
				input.close();
			}
			if (temp.isDirectory()) {
				copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
			}
		}
	}


	public static void moveFile(String[] oldFiles, String srcPath, String destPath) {
		if (oldFiles == null || oldFiles.length == 0 || srcPath == null || srcPath.trim().length() == 0 || destPath == null
				|| destPath.trim().length() == 0)
			return;
		for (int i = 0; i < oldFiles.length; i++) {
			String srcFile = null;
			if (srcPath.endsWith(File.separator))
				srcFile = srcPath + oldFiles[i];
			else
				srcFile = srcPath + oldFiles[i];
			String destFile = null;
			if (destPath.endsWith(File.separator))
				destFile = destPath + oldFiles[i];
			else
				destFile = destPath + File.separator + oldFiles[i];
			moveFile(srcFile, destFile);
		}
	}


	public static void moveFile(String[] oldFiles, String destPath) {
		if (oldFiles == null || oldFiles.length == 0 || destPath == null || destPath.trim().length() == 0)
			return;
		for (int i = 0; i < oldFiles.length; i++) {
			String tmp = oldFiles[i].substring(oldFiles[i].lastIndexOf(File.separator) + File.separator.length());
			String destFile = null;
			if (destPath.endsWith(File.separator))
				destFile = destPath + tmp;
			else
				destFile = destPath + File.separator + tmp;
			moveFile(oldFiles[i], destFile);
		}
	}

	
	public static void moveFile(String oldPath, String newPath) {
		File f = new File(oldPath);
		// 锟斤拷锟侥匡拷锟斤拷募锟斤拷汛锟斤拷诘幕锟斤拷岬硷拷锟斤拷贫锟绞э拷埽锟斤拷薷模锟斤拷锟斤拷锟窖达拷锟节撅拷锟斤拷目锟斤拷锟侥硷拷锟斤拷锟斤拷痈锟斤拷锟斤拷锟�wangqun
		File newFile = new File(newPath);
		if (newFile.exists()) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateStr = formatter.format(new Date());
			newPath = newPath + "." + dateStr;
		}
		f.renameTo(new File(newPath));
	}

	public static void moveFolder(String oldPath, String newPath) throws IOException {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}


	public static String[] getFileListStartsWithFilter(String sDir, final String filterBegin) {
		File fileDir = new File(sDir);
		return fileDir.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String rr = new File(name).toString();
				return (rr.startsWith(filterBegin));
			}
		});
	}

	public static String getFileContent(String fileName, String charset) throws Exception {
		try {
			return new String(getFileContentBytes(fileName), charset);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage() + "[" + fileName + "]", ex);
		}
	}


	public static long getFileSize(String filepath) {
		File file = new File(filepath);
		return file.length();
	}


	public static String getFileContent(String fileName) throws Exception {
		return new String(getFileContentBytes(fileName));
	}

	public static String getFileContent(File file) throws Exception {
		try {
			return new String(getFileContentBytes(new FileInputStream(file)));
		} catch (IOException ex) {
			throw new Exception(ex.getMessage() + "[" + file + "]", ex);
		}
	}


	public static byte[] getFileContentBytes(String fileName) throws Exception {
		try {
			return getFileContentBytes(new FileInputStream(fileName));
		} catch (IOException e) {
			throw new Exception(e.getMessage() + "[" + fileName + "]", e);
		}
	}


	public static byte[] getFileContentBytes(InputStream is) throws IOException {
		ByteArrayOutputStream byOut = new ByteArrayOutputStream();
		try {
			byte[] datas = new byte[1024];
			int len = -1;
			while ((len = is.read(datas)) > 0) {
				byOut.write(datas, 0, len);
			}
			return byOut.toByteArray();
		} finally {
			
			byOut.close();
			is.close();
		}
	}


	public static boolean isFile(String filepath) throws IOException {
		return checkFileExists(filepath) != null;
	}


	public static boolean isDirectory(String dirpath) throws IOException {
		return checkDirectoryExists(dirpath) != null;
	}


	public static File checkFileExists(String filepath) throws IOException {
		File file = new File(filepath);
		if (!file.isFile()) {
			return null;
		}
		return file;
	}

	
	public static File checkDirectoryExists(String dirpath) {
		File dir = new File(dirpath);
		if (!dir.isDirectory()) {
			
			return null;
		}
		return dir;
	}

	public static void appendFile(String fileName, String content) throws Exception {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileName, true);
			fileWriter.write(content);
			fileWriter.flush();
		} catch (java.lang.Exception e) {
			throw new Exception(e.getMessage() + "[" + fileName + "]", e);
		} finally {
			try {
				if (fileWriter != null) {
					fileWriter.close();
					fileWriter = null;
				}
			} catch (Exception e) {
				// do nothing
			}
		}
	}

	public static void makeDir(String dir) throws Exception {
		try {
			File f = new File(dir);
			// 2008-09-08 zengxr f.mkdir to f.mkdirs
			f.mkdirs();
		} catch (Exception e) {
			throw new Exception(e.getMessage() + "[" + dir + "]", e);
		}
	}

	public static InputStream getStream(String fileName) throws Exception {
		InputStream in;
		try {
			in = new FileInputStream(fileName);
		} catch (FileNotFoundException f) {
			// 
			// 
			in = FileUtil.class.getResourceAsStream("/" + fileName);
		}
		if (in == null)
			throw new Exception("[" + fileName + "]");
		return in;
	}

	
	public static Properties loadInitProperties(String fileName) throws Exception {
		Properties p = new Properties();
		InputStream in = null;
		try {
			in = FileUtil.getStream(fileName);
			p.load(in);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
		return p;
	}
}
