package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v0.AMF0_Type;
import de.uxnr.amf.v0.base.UTF8;

public class Object extends AMF0_Type {
	private static final UTF8 EMPTY_KEY = new UTF8();
	
	private Map<UTF8, AMF0_Type> value = new HashMap<UTF8, AMF0_Type>();
	
	public Object() { }
	
	public Object(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		for (Entry<UTF8, AMF0_Type> entry : this.value.entrySet()) {
			entry.getKey().write(context, output);
			AMF0_Type.writeType(context, output, entry.getValue());
		}
		
		Object.EMPTY_KEY.write(context, output);
		AMF0_Type.writeType(context, output, new ObjectEnd());
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		UTF8 key = new UTF8();
		AMF0_Type value = new ObjectEnd();
		
		do {
			key = new UTF8(context, input);
			value = AMF0_Type.readType(context, input);
			
			if (key.get().isEmpty() || value instanceof ObjectEnd) {
				break;
			} else {
				this.value.put(key, value);
			}
		} while (key != null && value != null);
		
		context.addAMF0Object(this);
		
		return this;
	}

	public Map<UTF8, AMF0_Type> get() {
		return this.value;
	}
	
	public void set(UTF8 key, AMF0_Type value) {
		this.value.put(key, value);
	}
	
	public AMF0_Type get(UTF8 key) {
		return this.value.get(key);
	}
	
	@Override
	public java.lang.String toString() {
		java.lang.String str = "Object";
		for (Entry<UTF8, AMF0_Type> entry : this.value.entrySet()) {
			str += "\n"+entry.getKey().toString()+": "+entry.getValue().toString();
		}
		return str;
	}
	
	@Override
	public int hashCode() {
		return this.value.hashCode();
	}
	
	public static class ObjectEnd extends EmptyType {}
}
