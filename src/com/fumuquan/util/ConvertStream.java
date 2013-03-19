package com.joyeon.ppevaluate.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.joyeon.ppevaluate.exception.OperateStreamException;



public class ConvertStream {
	
	public static String toJSONString(InputStream in) throws OperateStreamException
	{
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line ="";
		try {
			while((line=br.readLine())!=null)
					sb.append(line);
		} catch (IOException e) {
			throw new OperateStreamException("读取流出错");
		}
		String json = sb.toString();
		return json;
	}
	
	public static boolean toBoolean(InputStream in) throws OperateStreamException
	{
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in)); 
		StringBuffer buffer = new StringBuffer(); 
		String tempOneLine = new String(""); 
		try {
			while ((tempOneLine = bufferedReader.readLine()) != null){ 
				buffer.append(tempOneLine); 
			}
		} catch (IOException e) {
			throw new OperateStreamException("读取流出错");
		} 
		if(buffer.toString().trim().equals("true"))
			return true;
		else
			return false;
	}
	
	public static String toString(InputStream in) throws OperateStreamException
	{
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in)); 
		StringBuffer buffer = new StringBuffer(); 
		String tempOneLine = new String(""); 
		try {
			while ((tempOneLine = bufferedReader.readLine()) != null){ 
				buffer.append(tempOneLine); 
			}
		} catch (IOException e) {
			throw new OperateStreamException("读取流出错");
		} 
		return buffer.toString().trim();	
	}
	
	public static int toInt(InputStream in) throws OperateStreamException
	{
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in)); 
		StringBuffer buffer = new StringBuffer(); 
		String tempOneLine = new String(""); 
		try {
			while ((tempOneLine = bufferedReader.readLine()) != null){ 
				buffer.append(tempOneLine); 
			}
		} catch (IOException e) {
			throw new OperateStreamException("读取流出错");
		} 
		return Integer.valueOf(buffer.toString().trim()).intValue();
	}
}
