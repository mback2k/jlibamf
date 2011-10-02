package de.uxnr.amf.v3.base;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v3.AMF3_Type;

public class UTF8 extends AMF3_Type {
	private String value = "";

	public UTF8() { }

	public UTF8(String value) {
		this.set(value);
	}

	public UTF8(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		int reference = context.getAMF3StringReference(this);
		if (reference >= 0) {
			U29 flag = new U29(reference << 1);
			flag.write(context, output);
			return;
		}

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(stream, "UTF-8");
		writer.write(this.value);
		writer.flush();

		U29 length = new U29((stream.size() << 1) | 1);
		length.write(context, output);
		writer.close();

		writer = new OutputStreamWriter(output, "UTF-8");
		writer.write(this.value);
		writer.flush();

		context.addAMF3String(this);
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		U29 flag = new U29(context, input);
		if (flag.get() == 1)
			return this;

		if ((flag.get() & 1) == 0)
			return context.getAMF3String(flag.get() >> 1);

		int length = (flag.get() >> 1);
		byte[] buf = new byte[length];

		if (input.read(buf) == length)
			this.value = new String(buf);
		else
			throw new IOException("Not enough data to read UTF8");

		context.addAMF3String(this);

		return this;
	}

	public void set(String value) {
		this.value = value;
	}

	public String get() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}
}
