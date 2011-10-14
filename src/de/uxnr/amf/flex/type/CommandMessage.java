package de.uxnr.amf.flex.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.flex.base.CommandMessageBase;

public class CommandMessage extends CommandMessageBase {
	private int operation;

	public int getOperation() {
		return this.operation;
	}

	@Override
	public void write(Context context, DataOutputStream output) throws IOException {
		// TODO Write object fields

		super.write(context, output);
	}

	@Override
	public AMF_Type read(Context context, DataInputStream input) throws IOException {
		super.read(context, input);

		this.readFields(CommandMessage.class, this.getData());

		return this;
	}
}
