package com.atixlabs.challenge.linkedlinecommonlog.service;

import java.io.File;

import com.atixlabs.challenge.linkedlinecommonlog.data.Line;


public interface FileLinkedLineService {
	
	public boolean createFile(File file);
	
	public void writeLogLine(File file, boolean appendDataFlag, StringBuilder stbLine);

	public String getPreviousHash(File file);
	
	public void generateHash(Line line, boolean appedData);
	

}
