package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.v0.base.UTF8long;

public class LongString extends UTF8long {
	public LongString() { }

	public LongString(java.lang.String value) {
		this.set(value);
	}

	public LongString(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}
}
