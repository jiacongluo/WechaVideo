package com.jiacong.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 初始化项目,用于测试
 * @author jiacongluo
 *
 */
@RestController
public class HelloWorldController {
	@RequestMapping("/hello")
	public String Hello() {
		return "hello";
	}
}
