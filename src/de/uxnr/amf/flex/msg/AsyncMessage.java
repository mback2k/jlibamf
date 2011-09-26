package de.uxnr.amf.flex.msg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v3.AMF3_Type;
import de.uxnr.amf.v3.base.UTF8;

public class AsyncMessage extends AbstractMessage {
	private static final UTF8[][] names = new UTF8[][] {
		{
			new UTF8("correlationId"),
			new UTF8("correlationId"),
		}
	};
	
	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		super.read(context, input);
		
		List<Integer> flags = this.readFlags(context, input);
		
		int index = 0;
		for (Integer flag : flags) {
			int reserved = 0;
			
			for (UTF8 name : AsyncMessage.names[index++]) {
				if (((flag >> (reserved++)) & 1) == 1) {
					this.set(name, AMF3_Type.readType(context, input), true);
				}
			}
		}
		
		return this;
	}
}
