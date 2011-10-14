package de.uxnr.amf.v0.base;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class DataOutput extends DataOutputStream {
	public DataOutput(OutputStream stream) {
		super(stream);
	}

	public void writeU8(int value) throws IOException {
		this.writeByte(value);
	}

	public void writeU16(int value) throws IOException {
		this.writeShort(value);
	}

	public void writeS16(int value) throws IOException {
		this.writeShort(value);
	}

	public void writeU32(long value) throws IOException {
		byte[] buf = new byte[4];
		buf[0] = (byte) ((value >> 24) & 0xFF);
		buf[1] = (byte) ((value >> 16) & 0xFF);
		buf[2] = (byte) ((value >> 8) & 0xFF);
		buf[3] = (byte) ((value) & 0xFF);
		this.write(buf);
	}

	public void writeDOUBLE(double value) throws IOException {
		this.writeDouble(value);
	}

	public void writeUTF8(String value) throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(stream, "UTF-8");
		writer.write(value);
		writer.flush();

		this.writeU16(stream.size());
		writer.close();

		writer = new OutputStreamWriter(this, "UTF-8");
		writer.write(value);
		writer.flush();
	}

	public void writeUTF8long(String value) throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(stream, "UTF-8");
		writer.write(value);
		writer.flush();

		this.writeU32(stream.size());
		writer.close();

		writer = new OutputStreamWriter(this, "UTF-8");
		writer.write(value);
		writer.flush();
	}
}
