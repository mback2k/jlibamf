package de.uxnr.amf.flex.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;

public class RemotingMessage extends AbstractMessage {
  private String operation;
  private String status;

  public String getOperation() {
    return this.operation;
  }

  public String getStatus() {
    return this.status;
  }

  @Override
  public void write(AMF_Context context, DataOutputStream output) throws IOException {
    // TODO Write object fields

    super.write(context, output);
  }

  @Override
  public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
    super.read(context, input);

    this.readFields(RemotingMessage.class, this.getObjectData());

    return this;
  }
}
