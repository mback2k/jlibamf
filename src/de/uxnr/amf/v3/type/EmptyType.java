package de.uxnr.amf.v3.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v3.AMF3_Type;

public abstract class EmptyType extends AMF3_Type {
	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		// Nothing to do here
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		// Nothing to do here
		return this;
	}
}
