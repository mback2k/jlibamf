package de.uxnr.amf;

import java.util.List;
import java.util.Vector;

import de.uxnr.amf.v0.AMF0_Type;
import de.uxnr.amf.v3.AMF3_Trait;
import de.uxnr.amf.v3.AMF3_Type;
import de.uxnr.amf.v3.base.UTF8;

public class AMF_Context {
	private final List<AMF0_Type> amf0objects = new Vector<AMF0_Type>();
	private final List<AMF3_Type> amf3objects = new Vector<AMF3_Type>();
	private final List<AMF3_Trait> amf3trait = new Vector<AMF3_Trait>();
	private final List<UTF8> amf3strings = new Vector<UTF8>();

	public int addAMF0Object(AMF0_Type value) {
		this.amf0objects.add(value);
		return this.amf0objects.indexOf(value);
	}

	public AMF0_Type getAMF0Object(int index) {
		return this.amf0objects.get(index);
	}

	public int getAMF0ObjectReference(AMF0_Type value) {
		return this.amf0objects.indexOf(value);
	}

	public int addAMF3Object(AMF3_Type value) {
		this.amf3objects.add(value);
		return this.amf3objects.indexOf(value);
	}

	public AMF3_Type getAMF3Object(int index) {
		return this.amf3objects.get(index);
	}

	public int getAMF3ObjectReference(AMF3_Type value) {
		return this.amf3objects.indexOf(value);
	}

	public int addAMF3Trait(AMF3_Trait value) {
		this.amf3trait.add(value);
		return this.amf3trait.indexOf(value);
	}

	public AMF3_Trait getAMF3Trait(int index) {
		return this.amf3trait.get(index);
	}

	public int getAMF3TraitReference(AMF3_Trait value) {
		return this.amf3trait.indexOf(value);
	}

	public int addAMF3String(UTF8 value) {
		this.amf3strings.add(value);
		return this.amf3strings.indexOf(value);
	}

	public UTF8 getAMF3String(long index) {
		return this.amf3strings.get((int) index);
	}

	public long getAMF3StringReference(UTF8 value) {
		return this.amf3strings.indexOf(value);
	}
}
