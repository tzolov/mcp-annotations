/*
 * Copyright 2025-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.logaritex.mcp.provider;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

import com.logaritex.mcp.annotation.McpPrompt;
import com.logaritex.mcp.annotation.PromptAdaptor;
import com.logaritex.mcp.method.prompt.SyncMcpPromptMethodCallback;
import io.modelcontextprotocol.server.McpServerFeatures.SyncPromptSpecification;
import io.modelcontextprotocol.util.Assert;
import reactor.core.publisher.Mono;

/**
 */
public class SyncMcpPromptProvider {

	private final List<Object> promptObjects;

	public SyncMcpPromptProvider(List<Object> promptObjects) {
		Assert.notNull(promptObjects, "promptObjects cannot be null");
		this.promptObjects = promptObjects;
	}

	public List<SyncPromptSpecification> getPromptSpecifications() {

		List<SyncPromptSpecification> syncPromptSpecification = this.promptObjects.stream()
			.map(resourceObject -> Stream.of(doGetClassMethods(resourceObject))
				.filter(method -> method.isAnnotationPresent(McpPrompt.class))
				.filter(method -> !Mono.class.isAssignableFrom(method.getReturnType()))
				.map(mcpPromptMethod -> {
					var promptAnnotation = mcpPromptMethod.getAnnotation(McpPrompt.class);
					var mcpPrompt = PromptAdaptor.asPrompt(promptAnnotation, mcpPromptMethod);

					var methodCallback = SyncMcpPromptMethodCallback.builder()
						.method(mcpPromptMethod)
						.bean(resourceObject)
						.prompt(mcpPrompt)
						.build();

					return new SyncPromptSpecification(mcpPrompt, methodCallback);
				})
				.toList())
			.flatMap(List::stream)
			.toList();

		return syncPromptSpecification;
	}

	/**
	 * Returns the methods of the given bean class.
	 * @param bean the bean instance
	 * @return the methods of the bean class
	 */
	protected Method[] doGetClassMethods(Object bean) {
		return bean.getClass().getDeclaredMethods();
	}

}
