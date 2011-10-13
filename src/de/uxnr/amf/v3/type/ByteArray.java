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
		int reference = context.getAMF3ObjectReference(this);
		if (reference >= 0) {
			U29 flag = new U29((reference << 1) & ~1);
			flag.write(context, output);
			return;
		}

		context.addAMF3Object(this);

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
		int flags = flag.get();

		if ((flags & 1) == 0)
			return context.getAMF3Object(flags >> 1);

		context.addAMF3Object(this);

		int length = (flags >> 1);
		this.value = new int[length];

		for (int index = 0; index < length; index++) {
			this.value[index] = input.read();
		}

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
		StringBuilder sb = new StringBuilder();
		sb.append("ByteArray '");
		for (int i = 0; i < this.value.length; i++) {
			int value = this.value[i];
			if (value < 10) {
				sb.append('0');
			}
			sb.append(java.lang.Integer.toHexString(value));
		}
		sb.append("'");
		return sb.toString();
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
