package de.uxnr.amf.v0.base;

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
import de.uxnr.amf.v0.AMF0;
import de.uxnr.amf.v0.type.AVMPlusObject;
import de.uxnr.amf.v0.type.AnonymousObject;
import de.uxnr.amf.v0.type.Movieclip;
import de.uxnr.amf.v0.type.ObjectEnd;
import de.uxnr.amf.v0.type.RecordSet;
import de.uxnr.amf.v0.type.TypedObject;
import de.uxnr.amf.v0.type.Unsupported;
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

	public void writeNumber(Double value) throws IOException {
		this.output.writeDOUBLE(value.doubleValue());
	}

	public void writeBoolean(Boolean value) throws IOException {
		this.output.writeU8(value.booleanValue() ? 1 : 0);
	}

	public void writeString(String value) throws IOException {
		this.output.writeUTF8(value);
	}

	public void writeObject(AnonymousObject object) throws IOException {
		this.writeObjectAttributes(object);

		this.context.addAMF0Object(object);
	}

	public void writeMovieclip(Movieclip value) throws IOException {
		return;
	}

	public void writeReference(Object value) throws IOException {
		int reference = this.context.getAMF0ObjectReference(value);

		this.output.writeU16(reference);
	}

	public void writeECMAArray(Map<String, Object> array) throws IOException {
		this.output.writeU32(array.size());

		for (Entry<String, Object> entry : array.entrySet()) {
			this.output.writeUTF8(entry.getKey());
			AMF0.encode(this, entry.getValue());
		}

		this.context.addAMF0Object(this);
	}

	public void writeObjectEnd(ObjectEnd value) throws IOException {
		return;
	}

	public void writeStrictArray(List<Object> array) throws IOException {
		this.output.writeU32(array.size());

		for (Object value : array) {
			AMF0.encode(this, value);
		}

		this.context.addAMF0Object(array);
	}

	public void writeDate(Date date) throws IOException {
		this.output.writeDOUBLE(date.getTime());
		this.output.writeS16(0);
	}

	public void writeLongString(String value) throws IOException {
		this.output.writeUTF8long(value);
	}

	public void writeUnsupported(Unsupported value) throws IOException {
		return;
	}

	public void writeRecordSet(RecordSet value) throws IOException {
		return;
	}

	public void writeXMLDocument(Document value) throws IOException {
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

		this.output.writeUTF8long(xml);
	}

	public void writeTypedObject(TypedObject object) throws IOException {
		String className = object.getClassName();
		this.output.writeUTF8(className);

		this.writeObjectAttributes(object);

		this.context.addAMF0Object(object);
	}

	public void writeAVMPlusObject(AVMPlusObject object) throws IOException {
		AMF3.encode(this, object.getInnerObject());
	}

	private void writeObjectAttributes(Map<String, Object> attributes) throws IOException {
		for (Entry<String, Object> entry : attributes.entrySet()) {
			this.output.writeUTF8(entry.getKey());
			AMF0.encode(this, entry.getValue());
		}

		this.output.writeUTF8("");
		AMF0.encode(this, new ObjectEnd());
	}
}
