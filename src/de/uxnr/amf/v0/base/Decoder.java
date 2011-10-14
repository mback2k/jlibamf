package de.uxnr.amf.v0.base;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import de.uxnr.amf.Context;
import de.uxnr.amf.v0.AMF0;
import de.uxnr.amf.v0.type.AVMPlusObject;
import de.uxnr.amf.v0.type.AnonymousObject;
import de.uxnr.amf.v0.type.Movieclip;
import de.uxnr.amf.v0.type.ObjectEnd;
import de.uxnr.amf.v0.type.RecordSet;
import de.uxnr.amf.v0.type.TypedObject;
import de.uxnr.amf.v0.type.Unsupported;
import de.uxnr.amf.v3.AMF3;

public class Decoder {
	private final Context context;
	private final DataInput input;

	public Decoder(Context context, DataInput input) {
		this.context = context;
		this.input = input;
	}

	public Context getContext() {
		return this.context;
	}

	public int readType() throws IOException {
		return this.input.readU8();
	}

	public Double readNumber() throws IOException {
		return this.input.readDOUBLE();
	}

	public Boolean readBoolean() throws IOException {
		return this.input.readU8() != 0;
	}

	public String readString() throws IOException {
		return this.input.readUTF8();
	}

	public AnonymousObject readObject() throws IOException {
		Map<String, Object> attributes = this.readObjectAttributes();

		AnonymousObject object = new AnonymousObject(attributes);

		this.context.addAMF0Object(object);

		return object;
	}

	public Movieclip readMovieclip() throws IOException {
		return new Movieclip();
	}

	public Object readReference() throws IOException {
		int reference = this.input.readU16();

		return this.context.getAMF0Object(reference);
	}

	public Map<String, Object> readECMAArray() throws IOException {
		Map<String, Object> array = new LinkedHashMap<String, Object>();
		long length = this.input.readU32();

		for (long index = 0; index < length; index++) {
			String key = this.input.readUTF8();
			Object value = AMF0.decode(this);

			array.put(key, value);
		}

		this.context.addAMF0Object(array);

		return array;
	}

	public ObjectEnd readObjectEnd() throws IOException {
		return new ObjectEnd();
	}

	public Collection<Object> readStrictArray() throws IOException {
		Collection<Object> array = new Vector<Object>();
		long length = this.input.readU32();

		for (long index = 0; index < length; index++) {
			Object value = AMF0.decode(this);

			array.add(value);
		}

		this.context.addAMF0Object(array);

		return array;
	}

	public Date readDate() throws IOException {
		Date date = new Date((long) this.input.readDOUBLE());
		this.input.readS16();

		return date;
	}

	public String readLongString() throws IOException {
		return this.input.readUTF8long();
	}

	public Unsupported readUnsupported() throws IOException {
		return new Unsupported();
	}

	public RecordSet readRecordSet() throws IOException {
		return new RecordSet();
	}

	public Document readXMLDocument() throws IOException {
		String xml = this.input.readUTF8long();

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();

			ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
			return builder.parse(stream);

		} catch (Exception e) {
			throw new IOException();
		}
	}

	public TypedObject readTypedObject() throws IOException {
		String className = this.input.readUTF8();
		Map<String, Object> attributes = this.readObjectAttributes();

		TypedObject object = new TypedObject(className, attributes);

		this.context.addAMF0Object(object);

		return object;
	}

	public AVMPlusObject readAVMPlusObject() throws IOException {
		return new AVMPlusObject(AMF3.decode(this));
	}

	private Map<String, Object> readObjectAttributes() throws IOException {
		Map<String, Object> attributes = new LinkedHashMap<String, Object>();
		String key;
		Object value;

		do {
			key = this.input.readUTF8();
			value = AMF0.decode(this);

			if (key.isEmpty() || value instanceof ObjectEnd) {
				break;
			} else {
				attributes.put(key, value);
			}
		} while (key != null && value != null);

		return attributes;
	}
}
