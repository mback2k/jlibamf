package de.uxnr.amf.flex.base;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.flex.type.AsyncMessage;
import de.uxnr.amf.v3.base.UTF8;

public abstract class CommandMessageBase extends AsyncMessage {
	private static final UTF8[][] names = new UTF8[][] {
		{
			new UTF8("operation"),
		}
	};

	@Override
	public void writeExternal(AMF_Context context, DataOutputStream output) throws IOException {
		super.writeExternal(context, output);

		this.writeFields(context, output, CommandMessageBase.names);
	}

	@Override
	public void readExternal(AMF_Context context, DataInputStream input) throws IOException {
		super.readExternal(context, input);

		this.readFields(context, input, CommandMessageBase.names);
	}
}
