package de.uxnr.amf.flex.msg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v3.base.UTF8;

public class CommandMessage extends AsyncMessage {
	private static final UTF8[][] names = new UTF8[][] {
		{
			new UTF8("operation"),
		}
	};

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		super.read(context, input);

		this.readFields(context, input, CommandMessage.names);

		return this;
	}
}
