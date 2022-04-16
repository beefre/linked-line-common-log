package com.atixlabs.challenge.linkedlinecommonlog.controller;

import java.io.File;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.atixlabs.challenge.linkedlinecommonlog.controller.service.util.Constants;
import com.atixlabs.challenge.linkedlinecommonlog.controller.service.util.Util;
import com.atixlabs.challenge.linkedlinecommonlog.data.Line;
import com.atixlabs.challenge.linkedlinecommonlog.service.FileLinkedLineService;

@RestController
public class LinkedLineController {
	
	@Autowired
	FileLinkedLineService fileLinkedLineService;
	
	@PostMapping(value = "/create")
	public ResponseEntity<Line> create(@RequestBody Line line) {
		writeLog(line);
		return ResponseEntity.created(URI.create(String.format("/lines/%s", line.getMessage()))).body(line);
	}

	private void writeLog(Line line) {
		File file = new File("D:\\demo\\challenge.csv");
		boolean appendDataFlag = (fileLinkedLineService.createFile(file) ? Constants.NOT_APPEND : Constants.APPEND);
		StringBuilder stbLine = new StringBuilder();
		
		line.setPreviousHash(fileLinkedLineService.getPreviousHash(file));
		fileLinkedLineService.generateHash(line, appendDataFlag);
		
		
		while(appendDataFlag && Util.notGoldenHash(line)) {
			line.incrementNonce();
			fileLinkedLineService.generateHash(line,appendDataFlag);
		}
		
		stbLine.append(line.getHash()).append(",").append(line.getMessage()).append(",").append(line.getNonce()).append("\n");
		fileLinkedLineService.writeLogLine(file, appendDataFlag, stbLine);
		
	}

}
