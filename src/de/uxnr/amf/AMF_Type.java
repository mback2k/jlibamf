package de.uxnr.amf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface AMF_Type {
	public void write(AMF_Context context, DataOutputStream output) throws IOException;

	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException;

	@Override
	public String toString();

	@Override
	public int hashCode();

	@Override
	public boolean equals(Object obj);
}
