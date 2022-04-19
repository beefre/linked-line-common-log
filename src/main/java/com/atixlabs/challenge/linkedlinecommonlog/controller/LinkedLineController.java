package com.atixlabs.challenge.linkedlinecommonlog.controller;

import java.io.File;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${file.name.location}")
	private String location;
	
	public LinkedLineController(FileLinkedLineService fileLinkedLineService) {
		this.fileLinkedLineService = fileLinkedLineService;
	}

	@PostMapping(value = "/log-lines")
	public ResponseEntity<Line> create(@RequestBody Line line) {
		writeLog(line);
		return ResponseEntity.created(URI.create(String.format("/lines/%s", line.getMessage()))).body(line); 
	}

	private void writeLog(Line line) {
		File file = new File(location);
		boolean appendDataFlag = isAppendData(file);
		String strLine = "";
		setPreviousHash(line, file);
		generateHash(line, appendDataFlag);
		strLine = generateFileLine(line, appendDataFlag);
		fileLinkedLineService.writeLogLine(file, appendDataFlag, strLine);
	}

	private boolean isAppendData(File file) {
		return (fileLinkedLineService.createFile(file) ? Constants.NOT_APPEND : Constants.APPEND);
	}

	private void setPreviousHash(Line line, File file) {
		line.setPreviousHash(fileLinkedLineService.getPreviousHash(file));
	}

	private void generateHash(Line line, boolean appendDataFlag) {
		fileLinkedLineService.generateHash(line, appendDataFlag);
	}

	private String generateFileLine(Line line, boolean appendDataFlag) {
		StringBuilder stbLine = new StringBuilder();
		while (appendDataFlag && Util.notGoldenHash(line)) {
			line.incrementNonce();
			generateHash(line, appendDataFlag);
		}
		return stbLine.append(line.getHash()).append(",").append(line.getMessage()).append(",").append(line.getNonce())
				.append("\n").toString();
	}
}
