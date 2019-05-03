package com.dematte.waestest;

import java.util.Base64;

public class Base64Encoder {

	public static byte[] encode(String string) {
		return Base64.getEncoder().encode(string.getBytes());
	}

}
