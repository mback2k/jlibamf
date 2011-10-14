package de.uxnr.amf;

import java.io.IOException;

import de.uxnr.amf.v0.AMF0;
import de.uxnr.amf.v0.base.DataInput;
import de.uxnr.amf.v0.base.DataOutput;

public class Header {
	private String headerName;
	private boolean mustUnderstand;
	private long headerLength;
	private Object body;

	public Header(String headerName) {
		this(headerName, false);
	}

	public Header(String headerName, boolean mustUnderstand) {
		this(headerName, mustUnderstand, -1);
	}

	public Header(String headerName, boolean mustUnderstand, long headerLength) {
		this.headerName = headerName;
		this.mustUnderstand = mustUnderstand;
		this.headerLength = headerLength;
	}

	public Header(DataInput input) throws IOException {
		this.read(input);
	}

	public void write(DataOutput output) throws IOException {
		Context context = new Context();

		output.writeUTF8(this.headerName);
		output.writeU8(this.mustUnderstand ? 1 : 0);
		output.writeU32(this.headerLength);

		AMF0.encode(encoder, this.body);
	}

	public void read(DataInput input) throws IOException {
		Context context = new Context();

		this.headerName = input.readUTF8();
		this.mustUnderstand = input.readU8() != 0;
		this.headerLength = input.readU32();

		this.body = AMF0.decode(decoder);
	}

	public String getHeaderName() {
		return this.headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public boolean getMustUnderstand() {
		return this.mustUnderstand;
	}

	public void setMustUnderstand(boolean mustUnderstand) {
		this.mustUnderstand = mustUnderstand;
	}

	public long getHeaderLength() {
		return this.headerLength;
	}

	public void setHeaderLength(long headerLength) {
		this.headerLength = headerLength;
	}

	public Object getBody() {
		return this.body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "AMF_Header {headerName: " + this.headerName + ", mustUnderstand: " + this.mustUnderstand + ", headerLength: " + this.headerLength + "} (" + this.body + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof Header))
			return false;

		if (obj.hashCode() != this.hashCode())
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return this.headerName.hashCode() ^ this.body.hashCode();
	}
}
