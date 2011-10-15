package de.uxnr.amf.flex.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.v3.AMF3_Externalizable;
import de.uxnr.amf.v3.AMF3_Type;
import de.uxnr.amf.v3.type.Object;

public class ObjectProxy extends AMF3_Externalizable {
	private Object object;

	@Override
	public void writeExternal(AMF_Context context, DataOutputStream output) throws IOException {
		AMF3_Type.writeType(context, output, this.object);
	}

	@Override
	public void readExternal(AMF_Context context, DataInputStream input) throws IOException {
		this.object = (Object) AMF3_Type.readType(context, input);
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object getObject() {
		return this.object;
	}

	@Override
	public String toString() {
		return "ObjectProxy(" + this.object + ")";
	}

	@Override
	public int hashCode() {
		return Integer.reverse(this.object.hashCode());
	}
}
