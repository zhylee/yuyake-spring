package cn.yuyake.controller;

import cn.yuyake.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class HelloController {

	@Autowired
	private HelloService helloService;

	public void handleRequest() {
		helloService.sayHello();
		helloService.justWantToThrowException();
	}
}
