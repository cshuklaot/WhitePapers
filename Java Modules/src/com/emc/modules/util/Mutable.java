package com.emc.modules.util;

public class Mutable<T> {

	/**
	 * Creates an instance with a <code>null</code> value.
	 */
	public Mutable() {
		this.obj = null;
	}

	/**
	 * Constructor.
	 * 
	 * @param obj
	 *            Initial value.
	 */
	public Mutable(final T obj) {
		this.obj = obj;
	}

	/**
	 * Sets a new value.
	 * 
	 * @param newObj
	 *            The new value.
	 */
	public void set(final T newObj) {
		this.obj = newObj;
	}

	/**
	 * @return Returns the current value.
	 */
	public T get() {
		return this.obj;
	}

	/**
	 * @return Returns <code>true</code> if and only if the current value is <code>null</code>.
	 */
	public boolean isNull() {
		return this.obj == null;
	}

	private T obj;

}
