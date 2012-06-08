package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.v0.base.U8;

public class Boolean extends U8 {
	public Boolean() {
	}

	public Boolean(boolean value) {
		this.set(value);
	}

	public Boolean(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}

	public void set(boolean value) {
		this.set(value ? 1 : 0);
	}

	public boolean booleanValue() {
		return this.get() != 0;
	}

	@Override
	public java.lang.String toString() {
		return this.booleanValue() ? "True" : "False";
	}

	@Override
	public int hashCode() {
		return this.get();
	}
}
