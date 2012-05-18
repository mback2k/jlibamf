package de.uxnr.amf.v3.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v3.base.DOUBLE;
import de.uxnr.amf.v3.base.U29;

public class Date extends DOUBLE {
	public Date() {
	}

	public Date(java.util.Date value) {
		this.set(value);
	}

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		int reference = context.getAMF3ObjectReference(this);
		if (reference >= 0) {
			U29 flag = new U29((reference << 1) & ~1);
			flag.write(context, output);
			return;
		}

		context.addAMF3Object(this);

		U29 flag = new U29(1);
		flag.write(context, output);

		super.write(context, output);
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		U29 flag = new U29(context, input);
		int flags = flag.get();

		if ((flags & 1) == 0)
			return context.getAMF3Object(flags >> 1);

		context.addAMF3Object(this);

		super.read(context, input);

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
