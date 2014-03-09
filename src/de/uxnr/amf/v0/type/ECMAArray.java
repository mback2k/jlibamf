package de.uxnr.amf.v0.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.v0.AMF0_Type;
import de.uxnr.amf.v0.base.U32;
import de.uxnr.amf.v0.base.UTF8;

public class ECMAArray extends AMF0_Type {
  private final Map<UTF8, AMF0_Type> value = new LinkedHashMap<UTF8, AMF0_Type>();

  private Integer hashCode = null;

  public ECMAArray() {}

  public ECMAArray(AMF_Context context, DataInputStream input) throws IOException {
    this.read(context, input);
  }

  @Override
  public void write(AMF_Context context, DataOutputStream output) throws IOException {
    U32 length = new U32(this.value.size());
    length.write(context, output);

    for (Entry<UTF8, AMF0_Type> entry : this.value.entrySet()) {
      entry.getKey().write(context, output);
      AMF0_Type.writeType(context, output, entry.getValue());
    }

    context.addAMF0Object(this);
  }

  @Override
  public AMF0_Type read(AMF_Context context, DataInputStream input) throws IOException {
    U32 length = new U32(context, input);

    for (long index = 0; index < length.get(); index++) {
      UTF8 key = new UTF8(context, input);
      AMF0_Type value = AMF0_Type.readType(context, input);

      this.value.put(key, value);
    }

    context.addAMF0Object(this);

    return this;
  }

  public Map<UTF8, AMF0_Type> getArrayData() {
    return this.value;
  }

  public Set<UTF8> keySet() {
    return this.value.keySet();
  }

  public Set<Entry<UTF8, AMF0_Type>> entrySet() {
    return this.value.entrySet();
  }

  public Collection<AMF0_Type> values() {
    return this.value.values();
  }

  public void put(UTF8 key, AMF0_Type value) {
    this.hashCode = null;
    this.value.put(key, value);
  }

  public void put(java.lang.String key, AMF0_Type value) {
    this.put(new UTF8(key), value);
  }

  public void set(UTF8 key, AMF0_Type value) {
    this.put(key, value);
  }

  public void set(java.lang.String key, AMF0_Type value) {
    this.put(new UTF8(key), value);
  }

  public AMF0_Type get(UTF8 key) {
    return this.value.get(key);
  }

  public AMF0_Type get(java.lang.String key) {
    return this.get(new UTF8(key));
  }

  @Override
  public java.lang.String toString() {
    return "ECMA Array " + this.value;
  }

  @Override
  public int hashCode() {
    if (this.hashCode != null)
      return this.hashCode;
    return this.hashCode = this.value.hashCode();
  }
}
