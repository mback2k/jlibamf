package de.uxnr.amf.v0.type;

public class AVMPlusObject {
	private final Object innerObject;

	public AVMPlusObject(Object innerObject) {
		this.innerObject = innerObject;
	}

	public Object getInnerObject() {
		return this.innerObject;
	}
}
