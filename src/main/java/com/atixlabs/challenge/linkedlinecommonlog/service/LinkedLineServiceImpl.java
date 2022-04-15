package com.atixlabs.challenge.linkedlinecommonlog.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class LinkedLineServiceImpl implements LinkedLineService {

	@Override
	public boolean create(File file) {

		boolean result = false;

		try {
			result = file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

}
