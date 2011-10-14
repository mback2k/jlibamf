package de.uxnr.amf.flex.type;

public class ErrorMessage extends AcknowledgeMessage {
	private Object extendedData;
	private String faultCode;
	private String faultDetail;
	private String faultString;
	private Object rootCause;

	public Object getExtendedData() {
		return this.extendedData;
	}

	public String getFaultCode() {
		return this.faultCode;
	}

	public String getFaultDetail() {
		return this.faultDetail;
	}

	public String getFaultString() {
		return this.faultString;
	}

	public Object getRootCause() {
		return this.rootCause;
	}
}
