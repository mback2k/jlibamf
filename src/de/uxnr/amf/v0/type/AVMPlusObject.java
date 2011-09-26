package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v3.AMF3_Type;

public class AVMPlusObject extends EmptyType {
	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		return AMF3_Type.readType(context, input);
	}
}
