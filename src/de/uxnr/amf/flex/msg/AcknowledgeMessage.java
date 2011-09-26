package de.uxnr.amf.flex.msg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;

public class AcknowledgeMessage extends AsyncMessage {
	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		super.read(context, input);
		
		this.readFlags(context, input);
		
		return this;
	}
}
