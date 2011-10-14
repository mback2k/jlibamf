package de.uxnr.amf.v3.type;

import java.util.LinkedHashMap;
import java.util.Map;

public class TypedObject extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = -8470834700689158588L;

	private final String className;

	public TypedObject(String className, Map<String, Object> attributes) {
		super(attributes);
		this.className = className;
	}

	public String getClassName() {
		return this.className;
	}
}
