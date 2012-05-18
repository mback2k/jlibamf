package de.uxnr.amf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

import de.uxnr.amf.flex.Flex;
import de.uxnr.amf.v0.AMF0;
import de.uxnr.amf.v0.base.U16;
import de.uxnr.amf.v3.AMF3;
import de.uxnr.amf.v3.AMF3_Object;
import de.uxnr.amf.v3.base.UTF8;
import de.uxnr.amf.v3.type.Object;

public class AMF {
	static {
		AMF0.register();
		AMF3.register();
		Flex.register();
	}

	private final List<AMF_Header> headers = new Vector<AMF_Header>();
	private final List<AMF_Message> messages = new Vector<AMF_Message>();

	public AMF() {
	}

	public AMF(InputStream input) throws IOException {
		this.read(input);
	}

	public void read(InputStream stream) throws IOException {
		DataInputStream input = new DataInputStream(stream);
		AMF_Context context = new AMF_Context();

		U16 version = new U16(context, input);
		if (version.get() != 0 && version.get() != 3)
			throw new IOException("Unsupported AMF version");

		U16 headers = new U16(context, input);
		for (int index = 0; index < headers.get(); index++)
			this.headers.add(new AMF_Header(input));

		U16 messages = new U16(context, input);
		for (int index = 0; index < messages.get(); index++)
			this.messages.add(new AMF_Message(input));
	}

	public void write(OutputStream stream) throws IOException {
		DataOutputStream output = new DataOutputStream(stream);
		AMF_Context context = new AMF_Context();

		U16 version = new U16(0);
		version.write(context, output);

		U16 headers = new U16(this.headers.size());
		headers.write(context, output);
		for (AMF_Header header : this.headers) {
			header.write(output);
		}

		U16 messages = new U16(this.messages.size());
		messages.write(context, output);
		for (AMF_Message message : this.messages) {
			message.write(output);
		}
	}

	public List<AMF_Header> getHeaders() {
		return this.headers;
	}

	public List<AMF_Message> getMessages() {
		return this.messages;
	}

	public void addHeader(AMF_Header header) {
		this.headers.add(header);
	}

	public void addMessage(AMF_Message message) {
		this.messages.add(message);
	}

	public void removeHeader(AMF_Header header) {
		this.headers.remove(header);
	}

	public void removeHeader(int index) {
		this.headers.remove(index);
	}

	public void removeMessage(AMF_Message message) {
		this.messages.remove(message);
	}

	public void removeMessage(int index) {
		this.messages.remove(index);
	}

	@Override
	public String toString() {
		return "AMF {headers: " + this.headers + ", messages: " + this.messages + "}";
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof AMF))
			return false;

		if (obj.hashCode() != this.hashCode())
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return this.headers.hashCode() ^ this.messages.hashCode();
	}

	public static void registerClass(String className, Class<? extends AMF3_Object> classType) {
		Object.registerClass(new UTF8(className), classType);
	}

	public static void registerClassMapping(String remoteName, String localName) {
		Object.registerClassMapping(remoteName, localName);
	}
}
