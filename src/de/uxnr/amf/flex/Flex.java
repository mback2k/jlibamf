package de.uxnr.amf.flex;

import de.uxnr.amf.flex.type.AcknowledgeMessage;
import de.uxnr.amf.flex.type.ArrayCollection;
import de.uxnr.amf.flex.type.AsyncMessage;
import de.uxnr.amf.flex.type.CommandMessage;
import de.uxnr.amf.flex.type.ErrorMessage;
import de.uxnr.amf.flex.type.ObjectProxy;
import de.uxnr.amf.flex.type.RemotingMessage;
import de.uxnr.amf.v3.base.UTF8;
import de.uxnr.amf.v3.type.Object;

public class Flex {
	public static final UTF8 FLEX_ASYNC_SMALL_MESSAGE = new UTF8("DSA");
	public static final UTF8 FLEX_ACKNOWLEDGE_SMALL_MESSAGE = new UTF8("DSK");
	public static final UTF8 FLEX_COMMAND_SMALL_MESSAGE = new UTF8("DSC");

	public static final UTF8 FLEX_REMOTING_MESSAGE = new UTF8("flex.messaging.messages.RemotingMessage");
	public static final UTF8 FLEX_ASYNC_MESSAGE = new UTF8("flex.messaging.messages.AsyncMessage");
	public static final UTF8 FLEX_ACKNOWLEDGE_MESSAGE = new UTF8("flex.messaging.messages.AcknowledgeMessage");
	public static final UTF8 FLEX_COMMAND_MESSAGE = new UTF8("flex.messaging.messages.CommandMessage");
	public static final UTF8 FLEX_ERROR_MESSAGE = new UTF8("flex.messaging.messages.ErrorMessage");

	public static final UTF8 FLEX_ARRAY_COLLECTION = new UTF8("flex.messaging.io.ArrayCollection");
	public static final UTF8 FLEX_OBJECT_PROXY = new UTF8("flex.messaging.io.ObjectProxy");

	public static void register() {
		Object.registerClass(FLEX_ASYNC_SMALL_MESSAGE, AsyncMessage.class);
		Object.registerClass(FLEX_ACKNOWLEDGE_SMALL_MESSAGE, AcknowledgeMessage.class);
		Object.registerClass(FLEX_COMMAND_SMALL_MESSAGE, CommandMessage.class);

		Object.registerClass(FLEX_REMOTING_MESSAGE, RemotingMessage.class);
		Object.registerClass(FLEX_ASYNC_MESSAGE, AsyncMessage.class);
		Object.registerClass(FLEX_ACKNOWLEDGE_MESSAGE, AcknowledgeMessage.class);
		Object.registerClass(FLEX_COMMAND_MESSAGE, CommandMessage.class);
		Object.registerClass(FLEX_ERROR_MESSAGE, ErrorMessage.class);

		Object.registerClass(FLEX_ARRAY_COLLECTION, ArrayCollection.class);
		Object.registerClass(FLEX_OBJECT_PROXY, ObjectProxy.class);
	}
}
