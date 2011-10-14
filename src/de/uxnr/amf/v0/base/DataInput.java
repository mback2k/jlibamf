package de.uxnr.amf.v0.base;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataInput extends DataInputStream {
	public DataInput(InputStream stream) {
		super(stream);
	}

	public int readU8() throws IOException {
		return this.readUnsignedByte();
	}

	public int readU16() throws IOException {
		return this.readUnsignedShort();
	}

	public int readS16() throws IOException {
		return this.readShort();
	}

	public long readU32() throws IOException {
		byte[] buf = new byte[4];

		if (this.read(buf) != 4)
			throw new IOException("Not enough data to read U32");

		return (buf[0] << 24) | (buf[1] << 16) | (buf[2] << 8) | (buf[3]);
	}

	public double readDOUBLE() throws IOException {
		return this.readDouble();
	}

	public String readUTF8() throws IOException {
		int length = this.readU16();
		byte[] buf = new byte[length];

		if (this.read(buf) != length)
			throw new IOException("Not enough data to read UTF8");

		return new String(buf);
	}

	public String readUTF8long() throws IOException {
		long longlength = this.readU32();
		if (longlength > Integer.MAX_VALUE)
			throw new IOException("Not enough space to handle UTF8long");

		int length = (int) longlength;
		byte[] buf = new byte[length];

		if (this.read(buf) != length)
			throw new IOException("Not enough data to read UTF8");

		return new String(buf);
	}
}
