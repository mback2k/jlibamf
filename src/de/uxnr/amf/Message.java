package de.uxnr.amf;

import java.io.IOException;

import de.uxnr.amf.v0.AMF0;
import de.uxnr.amf.v0.base.DataInput;
import de.uxnr.amf.v0.base.DataOutput;

public class Message {
	private String targetURI;
	private String responseURI;
	private long messageLength;
	private Object body;

	public Message(String targetURI, String responseURI) {
		this(targetURI, responseURI, -1);
	}

	public Message(String targetURI, String responseURI, long messageLength) {
		this.targetURI = targetURI;
		this.responseURI = responseURI;
		this.messageLength = messageLength;
	}

	public Message(DataInput input) throws IOException {
		this.read(input);
	}

	public void write(DataOutput output) throws IOException {
		Context context = new Context();

		output.writeUTF8(this.targetURI);
		output.writeUTF8(this.responseURI);
		output.writeU32(this.messageLength);

		AMF0.encode(encoder, this.body);
	}

	public void read(DataInput input) throws IOException {
		Context context = new Context();

		this.targetURI = input.readUTF8();
		this.responseURI = input.readUTF8();
		this.messageLength = input.readU32();

		this.body = AMF0.decode(decoder);
	}

	public String getTargetURI() {
		return this.targetURI;
	}

	public void setTargetURI(String targetURI) {
		this.targetURI = targetURI;
	}

	public String getResponseURI() {
		return this.responseURI;
	}

	public void setResponseURI(String responseURI) {
		this.responseURI = responseURI;
	}

	public long getMessageLength() {
		return this.messageLength;
	}

	public void setMessageLength(long messageLength) {
		this.messageLength = messageLength;
	}

	public Object getBody() {
		return this.body;
	}

	public void setBody(Object body) {
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

		if (!(obj instanceof Message))
			return false;

		if (obj.hashCode() != this.hashCode())
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return this.targetURI.hashCode() ^ this.responseURI.hashCode() ^ this.body.hashCode();
	}
}
