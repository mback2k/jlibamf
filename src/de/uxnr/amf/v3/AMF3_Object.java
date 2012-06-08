package de.uxnr.amf.v3;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.flex.type.ArrayCollection;
import de.uxnr.amf.flex.type.ObjectProxy;
import de.uxnr.amf.v3.base.UTF8;
import de.uxnr.amf.v3.type.Array;
import de.uxnr.amf.v3.type.ByteArray;
import de.uxnr.amf.v3.type.Double;
import de.uxnr.amf.v3.type.False;
import de.uxnr.amf.v3.type.Integer;
import de.uxnr.amf.v3.type.Null;
import de.uxnr.amf.v3.type.Object;
import de.uxnr.amf.v3.type.String;
import de.uxnr.amf.v3.type.True;
import de.uxnr.amf.v3.type.Undefined;

@SuppressWarnings("rawtypes")
public abstract class AMF3_Object extends Object {
	private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		this.writeFields(this.getClass(), this.getObjectData());
		this.writeAttributes(context, output);
	}

	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		this.readAttributes(context, input);
		this.readFields(this.getClass(), this.getObjectData());

		return this;
	}

	@SuppressWarnings("unused")
	protected final void writeFields(Class type, Map<UTF8, AMF3_Type> fields) throws IOException {
		try {
			for (Field field : type.getDeclaredFields()) {
				int modifiers = field.getModifiers();
				if ((modifiers & (Modifier.STATIC | Modifier.TRANSIENT)) != 0)
					continue;

				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, (modifiers & ~Modifier.PRIVATE & ~Modifier.PROTECTED) | Modifier.PUBLIC);

				java.lang.String fieldName = field.getName();
				java.lang.Object fieldValue = field.get(this);

				try {
					// TODO Implement writeFields

				} catch (ClassCastException e) {
					continue;

				} finally {
					modifiersField.setInt(field, modifiers);
					modifiersField.setAccessible(false);
				}
			}
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	protected final void readFields(Class type, Map<UTF8, AMF3_Type> fields) throws IOException {
		try {
			for (Field field : type.getDeclaredFields()) {
				int modifiers = field.getModifiers();
				if ((modifiers & (Modifier.STATIC | Modifier.TRANSIENT)) != 0)
					continue;

				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, (modifiers & ~Modifier.PRIVATE & ~Modifier.PROTECTED) | Modifier.PUBLIC);

				java.lang.String fieldName = field.getName();
				java.lang.Object fieldValue = field.get(this);

				try {
					AMF3_Type data = fields.get(new UTF8(fieldName));

					if (data instanceof Null || data instanceof Undefined) {
						if (java.lang.Object.class.isAssignableFrom(field.getType())) {
							field.set(this, null);
						}
						continue;
					}

					if (field.getType() == java.lang.String.class) {
						java.lang.String value = null;

						if (data instanceof String) {
							value = ((String) data).get();
						}

						this.firePropertyChange(fieldName, fieldValue, value);
						field.set(this, value);

					} else if (field.getType() == int.class) {
						int value = 0;

						if (data instanceof Integer) {
							value = ((Integer) data).get();
						}

						this.firePropertyChange(fieldName, fieldValue, value);
						field.set(this, value);

					} else if (field.getType() == java.lang.Integer.class) {
						java.lang.Integer value = null;

						if (data instanceof Integer) {
							value = ((Integer) data).get();
						}

						this.firePropertyChange(fieldName, fieldValue, value);
						field.set(this, value);

					} else if (field.getType() == double.class) {
						double value = 0;

						if (data instanceof Double) {
							value = ((Double) data).get();
						}

						this.firePropertyChange(fieldName, fieldValue, value);
						field.set(this, value);

					} else if (field.getType() == java.lang.Double.class) {
						java.lang.Double value = null;

						if (data instanceof Double) {
							value = ((Double) data).get();
						}

						this.firePropertyChange(fieldName, fieldValue, value);
						field.set(this, value);

					} else if (field.getType() == boolean.class) {
						boolean value = false;

						if (data instanceof True) {
							value = true;

						} else if (data instanceof False) {
							value = false;
						}

						this.firePropertyChange(fieldName, fieldValue, value);
						field.set(this, value);

					} else if (field.getType() == java.lang.Boolean.class) {
						java.lang.Boolean value = null;

						if (data instanceof True) {
							value = true;

						} else if (data instanceof False) {
							value = false;
						}

						this.firePropertyChange(fieldName, fieldValue, value);
						field.set(this, value);

					} else if (field.getType() == int[].class) {
						int[] value = null;

						if (data instanceof ByteArray) {
							value = ((ByteArray) data).get();
						}

						this.firePropertyChange(fieldName, fieldValue, value);
						field.set(this, value);

					} else if (field.getType() == ObjectProxy.class) {
						ObjectProxy value = null;

						if (data instanceof ObjectProxy) {
							value = (ObjectProxy) data;
						}

						this.firePropertyChange(fieldName, fieldValue, value);
						field.set(this, value);

					} else if (field.getType() == ArrayCollection.class) {
						ArrayCollection value = null;

						if (data instanceof ArrayCollection) {
							value = (ArrayCollection) data;
						}

						this.firePropertyChange(fieldName, fieldValue, value);
						field.set(this, value);

					} else if (AMF3_Type.class.isAssignableFrom(field.getType())) {
						AMF3_Type value = null;

						if (data instanceof AMF3_Type) {
							value = data;
						}

						this.firePropertyChange(fieldName, fieldValue, value);
						field.set(this, value);

					} else if (Collection.class.isAssignableFrom(field.getType())) {
						Collection<AMF3_Type> value = null;

						if (data instanceof Array) {
							value = ((Array) data).values();

						} else if (data instanceof ArrayCollection) {
							value = ((ArrayCollection) data).getArray().values();

						} else if (data instanceof Object) {
							value = ((Object) data).values();

						} else if (data instanceof ObjectProxy) {
							value = ((ObjectProxy) data).getObject().values();
						}

						this.firePropertyChange(fieldName, fieldValue, value);
						field.set(this, value);

					} else if (Map.class.isAssignableFrom(field.getType())) {
						Map<java.lang.String, AMF3_Type> value = null;

						if (data instanceof Array) {
							value = this.convertMap(((Array) data).getArrayData());

						} else if (data instanceof ArrayCollection) {
							value = this.convertMap(((ArrayCollection) data).getArray().getArrayData());

						} else if (data instanceof Object) {
							value = this.convertMap(((Object) data).getObjectData());

						} else if (data instanceof ObjectProxy) {
							value = this.convertMap(((ObjectProxy) data).getObject().getObjectData());
						}

						this.firePropertyChange(fieldName, fieldValue, value);
						field.set(this, value);

					}

				} catch (ClassCastException e) {
					continue;

				} finally {
					modifiersField.setInt(field, modifiers);
					modifiersField.setAccessible(false);
				}
			}
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	private final Map<java.lang.String, AMF3_Type> convertMap(Map<UTF8, AMF3_Type> input) {
		Map<java.lang.String, AMF3_Type> output = new LinkedHashMap<java.lang.String, AMF3_Type>();
		for (Entry<UTF8, AMF3_Type> entry : input.entrySet()) {
			output.put(entry.getKey().get(), entry.getValue());
		}
		return output;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(java.lang.String propertyName, PropertyChangeListener listener) {
		this.propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(java.lang.String propertyName, PropertyChangeListener listener) {
		this.propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	protected void firePropertyChange(java.lang.String propertyName, java.lang.Object oldValue, java.lang.Object newValue) {
		this.propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
}
