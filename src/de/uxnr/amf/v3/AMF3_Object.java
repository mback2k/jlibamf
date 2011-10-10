package de.uxnr.amf.v3;

import java.beans.PropertyChangeSupport;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.v3.type.Array;
import de.uxnr.amf.v3.type.Double;
import de.uxnr.amf.v3.type.Integer;
import de.uxnr.amf.v3.type.Object;
import de.uxnr.amf.v3.type.String;

public abstract class AMF3_Object extends AMF3_Type {
	private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private transient Object innerObject = null;

	public void setInnerObject(Object innerObject) {
		this.innerObject = innerObject;
	}

	@Override
	public final void write(AMF_Context context, DataOutputStream output) throws IOException {
		this.innerObject.write(context, output);
	}

	@Override
	public final AMF3_Object read(AMF_Context context, DataInputStream input) throws IOException {
		return this;
	}

	public final void readFields(Object object) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		for (Field field : this.getClass().getDeclaredFields()) {
			int modifiers = field.getModifiers();
			if ((modifiers & (Modifier.STATIC | Modifier.TRANSIENT)) != 0)
				continue;

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, (modifiers & ~Modifier.PRIVATE & ~Modifier.PROTECTED) | Modifier.PUBLIC);

			java.lang.String fieldName = field.getName();
			java.lang.Object fieldValue = field.get(this);

			try {
				if (field.getType() == Object.class) {
					Object value = (Object) object.get(fieldName);
					this.propertyChangeSupport.firePropertyChange(fieldName, fieldValue, value);
					field.set(this, value);
				} else if (field.getType() == Array.class) {
					Array value = (Array) ((Object) object.get(fieldName)).getExternal();
					this.propertyChangeSupport.firePropertyChange(fieldName, fieldValue, value);
					field.set(this, value);
				} else if (field.getType() == java.lang.String.class) {
					java.lang.String value = ((String) object.get(fieldName)).get();
					this.propertyChangeSupport.firePropertyChange(fieldName, fieldValue, value);
					field.set(this, value);
				} else if (field.getType() == java.lang.Integer.class) {
					java.lang.Integer value = ((Integer) object.get(fieldName)).get();
					this.propertyChangeSupport.firePropertyChange(fieldName, fieldValue, value);
					field.set(this, value);
				} else if (field.getType() == java.lang.Double.class) {
					java.lang.Double value = ((Double) object.get(fieldName)).get();
					this.propertyChangeSupport.firePropertyChange(fieldName, fieldValue, value);
					field.set(this, value);
				}

			} catch (ClassCastException e) {
				continue;

			} finally {
				modifiersField.setInt(field, modifiers);
				modifiersField.setAccessible(false);
			}
		}
	}
}
