package de.uxnr.amf.v3;

public class AMF3_Reference {
	private AMF3_Type value;

	public AMF3_Reference(AMF3_Type value) {
		this.setValue(value);
	}

	public void setValue(AMF3_Type value) {
		this.value = value;
	}

	public AMF3_Type getValue() {
		return this.value;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof AMF3_Reference))
			return false;

		if (this.hashCode() != obj.hashCode())
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}
}
