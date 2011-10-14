package de.uxnr.amf.v3.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import de.uxnr.amf.Context;

public class DataOutput extends de.uxnr.amf.v0.base.DataOutput {
	public static final int U29_MAX_VALUE = 536870912;
	public static final int U29_MIN_VALUE = 0;

	public static final int S29_MAX_VALUE = 268435455;
	public static final int S29_MIN_VALUE = -268435456;

	public DataOutput(OutputStream stream) {
		super(stream);
	}

	public void writeU29(int value) throws IOException {
		if (value < U29_MIN_VALUE || value > U29_MAX_VALUE)
			throw new IllegalArgumentException("Out of range");

		byte[] bytes = new byte[4];
		int real_value = 0;
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

		if (real_value != 0)
			value = real_value;

		if (value > 0x1fffff)
			bytes[index++] = (byte) (value & 0xff);
		else
			bytes[index++] = (byte) (value & 0x7f);

		this.write(bytes, 0, index);
	}

	public void writeS29(int value) throws IOException {
		if (value < U29_MIN_VALUE)
			value += U29_MAX_VALUE;

		this.writeU29(value);
	}

	public void writeUTF8(Context context, String value) throws IOException {
		if (value.isEmpty()) {
			this.writeU29(1);
			return;
		}

		int reference = context.getAMF3StringReference(value);
		if (reference >= 0) {
			this.writeU29((reference << 1) & ~1);
			return;
		}

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(stream, "UTF-8");
		writer.write(value);
		writer.flush();

		this.writeU29((stream.size() << 1) | 1);
		writer.close();

		writer = new OutputStreamWriter(this, "UTF-8");
		writer.write(value);
		writer.flush();

		context.addAMF3String(value);
	}
}
