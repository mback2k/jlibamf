package de.uxnr.amf.flex;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.flex.msg.AbstractMessage;
import de.uxnr.amf.flex.msg.AcknowledgeMessage;
import de.uxnr.amf.flex.msg.AsyncMessage;
import de.uxnr.amf.flex.msg.CommandMessage;
import de.uxnr.amf.v3.AMF3_Type;
import de.uxnr.amf.v3.base.UTF8;

@SuppressWarnings("rawtypes")
public class Flex {
	public static final UTF8 FLEX_ASYNC_MESSAGE 			= new UTF8("DSA"); // flex.messaging.messages.AsyncMessage
	public static final UTF8 FLEX_COMMAND_MESSAGE 			= new UTF8("DSC"); // flex.messaging.messages.CommandMessage
	public static final UTF8 FLEX_ACKNOWLEDGE_MESSAGE 		= new UTF8("DSK"); // flex.messaging.messages.AcknowledgeMessage
	public static final UTF8 FLEX_ARRAY_COLLECTION			= new UTF8("flex.messaging.io.ArrayCollection");
	public static final UTF8 FLEX_OBJECT_PROXY				= new UTF8("flex.messaging.io.ObjectProxy");

	public static void register() {
		Flex.registerMessage(FLEX_ASYNC_MESSAGE,			AsyncMessage.class);
		Flex.registerMessage(FLEX_COMMAND_MESSAGE,			CommandMessage.class);
		Flex.registerMessage(FLEX_ACKNOWLEDGE_MESSAGE,		AcknowledgeMessage.class);
		Flex.registerMessage(FLEX_ARRAY_COLLECTION,			AMF3_Type.class);
		Flex.registerMessage(FLEX_OBJECT_PROXY,				AMF3_Type.class);
	}

	private static final Map<UTF8, Class> messages = new HashMap<UTF8, Class>();

	public static void registerMessage(UTF8 className, Class typeClass) {
		Flex.messages.put(className, typeClass);
	}

	public static void writeMessage(AMF_Context context, DataOutputStream output, UTF8 className, AMF3_Type value) throws IOException {
		if (!Flex.messages.containsKey(className)) {
			throw new RuntimeException("Unsupported message/class "+className);
		}

		if (value instanceof AbstractMessage) {
			value.write(context, output);
		} else {
			AMF3_Type.writeType(context, output, value);
		}
	}

	public static AMF3_Type readMessage(AMF_Context context, DataInputStream input, UTF8 className) throws IOException {
		if (!Flex.messages.containsKey(className)) {
			throw new RuntimeException("Unsupported message/class "+className);
		}

		AMF3_Type value;
		try {
			value = (AMF3_Type) Flex.messages.get(className).newInstance();
		} catch (InstantiationException e) {
			return AMF3_Type.readType(context, input);
		} catch (IllegalAccessException e) {
			throw new IOException(e);
		}

		value = (AMF3_Type) value.read(context, input);

		return value;
	}
}
