package cn.yuyake.controller;

import cn.yuyake.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * create by yeah on 2021/4/4 18:13
 */
@Controller
public class WelcomeController {
	@Autowired
	private WelcomeService welcomeService;
	public void handleRequest() {
		welcomeService.sayHello("from controller");
	}
}
