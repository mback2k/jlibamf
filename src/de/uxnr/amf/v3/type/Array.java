package de.uxnr.amf.v3.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v3.AMF3_Type;
import de.uxnr.amf.v3.base.U29;
import de.uxnr.amf.v3.base.UTF8;

public class Array extends AMF3_Type {
	private static final UTF8 EMPTY_KEY = new UTF8();
	
	private Map<UTF8, AMF3_Type> value1 = new HashMap<UTF8, AMF3_Type>();
	private List<AMF3_Type> value2 = new Vector<AMF3_Type>();

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		U29 length = new U29((this.value2.size() << 1) | 1);
		length.write(context, output);
		
		for (Entry<UTF8, AMF3_Type> entry : this.value1.entrySet()) {
			entry.getKey().write(context, output);
			AMF3_Type.writeType(context, output, entry.getValue());
		}
		
		Array.EMPTY_KEY.write(context, output);
		
		for (AMF3_Type value : this.value2) {
			AMF3_Type.writeType(context, output, value);
		}
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		U29 flag = new U29(context, input);
		
		if ((flag.get() & 1) == 0)
			return context.getAMF3Object(flag.get() >> 1);
		
		context.addAMF3Object(this);
		
		UTF8 key = new UTF8();
		AMF3_Type value = new Undefined();
		do {
			key = new UTF8(context, input);
			
			if (key.equals(Array.EMPTY_KEY)) {
				break;
			} else {
				value = AMF3_Type.readType(context, input);
				
				this.value1.put(key, value);
			}
		} while (key != null && value != null);
		
		long length = flag.get() >> 1;
		for (long index = 0; index < length; index++) {
			value = AMF3_Type.readType(context, input);
			
			this.value2.add(value);
		}
		
		return this;
	}
	
	public Map<UTF8, AMF3_Type> get() {
		return this.value1;
	}
	
	public void set(UTF8 key, AMF3_Type value) {
		this.value1.put(key, value);
	}
	
	public AMF3_Type get(UTF8 key) {
		return this.value1.get(key);
	}
	
	public void set(int index, AMF3_Type value) {
		this.value2.set(index, value);
	}
	
	public AMF3_Type get(int index) {
		return this.value2.get(index);
	}
	
	@Override
	public java.lang.String toString() {
		java.lang.String str = "Array";
		for (Entry<UTF8, AMF3_Type> entry : this.value1.entrySet()) {
			str += "\n"+entry.getKey().toString()+": "+entry.getValue().toString();
		}
		int index = 0;
		for (AMF3_Type value : this.value2) {
			str += "\n"+(index++)+": "+value.toString();
		}
		return str;
	}
	
	@Override
	public int hashCode() {
		return this.value1.hashCode() ^ this.value2.hashCode();
	}
}
