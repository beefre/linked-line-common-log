package com.atixlabs.challenge.linkedlinecommonlog.controller.service.util;

import java.lang.invoke.MethodHandles;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHA256Helper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static String generateHash(String data) {
		try {
			
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(data.getBytes("UTF-8"));

			//we want to end up with hexadecimal values not bytes
			StringBuffer hexadecimalString = new StringBuffer();
		
			for (int i = 0; i < hash.length; i++) {
				String hexadecimal = Integer.toHexString(0xff & hash[i]);
				hexadecimalString.append(hexadecimal);
			}
			
			return hexadecimalString.toString();
			
		} catch (Exception e) {
			LOGGER.error("Error on creating Hash: "+e.getMessage());
			return null;
		}
	}
}
