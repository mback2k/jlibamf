package de.uxnr.amf.flex.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.flex.base.AsyncMessageBase;

public class AsyncMessage extends AsyncMessageBase {
	private String correlationId;
	private int[] correlationUuid;

	public String getCorrelationId() {
		return this.correlationId;
	}

	public int[] getCorrelationUuid() {
		return this.correlationUuid;
	}

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		// TODO Write object fields

		super.write(context, output);
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		super.read(context, input);

		this.readFields(AsyncMessage.class, this.getObjectData());

		return this;
	}

	@Override
	public void writeExternal(AMF_Context context, DataOutputStream output) throws IOException {
		// TODO Write object fields

		super.writeExternal(context, output);
	}

	@Override
	public void readExternal(AMF_Context context, DataInputStream input) throws IOException {
		super.readExternal(context, input);

		this.readFields(AsyncMessage.class, this.getObjectData());
	}
}
