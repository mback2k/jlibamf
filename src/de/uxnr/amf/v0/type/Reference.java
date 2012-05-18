package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v0.AMF0_Type;
import de.uxnr.amf.v0.base.U16;

public class Reference extends AMF0_Type {
	private AMF0_Type value = null;

	public Reference() {
	}

	public Reference(AMF0_Type value) {
		this.value = value;
	}

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		U16 reference = new U16(context.getAMF0ObjectReference(this.value));

		reference.write(context, output);
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		U16 reference = new U16(context, input);

		this.value = context.getAMF0Object(reference.get());

		return this.value;
	}
}
