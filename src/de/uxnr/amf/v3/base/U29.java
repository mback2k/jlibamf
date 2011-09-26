package de.uxnr.amf.v3.base;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v3.AMF3_Type;

public class U29 extends AMF3_Type {
	private long value = 0;
	
	public U29() { }
	
	public U29(long value) {
		this.set(value);
	}
	
	public U29(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		boolean more = false;
		int read = 0;
		
		for (int index = 0; index < 3; index++) {
			read = input.read();
			more = (read & 0x80) == 0x80;
			
			this.value = read & 0x7F;
			
			if (!more)
				return this;
			
			this.value <<= 7;
		}
		
		this.value <<= 1;
		
		read = input.read();
		
		this.value = read & 0xFF;
		
		return this;
	}

	public void set(long value) {
		this.value = value;
	}

	public long get() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.value);
	}
	
	@Override
	public int hashCode() {
		return (int) this.value;
	}
}
