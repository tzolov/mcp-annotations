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
 * Annotates a method used for completion functionality in the MCP framework. This
 * annotation can be used in two mutually exclusive ways: 1. To complete an expression
 * within a URI template of a resource 2. To complete a prompt argument
 *
 * Note: You must use either the prompt or the uri attribute, but not both simultaneously.
 *
 * @author Christian Tzolov
 */
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface McpComplete {

	/**
	 * The name reference to a prompt. This is used when the completion method is intended
	 * to complete a prompt argument.
	 */
	String prompt() default "";

	/**
	 * The name reference to a resource template URI. This is used when the completion
	 * method is intended to complete an expression within a URI template of a resource.
	 */
	String uri() default "";

}
