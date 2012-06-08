package de.uxnr.amf.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.v0.AMF0_Type;
import de.uxnr.amf.v0.base.U8;

abstract public class AMF3_Type extends AMF0_Type {
	private static final Map<U8, Class<? extends AMF3_Type>> types = new HashMap<U8, Class<? extends AMF3_Type>>();
	private static final Map<Class<? extends AMF3_Type>, U8> classes = new HashMap<Class<? extends AMF3_Type>, U8>();

	public static void registerType(U8 type, Class<? extends AMF3_Type> typeClass) {
		AMF3_Type.types.put(type, typeClass);
		AMF3_Type.classes.put(typeClass, type);
	}

	public static void writeType(AMF_Context context, DataOutputStream output, AMF3_Type value) throws IOException {
		if (value instanceof AMF3_Object) {
			AMF3.AMF3_OBJECT_MARKER.write(context, output);

			if (((AMF3_Object) value).writeTrait(context, output)) {
				if (value instanceof AMF3_Externalizable) {
					((AMF3_Externalizable) value).writeExternal(context, output);

				} else {
					((AMF3_Object) value).write(context, output);
				}
			}

		} else {
			AMF3_Type.classes.get(value.getClass()).write(context, output);
			value.write(context, output);
		}
	}

	public static AMF3_Type readType(AMF_Context context, DataInputStream input) throws IOException {
		U8 type = new U8(context, input);
		AMF3_Type value = null;

		if (!AMF3_Type.types.containsKey(type)) {
			throw new RuntimeException("Unsupported type " + type);
		}

		try {
			value = AMF3_Type.types.get(type).newInstance();
		} catch (Exception e) {
			throw new IOException(e);
		}

		value = (AMF3_Type) value.read(context, input);

		return value;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof AMF3_Type))
			return false;

		if (this.hashCode() != obj.hashCode())
			return false;

		return true;
	}
}
