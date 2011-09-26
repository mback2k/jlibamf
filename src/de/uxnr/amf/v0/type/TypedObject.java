package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v0.base.UTF8;

public class TypedObject extends Object {
	private UTF8 className = new UTF8();
	
	public TypedObject() { }
	
	public TypedObject(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		this.className.write(context, output);
		
		super.write(context, output);
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		this.className = new UTF8(context, input);
		
		return super.read(context, input);
	}

	public java.lang.String getClassName() {
		return this.className.get();
	}
	
	@Override
	public java.lang.String toString() {
		return "'"+this.className.toString()+"' "+super.toString();
	}
}
