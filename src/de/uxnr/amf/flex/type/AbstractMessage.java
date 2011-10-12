package de.uxnr.amf.flex.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.flex.base.AbstractMessageBase;
import de.uxnr.amf.v3.AMF3_Type;
import de.uxnr.amf.v3.type.ByteArray;

public abstract class AbstractMessage extends AbstractMessageBase {
	private AMF3_Type body;
	private String clientId;
	private String destination;
	private Collection<AMF3_Type> headers;
	private String messageId;
	private Double timestamp;
	private Double timeToLive;
	private ByteArray clientUuid;
	private ByteArray messageUuid;

	public AMF3_Type getBody() {
		return this.body;
	}

	public String getClientId() {
		return this.clientId;
	}

	public String getDestination() {
		return this.destination;
	}

	public Collection<AMF3_Type> getHeaders() {
		return this.headers;
	}

	public String getMessageId() {
		return this.messageId;
	}

	public Double getTimestamp() {
		return this.timestamp;
	}

	public Double getTimeToLive() {
		return this.timeToLive;
	}

	public ByteArray getClientUuid() {
		return this.clientUuid;
	}

	public ByteArray getMessageUuid() {
		return this.messageUuid;
	}

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		// TODO Write object fields

		super.write(context, output);
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		super.read(context, input);

		this.readFields(AbstractMessage.class, this.getData());

		return this;
	}
}
