package com.xinwen121.zhongzhong.util;

import java.io.File;
import java.io.FileOutputStream;

import com.xinwen121.zhongzhong.config.Config;



public class SDOperate {
	
	public static void saveToSDCard(String filepath, String content) throws Exception  
    {  
		File rootDir = new File(Config.sMenuFolderPath);
		if (!rootDir.exists()) {
			rootDir.mkdirs();
		}
        File file = new File(filepath);  
        FileOutputStream outStream = new FileOutputStream(file);  
        outStream.write(content.getBytes());  
        outStream.close();  
    }  
}
