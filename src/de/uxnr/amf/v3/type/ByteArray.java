package de.uxnr.amf.v3.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v0.base.U8;
import de.uxnr.amf.v3.AMF3_Type;
import de.uxnr.amf.v3.base.U29;

public class ByteArray extends AMF3_Type {
	private int[] value = new int[0];

	private java.lang.Integer hashCode = null;

	public ByteArray() { }

	public ByteArray(int[] value) {
		this.value = value;
	}

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		U29 length = new U29((this.value.length << 1) | 1);
		length.write(context, output);

		for (int elem : this.value) {
			U8 ubyte = new U8(elem);
			ubyte.write(context, output);
		}
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		U29 flag = new U29(context, input);

		if ((flag.get() & 1) == 0)
			return context.getAMF3Object(flag.get() >> 1);

		int length = (flag.get() >> 1);
		this.value = new int[length];

		for (int index = 0; index < length; index++) {
			this.value[index] = input.read();
		}

		context.addAMF3Object(this);

		return this;
	}

	public void set(int[] value) {
		this.hashCode = null;
		this.value = value;
	}

	public int[] get() {
		return this.value;
	}

	@Override
	public java.lang.String toString() {
		return "ByteArray";
	}

	@Override
	public int hashCode() {
		if (this.hashCode != null)
			return this.hashCode;
		this.hashCode = 0;
		for (int value : this.value)
			this.hashCode ^= value;
		return this.hashCode;
	}
}
