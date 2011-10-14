package de.uxnr.amf.flex.type;

public class RemotingMessage extends AbstractMessage {
	private String operation;
	private String status;

	public String getOperation() {
		return this.operation;
	}

	public String getStatus() {
		return this.status;
	}
}
