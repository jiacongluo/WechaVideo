package com.jiacong;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jiacong.controller.intercepter.MiniInterceptor;
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	//静态资源映射
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
		.addResourceLocations("classpath:/META-INF/resources/")
		.addResourceLocations("file:/Users/jiacongluo/Document/Documents/jiacong_videos_dev/");
	}
	
	@Bean(initMethod="init")
	public ZKCuratorClient zkCuratorClient() {
		return new ZKCuratorClient();
	}
	
	
	@Bean
	public MiniInterceptor miniInterceptor() {
		return new MiniInterceptor();
	}
	
	/**
	 * 给需要拦截的路径添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(miniInterceptor()).addPathPatterns("/user/**")
				       .addPathPatterns("/video/upload", "/video/uploadCover",
				    		   			"/video/userLike", "/video/userUnLike")
								  .addPathPatterns("/bgm/**")
								  .excludePathPatterns("/user/queryPublisher")
								  .excludePathPatterns("/wxLogin");
		
		super.addInterceptors(registry);
	}
	
}
