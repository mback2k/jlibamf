package de.uxnr.amf.flex.base;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.flex.type.AsyncMessage;
import de.uxnr.amf.v3.base.UTF8;

public abstract class AcknowledgeMessageBase extends AsyncMessage {
	private static final UTF8[][] names = new UTF8[0][0];

	@Override
	public void write(Context context, DataOutputStream output) throws IOException {
		super.write(context, output);

		this.writeFields(context, output, AcknowledgeMessageBase.names);
	}

	@Override
	public AMF_Type read(Context context, DataInputStream input) throws IOException {
		super.read(context, input);

		this.readFields(context, input, AcknowledgeMessageBase.names);

		return this;
	}
}
