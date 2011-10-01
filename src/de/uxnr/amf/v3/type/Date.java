package de.uxnr.amf.v3.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v3.base.DOUBLE;
import de.uxnr.amf.v3.base.U29;

public class Date extends DOUBLE {
	public Date() { }

	public Date(java.util.Date value) {
		this.set(value);
	}

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		U29 flag = new U29(1);
		flag.write(context, output);

		super.write(context, output);
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		U29 flag = new U29(context, input);

		if ((flag.get() & 1) == 0)
			return context.getAMF3Object(flag.get() >> 1);

		super.read(context, input);

		context.addAMF3Object(this);

		return this;
	}

	public void set(java.util.Date value) {
		this.set(java.lang.Double.valueOf(value.getTime()));
	}

	public java.util.Date dateValue() {
		return new java.util.Date((long) super.get());
	}

	@Override
	public java.lang.String toString() {
		return this.dateValue().toString();
	}

	@Override
	public int hashCode() {
		return this.dateValue().hashCode();
	}
}
