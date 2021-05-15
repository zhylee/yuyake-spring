package cn.yuyake.service.impl;

import cn.yuyake.service.HiService;
import org.springframework.stereotype.Service;

@Service
public class HiServiceImpl implements HiService {

	@Override
	public void sayHi() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Hi everybody");
	}

	@Override
	public String justWantToSayHi() {
		return "Just want to say hi";
	}
}
