package de.uxnr.amf.v0.base;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v0.AMF0_Type;

public class U32 extends AMF0_Type {
	private long value = 0;

	public U32() { }

	public U32(long value) {
		this.set(value);
	}

	public U32(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		byte[] buf = new byte[4];
		buf[0] = (byte) ((this.value >> 24) & 0xFF);
		buf[1] = (byte) ((this.value >> 16) & 0xFF);
		buf[2] = (byte) ((this.value >> 8) & 0xFF);
		buf[3] = (byte) ((this.value) & 0xFF);
		output.write(buf);
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		byte[] buf = new byte[4];
		if (input.read(buf) == 4)
			this.value = (buf[0] << 24) | (buf[1] << 16) | (buf[2] << 8) | (buf[3]);
		else
			throw new IOException("Not enough data to read U32");

		return this;
	}

	public void set(long value) {
		this.value = value;
	}

	public long get() {
		return this.value;
	}

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}

	@Override
	public int hashCode() {
		return (int) (this.value * 31);
	}
}
