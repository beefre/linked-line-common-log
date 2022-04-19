package com.atixlabs.challenge.linkedlinecommonlog.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atixlabs.challenge.linkedlinecommonlog.controller.service.util.Constants;
import com.atixlabs.challenge.linkedlinecommonlog.controller.service.util.SHA256Helper;
import com.atixlabs.challenge.linkedlinecommonlog.data.Line;

@Service
public class FileLinkedLineServiceImpl implements FileLinkedLineService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	

	@Override
	public boolean createFile(File file) {
		boolean result = false;
		try {
			result = file.createNewFile();
		} catch (IOException e) {
			LOGGER.error("Error on creating file: " + e.getMessage());
		}
		return result;
	}

	@Override
	public void writeLogLine(File file, boolean appendDataFlag, String strLine) {
		BufferedWriter out = null;
		try {
			FileWriter fstream = new FileWriter(file, appendDataFlag);
			out = new BufferedWriter(fstream);
			out.write(strLine);
		} catch (IOException e) {
			LOGGER.error("Error on writting a line in file: " + e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LOGGER.error("Error on closing file: " + e.getMessage());
				}
			}
		}
	}

	@Override
	public String getPreviousHash(File file) {
		Scanner myReader = null;
		String lastLine = "";
		String result = "";
		try {
			myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				lastLine = data;
				result = lastLine.split(",")[0];
			}

		} catch (FileNotFoundException e) {
			LOGGER.error("Error: File not found exception: " + e.getMessage());
		}
		return result;
	}

	@Override
	public void generateHash(Line line, boolean appendData) {
		if (appendData) {
			setNewHash(line);
		} else {
			line.setHash(Constants.GENESIS_PREV_HASH); 
			line.incrementNonce();
		}
	}

	private void setNewHash(Line line) {
		String dataToHash = line.getPreviousHash() + line.getMessage() + line.getNonce();
		String hashValue = SHA256Helper.generateHash(dataToHash);
		line.setHash(hashValue);
	}

}
