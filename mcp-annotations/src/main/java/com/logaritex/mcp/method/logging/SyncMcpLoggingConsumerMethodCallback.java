/*
 * Copyright 2025-2025 the original author or authors.
 */

package com.logaritex.mcp.method.logging;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import com.logaritex.mcp.annotation.McpLoggingConsumer;
import io.modelcontextprotocol.spec.McpSchema.LoggingMessageNotification;

/**
 * Class for creating Consumer callbacks around logging consumer methods.
 *
 * This class provides a way to convert methods annotated with {@link McpLoggingConsumer}
 * into callback functions that can be used to handle logging message notifications. It
 * supports methods with either a single LoggingMessageNotification parameter or three
 * parameters (LoggingLevel, String, String).
 *
 * @author Christian Tzolov
 */
public final class SyncMcpLoggingConsumerMethodCallback extends AbstractMcpLoggingConsumerMethodCallback
		implements Consumer<LoggingMessageNotification> {

	private SyncMcpLoggingConsumerMethodCallback(Builder builder) {
		super(builder.method, builder.bean);
	}

	/**
	 * Accept the logging message notification and process it.
	 * <p>
	 * This method builds the arguments for the method call and invokes the method.
	 * @param notification The logging message notification, must not be null
	 * @throws McpLoggingConsumerMethodException if there is an error invoking the logging
	 * consumer method
	 * @throws IllegalArgumentException if the notification is null
	 */
	@Override
	public void accept(LoggingMessageNotification notification) {
		if (notification == null) {
			throw new IllegalArgumentException("Notification must not be null");
		}

		try {
			// Build arguments for the method call
			Object[] args = this.buildArgs(this.method, null, notification);

			// Invoke the method
			this.method.setAccessible(true);
			this.method.invoke(this.bean, args);
		}
		catch (Exception e) {
			throw new McpLoggingConsumerMethodException(
					"Error invoking logging consumer method: " + this.method.getName(), e);
		}
	}

	/**
	 * Validates that the method return type is compatible with the logging consumer
	 * callback.
	 * @param method The method to validate
	 * @throws IllegalArgumentException if the return type is not compatible
	 */
	@Override
	protected void validateReturnType(Method method) {
		Class<?> returnType = method.getReturnType();

		if (returnType != void.class) {
			throw new IllegalArgumentException("Method must have void return type: " + method.getName() + " in "
					+ method.getDeclaringClass().getName() + " returns " + returnType.getName());
		}
	}

	/**
	 * Checks if a parameter type is compatible with the exchange type.
	 * @param paramType The parameter type to check
	 * @return true if the parameter type is compatible with the exchange type, false
	 * otherwise
	 */
	@Override
	protected boolean isExchangeType(Class<?> paramType) {
		// No exchange type for logging consumer methods
		return false;
	}

	/**
	 * Builder for creating SyncMcpLoggingConsumerMethodCallback instances.
	 * <p>
	 * This builder provides a fluent API for constructing
	 * SyncMcpLoggingConsumerMethodCallback instances with the required parameters.
	 */
	public static class Builder extends AbstractBuilder<Builder, SyncMcpLoggingConsumerMethodCallback> {

		/**
		 * Build the callback.
		 * @return A new SyncMcpLoggingConsumerMethodCallback instance
		 */
		@Override
		public SyncMcpLoggingConsumerMethodCallback build() {
			validate();
			return new SyncMcpLoggingConsumerMethodCallback(this);
		}

	}

	/**
	 * Create a new builder.
	 * @return A new builder instance
	 */
	public static Builder builder() {
		return new Builder();
	}

}
