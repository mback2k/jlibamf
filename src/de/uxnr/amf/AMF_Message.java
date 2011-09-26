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
	
	public AMF_Message(String targetURI, String responseURI, long messageLength) {
		this.targetURI = new UTF8(targetURI);
		this.responseURI = new UTF8(responseURI);
		this.messageLength = new U32(messageLength);
	}
	
	public AMF_Message(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}
	
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		this.targetURI.write(context, output);
		this.responseURI.write(context, output);
		this.messageLength.write(context, output);
		AMF0_Type.writeType(context, output, this.body);
	}
	
	public void read(AMF_Context context, DataInputStream input) throws IOException {
		this.targetURI = new UTF8(context, input);
		this.responseURI = new UTF8(context, input);
		this.messageLength = new U32(context, input);
		this.body = AMF0_Type.readType(context, input);
	}
	
	public String getTargetURI() {
		return this.targetURI.get();
	}
	
	public String getResponseURI() {
		return this.responseURI.get();
	}
	
	public long getMessageLength() {
		return this.messageLength.get();
	}
	
	public AMF0_Type getBody() {
		return this.body;
	}
	
	@Override
	public String toString() {
		String str = "AMF_Message\n";
		str += "\tTargetURI: "+this.targetURI+"\n";
		str += "\tResponseURI: "+this.responseURI+"\n";
		str += "\tMessageLength: "+this.messageLength+"\n";
		str += this.body;
		return str;
	}
}
