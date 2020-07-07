package cn.qf.utils;

import cn.qf.utils.CommonUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class FileIOUtil implements Serializable{

	public static Boolean volidateDir(String path, FilenameFilter filter) {
		boolean result = false;
		if(!StringUtils.isEmpty(path) && null != filter){
			File dir = new File(path);
			if(dir.exists() && dir.isDirectory()){
				String[] files = dir.list(filter);
				if(null != files && files.length > 0){
					result = true;
				}
			}
		}
		return result;
	}

	public static Boolean volidateDir(File dir, FilenameFilter filter) {
		boolean result = false;
		if(null != dir && null != filter){
			if(dir.exists() && dir.isDirectory()){
				String[] files = dir.list(filter);
				if(null != files && files.length > 0){
					result = true;
				}
			}
		}
		return result;
	}

	public static File[] getFiles(File dir, FilenameFilter filter) {
		File[] result = null;
		if(null != dir && null != filter){
			if(dir.exists() && dir.isDirectory()){
				result = dir.listFiles(filter);
			}
		}
		return result;
	}

	public static Boolean createdFile(String fileName, String dirPath) {
		boolean result = false;
		String filePath = null;
		File nFile = null;
		try {
			if (!StringUtils.isEmpty(fileName) && !StringUtils.isEmpty(dirPath)) {

				dirPath.replace("\\", "/");

				File dirFile = new File(dirPath);
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}

				nFile = new File(dirFile, fileName);
				if(nFile.exists()){
					result = true;
				}else {
					result = nFile.createNewFile();
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return result;
	}

	public static InputStream loadFile(String path) {
		File file = null;
		InputStream is = null;
		try {
			if (!StringUtils.isEmpty(path)) {
				file = new File(path);
				if (file.exists()) {
					is = new FileInputStream(file);
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return is;
	}

	/**
	 * 移动文件到目录
	 *
	 * @param srcFile
	 * @param dest
	 * @return
	 */
	public static boolean moveFile2Dir(File srcFile, String dest) {
		boolean result = false;
		try {
			if (null != srcFile && srcFile.exists() && !StringUtils.isEmpty(dest)) {
				File destFile = new File(dest);
				FileUtils.moveFileToDirectory(srcFile, destFile, true);
				result = true;
			}
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return result;
	}

	public static boolean bakFile(File file) {
		boolean result = false;
		try {
			if (null != file && file.exists()) {
				String path = file.getPath();
				File bak = new File(path + ".bak");
				result = file.renameTo(bak);
			}
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return result;
	}

	public static Long getSize(File file) {
		Long size = 0L;
		Long temp = 0L;
		if (null != file) {
			temp = file.length();
			size = temp / (1024 * 1024);
		}
		return size;
	}



	public static String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
	
	/**
	 * 写入多行
	 * @param path
	 */
	public static void multiRow(String path){
		FileReader fr = null;
		try {
			fr = new FileReader(path);
			int tx = 0;
			while((tx = fr.read()) != -1){
				System.out.write(tx);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(null != fr){
					fr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public static String getPath(String parentPath, String name) {
		String path = null;
		if (!StringUtils.isEmpty(parentPath) && !StringUtils.isEmpty(name)) {
			// 字符转码
			// parentPath = changeChar(parentPath,"UTF-8");
			// name = changeChar(name,"UTF-8");

			if (parentPath.indexOf("\\") != -1) {
				parentPath.replaceAll("\\\\", "/");
			}

			if (parentPath.lastIndexOf("/") != parentPath.length() - 1) {
				path = parentPath + "/" + name;
			} else {
				path = parentPath + name;
			}
		}
		return path;
	}

	public static String movePath(String from, String to) {
		String path = null;
		if (!StringUtils.isEmpty(from) && !StringUtils.isEmpty(to)) {
			// 字符转码
			// from = changeChar(from,"UTF-8");
			// to = changeChar(to,"UTF-8");

			if (from.indexOf("\\") != -1) {
				from.replaceAll("\\\\", "/");
			}

			if (to.indexOf("\\") != -1) {
				to.replaceAll("\\\\", "/");
			}

			path = to
					+ from.substring(from.lastIndexOf("/") + 1, from.length());
		}
		return path;
	}
	
	//---字节流相关-------------------------------------------------------------
		public static InputStream getInputStream2File(String path){
			InputStream is = null;
			try {
				if(!StringUtils.isEmpty(path)){
					is = new FileInputStream(path);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return is;
		}
		
		public static void readData(String path){
			DataInputStream dis = null;
			BufferedInputStream bis = null;
			FileInputStream fis = null;
			try {
				if(!StringUtils.isEmpty(path)){
					File sourceFile = new File(path);
					if(sourceFile.exists()){
						fis = new FileInputStream(sourceFile);
						bis = new BufferedInputStream(fis);
						dis = new DataInputStream(bis);
						
						Integer data4Int = dis.readInt();
						Double data4Double = dis.readDouble();
						char data4Char = dis.readChar();
						
						System.out.println("data4Int="+data4Int);
						System.out.println("data4Double="+data4Double);
						System.out.println("data4Char="+data4Char);
						
					}
				}
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			}catch (IOException ioe) {
				ioe.printStackTrace();
			}finally{
				try {
					if(null != dis){
						dis.close();
					}
					if(null != bis){
						bis.close();			
					}
					if(null != fis){
						fis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		public static void writeData(String path,Map<String,Object> data){
			DataOutputStream dos = null;
			FileOutputStream fos = null;
			try {
				if(!StringUtils.isEmpty(path)){
					File sourceFile = new File(path);
					fos = new FileOutputStream(sourceFile);
					dos = new DataOutputStream(fos);
					
					if(!CommonUtil.isEmpty4Map(data)){
						for(Iterator ite = data.entrySet().iterator(); ite.hasNext();){
							Map.Entry<String,Object> entryData = (Map.Entry<String,Object>)ite.next();
							
							String key = entryData.getKey();
							Object value = entryData.getValue();
							
							if(Integer.class.getName().equals(key)){
								Integer value4Int = (Integer)value;
								dos.writeInt(value4Int);
							}else if(Double.class.getName().equals(key)){
								Double value4Double = (Double)value;
								dos.writeDouble(value4Double);
							}else if(Character.class.getName().equals(key)){
								Integer value4Int = (Integer)value;
								dos.writeChar(value4Int);
							}else if(String.class.getName().equals(key)){
								String value4String = (String)value;
								dos.writeChars(value4String);
							}
							
							dos.writeChars("\n");
							
						}
						
						dos.flush();
					}
				}
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			}catch (IOException ioe) {
				ioe.printStackTrace();
			}finally{
				try {
					if(null != dos){
						dos.close();
					}
					if(null != fos){
						fos.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
		
		public static void readPushback(){
			ByteArrayInputStream bais = null;
			PushbackInputStream pbis = null;
			try {
				String info = "abcdefg";
				byte[] data = info.getBytes();
				
				bais = new ByteArrayInputStream(data);
				pbis = new PushbackInputStream(bais);
				
				byte[] idata = new byte[1];
				int idx = 0;
				while(pbis.read(idata) != -1)
				{
					if(idx % 2 == 0){
						pbis.unread(idata);
					}
					
					System.out.println("info[" + idx + "]=" + new String(idata));
					
					idx++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if(null != pbis){
						pbis.close();
					}
					
					if(null != bais){
						bais.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		//---Unicode-------------------------------------------------------------
		public static void copyFile(String sourcePath,String destPath)
		{
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				if(!StringUtils.isEmpty(sourcePath) && !StringUtils.isEmpty(destPath)){
					File sourceFile = new File(sourcePath);
					if(sourceFile.exists()){
						fis = new FileInputStream(sourceFile);
						fos = new FileOutputStream(destPath);
						
						int inputData = 0;
						while((inputData = fis.read()) != -1)
						{
							fos.write(inputData);
						}
						fos.flush();
					}
				}
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			}catch (IOException ioe) {
				ioe.printStackTrace();
			}finally{
				try {
					if(null != fis){
						fis.close();
					}
					if(null != fos){
						fos.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		
	
	
	public static String getRandom(int count){
		String s = "abcdefghijkmnopqrstuvwxz";
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<count;i++){
			int idx = new Random().nextInt(22)+1;
			sb.append(s.charAt(idx));
		}
		return sb.toString();
	}
	
	public static String getRandomNum(int count){
		String range = "0123456789";
		int len = range.length();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<count;i++){
			int idx = new Random().nextInt(10)+1;
			if(idx >= len){
				sb.append(range.charAt(idx-len));
			}else {
				sb.append(range.charAt(idx));
			}
		}
		return sb.toString();
	}
	
	
	//-----------------------------------------------

	public static void writeFileLines(String path, List<String> datas, boolean append) throws IOException{
		Validate.notEmpty(path, "file path is empty");
		Validate.notNull(datas, "datas is empty");

		File dataFile = new File(path);
		if(!dataFile.exists()){
			//dataFile.mkdirs();
			dataFile.createNewFile();
		}

		FileUtils.writeLines(dataFile, datas, append);
	}

	public static void writeFile(String path, String data, boolean append) throws IOException{
		Validate.notEmpty(path, "file path is empty");
		Validate.notEmpty(data, "data is empty");

		File dataFile = new File(path);
		if(!dataFile.exists()){
			//dataFile.mkdirs();
			dataFile.createNewFile();
		}

		FileUtils.write(dataFile, data, append);
	}
		

	public static void main(String[] args) throws Exception{
		

	}

}
