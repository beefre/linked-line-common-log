package com.atixlabs.challenge.linkedlinecommonlog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.atixlabs.challenge.linkedlinecommonlog.controller.service.util.Util;
import com.atixlabs.challenge.linkedlinecommonlog.data.Line;

class UtilTest {
	

	@Test
	void testNotGoldenHash_true() {
		Line line = new Line("","");
		line.setHash("9861e16a52e13657d01d2fe483f1f6337ee92fda1b7b92f668f1e51d5a4b");
		assertTrue(Util.notGoldenHash(line));
	}
	
	@Test
	void testNotGoldenHash_false() {
		Line line = new Line("","");
		line.setHash("0061e16a52e13657d01d2fe483f1f6337ee92fda1b7b92f668f1e51d5a4b");
		assertFalse(Util.notGoldenHash(line));
	}

}
