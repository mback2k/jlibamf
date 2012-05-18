package de.uxnr.amf.v3.base;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v0.base.U8;
import de.uxnr.amf.v3.AMF3_Type;

public class U29 extends AMF3_Type {
	public static final int MAX_VALUE = 536870912;
	public static final int MIN_VALUE = 0;

	private int value = 0;

	public boolean signed = false;

	public U29() {
	}

	public U29(int value) {
		this.set(value);
	}

	public U29(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		long value = this.value;

		if (value < MIN_VALUE)
			value += MAX_VALUE;

		if (value < MIN_VALUE || value > MAX_VALUE)
			throw new IllegalArgumentException("Out of range");

		byte[] bytes = new byte[4];
		Long real_value = null;
		int index = 0;

		if (value > 0x1fffff) {
			real_value = value;
			value >>= 1;
			bytes[index++] = (byte) (0x80 | ((value >> 21) & 0xff));
		}

		if (value > 0x3fff)
			bytes[index++] = (byte) (0x80 | ((value >> 14) & 0xff));

		if (value > 0x7f)
			bytes[index++] = (byte) (0x80 | ((value >> 7) & 0xff));

		if (real_value != null)
			value = real_value;

		if (value > 0x1fffff)
			bytes[index++] = (byte) (value & 0xff);
		else
			bytes[index++] = (byte) (value & 0x7f);

		output.write(bytes, 0, index);
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		int value = 0;
		int more = 0;
		int read = new U8(context, input).get();

		while ((read & 0x80) != 0 && more < 3) {
			value <<= 7;
			value |= read & 0x7F;

			read = input.read();
			more++;
		}

		if (more < 3) {
			value <<= 7;
			value |= read;
		} else {
			value <<= 8;
			value |= read;

			if (this.signed && (value & 0x10000000) != 0) {
				value -= 0x20000000;
			}
		}

		this.value = value;

		return this;
	}

	public void set(int value) {
		this.value = value;
	}

	public int get() {
		return this.value;
	}

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}

	@Override
	public int hashCode() {
		return this.value * 31;
	}
}
