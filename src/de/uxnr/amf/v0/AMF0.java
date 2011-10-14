package de.uxnr.amf.v0;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import de.uxnr.amf.v0.base.Decoder;
import de.uxnr.amf.v0.base.Encoder;
import de.uxnr.amf.v0.type.AVMPlusObject;
import de.uxnr.amf.v0.type.AnonymousObject;
import de.uxnr.amf.v0.type.Movieclip;
import de.uxnr.amf.v0.type.ObjectEnd;
import de.uxnr.amf.v0.type.RecordSet;
import de.uxnr.amf.v0.type.TypedObject;
import de.uxnr.amf.v0.type.Unsupported;

public class AMF0 {
	public static final int NUMBER_MARKER 			= 0x00;
	public static final int BOOLEAN_MARKER			= 0x01;
	public static final int STRING_MARKER			= 0x02;
	public static final int OBJECT_MARKER			= 0x03;
	public static final int MOVIECLIP_MARKER		= 0x04;
	public static final int NULL_MARKER				= 0x05;
	public static final int UNDEFINED_MARKER		= 0x06;
	public static final int REFERENCE_MARKER		= 0x07;
	public static final int ECMA_ARRAY_MARKER		= 0x08;
	public static final int OBJECT_END_MARKER		= 0x09;
	public static final int STRICT_ARRAY_MARKER		= 0x0A;
	public static final int DATE_MARKER				= 0x0B;
	public static final int LONG_STRING_MARKER		= 0x0C;
	public static final int UNSUPPORTED_MARKER		= 0x0D;
	public static final int RECORDSET_MARKER		= 0x0E;
	public static final int XML_DOCUMENT_MARKER		= 0x0F;
	public static final int TYPED_OBJECT_MARKER		= 0x10;
	public static final int AVMPLUS_OBJECT_MARKER	= 0x11;

	@SuppressWarnings("unchecked")
	public static void encode(Encoder encoder, Object value) throws IOException {
		if (value == null) {
			encoder.writeType(NULL_MARKER);

		} else if (encoder.getContext().getAMF0ObjectReference(value) >= 0) {
			encoder.writeType(REFERENCE_MARKER);
			encoder.writeReference(value);

		} else if (value instanceof Double) {
			encoder.writeType(NUMBER_MARKER);
			encoder.writeNumber(((Double) value));

		} else if (value instanceof Boolean) {
			encoder.writeType(BOOLEAN_MARKER);
			encoder.writeBoolean((Boolean) value);

		} else if (value instanceof String) {
			encoder.writeType(STRING_MARKER);
			encoder.writeString((String) value);

		} else if (value instanceof AnonymousObject) {
			encoder.writeType(OBJECT_MARKER);
			encoder.writeObject((AnonymousObject) value);

		} else if (value instanceof Movieclip) {
			encoder.writeType(MOVIECLIP_MARKER);
			encoder.writeMovieclip((Movieclip) value);

		} else if (value instanceof ObjectEnd) {
			encoder.writeType(OBJECT_END_MARKER);
			encoder.writeObjectEnd((ObjectEnd) value);

		} else if (value instanceof List) {
			encoder.writeType(STRICT_ARRAY_MARKER);
			encoder.writeStrictArray((List<Object>) value);

		} else if (value instanceof Date) {
			encoder.writeType(DATE_MARKER);
			encoder.writeDate((Date) value);

		} else if (value instanceof Unsupported) {
			encoder.writeType(UNSUPPORTED_MARKER);
			encoder.writeUnsupported((Unsupported) value);

		} else if (value instanceof RecordSet) {
			encoder.writeType(RECORDSET_MARKER);
			encoder.writeRecordSet((RecordSet) value);

		} else if (value instanceof Document) {
			encoder.writeType(XML_DOCUMENT_MARKER);
			encoder.writeXMLDocument((Document) value);

		} else if (value instanceof TypedObject) {
			encoder.writeType(TYPED_OBJECT_MARKER);
			encoder.writeTypedObject((TypedObject) value);

		} else if (value instanceof Map) {
			encoder.writeType(ECMA_ARRAY_MARKER);
			encoder.writeECMAArray((Map<String, Object>) value);

		} else if (value instanceof AVMPlusObject) {
			encoder.writeType(AVMPLUS_OBJECT_MARKER);
			encoder.writeAVMPlusObject((AVMPlusObject) value);

		} else {
			encoder.writeType(UNDEFINED_MARKER);
		}
	}

	public static Object decode(Decoder decoder) throws IOException {
		int type = decoder.readType();

		switch (type) {
			case NUMBER_MARKER:
				return decoder.readNumber();

			case BOOLEAN_MARKER:
				return decoder.readBoolean();

			case STRING_MARKER:
				return decoder.readString();

			case OBJECT_MARKER:
				return decoder.readObject();

			case MOVIECLIP_MARKER:
				return decoder.readMovieclip();

			case NULL_MARKER:
				return null;

			case UNDEFINED_MARKER:
				return null;

			case REFERENCE_MARKER:
				return decoder.readReference();

			case ECMA_ARRAY_MARKER:
				return decoder.readECMAArray();

			case OBJECT_END_MARKER:
				return decoder.readObjectEnd();

			case STRICT_ARRAY_MARKER:
				return decoder.readStrictArray();

			case DATE_MARKER:
				return decoder.readDate();

			case LONG_STRING_MARKER:
				return decoder.readLongString();

			case UNSUPPORTED_MARKER:
				return decoder.readUnsupported();

			case RECORDSET_MARKER:
				return decoder.readRecordSet();

			case XML_DOCUMENT_MARKER:
				return decoder.readXMLDocument();

			case TYPED_OBJECT_MARKER:
				return decoder.readTypedObject();

			case AVMPLUS_OBJECT_MARKER:
				return decoder.readAVMPlusObject();
		}

		return null;
	}
}
