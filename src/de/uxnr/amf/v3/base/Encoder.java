package de.uxnr.amf.v3.base;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import de.uxnr.amf.Context;
import de.uxnr.amf.v3.AMF3;

public class Encoder {
	private final Context context;
	private final DataOutput output;

	public Encoder(Context context, DataOutput output) {
		this.context = context;
		this.output = output;
	}

	public Context getContext() {
		return this.context;
	}

	public void writeType(int type) throws IOException {
		this.output.writeU8(type);
	}

	public void writeInteger(Integer value) throws IOException {
		this.output.writeS29(value.intValue());
	}

	public void writeDouble(Double value) throws IOException {
		this.output.writeDOUBLE(value.doubleValue());
	}

	public void writeString(String value) throws IOException {
		this.output.writeUTF8(this.context, value);
	}

	public void writeXMLDocument(Document value) throws IOException {
		int reference = this.context.getAMF3ObjectReference(value);
		if (reference >= 0) {
			this.output.writeU29((reference << 1) & ~1);
			return;
		}

		this.context.addAMF3Object(value);

		String xml = "";

		try {
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			DOMSource source = new DOMSource(value);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer trans = factory.newTransformer();

			trans.transform(source, result);
			xml = writer.toString();
		} catch (TransformerException e) {
			throw new IOException(e);
		}

		this.output.writeUTF8(this.context, xml);
	}

	public void writeDate(Date date) throws IOException {
		int reference = this.context.getAMF3ObjectReference(date);
		if (reference >= 0) {
			this.output.writeU29((reference << 1) & ~1);
			return;
		}

		this.context.addAMF3Object(date);

		this.output.writeU29(1);
		this.output.writeDOUBLE(date.getTime());
	}

	public void writeArray(Map<String, Object> array) throws IOException {
		int reference = this.context.getAMF3ObjectReference(array);
		if (reference >= 0) {
			this.output.writeU29((reference << 1) & ~1);
			return;
		}

		this.context.addAMF3Object(array);

		int index = 0;
		boolean isAssociative = false;
		for (String key : array.keySet()) {
			int keyIndex = -1;
			try {
				keyIndex = Integer.parseInt(key);
			} catch (NumberFormatException e) {
				isAssociative = true;
				break;
			}
			if (keyIndex != index++) {
				isAssociative = true;
				break;
			}
		}

		if (isAssociative) {
			this.output.writeU29(0);

			for (Entry<String, Object> entry : array.entrySet()) {
				this.output.writeUTF8(this.context, entry.getKey());
				AMF3.encode(this, entry.getValue());
			}
		} else {
			this.output.writeU29((array.size() << 1) | 1);
		}

		this.output.writeUTF8(this.context, "");

		if (!isAssociative) {
			for (Object value : array.values()) {
				AMF3.encode(this, value);
			}
		}
	}

	public void writeObject(Object object) throws IOException {
		int reference = this.context.getAMF3ObjectReference(object);
		if (reference >= 0) {
			this.output.writeU29((reference << 1) & ~1);
			return;
		}

		this.context.addAMF3Object(object);

		String className = object.getClassName();
		List<UTF8> names = this.trait.getNames();

		reference = this.context.getAMF3TraitReference(this.trait);
		if (reference >= 0) {
			U29 flag = new U29((reference << 2) & ~2 | 1);
			flag.write(this.context, this.output);

		} else {
			int flags = this.trait.getCount() << 4;
			flags |= 1 | 2 | (this.trait.isExternalizable() ? 4 : 0) | (this.trait.isDynamic() ? 8 : 0);

			U29 flag = new U29(flags);
			flag.write(this.context, this.output);

			className.write(this.context, this.output);

			for (UTF8 name : names) {
				name.write(this.context, this.output);
			}

			this.context.addAMF3Trait(this.trait);
		}

		if (this.trait.isExternalizable()) {
			if (Object.externalClasses.containsKey(className)) {
				this.external.write(this.context, this.output);

			} else if (Object.internalClasses.containsKey(className)) {
				AMF3_Type.writeType(this.context, this.output, this.external);

			} else {
				throw new RuntimeException("Unsupported message/class "+className);
			}
		} else {
			for (UTF8 name : names) {
				AMF3_Type.writeType(this.context, this.output, this.value.get(name));
			}

			if (this.trait.isDynamic()) {
				for (Entry<UTF8, AMF3_Type> entry : this.value.entrySet()) {
					if (!names.contains(entry.getKey())) {
						entry.getKey().write(this.context, this.output);
						AMF3_Type.writeType(this.context, this.output, entry.getValue());
					}
				}

				Object.EMPTY_KEY.write(this.context, this.output);
			}
		}
	}

	public void writeByteArray(int[] value) throws IOException {
		int reference = this.context.getAMF3ObjectReference(value);
		if (reference >= 0) {
			this.output.writeU29((reference << 1) & ~1);
			return;
		}

		this.context.addAMF3Object(this);

		this.output.writeU29((value.length << 1) | 1);

		for (int elem : value) {
			this.output.writeU8(elem);
		}
	}
}
