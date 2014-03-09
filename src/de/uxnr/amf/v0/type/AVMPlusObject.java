package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v0.AMF0_Type;
import de.uxnr.amf.v3.AMF3_Type;

public class AVMPlusObject extends AMF0_Type {
  private AMF3_Type value = null;

  @Override
  public void write(AMF_Context context, DataOutputStream output) throws IOException {
    AMF3_Type.writeType(context, output, this.value);
  }

  @Override
  public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
    this.value = AMF3_Type.readType(context, input);

    return this;
  }

  public void set(AMF3_Type value) {
    this.value = value;
  }

  public AMF3_Type get() {
    return this.value;
  }

  @Override
  public java.lang.String toString() {
    return "AVMPlusObject(" + this.value + ")";
  }

  @Override
  public int hashCode() {
    return this.value.hashCode();
  }
}
