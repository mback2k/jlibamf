package de.uxnr.amf.v3.type;

import java.io.DataInputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;

public class True extends EmptyType {
	public True() { }
	
	public True(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}
	
	@Override
	public java.lang.String toString() {
		return "True";
	}
	
	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}
}
