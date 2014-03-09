package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;

public class RecordSet extends EmptyType {
  public RecordSet() {}

  public RecordSet(AMF_Context context, DataInputStream input) throws IOException {
    this.read(context, input);
  }

  @Override
  public java.lang.String toString() {
    return "RecordSet";
  }

  @Override
  public int hashCode() {
    return this.getClass().hashCode();
  }
}
