package de.uxnr.amf.v0.type;

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
import de.uxnr.amf.v0.base.UTF8long;

public class XMLDocument extends UTF8long {
	private Document value;

	public XMLDocument() {
	}

	public XMLDocument(Document value) throws TransformerException {
		this.set(value);
	}

	public XMLDocument(AMF_Context context, DataInputStream input) throws IOException {
		this.read(context, input);
	}

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
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
		super.read(context, input);

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();

			ByteArrayInputStream stream = new ByteArrayInputStream(this.get().getBytes());
			this.value = builder.parse(stream);

			return this;
		} catch (SAXException e) {
			throw new IOException(e);
		} catch (ParserConfigurationException e) {
			throw new IOException(e);
		}
	}

	public void set(Document value) {
		this.value = value;
	}

	public Document getDocument() {
		return this.value;
	}

	@Override
	public java.lang.String toString() {
		return "XML Document";
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}
}
