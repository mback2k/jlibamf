package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v0.AMF0_Type;
import de.uxnr.amf.v0.base.UTF8;

public class Object extends AMF0_Type {
	private static final UTF8 EMPTY_KEY = new UTF8();

	private final Map<UTF8, AMF0_Type> value = new LinkedHashMap<UTF8, AMF0_Type>();

	private Integer hashCode = null;

	public Object() { }

	public Object(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		for (Entry<UTF8, AMF0_Type> entry : this.value.entrySet()) {
			entry.getKey().write(context, output);
			AMF0_Type.writeType(context, output, entry.getValue());
		}

		Object.EMPTY_KEY.write(context, output);
		AMF0_Type.writeType(context, output, new ObjectEnd());

		context.addAMF0Object(this);
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		UTF8 key = new UTF8();
		AMF0_Type value = new ObjectEnd();

		do {
			key = new UTF8(context, input);
			value = AMF0_Type.readType(context, input);

			if (key.get().isEmpty() || value instanceof ObjectEnd) {
				break;
			} else {
				this.value.put(key, value);
			}
		} while (key != null && value != null);

		context.addAMF0Object(this);

		return this;
	}

	public Set<UTF8> keySet() {
		return this.value.keySet();
	}

	public Set<Entry<UTF8, AMF0_Type>> entrySet() {
		return this.value.entrySet();
	}

	public Collection<AMF0_Type> values() {
		return this.value.values();
	}

	public void put(UTF8 key, AMF0_Type value) {
		this.hashCode = null;
		this.value.put(key, value);
	}

	public void put(java.lang.String key, AMF0_Type value) {
		this.put(new UTF8(key), value);
	}

	public void set(UTF8 key, AMF0_Type value) {
		this.put(key, value);
	}

	public void set(java.lang.String key, AMF0_Type value) {
		this.put(new UTF8(key), value);
	}

	public AMF0_Type get(UTF8 key) {
		return this.value.get(key);
	}

	public AMF0_Type get(java.lang.String key) {
		return this.get(new UTF8(key));
	}

	@Override
	public java.lang.String toString() {
		return "Object " + this.value;
	}

	@Override
	public int hashCode() {
		if (this.hashCode != null)
			return this.hashCode;
		return this.hashCode = this.value.hashCode();
	}

	public static class ObjectEnd extends EmptyType {}
}
