package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.v0.base.UTF8;

public class String extends UTF8 {
	public String() {
	}

	public String(java.lang.String value) {
		this.set(value);
	}

	public String(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}
}
