package de.uxnr.amf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.v0.AMF0_Type;
import de.uxnr.amf.v0.base.U32;
import de.uxnr.amf.v0.base.UTF8;

public class AMF_Message {
	private UTF8 targetURI;
	private UTF8 responseURI;
	private U32 messageLength;
	private AMF0_Type body;

	public AMF_Message(String targetURI, String responseURI) {
		this(targetURI, responseURI, -1);
	}

	public AMF_Message(String targetURI, String responseURI, long messageLength) {
		this.targetURI = new UTF8(targetURI);
		this.responseURI = new UTF8(responseURI);
		this.messageLength = new U32(messageLength);
	}

	public AMF_Message(DataInputStream input) throws IOException {
		this.read(input);
	}

	public void write(DataOutputStream output) throws IOException {
		AMF_Context context = new AMF_Context();

		this.targetURI.write(context, output);
		this.responseURI.write(context, output);
		this.messageLength.write(context, output);

		AMF0_Type.writeType(context, output, this.body);
	}

	public void read(DataInputStream input) throws IOException {
		AMF_Context context = new AMF_Context();

		this.targetURI = new UTF8(context, input);
		this.responseURI = new UTF8(context, input);
		this.messageLength = new U32(context, input);

		this.body = AMF0_Type.readType(context, input);
	}

	public String getTargetURI() {
		return this.targetURI.get();
	}

	public void setTargetURI(String targetURI) {
		this.targetURI = new UTF8(targetURI);
	}

	public String getResponseURI() {
		return this.responseURI.get();
	}

	public void setResponseURI(String responseURI) {
		this.responseURI = new UTF8(responseURI);
	}

	public long getMessageLength() {
		return this.messageLength.get();
	}

	public void setMessageLength(long messageLength) {
		this.messageLength = new U32(messageLength);
	}

	public AMF0_Type getBody() {
		return this.body;
	}

	public void setBody(AMF0_Type body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "AMF_Message {targetURI: " + this.targetURI + ", responseURI: " + this.responseURI + ", messageLength: " + this.messageLength + "} (" + this.body + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof AMF_Message))
			return false;

		if (obj.hashCode() != this.hashCode())
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return this.targetURI.hashCode() ^ this.responseURI.hashCode() ^ this.messageLength.hashCode() ^ this.body.hashCode();
	}
}
