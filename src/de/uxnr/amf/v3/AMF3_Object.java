package de.uxnr.amf.v3;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.v3.type.Array;
import de.uxnr.amf.v3.type.Double;
import de.uxnr.amf.v3.type.False;
import de.uxnr.amf.v3.type.Integer;
import de.uxnr.amf.v3.type.Null;
import de.uxnr.amf.v3.type.Object;
import de.uxnr.amf.v3.type.String;
import de.uxnr.amf.v3.type.True;
import de.uxnr.amf.v3.type.Undefined;

public abstract class AMF3_Object extends AMF3_Type {
	private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private transient Object innerObject = null;

	public final void setInnerObject(Object innerObject) {
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
				AMF3_Type data = object.get(fieldName);

				if (data instanceof Null || data instanceof Undefined) {
					if (java.lang.Object.class.isAssignableFrom(field.getType())) {
						field.set(this, null);
					}
					continue;
				}

				if (field.getType() == Object.class) {
					Object value = null;

					if (data instanceof Object) {
						value = (Object) data;
					}

					this.propertyChangeSupport.firePropertyChange(fieldName, fieldValue, value);
					field.set(this, value);

				} else if (field.getType() == Array.class) {
					Array value = null;

					if (data instanceof Array) {
						value = (Array) data;

					} else if (data instanceof Object) {
						AMF3_Type external = ((Object) data).getExternal();

						if (external instanceof Array) {
							value = (Array) external;
						}
					}

					this.propertyChangeSupport.firePropertyChange(fieldName, fieldValue, value);
					field.set(this, value);

				} else if (field.getType() == java.lang.String.class) {
					java.lang.String value = null;

					if (data instanceof String) {
						value = ((String) data).get();
					}

					this.propertyChangeSupport.firePropertyChange(fieldName, fieldValue, value);
					field.set(this, value);

				} else if (field.getType() == java.lang.Integer.class) {
					java.lang.Integer value = null;

					if (data instanceof Integer) {
						value = ((Integer) data).get();
					}

					this.propertyChangeSupport.firePropertyChange(fieldName, fieldValue, value);
					field.set(this, value);

				} else if (field.getType() == java.lang.Double.class) {
					java.lang.Double value = null;

					if (data instanceof Double) {
						value = ((Double) data).get();
					}

					this.propertyChangeSupport.firePropertyChange(fieldName, fieldValue, value);
					field.set(this, value);

				} else if (field.getType() == java.lang.Boolean.class) {
					java.lang.Boolean value = null;

					if (data instanceof True) {
						value = true;

					} else if (data instanceof False) {
						value = false;
					}

					this.propertyChangeSupport.firePropertyChange(fieldName, fieldValue, value);
					field.set(this, value);

				} else if (AMF3_Object.class.isAssignableFrom(field.getType())) {
					AMF3_Object value = null;

					if (data instanceof AMF3_Object) {
						value = (AMF3_Object) data;
					}

					this.propertyChangeSupport.firePropertyChange(fieldName, fieldValue, value);
					field.set(this, value);

				} else if (Collection.class.isAssignableFrom(field.getType())) {
					Collection<AMF3_Type> value = null;

					if (data instanceof Array) {
						value = ((Array) data).values();

					} else if (data instanceof Object) {
						AMF3_Type external = ((Object) data).getExternal();

						if (external instanceof Array) {
							value = ((Array) external).values();
						}
					}

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

	public void addPropertyChangeListener(java.lang.String propertyName, PropertyChangeListener listener) {
		this.propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.removePropertyChangeListener(listener);
	}
}
