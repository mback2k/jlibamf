package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.v0.base.DOUBLE;

public class Number extends DOUBLE {
	public Number() {
	}

	public Number(double value) {
		this.set(value);
	}

	public Number(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}
}
