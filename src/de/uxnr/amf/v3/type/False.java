package de.uxnr.amf.v3.type;

import java.io.DataInputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;

public class False extends EmptyType {
	public False() {
	}

	public False(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}

	@Override
	public java.lang.String toString() {
		return "False";
	}

	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}
}
