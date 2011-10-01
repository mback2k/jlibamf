package de.uxnr.amf.v0;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v0.base.U8;

@SuppressWarnings("rawtypes")
abstract public class AMF0_Type implements AMF_Type {
	private static final Map<U8, Class> types = new HashMap<U8, Class>();
	private static final Map<Class, U8> classes = new HashMap<Class, U8>();

	public static void registerType(U8 type, Class typeClass) {
		AMF0_Type.types.put(type, typeClass);
		AMF0_Type.classes.put(typeClass, type);
	}

	public static void writeType(AMF_Context context, DataOutputStream output, AMF0_Type value) throws IOException {
		AMF0_Type.classes.get(value.getClass()).write(context, output);
		value.write(context, output);
	}

	public static AMF0_Type readType(AMF_Context context, DataInputStream input) throws IOException {
		U8 type = new U8(context, input);
		AMF0_Type value = null;

		if (!AMF0_Type.types.containsKey(type)) {
			throw new RuntimeException("Unsupported type "+type);
		}

		try {
			value = (AMF0_Type) AMF0_Type.types.get(type).newInstance();
		} catch (Exception e) {
			throw new IOException(e);
		}

		value = (AMF0_Type) value.read(context, input);

		return value;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof AMF0_Type))
			return false;

		if (this.hashCode() != obj.hashCode())
			return false;

		return true;
	}
}
