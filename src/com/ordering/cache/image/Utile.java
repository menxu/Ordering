package com.ordering.cache.image;

import java.io.File;

import android.os.Environment;

public class Utile {
	public static boolean is_str_blank(String str) {
		int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
	}
	
	
	// 尝试获得一个文件夹引用，如果文件夹不存在就创建该文件夹
    public static File get_or_create_dir(String path) {
        File dir = new File(
                Environment.getExternalStorageDirectory().getPath() + path
        );
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
	
	public final static File IMAGE_CACHE_DIR = get_or_create_dir("/ording/image_cache");
}
