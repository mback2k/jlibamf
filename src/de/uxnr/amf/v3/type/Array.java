package de.uxnr.amf.v3.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v3.AMF3_Type;
import de.uxnr.amf.v3.base.U29;
import de.uxnr.amf.v3.base.UTF8;

public class Array extends AMF3_Type {
	private static final UTF8 EMPTY_KEY = new UTF8();

	private final Map<UTF8, AMF3_Type> value1 = new LinkedHashMap<UTF8, AMF3_Type>();
	private final List<AMF3_Type> value2 = new Vector<AMF3_Type>();

	private java.lang.Integer hashCode = null;

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		int reference = context.getAMF3ObjectReference(this);
		if (reference >= 0) {
			U29 flag = new U29((reference << 1) & ~1);
			flag.write(context, output);
			return;
		}

		context.addAMF3Object(this);

		U29 length = new U29((this.value2.size() << 1) | 1);
		length.write(context, output);

		for (Entry<UTF8, AMF3_Type> entry : this.value1.entrySet()) {
			entry.getKey().write(context, output);
			AMF3_Type.writeType(context, output, entry.getValue());
		}

		Array.EMPTY_KEY.write(context, output);

		for (AMF3_Type value : this.value2) {
			AMF3_Type.writeType(context, output, value);
		}
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		U29 flag = new U29(context, input);
		int flags = flag.get();

		if ((flags & 1) == 0)
			return context.getAMF3Object(flags >> 1);

		context.addAMF3Object(this);

		UTF8 key = null;
		AMF3_Type value = null;
		do {
			key = new UTF8();
			key = (UTF8) key.read(context, input);

			if (key.equals(Array.EMPTY_KEY)) {
				break;
			} else {
				value = AMF3_Type.readType(context, input);

				this.value1.put(key, value);
			}
		} while (key != null && value != null);

		int length = (flags >> 1);
		for (int index = 0; index < length; index++) {
			value = AMF3_Type.readType(context, input);

			this.value2.add(value);
		}

		this.hashCode = null;

		return this;
	}

	public Set<UTF8> keySet() {
		return this.value1.keySet();
	}

	public Set<Entry<UTF8, AMF3_Type>> entrySet() {
		return this.value1.entrySet();
	}

	public void put(UTF8 key, AMF3_Type value) {
		this.hashCode = null;
		this.value1.put(key, value);
	}

	public void set(UTF8 key, AMF3_Type value) {
		this.put(key, value);
	}

	public AMF3_Type get(UTF8 key) {
		return this.value1.get(key);
	}

	public void set(int index, AMF3_Type value) {
		this.hashCode = null;
		this.value2.set(index, value);
	}

	public AMF3_Type get(int index) {
		return this.value2.get(index);
	}

	@Override
	public java.lang.String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Array {\n");
		for (Entry<UTF8, AMF3_Type> entry : this.value1.entrySet()) {
			sb.append(entry.getKey());
			sb.append(": ");
			sb.append(entry.getValue());
			sb.append(",\n");
		}
		sb.append("} ; [\n");
		for (AMF3_Type value : this.value2) {
			sb.append(value.toString());
			sb.append(",\n");
		}
		sb.append("]\n");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		if (this.hashCode != null)
			return this.hashCode;
		return this.hashCode = this.value1.hashCode() ^ this.value2.hashCode();
	}
}
