/*
 * Copyright 2025-2025 the original author or authors.
 */

package com.logaritex.mcp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for methods that handle logging message notifications from MCP servers.
 *
 * <p>
 * Methods annotated with this annotation can be used to consume logging messages from MCP
 * servers. The methods can have one of two signatures:
 * <ul>
 * <li>A single parameter of type {@code LoggingMessageNotification}
 * <li>Three parameters of types {@code LoggingLevel}, {@code String} (logger), and
 * {@code String} (data)
 * </ul>
 *
 * <p>
 * For synchronous consumers, the method must have a void return type. For asynchronous
 * consumers, the method can have either a void return type or return {@code Mono<Void>}.
 *
 * <p>
 * Example usage: <pre>{@code
 * &#64;McpLoggingConsumer
 * public void handleLoggingMessage(LoggingMessageNotification notification) {
 *     // Handle the notification
 * }
 *
 *

&#64;McpLoggingConsumer
 * public void handleLoggingMessageWithParams(LoggingLevel level, String logger, String data) {
 *     // Handle the logging message
 * }
 * }</pre>
 *
 * @author Christian Tzolov
 * @see io.modelcontextprotocol.spec.McpSchema.LoggingMessageNotification
 * @see io.modelcontextprotocol.spec.McpSchema.LoggingLevel
 */
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface McpLoggingConsumer {

}
