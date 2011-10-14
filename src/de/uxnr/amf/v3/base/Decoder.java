package de.uxnr.amf.v3.base;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import de.uxnr.amf.Context;
import de.uxnr.amf.Externalizable;
import de.uxnr.amf.flex.IExternalizable;
import de.uxnr.amf.v3.AMF3;
import de.uxnr.amf.v3.type.ObjectTrait;

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

	public Integer readInteger() throws IOException {
		return this.input.readS29();
	}

	public Double readDouble() throws IOException {
		return this.input.readDOUBLE();
	}

	public String readString() throws IOException {
		return this.input.readUTF8(this.context);
	}

	public Document readXMLDocument() throws IOException {
		int flags = this.input.readU29();
		if ((flags & 1) == 0)
			return (Document) this.context.getAMF3Object(flags >> 1);

		int length = (flags >> 1);
		byte[] buf = new byte[length];

		if (this.input.read(buf) != length)
			throw new IOException("Not enough data to read XMLDocument");

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();

			ByteArrayInputStream stream = new ByteArrayInputStream(buf);
			Document doc = builder.parse(stream);

			this.context.addAMF3Object(doc);

			return doc;

		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	public Date readDate() throws IOException {
		int flags = this.input.readU29();
		if ((flags & 1) == 0)
			return (Date) this.context.getAMF3Object(flags >> 1);

		Date date = new Date((long) this.input.readDOUBLE());

		this.context.addAMF3Object(date);

		return date;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> readArray() throws IOException {
		int flags = this.input.readU29();
		if ((flags & 1) == 0)
			return (Map<String, Object>) this.context.getAMF3Object(flags >> 1);

		Map<String, Object> array = new LinkedHashMap<String, Object>();

		this.context.addAMF3Object(array);

		String key = null;
		Object value = null;
		do {
			key = this.input.readUTF8(this.context);

			if (key.isEmpty()) {
				break;
			} else {
				value = AMF3.decode(this);

				array.put(key, value);
			}
		} while (key != null && value != null);

		int length = (flags >> 1);
		for (int index = 0; index < length; index++) {
			value = AMF3.decode(this);

			array.put(String.valueOf(index), value);
		}

		return array;
	}

	@SuppressWarnings("unchecked")
	public Object readObject() throws IOException {
		int flags = this.input.readU29();
		if ((flags & 1) == 0)
			return this.context.getAMF3Object(flags >> 1);

		ObjectTrait trait = null;
		if ((flags & 2) == 0) {
			trait = this.context.getAMF3Trait(flags >> 2);

		} else {
			trait = new ObjectTrait();
			trait.setExternalizable((flags & 4) == 4);
			trait.setDynamic((flags & 8) == 8);
			trait.setClassName(this.input.readUTF8(this.context));

			int count = flags >> 4;
			for (int index = 0; index < count; index++) {
				trait.addName(this.input.readUTF8(this.context));
			}

			this.context.addAMF3Trait(trait);
		}

		String className = trait.getClassName();
		Object object = null;

		if (trait.isExternalizable()) {
			if (AMF3.hasExternalizableClass(className)) {
				try {
					object = AMF3.getExternalizableClass(className).newInstance();
				} catch (Exception e) {
					throw new IOException(e);
				}

			} else {
				throw new RuntimeException("Unsupported message/class "+className);
			}

		} else {
			if (AMF3.hasObjectClass(className)) {
				try {
					object = AMF3.getObjectClass(className).newInstance();
				} catch (Exception e) {
					throw new IOException(e);
				}

			} else {
				object = new LinkedHashMap<String, Object>();
			}
		}

		this.context.addAMF3Object(object);

		if (trait.isExternalizable()) {
			IExternalizable externalized = (IExternalizable) object;
			externalized.readExternal(this.input);

		} else if (object instanceof Map || object instanceof Externalizable) {
			Map<String, Object> map;
			if (object instanceof Map) {
				map = (Map<String, Object>) object;
			} else {
				map = new LinkedHashMap<String, Object>();
			}

			for (String name : trait.getNames()) {
				Object value = AMF3.decode(this);

				map.put(name, value);
			}

			if (trait.isDynamic()) {
				String key = null;
				Object value = null;
				do {
					key = this.input.readUTF8(this.context);

					if (key.isEmpty()) {
						break;
					} else {
						value = AMF3.decode(this);

						map.put(key, value);
					}
				} while (key != null && value != null);
			}

			if (object instanceof Externalizable) {
				Externalizable externalized = (Externalizable) object;
				externalized.unpackFields(map);
			}
		}

		return object;
	}

	public int[] readByteArray() throws IOException {
		int flags = this.input.readU29();
		if ((flags & 1) == 0)
			return (int[]) this.context.getAMF3Object(flags >> 1);

		int length = (flags >> 1);
		int[] value = new int[length];

		for (int index = 0; index < length; index++) {
			value[index] = this.input.readU8();
		}

		this.context.addAMF3Object(value);

		return value;
	}
}
