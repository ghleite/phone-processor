package com.phone.processor.mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CoutryCodeMapper {

	public Map<String, String> fileToObject(String coutryCodesFile) {
		
		Map<String, String> resultMap = new HashMap<>();

		File file = streamToFile(getClass().getClassLoader().getResourceAsStream(coutryCodesFile));

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] column = line.split("-");
				resultMap.put(column[0], column[1]);
			}
			
		} catch (IOException x) {
			System.err.println(x);
		}
		
		return resultMap;
	}
	
	public static File streamToFile(InputStream in) {
	    if (in == null) {
	        return null;
	    }

	    try {
	        File f = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
	        f.deleteOnExit();

	        FileOutputStream out = new FileOutputStream(f);
	        byte[] buffer = new byte[1024];

	        int bytesRead;
	        while ((bytesRead = in.read(buffer)) != -1) {
	            out.write(buffer, 0, bytesRead);
	        }

	        return f;
	    } catch (IOException e) {
	        return null;
	    }
	}
	
	
}
