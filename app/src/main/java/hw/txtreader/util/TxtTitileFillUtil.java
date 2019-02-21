package hw.txtreader.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TxtTitileFillUtil {

	public static void main(String[] args) {
		String txtFile = "D:/BaiduNetdiskDownload/无敌剑域80.txt";
		fillZhangjie(txtFile);
	}
	public static void fillZhangjie(String txtFilePath,String savedPath) {
		System.out.println("start fill :" + txtFilePath);
		try {
			FileInputStream fis = new FileInputStream(txtFilePath);

			BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"));
			String newTxtStr = getFixTxtStr(reader);
			FileOutputStream nFile = new FileOutputStream(savedPath);
			nFile.write(newTxtStr.getBytes());
			nFile.close();
			fis.close();
			System.out.println("finish");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void fillZhangjie(String txtFilePath) {
		int lastIndexOf = txtFilePath.lastIndexOf(".");
		String fName = txtFilePath.substring(0, lastIndexOf);
		fName += "_new.txt";
		fillZhangjie(txtFilePath,fName);
	}

	public static String getFixTxtStr(BufferedReader reader) {
		String pattern = "\\b[0-9]{1,4}章";
		String pattern2 = "第[0-9]{1,4}章";
		String pattern3 = "ps(：|:).*\\s";
		Pattern r = Pattern.compile(pattern);
		Pattern r2 = Pattern.compile(pattern2);
		// 现在创建 matcher 对象
		String tempt = "";
		StringBuilder sb = new StringBuilder();
		try {
			while ((tempt = reader.readLine()) != null) {
				Matcher m = r.matcher(tempt);
				String content = tempt;
				if (m.find()) {
					String group = m.group();
					System.out.println("match=" + group);
					content = tempt.replace(group, "第" + group);
				}
				sb.append(content);
				sb.append("\r\n");
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "error";
	}
}
