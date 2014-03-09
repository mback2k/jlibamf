package de.uxnr.amf.v0.base;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v0.AMF0_Type;

public class U8 extends AMF0_Type {
  private int value = 0;

  public U8() {}

  public U8(int value) {
    this.set(value);
  }

  public U8(AMF_Context context, DataInputStream input) throws IOException {
    this.read(context, input);
  }

  @Override
  public void write(AMF_Context context, DataOutputStream output) throws IOException {
    output.writeByte(this.value);
  }

  @Override
  public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
    this.value = input.readUnsignedByte();

    return this;
  }

  public void set(int value) {
    this.value = value;
  }

  public int get() {
    return this.value;
  }

  @Override
  public String toString() {
    return String.valueOf(this.value);
  }

  @Override
  public int hashCode() {
    return this.value * 15;
  }
}
