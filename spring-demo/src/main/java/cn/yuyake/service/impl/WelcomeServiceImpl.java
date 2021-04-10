package cn.yuyake.service.impl;

import cn.yuyake.service.WelcomeService;
import org.springframework.stereotype.Service;

/**
 * create by yeah on 2021/4/4 17:11
 */
@Service
public class WelcomeServiceImpl implements WelcomeService {

	@Override
	public String sayHello(String name) {
		System.out.println("Welcome: " + name);
		return "success";
	}
}
