package de.uxnr.amf.v0;

import de.uxnr.amf.v0.base.U8;
import de.uxnr.amf.v0.type.AVMPlusObject;
import de.uxnr.amf.v0.type.Boolean;
import de.uxnr.amf.v0.type.Date;
import de.uxnr.amf.v0.type.ECMAArray;
import de.uxnr.amf.v0.type.LongString;
import de.uxnr.amf.v0.type.Movieclip;
import de.uxnr.amf.v0.type.Null;
import de.uxnr.amf.v0.type.Number;
import de.uxnr.amf.v0.type.Object;
import de.uxnr.amf.v0.type.RecordSet;
import de.uxnr.amf.v0.type.Reference;
import de.uxnr.amf.v0.type.StrictArray;
import de.uxnr.amf.v0.type.String;
import de.uxnr.amf.v0.type.TypedObject;
import de.uxnr.amf.v0.type.Undefined;
import de.uxnr.amf.v0.type.Unsupported;
import de.uxnr.amf.v0.type.XMLDocument;

public class AMF0 {
	public static final U8 AMF0_NUMBER_MARKER = new U8(0x00);
	public static final U8 AMF0_BOOLEAN_MARKER = new U8(0x01);
	public static final U8 AMF0_STRING_MARKER = new U8(0x02);
	public static final U8 AMF0_OBJECT_MARKER = new U8(0x03);
	public static final U8 AMF0_MOVIECLIP_MARKER = new U8(0x04);
	public static final U8 AMF0_NULL_MARKER = new U8(0x05);
	public static final U8 AMF0_UNDEFINED_MARKER = new U8(0x06);
	public static final U8 AMF0_REFERENCE_MARKER = new U8(0x07);
	public static final U8 AMF0_ECMA_ARRAY_MARKER = new U8(0x08);
	public static final U8 AMF0_OBJECT_END_MARKER = new U8(0x09);
	public static final U8 AMF0_STRICT_ARRAY_MARKER = new U8(0x0A);
	public static final U8 AMF0_DATE_MARKER = new U8(0x0B);
	public static final U8 AMF0_LONG_STRING_MARKER = new U8(0x0C);
	public static final U8 AMF0_UNSUPPORTED_MARKER = new U8(0x0D);
	public static final U8 AMF0_RECORDSET_MARKER = new U8(0x0E);
	public static final U8 AMF0_XML_DOCUMENT_MARKER = new U8(0x0F);
	public static final U8 AMF0_TYPED_OBJECT_MARKER = new U8(0x10);
	public static final U8 AMF0_AVMPLUS_OBJECT_MARKER = new U8(0x11);

	public static void register() {
		AMF0_Type.registerType(AMF0.AMF0_NUMBER_MARKER, Number.class);
		AMF0_Type.registerType(AMF0.AMF0_BOOLEAN_MARKER, Boolean.class);
		AMF0_Type.registerType(AMF0.AMF0_STRING_MARKER, String.class);
		AMF0_Type.registerType(AMF0.AMF0_OBJECT_MARKER, Object.class);
		AMF0_Type.registerType(AMF0.AMF0_MOVIECLIP_MARKER, Movieclip.class);
		AMF0_Type.registerType(AMF0.AMF0_NULL_MARKER, Null.class);
		AMF0_Type.registerType(AMF0.AMF0_UNDEFINED_MARKER, Undefined.class);
		AMF0_Type.registerType(AMF0.AMF0_REFERENCE_MARKER, Reference.class);
		AMF0_Type.registerType(AMF0.AMF0_ECMA_ARRAY_MARKER, ECMAArray.class);
		AMF0_Type.registerType(AMF0.AMF0_OBJECT_END_MARKER, Object.ObjectEnd.class);
		AMF0_Type.registerType(AMF0.AMF0_STRICT_ARRAY_MARKER, StrictArray.class);
		AMF0_Type.registerType(AMF0.AMF0_DATE_MARKER, Date.class);
		AMF0_Type.registerType(AMF0.AMF0_LONG_STRING_MARKER, LongString.class);
		AMF0_Type.registerType(AMF0.AMF0_UNSUPPORTED_MARKER, Unsupported.class);
		AMF0_Type.registerType(AMF0.AMF0_RECORDSET_MARKER, RecordSet.class);
		AMF0_Type.registerType(AMF0.AMF0_XML_DOCUMENT_MARKER, XMLDocument.class);
		AMF0_Type.registerType(AMF0.AMF0_TYPED_OBJECT_MARKER, TypedObject.class);
		AMF0_Type.registerType(AMF0.AMF0_AVMPLUS_OBJECT_MARKER, AVMPlusObject.class);
	}
}
