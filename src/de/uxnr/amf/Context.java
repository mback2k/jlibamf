package de.uxnr.amf;

import java.util.List;
import java.util.Vector;

import de.uxnr.amf.v3.type.ObjectTrait;

public class Context {
	private final List<Object> amf0objects = new Vector<Object>();
	private final List<Object> amf3objects = new Vector<Object>();
	private final List<ObjectTrait> amf3trait = new Vector<ObjectTrait>();
	private final List<String> amf3strings = new Vector<String>();

	public void addAMF0Object(Object value) {
		this.amf0objects.add(value);
	}

	public Object getAMF0Object(int index) {
		return this.amf0objects.get(index);
	}

	public int getAMF0ObjectReference(Object value) {
		return this.amf0objects.indexOf(value);
	}

	public void addAMF3Object(Object value) {
		this.amf3objects.add(value);
	}

	public Object getAMF3Object(int index) {
		return this.amf3objects.get(index);
	}

	public int getAMF3ObjectReference(Object value) {
		return this.amf3objects.indexOf(value);
	}

	public void addAMF3Trait(ObjectTrait value) {
		this.amf3trait.add(value);
	}

	public ObjectTrait getAMF3Trait(int index) {
		return this.amf3trait.get(index);
	}

	public int getAMF3TraitReference(ObjectTrait value) {
		return this.amf3trait.indexOf(value);
	}

	public void addAMF3String(String value) {
		this.amf3strings.add(value);
	}

	public String getAMF3String(int index) {
		return this.amf3strings.get(index);
	}

	public int getAMF3StringReference(String value) {
		return this.amf3strings.indexOf(value);
	}
}
