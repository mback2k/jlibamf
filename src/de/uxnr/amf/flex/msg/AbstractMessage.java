package de.uxnr.amf.flex.msg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v0.base.U8;
import de.uxnr.amf.v3.AMF3_Type;
import de.uxnr.amf.v3.base.UTF8;
import de.uxnr.amf.v3.type.Object;

public abstract class AbstractMessage extends Object {
	private static final UTF8[][] names = new UTF8[][] {
		{
			new UTF8("body"),
			new UTF8("clientId"),
			new UTF8("destination"),
			new UTF8("headers"),
			new UTF8("messageId"),
			new UTF8("timestamp"),
			new UTF8("timeToLive"),
		},
		{
			new UTF8("clientId"),
			new UTF8("messageId"),
		}
	};
	
	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		List<Integer> flags = this.readFlags(context, input);
		
		int index = 0;
		for (Integer flag : flags) {
			int reserved = 0;
			
			for (UTF8 name : AbstractMessage.names[index++]) {
				if (((flag >> (reserved++)) & 1) == 1) {
					this.set(name, AMF3_Type.readType(context, input), true);
				}
			}
		}
		
		return this;
	}
	
	protected List<Integer> readFlags(AMF_Context context, DataInputStream input) throws IOException {
		List<Integer> flags = new Vector<Integer>();
		U8 ubyte = new U8(0x80);
		do {
			ubyte = new U8(context, input);
			flags.add(ubyte.get() & ~0x80);
		} while ((ubyte.get() & 0x80) == 0x80);
		return flags;
	}
}
