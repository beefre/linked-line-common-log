package com.atixlabs.challenge.linkedlinecommonlog.controller.service.util;

import java.security.MessageDigest;

public class SHA256Helper {

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
			throw new RuntimeException(e);
		}
	}
}
