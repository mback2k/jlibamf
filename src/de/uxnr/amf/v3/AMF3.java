package de.uxnr.amf.v3;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

import de.uxnr.amf.Externalizable;
import de.uxnr.amf.flex.IExternalizable;
import de.uxnr.amf.v3.base.Decoder;
import de.uxnr.amf.v3.base.Encoder;
import de.uxnr.amf.v3.type.TypedObject;

public class AMF3 {
	public static final int UNDEFINED_MARKER 	= 0x00;
	public static final int NULL_MARKER			= 0x01;
	public static final int FALSE_MARKER		= 0x02;
	public static final int TRUE_MARKER			= 0x03;
	public static final int INTEGER_MARKER		= 0x04;
	public static final int DOUBLE_MARKER		= 0x05;
	public static final int STRING_MARKER		= 0x06;
	public static final int XML_DOC_MARKER		= 0x07;
	public static final int DATE_MARKER			= 0x08;
	public static final int ARRAY_MARKER		= 0x09;
	public static final int OBJECT_MARKER		= 0x0A;
	public static final int XML_MARKER			= 0x0B;
	public static final int BYTE_ARRAY_MARKER	= 0x0C;

	public static void encode(Encoder encoder, Object value) throws IOException {
		if (value == null) {
			encoder.writeType(NULL_MARKER);

		} else if (value instanceof Boolean) {
			if (((Boolean) value).booleanValue()) {
				encoder.writeType(TRUE_MARKER);
			} else {
				encoder.writeType(FALSE_MARKER);
			}

		} else if (value instanceof Integer) {
			encoder.writeType(INTEGER_MARKER);
			encoder.writeInteger((Integer) value);

		} else if (value instanceof Double) {
			encoder.writeType(DOUBLE_MARKER);
			encoder.writeDouble((Double) value);

		} else if (value instanceof String) {
			encoder.writeType(STRING_MARKER);
			encoder.writeString((String) value);

		} else if (value instanceof Document) {
			encoder.writeType(XML_DOC_MARKER);
			encoder.writeXMLDocument((Document) value);

		} else if (value instanceof Date) {
			encoder.writeType(DATE_MARKER);
			encoder.writeDate((Date) value);

		} else if (value instanceof TypedObject) {
			encoder.writeType(OBJECT_MARKER);
			encoder.writeObject(value);

		} else if (value instanceof Map) {
			encoder.writeType(ARRAY_MARKER);
			encoder.writeArray((Map<String, Object>) value);

		} else {
			encoder.writeType(UNDEFINED_MARKER);
		}
	}

	public static Object decode(Decoder decoder) throws IOException {
		int type = decoder.readType();

		switch (type) {
			case UNDEFINED_MARKER:
				return null;

			case NULL_MARKER:
				return null;

			case FALSE_MARKER:
				return new Boolean(false);

			case TRUE_MARKER:
				return new Boolean(true);

			case INTEGER_MARKER:
				return decoder.readInteger();

			case DOUBLE_MARKER:
				return decoder.readDouble();

			case STRING_MARKER:
				return decoder.readString();

			case XML_DOC_MARKER:
				return decoder.readXMLDocument();

			case DATE_MARKER:
				return decoder.readDate();

			case ARRAY_MARKER:
				return decoder.readArray();

			case OBJECT_MARKER:
				return decoder.readObject();

			case XML_MARKER:
				return decoder.readXMLDocument();

			case BYTE_ARRAY_MARKER:
				return decoder.readByteArray();
		}

		return null;
	}

	// ------------------------------------------------------------------------

	private static final Map<String, Class<? extends Externalizable>> objectClasses = new HashMap<String, Class<? extends Externalizable>>();
	private static final Map<String, Class<IExternalizable>> externalizableClasses = new HashMap<String, Class<IExternalizable>>();

	public static void registerObjectClass(String className, Class<? extends Externalizable> objectClass) {
		AMF3.objectClasses.put(className, objectClass);
	}

	public static boolean hasObjectClass(String className) {
		return AMF3.objectClasses.containsKey(className);
	}

	public static Class<? extends Externalizable> getObjectClass(String className) {
		return AMF3.objectClasses.get(className);
	}

	public static void registerExternalizableClass(String className, Class<IExternalizable> externalizableClass) {
		AMF3.externalizableClasses.put(className, externalizableClass);
	}

	public static boolean hasExternalizableClass(String className) {
		return AMF3.externalizableClasses.containsKey(className);
	}

	public static Class<IExternalizable> getExternalizableClass(String className) {
		return AMF3.externalizableClasses.get(className);
	}
}
