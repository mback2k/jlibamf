package de.uxnr.amf.v3;

import java.util.List;
import java.util.Vector;

import de.uxnr.amf.v3.base.UTF8;

public class AMF3_Trait {
	private List<UTF8> names = new Vector<UTF8>();
	
	private UTF8 className = new UTF8();
	
	private long count = 0;
	
	private boolean dynamic = false;
	private boolean externalizable = false;
	
	public List<UTF8> getNames() {
		return names;
	}
	
	public void addName(UTF8 name) {
		this.names.add(name);
	}
	
	public boolean hasName(UTF8 name) {
		return this.names.contains(name);
	}
	
	public UTF8 getClassName() {
		return className;
	}
	
	public void setClassName(UTF8 className) {
		this.className = className;
	}
	
	public long getCount() {
		return count;
	}
	
	public void setCount(long count) {
		this.count = count;
	}
	
	public boolean isDynamic() {
		return dynamic;
	}
	
	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}
	
	public boolean isExternalizable() {
		return externalizable;
	}
	
	public void setExternalizable(boolean externalizable) {
		this.externalizable = externalizable;
	}
}
