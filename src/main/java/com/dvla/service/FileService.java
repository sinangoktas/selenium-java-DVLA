package com.dvla.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class implements the service layer 
 *
 */

public class FileService {

	private List<File> list = new ArrayList<>();

	public FileService(String configurationFolder) {

		File folder = new File(configurationFolder);
		File[]  files = folder.listFiles();
		if (files != null)
			list  = Arrays.asList(files);
	}
	
	/**
	 * 
	 * @return All the files under the  with name, size, mime-type and extension info
	 * @see FileInfo
	 */
	public List<FileInfo> getAllFiles() {
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		for (File file : list) {
			FileInfo fileInfo = new FileInfo();
			fileInfo.setName(file.getName());
			fileInfo.setSize(file.length());
			fileInfo.setExt(getFileExtension(file));
			fileInfoList.add(fileInfo);
		}
		return fileInfoList;
	}

	/**
	 * 
	 * @param fileExts list of file extensions to filter 
	 * @return list of files with the FileInfo metadata
	 * @see FileInfo
	 */
	public List<FileInfo> getSupportedFiles(List<String> fileExts) {
		List<FileInfo> supportedFiles = new ArrayList<FileInfo>();
		for(FileInfo fileInfo : getAllFiles()) {
			if (fileExts.contains(fileInfo.getExt())) {
				supportedFiles.add(fileInfo);
			}
			
		}
		return supportedFiles;
	}
	
	/**
	 * 
	 * @param file file name 
	 * @return file extension
	 * @see FileInfo
	 */
	
	private String getFileExtension(File file) {
	    String name = file.getName();
	    try {
	        return name.substring(name.lastIndexOf(".") + 1);
	    } catch (Exception e) {
	        return "";
	    }
	}

	

}
