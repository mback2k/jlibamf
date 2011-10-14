package de.uxnr.amf.flex;

import de.uxnr.amf.flex.type.AcknowledgeMessage;
import de.uxnr.amf.flex.type.AsyncMessage;
import de.uxnr.amf.flex.type.CommandMessage;
import de.uxnr.amf.v3.base.UTF8;
import de.uxnr.amf.v3.type.Array;
import de.uxnr.amf.v3.type.Object;

public class Flex {
	public static final UTF8 FLEX_ASYNC_MESSAGE 			= new UTF8("DSA"); // flex.messaging.messages.AsyncMessage
	public static final UTF8 FLEX_COMMAND_MESSAGE 			= new UTF8("DSC"); // flex.messaging.messages.CommandMessage
	public static final UTF8 FLEX_ACKNOWLEDGE_MESSAGE 		= new UTF8("DSK"); // flex.messaging.messages.AcknowledgeMessage
	public static final UTF8 FLEX_ARRAY_COLLECTION			= new UTF8("flex.messaging.io.ArrayCollection");
	public static final UTF8 FLEX_OBJECT_PROXY				= new UTF8("flex.messaging.io.ObjectProxy");

	public static void register() {
		Object.registerExternalizableClass(FLEX_ASYNC_MESSAGE,			AsyncMessage.class);
		Object.registerExternalizableClass(FLEX_COMMAND_MESSAGE,			CommandMessage.class);
		Object.registerExternalizableClass(FLEX_ACKNOWLEDGE_MESSAGE,		AcknowledgeMessage.class);

		Object.registerInternalClass(FLEX_ARRAY_COLLECTION,			Array.class);
		Object.registerInternalClass(FLEX_OBJECT_PROXY,				Object.class);
	}
}
