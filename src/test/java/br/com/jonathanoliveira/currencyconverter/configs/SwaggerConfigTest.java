package br.com.jonathanoliveira.currencyconverter.configs;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = SwaggerConfig.class)
public class SwaggerConfigTest {
	@InjectMocks
	private SwaggerConfig swaggerConfig;
	@Mock
	private ResourceHandlerRegistry resourceHandlerRegistry;
	@Mock
	private ResourceHandlerRegistration resourceHandlerRegistration;
	@Mock
	private InMemorySwaggerResourcesProvider inMemorySwaggerResourcesProvider;

	@Test
	public void swaggerResourcesProvider() {
		final SwaggerResourcesProvider response = this.swaggerConfig
				.swaggerResourcesProvider(inMemorySwaggerResourcesProvider);
		Assert.assertNotNull(response);
	}

	@Test
	public void swagger() {
		final Docket response = this.swaggerConfig.swagger();
		Assert.assertNotNull(response);
	}

	@Test
	public void addResourceHandlers() {
		Mockito.when(resourceHandlerRegistry.addResourceHandler(anyString())).thenReturn(resourceHandlerRegistration);
		Mockito.when(resourceHandlerRegistration.addResourceLocations(anyString()))
				.thenReturn(resourceHandlerRegistration);
		swaggerConfig.addResourceHandlers(resourceHandlerRegistry);
		verify(resourceHandlerRegistry, times(3)).addResourceHandler(anyString());
	}
}
