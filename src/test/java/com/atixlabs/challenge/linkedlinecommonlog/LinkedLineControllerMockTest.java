package com.atixlabs.challenge.linkedlinecommonlog;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.atixlabs.challenge.linkedlinecommonlog.controller.LinkedLineController;
import com.atixlabs.challenge.linkedlinecommonlog.controller.service.util.Constants;
import com.atixlabs.challenge.linkedlinecommonlog.controller.service.util.SHA256Helper;
import com.atixlabs.challenge.linkedlinecommonlog.controller.service.util.Util;
import com.atixlabs.challenge.linkedlinecommonlog.data.Line;
import com.atixlabs.challenge.linkedlinecommonlog.service.FileLinkedLineService;
import com.atixlabs.challenge.linkedlinecommonlog.service.FileLinkedLineServiceImpl;

@RunWith(MockitoJUnitRunner.class)
class LinkedLineControllerMockTest {
	
	@InjectMocks
	LinkedLineController linkedLineController;
	
	@Test
	void testCreate_genesisHash() {
		FileLinkedLineService fileLinkedLineService = mock(FileLinkedLineService.class);
		linkedLineController = new LinkedLineController(fileLinkedLineService);
		org.springframework.test.util.ReflectionTestUtils.setField(linkedLineController, "location", "C:\\demo\\test.csv");
		File file = new File("C:\\demo\\test.csv");
		Line line = new Line(Constants.GENESIS_PREV_HASH,"Hi");
		boolean bln = true;
		when(fileLinkedLineService.createFile(new File("C:\\demo\\test.csv"))).thenReturn(bln);
		when(fileLinkedLineService.getPreviousHash(file)).thenReturn(Constants.GENESIS_PREV_HASH);
		linkedLineController.create(line);
	}

}
