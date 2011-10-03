package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v0.AMF0_Type;
import de.uxnr.amf.v0.base.U32;

public class StrictArray extends AMF0_Type {
	private final List<AMF0_Type> value = new Vector<AMF0_Type>();

	private Integer hashCode = null;

	public StrictArray() { }

	public StrictArray(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		U32 length = new U32(this.value.size());
		length.write(context, output);

		for (AMF0_Type value : this.value) {
			AMF0_Type.writeType(context, output, value);
		}

		context.addAMF0Object(this);
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		U32 length = new U32(context, input);

		for (long index = 0; index < length.get(); index++) {
			AMF0_Type value = AMF0_Type.readType(context, input);

			this.value.add(value);
		}

		context.addAMF0Object(this);

		return this;
	}

	public Iterator<AMF0_Type> iterator() {
		return this.value.iterator();
	}

	public void add(AMF0_Type value) {
		this.hashCode = null;
		this.value.add(value);
	}

	public void set(int index, AMF0_Type value) {
		this.hashCode = null;
		this.value.set(index, value);
	}

	public AMF0_Type get(int index) {
		return this.value.get(index);
	}

	@Override
	public java.lang.String toString() {
		return "Strict Array " + this.value;
	}

	@Override
	public int hashCode() {
		if (this.hashCode != null)
			return this.hashCode;
		return this.hashCode = this.value.hashCode();
	}
}
