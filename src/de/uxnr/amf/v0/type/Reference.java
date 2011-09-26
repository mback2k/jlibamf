package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v0.base.U16;

public class Reference extends EmptyType {
	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		U16 reference = new U16(context, input);
		
		return context.getAMF0Object(reference.get());
	}
}
