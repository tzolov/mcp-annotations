/*
 * Copyright 2025-2025 the original author or authors.
 */

package com.logaritex.mcp.method.logging;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import com.logaritex.mcp.annotation.McpLoggingConsumer;
import io.modelcontextprotocol.spec.McpSchema.LoggingLevel;
import io.modelcontextprotocol.spec.McpSchema.LoggingMessageNotification;

/**
 * Example class demonstrating the use of {@link SyncMcpLoggingConsumerMethodCallback}.
 *
 * This class shows how to create and use a synchronous logging consumer method callback.
 * It provides examples of methods annotated with {@link McpLoggingConsumer} that can be
 * used to handle logging message notifications.
 *
 * @author Christian Tzolov
 */
public class SyncMcpLoggingConsumerMethodCallbackExample {

	/**
	 * Example method that accepts a LoggingMessageNotification.
	 * @param notification The logging message notification
	 */
	@McpLoggingConsumer
	public void handleLoggingMessage(LoggingMessageNotification notification) {
		System.out.println("Received logging message: " + notification.level() + " - " + notification.logger() + " - "
				+ notification.data());
	}

	/**
	 * Example method that accepts individual parameters (LoggingLevel, String, String).
	 * @param level The logging level
	 * @param logger The logger name
	 * @param data The log message data
	 */
	@McpLoggingConsumer
	public void handleLoggingMessageWithParams(LoggingLevel level, String logger, String data) {
		System.out.println("Received logging message with params: " + level + " - " + logger + " - " + data);
	}

	/**
	 * Example of how to create and use a SyncMcpLoggingConsumerMethodCallback.
	 * @param args Command line arguments
	 * @throws Exception If an error occurs
	 */
	public static void main(String[] args) throws Exception {
		// Create an instance of the example class
		SyncMcpLoggingConsumerMethodCallbackExample example = new SyncMcpLoggingConsumerMethodCallbackExample();

		// Create a callback for the handleLoggingMessage method
		Method method1 = SyncMcpLoggingConsumerMethodCallbackExample.class.getMethod("handleLoggingMessage",
				LoggingMessageNotification.class);
		Consumer<LoggingMessageNotification> callback1 = SyncMcpLoggingConsumerMethodCallback.builder()
			.method(method1)
			.bean(example)
			.build();

		// Create a callback for the handleLoggingMessageWithParams method
		Method method2 = SyncMcpLoggingConsumerMethodCallbackExample.class.getMethod("handleLoggingMessageWithParams",
				LoggingLevel.class, String.class, String.class);
		Consumer<LoggingMessageNotification> callback2 = SyncMcpLoggingConsumerMethodCallback.builder()
			.method(method2)
			.bean(example)
			.build();

		// Create a sample logging message notification
		LoggingMessageNotification notification = new LoggingMessageNotification(LoggingLevel.INFO, "test-logger",
				"This is a test message");

		// Use the callbacks
		System.out.println("Using callback1:");
		callback1.accept(notification);

		System.out.println("\nUsing callback2:");
		callback2.accept(notification);
	}

}
