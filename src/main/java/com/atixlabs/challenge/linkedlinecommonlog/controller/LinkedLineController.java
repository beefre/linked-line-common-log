package com.atixlabs.challenge.linkedlinecommonlog.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atixlabs.challenge.linkedlinecommonlog.controller.service.util.Constants;
import com.atixlabs.challenge.linkedlinecommonlog.controller.service.util.SHA256Helper;
import com.atixlabs.challenge.linkedlinecommonlog.controller.service.util.Util;
import com.atixlabs.challenge.linkedlinecommonlog.data.Line;
import com.atixlabs.challenge.linkedlinecommonlog.service.LinkedLineService;
import javax.xml.bind.DatatypeConverter;

@RestController
public class LinkedLineController {

	
	@Autowired
	LinkedLineService linkedLineService;
	

	@GetMapping(value = "/hello")
	@ResponseBody
	public String hello() {
		return "Hello World";
	}

	@PostMapping(value = "/create")
	public ResponseEntity<Line> create(@RequestBody Line line) {

		writeLog(line);
			
		return ResponseEntity.created(URI.create(String.format("/lines/%s", line.getMessage()))).body(line);
	}

	private void writeLog(Line line) {
		File file = new File("D:\\demo\\challenge.csv");
		boolean appendDataFlag = (linkedLineService.create(file) ? Constants.NOT_APPEND : Constants.APPEND);
		StringBuilder stbLine = new StringBuilder();
		
		
		line.setPreviousHash(getPreviousHash(file));
		generateHash(line, appendDataFlag);
		
		
		while(appendDataFlag && Util.notGoldenHash(line)) {
			line.incrementNonce();
			generateHash(line,appendDataFlag);
		}
		
		stbLine.append(line.getHash()).append(",").append(line.getMessage()).append(",").append(line.getNonce()).append("\n");
		BufferedWriter out = null;

		try {
			FileWriter fstream = new FileWriter(file,appendDataFlag); 
			out = new BufferedWriter(fstream);
			out.write(stbLine.toString());
		}catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}finally {
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

	
	private String getPreviousHash(File file) {
		// TODO Auto-generated method stub
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

	private void generateHash(Line line, boolean appendData) {
		if(appendData) {
			line.generateHash();
		}else {
			line.setHash(Constants.GENESIS_PREV_HASH);
		}
		
	}

//	private String generateHash(Line line, boolean appendData) {
//		if(!appendData) {
//			return concatenateLineFields(line,Constants.INITIAL_HASH).toString();
//		}
//
//		MessageDigest digest = null;
//		try {
//		    digest = MessageDigest.getInstance("SHA-256");
//		} catch (NoSuchAlgorithmException e) {
//		    e.printStackTrace();
//		}
//		String strLine = concatenateLine(line).toString();
//		byte[] hash = digest.digest(strLine.getBytes(StandardCharsets.UTF_8));
//		String encoded = DatatypeConverter.printHexBinary(hash);        
//		System.out.println(encoded.toLowerCase());
//		return encoded.toLowerCase();
//	}


}
