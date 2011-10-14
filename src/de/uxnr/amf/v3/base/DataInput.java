package de.uxnr.amf.v3.base;

import java.io.IOException;
import java.io.InputStream;

import de.uxnr.amf.Context;

public class DataInput extends de.uxnr.amf.v0.base.DataInput {
	public DataInput(InputStream stream) {
		super(stream);
	}

	public int readU29() throws IOException {
		int value = 0;
		int more = 0;
		int read = this.readU8();

		while ((read & 0x80) != 0 && more < 3) {
			value <<= 7;
			value |= read & 0x7F;

			read = this.readU8();
			more++;
		}

		if (more < 3) {
			value <<= 7;
			value |= read;
		} else {
			value <<= 8;
			value |= read;
		}

		return value;
	}

	public int readS29() throws IOException {
		int value = this.readU29();

		if ((value & 0x10000000) != 0) {
			value -= 0x20000000;
		}

		return value;
	}

	public String readUTF8(Context context) throws IOException {
		int flags = this.readU29();
		if (flags == 1)
			return "";

		if ((flags & 1) == 0)
			return context.getAMF3String(flags >> 1);

		int length = (flags >> 1);
		byte[] buf = new byte[length];

		if (this.read(buf) != length)
			throw new IOException("Not enough data to read UTF8");

		String str = new String(buf);
		context.addAMF3String(str);

		return str;
	}
}
