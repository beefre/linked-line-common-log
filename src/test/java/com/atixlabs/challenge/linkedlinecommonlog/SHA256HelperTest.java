package com.atixlabs.challenge.linkedlinecommonlog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.atixlabs.challenge.linkedlinecommonlog.controller.service.util.SHA256Helper;

class SHA256HelperTest {

	@Test
	void testGenerateHash() {
		assertNull(SHA256Helper.generateHash(null));
	}

}
