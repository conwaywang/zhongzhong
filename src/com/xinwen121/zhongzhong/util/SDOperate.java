package com.joyeon.smartmenu.util;

import java.io.File;
import java.io.FileOutputStream;

import com.joyeon.smartmenu.Config;

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
