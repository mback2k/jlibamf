package de.uxnr.amf.v3.type;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v3.base.U29;
import de.uxnr.amf.v3.base.UTF8;

public class XML extends UTF8 {
	private Document value;

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		int reference = context.getAMF3ObjectReference(this);
		if (reference >= 0) {
			U29 flag = new U29((reference << 1) & ~1);
			flag.write(context, output);
			return;
		}

		context.addAMF3Object(this);

		try {
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			DOMSource source = new DOMSource(this.value);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer trans = factory.newTransformer();

			trans.transform(source, result);
			this.set(writer.toString());
		} catch (TransformerException e) {
			throw new IOException(e);
		}

		super.write(context, output);
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		U29 flag = new U29(context, input);
		int flags = flag.get();

		if ((flags & 1) == 0)
			return context.getAMF3Object(flags >> 1);

		context.addAMF3Object(this);

		int length = (flags >> 1);
		byte[] buf = new byte[length];

		if (input.read(buf) == length) {
			try {
				super.set(new java.lang.String(buf));

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(true);
				DocumentBuilder builder = factory.newDocumentBuilder();

				ByteArrayInputStream stream = new ByteArrayInputStream(buf);
				this.value = builder.parse(stream);
			} catch (SAXException e) {
				throw new IOException(e);
			} catch (ParserConfigurationException e) {
				throw new IOException(e);
			}
		} else {
			throw new IOException("Not enough data to read XML");
		}

		return this;
	}

	public void set(Document value) {
		this.value = value;
	}

	public Document documentValue() {
		return this.value;
	}

	@Override
	public java.lang.String toString() {
		return "XML";
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}
}
