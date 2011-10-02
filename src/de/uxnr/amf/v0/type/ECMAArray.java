package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.v0.AMF0_Type;
import de.uxnr.amf.v0.base.U32;
import de.uxnr.amf.v0.base.UTF8;

public class ECMAArray extends AMF0_Type {
	private final Map<UTF8, AMF0_Type> value = new LinkedHashMap<UTF8, AMF0_Type>();

	public ECMAArray() { }

	public ECMAArray(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		U32 length = new U32(this.value.size());
		length.write(context, output);

		for (Entry<UTF8, AMF0_Type> entry : this.value.entrySet()) {
			entry.getKey().write(context, output);
			AMF0_Type.writeType(context, output, entry.getValue());
		}

		context.addAMF0Object(this);
	}

	@Override
	public AMF0_Type read(AMF_Context context, DataInputStream input) throws IOException {
		U32 length = new U32(context, input);

		for (long index = 0; index < length.get(); index++) {
			UTF8 key = new UTF8(context, input);
			AMF0_Type value = AMF0_Type.readType(context, input);

			this.value.put(key, value);
		}

		context.addAMF0Object(this);

		return this;
	}

	public Map<UTF8, AMF0_Type> get() {
		return this.value;
	}

	public void set(UTF8 key, AMF0_Type value) {
		this.value.put(key, value);
	}

	public AMF0_Type get(UTF8 key) {
		return this.value.get(key);
	}

	@Override
	public java.lang.String toString() {
		return "ECMA Array " + this.value;
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}
}
