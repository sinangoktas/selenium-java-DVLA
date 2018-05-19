package com.dvla.service;

public class FileInfo {
	
	private String name;
	private String mimeType;
	private long size;
	private String ext;
	
	public String getName() { return name; }
	public void setName(String name) {
		this.name = name;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getExt() { return ext; }
	public void setExt(String ext) { this.ext = ext; }

}
