package com.atixlabs.challenge.linkedlinecommonlog.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.atixlabs.challenge.linkedlinecommonlog.controller.service.util.Constants;
import com.atixlabs.challenge.linkedlinecommonlog.data.Line;

@Service
public class FileLinkedLineServiceImpl implements FileLinkedLineService {

	@Override
	public boolean createFile(File file) {
		boolean result = false;

		try {
			result = file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void writeLogLine(File file, boolean appendDataFlag, StringBuilder stbLine) {
		BufferedWriter out = null;

		try {
			FileWriter fstream = new FileWriter(file, appendDataFlag);
			out = new BufferedWriter(fstream);
			out.write(stbLine.toString());
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
				System.out.println(data);
				result  = lastLine.split(",")[0];
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	      
	      return result;
	}

	@Override
	public void generateHash(Line line, boolean appendData) {

		if(appendData) {
			line.generateHash();
		}else {
			line.setHash(Constants.GENESIS_PREV_HASH);
		}
	}

}
