package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;

public class Null extends EmptyType {
  public Null() {}

  public Null(AMF_Context context, DataInputStream input) throws IOException {
    this.read(context, input);
  }

  @Override
  public java.lang.String toString() {
    return "Null";
  }

  @Override
  public int hashCode() {
    return this.getClass().hashCode();
  }
}
