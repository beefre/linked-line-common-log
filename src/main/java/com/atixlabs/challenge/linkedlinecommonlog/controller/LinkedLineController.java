package com.atixlabs.challenge.linkedlinecommonlog.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atixlabs.challenge.linkedlinecommonlog.controller.service.util.Util;
import com.atixlabs.challenge.linkedlinecommonlog.data.Line;

@RestController
public class LinkedLineController {

	

	@GetMapping(value = "/hello")
	@ResponseBody
	public String hello() {
		return "Hello World";
	}

	@PostMapping(value = "/create")
	public ResponseEntity<Line> create(@RequestBody Line line) {

//		File file = new File("D:\\demo\\challenge.csv");
		File file = new File("challenge.csv");
		boolean appendDataFlag = (createNewLog(file) ? false : true);
		writeLog(file,line,appendDataFlag);
			
		return ResponseEntity.created(URI.create(String.format("/lines/%s", line.getMessage()))).body(line);
	}

	private void writeLog(File file,Line line, boolean appendData) {
		BufferedWriter out = null;
		if(!appendData) {
			line.setPreviousHash(Util.INITIAL_HASH);
		}

		try {
			FileWriter fstream = new FileWriter(file,appendData); 
			out = new BufferedWriter(fstream);
			StringBuilder stbLine = new StringBuilder();
			stbLine.append(line.getPreviousHash()).append(",").append(line.getMessage()).append(",").append(line.getNonce()).append("\n");
			out.write(stbLine.toString());
		}

		catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}

		finally {
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

	private boolean createNewLog(File file) {
		boolean result = false;

		try {
			result = file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

}
