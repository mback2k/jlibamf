package de.uxnr.amf.v0.base;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v0.AMF0_Type;

public class UTF8 extends AMF0_Type {
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
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(stream, "UTF-8");
		writer.write(this.value);
		writer.flush();

		U16 length = new U16(stream.size());
		length.write(context, output);
		writer.close();

		writer = new OutputStreamWriter(output, "UTF-8");
		writer.write(this.value);
		writer.flush();
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		U16 length = new U16(context, input);
		byte[] buf = new byte[length.get()];

		if (input.read(buf) == length.get())
			this.value = new String(buf).intern();
		else
			throw new IOException("Not enough data to read UTF8");

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
