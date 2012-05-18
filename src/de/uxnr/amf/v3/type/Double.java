package de.uxnr.amf.v3.type;

import java.io.DataInputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.v3.base.DOUBLE;

public class Double extends DOUBLE {
	public Double() {
	}

	public Double(double value) {
		this.set(value);
	}

	public Double(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}
}
