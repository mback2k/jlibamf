package de.uxnr.amf.v3.base;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v0.base.U8;
import de.uxnr.amf.v3.AMF3_Type;

public class U29 extends AMF3_Type {
	private long value = 0;
	
	public boolean signed = false;
	
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
		this.value = 0;
		int more = 0;
		int read = new U8(context, input).get();
		
		while ((read & 0x80) != 0 && more < 3) {
			this.value <<= 7;
			this.value |= read & 0x7F;
			
			read = input.read();
			more++;
		}
		
		if (more < 3) {
			this.value <<= 7;
			this.value |= read;
		} else {
			this.value <<= 8;
			this.value |= read;
			
	        if ((this.value & 0x10000000) != 0) {
	        	if (this.signed) {
	        		this.value -= 0x20000000;
	        	} else {
	        		this.value <<= 1;
	        		this.value += 1;
	        	}
	        }
		}
		
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
