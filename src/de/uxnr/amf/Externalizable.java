package de.uxnr.amf;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

@SuppressWarnings("rawtypes")
public abstract class Externalizable {
	private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	private transient Map<String, Object> fields;

	public Map<String, Object> packFields() throws IOException {
		return this.fields;
	}

	public void unpackFields(Map<String, Object> fields) throws IOException {
		this.fields = fields;
		this.unpackFields(this.getClass(), fields);
	}

	protected final void unpackFields(Class type, Map<String, Object> fields) throws IOException {
		try {
			for (Field field : type.getDeclaredFields()) {
				int modifiers = field.getModifiers();
				if ((modifiers & (Modifier.STATIC | Modifier.TRANSIENT)) != 0)
					continue;

				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, (modifiers & ~Modifier.PRIVATE & ~Modifier.PROTECTED) | Modifier.PUBLIC);

				String fieldName = field.getName();
				Object fieldValue = field.get(this);

				try {
					Object value = fields.get(fieldName);

					if (value != null && field.getType().isAssignableFrom(value.getClass())) {
						this.propertyChangeSupport.firePropertyChange(fieldName, fieldValue, value);
						field.set(this, value);
					}
					/*
				} catch (ClassCastException e) {
					continue;
					 */
				} finally {
					modifiersField.setInt(field, modifiers);
					modifiersField.setAccessible(false);
				}
			}
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.removePropertyChangeListener(listener);
	}
}
