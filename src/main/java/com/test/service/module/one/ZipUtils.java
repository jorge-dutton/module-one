package com.test.service.module.one;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public final class ZipUtils {

	public static byte[] compress(HashMap<String, byte[]> inputList) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		Set<String> keys = inputList.keySet();
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String name = iterator.next();
			byte[] input = inputList.get(name);
			ZipEntry entry = new ZipEntry(name);
			entry.setSize(input.length);
			zos.putNextEntry(entry);
			zos.write(input); 
			zos.closeEntry();
		}		
		zos.close();

		return baos.toByteArray();
	}
	
	
	public static HashMap<String, byte[]>  unCompressDocsFromZipFile(byte[] originalFile) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(originalFile);
		HashMap<String, byte[]> rsl = new HashMap<String, byte[]>(); 
		ZipInputStream zis = new ZipInputStream(bais);
		ZipEntry entry = zis.getNextEntry();
        try{
        	while(entry!=null)
            {
        		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        		copyStream(zis, baos, entry);
        		byte[] tmpData = baos.toByteArray();
        		rsl.put(entry.getName(), tmpData);
        		baos.close();
        		entry = zis.getNextEntry();
            }	
        }finally{
            zis.close();
            bais.close();        	
        }
		return rsl;
	}
	
	private static void copyStream(InputStream in, OutputStream out,
	        ZipEntry entry) throws IOException {
		int BUFFER  = 1024 * 4;
	    byte[] buffer = new byte[BUFFER];
	    int count = 0;
	    while ((count = in.read(buffer, 0, BUFFER)) != -1) {
	        out.write(buffer, 0, count);

	    }
	}
}

