package de.uxnr.amf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.v0.AMF0_Type;
import de.uxnr.amf.v0.base.U32;
import de.uxnr.amf.v0.base.U8;
import de.uxnr.amf.v0.base.UTF8;

public class AMF_Header {
	private UTF8 headerName;
	private U8 mustUnderstand;
	private U32 headerLength;
	private AMF0_Type body;

	public AMF_Header(String headerName) {
		this(headerName, false);
	}

	public AMF_Header(String headerName, boolean mustUnderstand) {
		this(headerName, mustUnderstand, -1);
	}

	public AMF_Header(String headerName, boolean mustUnderstand, long headerLength) {
		this.headerName = new UTF8(headerName);
		this.mustUnderstand = new U8(mustUnderstand ? 1 : 0);
		this.headerLength = new U32(headerLength);
	}

	public AMF_Header(DataInputStream input) throws IOException {
		this.read(input);
	}

	public void write(DataOutputStream output) throws IOException {
		AMF_Context context = new AMF_Context();

		this.headerName.write(context, output);
		this.mustUnderstand.write(context, output);
		this.headerLength.write(context, output);

		AMF0_Type.writeType(context, output, this.body);
	}

	public void read(DataInputStream input) throws IOException {
		AMF_Context context = new AMF_Context();

		this.headerName = new UTF8(context, input);
		this.mustUnderstand = new U8(context, input);
		this.headerLength = new U32(context, input);

		this.body = AMF0_Type.readType(context, input);
	}

	public String getHeaderName() {
		return this.headerName.get();
	}

	public void setHeaderName(String headerName) {
		this.headerName = new UTF8(headerName);
	}

	public boolean getMustUnderstand() {
		return this.mustUnderstand.get() != 0;
	}

	public void setMustUnderstand(boolean mustUnderstand) {
		this.mustUnderstand = new U8(mustUnderstand ? 1 : 0);
	}

	public long getHeaderLength() {
		return this.headerLength.get();
	}

	public void setHeaderLength(long length) {
		this.headerLength = new U32(length);
	}

	public AMF0_Type getBody() {
		return this.body;
	}

	public void setBody(AMF0_Type body) {
		this.body = body;
	}

	@Override
	public String toString() {
		String str = "AMF_Header\n";
		str += "\tHeaderName: "+this.headerName+"\n";
		str += "\tMustUnderstand: "+this.mustUnderstand+"\n";
		str += "\tHeaderLength: "+this.headerLength+"\n";
		str += this.body;
		return str;
	}
}
