package com.atixlabs.challenge.linkedlinecommonlog.controller.service.util;

import com.atixlabs.challenge.linkedlinecommonlog.data.Line;

public class Util {
	
	public static boolean notGoldenHash(Line line) {
		String leadingZeros = new String(new char[Constants.DIFFICULTY]).replace('\0', '0');
		return !line.getHash().substring(0,Constants.DIFFICULTY).equals(leadingZeros);
	}

}
