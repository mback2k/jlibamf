package de.uxnr.amf.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;

public abstract class AMF3_Externalizable extends AMF3_Object {
	public abstract void writeExternal(AMF_Context context, DataOutputStream output) throws IOException;

	public abstract void readExternal(AMF_Context context, DataInputStream input) throws IOException;
}
