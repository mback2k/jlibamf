package de.uxnr.amf.flex.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v3.AMF3_Type;

public class ErrorMessage extends AcknowledgeMessage {
  private AMF3_Type extendedData;
  private String faultCode;
  private String faultDetail;
  private String faultString;
  private AMF3_Type rootCause;

  public AMF3_Type getExtendedData() {
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

  public AMF3_Type getRootCause() {
    return this.rootCause;
  }

  @Override
  public void write(AMF_Context context, DataOutputStream output) throws IOException {
    // TODO Write object fields

    super.write(context, output);
  }

  @Override
  public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
    super.read(context, input);

    this.readFields(ErrorMessage.class, this.getObjectData());

    return this;
  }
}
