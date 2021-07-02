package com.test.service.module.one;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;


public class ZipUtilsTest {

		// Files on fileSystem 
		private final static String ZIPPED_RESOURCE = "zip/NuevaCarpeta.zip"; 
		private final static String ZIPPED_RESOURCE_BUS = "zip/realBusExample.zip";

		//Files included on ZIPPED_RESOURCE
		private final static List<String> FILES_ON_ZIP = Arrays.asList("fileOne.txt", "fileTwo.txt", "fileThree.txt","readme.txt","001001-9996-000000000219939.par.g2.signed.pdf","001001-9996-000000000219939.par.g2.pdf");
		private final static String README_FILE_VALUE = "Readme files are important";
		private final static String README_FILE_NAME = "readme.txt";

		//Files included on ZIPPED_RESOURCE_BUS
		private final static List<String> FILES_ON_ZIP_BUS = Arrays.asList("001001-9996-000000000219939.par.g2.signed.pdf","001001-9996-000000000219939.par.g2.pdf");
		
		
		@Test
		public void testUncompressExampleBus() {
			//1.0 prepare data
			byte[] data = readSingleFile(ZIPPED_RESOURCE_BUS);
			assertNotNull("You should be able to read files on ["+ZIPPED_RESOURCE+"]",data);
			//2.0 UnzipFiles
			HashMap<String, byte[]> rsl  = null;
			try {
				rsl = ZipUtils.unCompressDocsFromZipFile(data);
			} catch (IOException e) {
				fail("This error should not happen["+e.getMessage()+"]");
			} 
			checkCompressedFile(rsl,FILES_ON_ZIP_BUS);
		}
		
		@Test
		public void testUncompressCommonExample() {
			//1.0 prepare data
			byte[] data = readSingleFile(ZIPPED_RESOURCE);
			assertNotNull("You should be able to read files on ["+ZIPPED_RESOURCE+"]",data);
			//2.0 UnzipFiles
			HashMap<String, byte[]> rsl  = null;
			try {
				rsl = ZipUtils.unCompressDocsFromZipFile(data);
			} catch (IOException e) {
				fail("This error should not happen["+e.getMessage()+"]");
			} 
			checkCompressedFile(rsl,FILES_ON_ZIP);
			assertEquals(README_FILE_VALUE,new String(rsl.get(README_FILE_NAME)));
		}
	
	
		@Test
		public void testCompressCommonExample() {
			 HashMap<String, byte[]> filesToZip = readFilesFromFileSystem(FILES_ON_ZIP);
			 byte[] fileZipped = null;
			 try {
				fileZipped = ZipUtils.compress(filesToZip);
			} catch (IOException e) {
				fail("Unexpected error during zipping operation"+e.getMessage());
			}
			 String data = new String(Base64.encodeBase64(fileZipped));
			 HashMap<String, byte[]> uncompressData  = null;
			 try {
				 uncompressData = ZipUtils.unCompressDocsFromZipFile(fileZipped) ;
			} catch (IOException e) {
				fail("Unexpected error during Unzipping operation");
			}
			 
			 checkCompressedFile(uncompressData,FILES_ON_ZIP);
		}
		
		@Test
		public void testCompressBUSExample() {
			 HashMap<String, byte[]> filesToZip = readFilesFromFileSystem(FILES_ON_ZIP_BUS);
			 byte[] fileZipped = null;
			 try {
				fileZipped = ZipUtils.compress(filesToZip);
			} catch (IOException e) {
				fail("Unexpected error during zipping operation"+e.getMessage());
			}
			 HashMap<String, byte[]> uncompressData  = null;
			 try {
				 uncompressData = ZipUtils.unCompressDocsFromZipFile(fileZipped) ;
			} catch (IOException e) {
				fail("Unexpected error during Unzipping operation");
			}
			 checkCompressedFile(uncompressData,FILES_ON_ZIP_BUS);
		}

		private void checkCompressedFile(HashMap<String, byte[]> rsl , List<String> expectedFiles){
			assertNotNull("We expect some result from method",rsl);
			//3.0 Check files
			for(String fileName : expectedFiles){
				assertTrue("File with name ["+fileName+"] expected on file",rsl.containsKey(fileName));
				assertNotNull("Expected some data related to File with name ["+fileName+"] file is null",rsl.get(fileName));
				assertTrue("Expected more data related to File with name ["+fileName+"] file is empty",rsl.get(fileName).length>1);
			}
			assertEquals(expectedFiles.size() , rsl.size());
		}
		
		
		private HashMap<String, byte[]>  readFilesFromFileSystem( List<String> expectedFiles){
			 HashMap<String, byte[]> rsl = new HashMap <String, byte[]>(); 
			 for(String actualFileName : expectedFiles){
				 String pathToLookFile;
				 if(actualFileName.indexOf(".pdf")!=-1){
					 pathToLookFile = "pdf/"+actualFileName;
				 }else{
					 pathToLookFile = "txt/"+actualFileName;
				 }
				 rsl.put(actualFileName, readSingleFile(pathToLookFile));
			 }
			return rsl;
		}

		private byte[] readSingleFile(String pathToFile) {
			Path resourceDirectory = Paths.get("src", "test", "resources", pathToFile);

			byte[] data = null;
			try {
				InputStream is = Files.newInputStream(resourceDirectory);
				data = new byte[is.available()];
				is.read(data);
			} catch (IOException e) {
				fail("This error should not happen[" + e.getMessage() + "]");
			}
			return data;

		}
}