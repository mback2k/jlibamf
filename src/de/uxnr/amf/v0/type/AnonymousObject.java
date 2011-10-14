package de.uxnr.amf.v0.type;

import java.util.LinkedHashMap;
import java.util.Map;

public class AnonymousObject extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 4921978958031646547L;

	public AnonymousObject(Map<String, Object> attributes) {
		super(attributes);
	}
}
