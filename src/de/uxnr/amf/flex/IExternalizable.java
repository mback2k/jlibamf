package de.uxnr.amf.flex;

import de.uxnr.amf.v3.base.DataInput;
import de.uxnr.amf.v3.base.DataOutput;

public interface IExternalizable {
	public void writeExternal(DataOutput output);
	public void readExternal(DataInput input);
}
