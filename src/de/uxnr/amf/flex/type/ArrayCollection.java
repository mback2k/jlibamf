package de.uxnr.amf.flex.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.v3.AMF3_Externalizable;
import de.uxnr.amf.v3.AMF3_Type;
import de.uxnr.amf.v3.type.Array;

public class ArrayCollection extends AMF3_Externalizable {
	private Array array;

	@Override
	public void writeExternal(AMF_Context context, DataOutputStream output) throws IOException {
		AMF3_Type.writeType(context, output, this.array);
	}

	@Override
	public void readExternal(AMF_Context context, DataInputStream input) throws IOException {
		this.array = (Array) AMF3_Type.readType(context, input);
	}

	public void setArray(Array array) {
		this.array = array;
	}

	public Array getArray() {
		return this.array;
	}

	@Override
	public String toString() {
		return "ArrayCollection(" + this.array + ")";
	}

	@Override
	public int hashCode() {
		return Integer.reverse(this.array.hashCode());
	}
}
