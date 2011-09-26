package de.uxnr.amf.v3;

import de.uxnr.amf.v0.base.U8;
import de.uxnr.amf.v3.type.Array;
import de.uxnr.amf.v3.type.ByteArray;
import de.uxnr.amf.v3.type.Date;
import de.uxnr.amf.v3.type.Double;
import de.uxnr.amf.v3.type.False;
import de.uxnr.amf.v3.type.Integer;
import de.uxnr.amf.v3.type.Null;
import de.uxnr.amf.v3.type.Object;
import de.uxnr.amf.v3.type.String;
import de.uxnr.amf.v3.type.True;
import de.uxnr.amf.v3.type.Undefined;
import de.uxnr.amf.v3.type.XML;
import de.uxnr.amf.v3.type.XMLDocument;

public class AMF3 {
	public static final U8 AMF3_UNDEFINED_MARKER 		= new U8(0x00);
	public static final U8 AMF3_NULL_MARKER				= new U8(0x01);
	public static final U8 AMF3_FALSE_MARKER			= new U8(0x02);
	public static final U8 AMF3_TRUE_MARKER				= new U8(0x03);
	public static final U8 AMF3_INTEGER_MARKER			= new U8(0x04);
	public static final U8 AMF3_DOUBLE_MARKER			= new U8(0x05);
	public static final U8 AMF3_STRING_MARKER			= new U8(0x06);
	public static final U8 AMF3_XML_DOC_MARKER			= new U8(0x07);
	public static final U8 AMF3_DATE_MARKER				= new U8(0x08);
	public static final U8 AMF3_ARRAY_MARKER			= new U8(0x09);
	public static final U8 AMF3_OBJECT_MARKER			= new U8(0x0A);
	public static final U8 AMF3_XML_MARKER				= new U8(0x0B);
	public static final U8 AMF3_BYTE_ARRAY_MARKER		= new U8(0x0C);
	
	public static void register() {
		AMF3_Type.registerType(AMF3.AMF3_UNDEFINED_MARKER,		Undefined.class);
		AMF3_Type.registerType(AMF3.AMF3_NULL_MARKER,			Null.class);
		AMF3_Type.registerType(AMF3.AMF3_FALSE_MARKER,			False.class);
		AMF3_Type.registerType(AMF3.AMF3_TRUE_MARKER,			True.class);
		AMF3_Type.registerType(AMF3.AMF3_INTEGER_MARKER,		Integer.class);
		AMF3_Type.registerType(AMF3.AMF3_DOUBLE_MARKER,		Double.class);
		AMF3_Type.registerType(AMF3.AMF3_STRING_MARKER,		String.class);
		AMF3_Type.registerType(AMF3.AMF3_XML_DOC_MARKER,		XMLDocument.class);
		AMF3_Type.registerType(AMF3.AMF3_DATE_MARKER,			Date.class);
		AMF3_Type.registerType(AMF3.AMF3_ARRAY_MARKER,			Array.class);
		AMF3_Type.registerType(AMF3.AMF3_OBJECT_MARKER,		Object.class);
		AMF3_Type.registerType(AMF3.AMF3_XML_MARKER,			XML.class);
		AMF3_Type.registerType(AMF3.AMF3_BYTE_ARRAY_MARKER,	ByteArray.class);
	}
}
