package de.uxnr.amf.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF;
import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.v3.base.U29;
import de.uxnr.amf.v3.type.Integer;

public class AMFTest {
	public static void main(String[] args) throws IOException {
		for (int i = 0; i < 1000; i++) {
			int value = (int) (Integer.MAX_VALUE - (Math.random() * Integer.MAX_VALUE * 2));
			test(value, true);
		}

		for (int i = 0; i < 1000; i++) {
			int value = (int) (Math.random() * U29.MAX_VALUE);
			test(value, false);
		}

		File folder = new File(".");
		for (File file : folder.listFiles()) {
			if (file.getName().endsWith(".amf")) {
				test(file);
			}
		}
	}

	public static void test(int value, boolean signed) throws IOException {
		AMF_Context context = new AMF_Context();
		Integer test = new Integer();
		test.signed = signed;

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		test.set(value);
		test.write(context, new DataOutputStream(out));

		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

		test.read(context, new DataInputStream(in));

		System.out.println(value+" => "+test.get());

		if (value != test.get())
			throw new RuntimeException("Test failed!");
	}

	public static void test(File file) throws IOException {
		FileInputStream input = new FileInputStream(file);

		System.out.println(file.length());
		System.out.println(input.available());

		AMF amf = new AMF(input);
		int hashCode = amf.hashCode();

		System.out.println(amf.getHeaders().size());
		System.out.println(amf.getMessages().size());
		System.out.println(input.available());

		if (input.available() != 0)
			throw new RuntimeException("Test failed!");

		file = File.createTempFile("amf", file.getName());
		FileOutputStream out = new FileOutputStream(file);

		amf.write(out);

		out.close();

		FileInputStream in = new FileInputStream(file);

		amf = new AMF(in);

		System.out.println(hashCode+" == "+amf.hashCode());

		if (hashCode != amf.hashCode())
			throw new RuntimeException("Test failed!");
	}
}
