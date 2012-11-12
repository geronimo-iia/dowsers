package com.intelligentsia.dowsers.entity.manager;

public interface EntityFactoryProvider {
	public <T> T newInstance(final Class<T> expectedType) throws NullPointerException;
}
