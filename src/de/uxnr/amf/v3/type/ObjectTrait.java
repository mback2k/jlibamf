package de.uxnr.amf.v3.type;

import java.util.List;
import java.util.Vector;

public class ObjectTrait {
	private final List<String> names = new Vector<String>();

	private String className = new String();

	private boolean dynamic = false;
	private boolean externalizable = false;

	public List<String> getNames() {
		return this.names;
	}

	public void addName(String name) {
		this.names.add(name);
	}

	public boolean hasName(String name) {
		return this.names.contains(name);
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getCount() {
		return this.names.size();
	}

	public boolean isDynamic() {
		return this.dynamic;
	}

	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	public boolean isExternalizable() {
		return this.externalizable;
	}

	public void setExternalizable(boolean externalizable) {
		this.externalizable = externalizable;
	}

	@Override
	public String toString() {
		return "AMF_Trait {className: " + this.className + ", names: " + this.names + ", dynamic: " + this.dynamic + ", externalizable: " + this.externalizable + "}";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof ObjectTrait))
			return false;

		if (this.hashCode() != obj.hashCode())
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return this.className.hashCode() ^ this.names.hashCode() ^ (this.dynamic ? 8 : 0) ^ (this.externalizable ? 4 : 0);
	}
}
