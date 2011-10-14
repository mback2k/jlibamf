package de.uxnr.amf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

import de.uxnr.amf.flex.Flex;
import de.uxnr.amf.v0.base.DataInput;
import de.uxnr.amf.v0.base.DataOutput;
import de.uxnr.amf.v3.AMF3;

public class AMF {
	static {
		Flex.register();
	}

	private final List<Header> headers = new Vector<Header>();
	private final List<Message> messages = new Vector<Message>();

	public AMF() { }

	public AMF(InputStream input) throws IOException {
		this.read(input);
	}

	public void read(InputStream stream) throws IOException {
		DataInput input = new DataInput(stream);

		int version = input.readU16();
		if (version != 0 && version != 3)
			throw new IOException("Unsupported AMF version");

		int headers = input.readU16();
		for (int index = 0; index < headers; index++)
			this.headers.add(new Header(input));

		int messages = input.readU16();
		for (int index = 0; index < messages; index++)
			this.messages.add(new Message(input));
	}

	public void write(OutputStream stream) throws IOException {
		DataOutput output = new DataOutput(stream);

		output.writeU16(0);

		output.writeU16(this.headers.size());
		for (Header header : this.headers) {
			header.write(output);
		}

		output.writeU16(this.messages.size());
		for (Message message : this.messages) {
			message.write(output);
		}
	}

	public List<Header> getHeaders() {
		return this.headers;
	}

	public List<Message> getMessages() {
		return this.messages;
	}

	public void addHeader(Header header) {
		this.headers.add(header);
	}

	public void addMessage(Message message) {
		this.messages.add(message);
	}

	public void removeHeader(Header header) {
		this.headers.remove(header);
	}

	public void removeHeader(int index) {
		this.headers.remove(index);
	}

	public void removeMessage(Message message) {
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

	public static void registerObjectClass(String className, Class<? extends Externalizable> objectClass) {
		AMF3.registerObjectClass(className, objectClass);
	}
}
