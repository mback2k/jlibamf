package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v0.base.DOUBLE;
import de.uxnr.amf.v0.base.S16;

public class Date extends DOUBLE {
  public Date() {}

  public Date(java.util.Date value) {
    this.set(value);
  }

  public Date(AMF_Context context, DataInputStream input) throws IOException {
    this.read(context, input);
  }

  @Override
  public void write(AMF_Context context, DataOutputStream output) throws IOException {
    super.write(context, output);

    S16 timezone = new S16(0);
    timezone.write(context, output);
  }

  @Override
  public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
    super.read(context, input);

    new S16(context, input); // Ignoring not supported time zone information

    return this;
  }

  public void set(java.util.Date value) {
    this.set(java.lang.Double.valueOf(value.getTime()));
  }

  public java.util.Date dateValue() {
    return new java.util.Date((long) super.get());
  }

  @Override
  public java.lang.String toString() {
    return this.dateValue().toString();
  }

  @Override
  public int hashCode() {
    return this.dateValue().hashCode();
  }
}
