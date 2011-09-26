package de.uxnr.amf;

import java.util.List;
import java.util.Vector;

import de.uxnr.amf.v0.AMF0_Type;
import de.uxnr.amf.v3.AMF3_Trait;
import de.uxnr.amf.v3.AMF3_Type;
import de.uxnr.amf.v3.base.UTF8;

public class AMF_Context {
	private List<AMF0_Type> amf0objects = new Vector<AMF0_Type>();
	private List<AMF3_Type> amf3objects = new Vector<AMF3_Type>();
	private List<AMF3_Trait> amf3trait = new Vector<AMF3_Trait>();
	private List<UTF8> amf3strings = new Vector<UTF8>();
	
	public void addAMF0Object(AMF0_Type value) {
		this.amf0objects.add(value);
	}
	
	public AMF0_Type getAMF0Object(long index) {
		return this.amf0objects.get((int) index);
	}
	
	public void addAMF3Object(AMF3_Type value) {
		this.amf3objects.add(value);
	}
	
	public AMF3_Type getAMF3Object(long index) {
		return this.amf3objects.get((int) index);
	}
	
	public void addAMF3Trait(AMF3_Trait value) {
		this.amf3trait.add(value);
	}
	
	public AMF3_Trait getAMF3Trait(long index) {
		return this.amf3trait.get((int) index);
	}
	
	public void addAMF3String(UTF8 value) {
		this.amf3strings.add(value);
	}
	
	public UTF8 getAMF3String(long index) {
		return this.amf3strings.get((int) index);
	}
}
