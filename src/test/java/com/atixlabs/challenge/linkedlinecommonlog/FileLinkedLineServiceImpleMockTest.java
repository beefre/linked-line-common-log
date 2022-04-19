package com.atixlabs.challenge.linkedlinecommonlog;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atixlabs.challenge.linkedlinecommonlog.controller.service.util.Constants;
import com.atixlabs.challenge.linkedlinecommonlog.data.Line;
import com.atixlabs.challenge.linkedlinecommonlog.service.FileLinkedLineService;
import com.atixlabs.challenge.linkedlinecommonlog.service.FileLinkedLineServiceImpl;

@RunWith(MockitoJUnitRunner.class)
class FileLinkedLineServiceImpleMockTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@InjectMocks
	FileLinkedLineServiceImpl fileLinkedLineMockServiceImpl = new FileLinkedLineServiceImpl();

	@Test 
	void testCreateFile_success() throws IOException {
		File file = new File("test.txt");
		boolean blnResult = fileLinkedLineMockServiceImpl.createFile(file);
		file.delete();
		assertTrue(blnResult);
	}

	@Test
	void testCreateFile_fail() throws IOException {
		FileLinkedLineService fileLinkedLineMockService = mock(FileLinkedLineService.class);
		File file = new File("");
		when(fileLinkedLineMockService.createFile(file)).thenThrow(new RuntimeException());
		boolean blnResult = fileLinkedLineMockServiceImpl.createFile(file);
		assertFalse(blnResult);
	}

	@Test
	void testWriteLogLine() {

		File file = new File("test.txt");
		try {
			file.createNewFile();
			fileLinkedLineMockServiceImpl.writeLogLine(file, true, "line to be inserted in file");
			file.delete();
		} catch (IOException e) {
			LOGGER.error("Error on creating file in Mock Testing: " + e.getMessage());
		}
	}

	@Test
	void testWriteLogLine_fail() throws IOException {

		File file = new File("test.txt");
		file.mkdir();
		fileLinkedLineMockServiceImpl.writeLogLine(file, true, "");
		file.delete();
	}

	@Test
	void testGetPreviousHash() {

		File file = new File("test2.txt");
		try {
			file.createNewFile();
			fileLinkedLineMockServiceImpl.writeLogLine(file, false, "line to be inserted in file");
			String hash = fileLinkedLineMockServiceImpl.getPreviousHash(file);
			assertNotNull(hash);
			file.delete();
		} catch (IOException e) {
			LOGGER.error("Error on creating file in Mock Testing: " + e.getMessage());
		}
	}

	@Test
	void testGetPreviousHash_fail() throws IOException {
		File file = new File("text3.txt");
		String hash = fileLinkedLineMockServiceImpl.getPreviousHash(file);
		assertEquals("", hash);
	}

	@Test
	void testGenerateHash_genesisHash() {
		Line line = new Line("", "");
		fileLinkedLineMockServiceImpl.generateHash(line, false);
		assertEquals(Constants.GENESIS_PREV_HASH, line.getHash());

	}

	@Test
	void testGenerateHash() {
		Line line = new Line(Constants.GENESIS_PREV_HASH, "Hi");
		line.incrementNonce();
		fileLinkedLineMockServiceImpl.generateHash(line, true);
		assertNotNull(line.getHash());

	}

}
