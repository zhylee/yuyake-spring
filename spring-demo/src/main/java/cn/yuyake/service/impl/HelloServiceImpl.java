package cn.yuyake.service.impl;

import cn.yuyake.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

	@Override
	public void sayHello() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Hello everybody");
	}

	@Override
	public void justWantToThrowException() {
		throw new RuntimeException("Hello exception");
	}
}
