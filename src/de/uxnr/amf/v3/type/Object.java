package de.uxnr.amf.v3.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.uxnr.amf.AMF_Context;
import de.uxnr.amf.AMF_Type;
import de.uxnr.amf.v3.AMF3_Externalizable;
import de.uxnr.amf.v3.AMF3_Object;
import de.uxnr.amf.v3.AMF3_Trait;
import de.uxnr.amf.v3.AMF3_Type;
import de.uxnr.amf.v3.base.U29;
import de.uxnr.amf.v3.base.UTF8;

public class Object extends AMF3_Type {
	private static final Map<UTF8, Class<? extends AMF3_Object>> classes = new HashMap<UTF8, Class<? extends AMF3_Object>>();
	private static final Map<java.lang.String, java.lang.String> mappings = new HashMap<java.lang.String, java.lang.String>();

	private static final UTF8 EMPTY_KEY = new UTF8();

	private final Map<UTF8, AMF3_Type> value = new LinkedHashMap<UTF8, AMF3_Type>();

	private AMF3_Trait trait = null;

	private java.lang.Integer hashCode = null;

	public Object() { }

	@Override
	public void write(AMF_Context context, DataOutputStream output) throws IOException {
		if (this.writeTrait(context, output)) {
			this.writeAttributes(context, output);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AMF_Type read(AMF_Context context, DataInputStream input) throws IOException {
		AMF3_Type type = this.readTrait(context, input);

		if (type == this) {
			UTF8 className = this.trait.getClassName();
			boolean isExternalizable = this.trait.isExternalizable();

			Class<? extends AMF3_Object> classType = null;

			// Search for a custom class
			if (Object.classes.containsKey(className)) {
				classType = Object.classes.get(className);

			} else {
				for (Entry<java.lang.String, java.lang.String> entry : Object.mappings.entrySet()) {
					java.lang.String searchName = className.get().replace(entry.getKey(), entry.getValue());

					try {
						classType = (Class<? extends AMF3_Object>) Thread.currentThread().getContextClassLoader().loadClass(searchName);
						Object.registerClass(className, classType);
						break;
					} catch (ClassNotFoundException e) {
						continue;
					}
				}
			}

			// Create new instance of custom class
			if (classType != null) {
				try {
					Object object = classType.newInstance();
					object.trait = this.trait;
					type = object;
				} catch (Exception e) {
					throw new IOException(e);
				}
			}

			// Externalizable class not found
			if (type == this && isExternalizable) {
				throw new RuntimeException("Unknown externalizable class "+className);
			}

			context.addAMF3Object(type);

			if (isExternalizable) {
				if (type instanceof AMF3_Externalizable) {
					((AMF3_Externalizable) type).readExternal(context, input);

				} else {
					throw new RuntimeException("Class "+className+" is not externalizable");
				}

			} else if (type instanceof AMF3_Object) {
				((AMF3_Object) type).read(context, input);

			} else {
				((Object) type).readAttributes(context, input);
			}

			this.hashCode = null;
		}

		return type;
	}

	public final boolean writeTrait(AMF_Context context, DataOutputStream output) throws IOException {
		int reference = context.getAMF3ObjectReference(this);
		if (reference >= 0) {
			U29 flag = new U29((reference << 1) & ~1);
			flag.write(context, output);
			return false;
		}

		context.addAMF3Object(this);

		UTF8 className = this.trait.getClassName();
		List<UTF8> names = this.trait.getNames();

		int traitref = context.getAMF3TraitReference(this.trait);
		if (traitref >= 0) {
			U29 flag = new U29((traitref << 2) & ~2 | 1);
			flag.write(context, output);

		} else {
			int flags = this.trait.getCount() << 4;
			flags |= 1 | 2 | (this.trait.isExternalizable() ? 4 : 0) | (this.trait.isDynamic() ? 8 : 0);

			U29 flag = new U29(flags);
			flag.write(context, output);

			className.write(context, output);

			for (UTF8 name : names) {
				name.write(context, output);
			}

			context.addAMF3Trait(this.trait);
		}

		return true;
	}

	public final  AMF3_Type readTrait(AMF_Context context, DataInputStream input) throws IOException {
		U29 flag = new U29(context, input);
		int flags = flag.get();

		if ((flags & 1) == 0)
			return context.getAMF3Object(flags >> 1);

		if ((flags & 2) == 0)
			this.trait = context.getAMF3Trait(flags >> 2);

		if (this.trait == null) {
			this.trait = new AMF3_Trait();
			this.trait.setExternalizable((flags & 4) == 4);
			this.trait.setDynamic((flags & 8) == 8);

			UTF8 className = (UTF8) new UTF8().read(context, input);
			this.trait.setClassName(className);

			int count = flags >> 4;
			for (int index = 0; index < count; index++) {
				UTF8 name = new UTF8();
				name = (UTF8) name.read(context, input);
				this.trait.addName(name);
			}

			context.addAMF3Trait(this.trait);
		}

		return this;
	}

	protected final void writeAttributes(AMF_Context context, DataOutputStream output) throws IOException {
		for (UTF8 name : this.trait.getNames()) {
			AMF3_Type.writeType(context, output, this.value.get(name));
		}

		if (this.trait.isDynamic()) {
			for (Entry<UTF8, AMF3_Type> entry : this.value.entrySet()) {
				if (!this.trait.hasName(entry.getKey())) {
					entry.getKey().write(context, output);
					AMF3_Type.writeType(context, output, entry.getValue());
				}
			}

			Object.EMPTY_KEY.write(context, output);
		}
	}

	protected final void readAttributes(AMF_Context context, DataInputStream input) throws IOException {
		for (UTF8 name : this.trait.getNames()) {
			AMF3_Type value = AMF3_Type.readType(context, input);

			this.value.put(name, value);
		}

		if (this.trait.isDynamic()) {
			UTF8 key = null;
			AMF3_Type value = null;
			do {
				key = new UTF8();
				key = (UTF8) key.read(context, input);

				if (key.equals(Object.EMPTY_KEY)) {
					break;
				} else {
					value = AMF3_Type.readType(context, input);

					this.value.put(key, value);
				}
			} while (key != null && value != null);
		}
	}

	public Map<UTF8, AMF3_Type> getObjectData() {
		return this.value;
	}

	public Set<UTF8> keySet() {
		return this.value.keySet();
	}

	public Set<Entry<UTF8, AMF3_Type>> entrySet() {
		return this.value.entrySet();
	}

	public Collection<AMF3_Type> values() {
		return this.value.values();
	}

	public void put(UTF8 key, AMF3_Type value) {
		this.hashCode = null;
		this.value.put(key, value);
	}

	public void put(java.lang.String key, AMF3_Type value) {
		this.put(new UTF8(key), value);
	}

	public void set(UTF8 key, AMF3_Type value) {
		this.put(key, value);
	}

	public void set(java.lang.String key, AMF3_Type value) {
		this.put(new UTF8(key), value);
	}

	public void set(UTF8 key, AMF3_Type value, boolean dynamic) {
		if (!dynamic && !this.trait.hasName(key))
			this.trait.addName(key);

		this.put(key, value);
	}

	public AMF3_Type get(UTF8 key) {
		return this.value.get(key);
	}

	public AMF3_Type get(java.lang.String key) {
		return this.get(new UTF8(key));
	}

	public java.lang.String getClassName() {
		return this.trait.getClassName().get();
	}

	public boolean isDynamic() {
		return this.trait.isDynamic();
	}

	public boolean isExternalizable() {
		return this.trait.isExternalizable();
	}

	@Override
	public java.lang.String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Object '");
		sb.append(this.getClassName());
		sb.append("' {\n");
		for (Entry<UTF8, AMF3_Type> entry : this.value.entrySet()) {
			sb.append(entry.getKey());
			sb.append(": ");
			sb.append(entry.getValue());
			sb.append(",\n");
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		if (this.hashCode != null)
			return this.hashCode;
		int hashCode = this.value.hashCode();
		if (this.trait != null)
			hashCode ^= this.trait.hashCode();
		return this.hashCode = hashCode;
	}

	public static void registerClass(UTF8 className, Class<? extends AMF3_Object> classType) {
		Object.classes.put(className, classType);
	}

	public static void registerClassMapping(java.lang.String remoteName, java.lang.String localName) {
		Object.mappings.put(remoteName, localName);
	}
}
