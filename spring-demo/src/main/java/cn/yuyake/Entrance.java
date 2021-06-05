package cn.yuyake;

import cn.yuyake.aspects.OutSide;
import cn.yuyake.controller.HelloController;
import cn.yuyake.controller.HiController;
import cn.yuyake.controller.WelcomeController;
import cn.yuyake.introduction.LittleUniverse;
import cn.yuyake.service.WelcomeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * create by yeah on 2021/4/4 17:09
 */
@Configuration
@EnableAspectJAutoProxy
@Import(OutSide.class) // import 可以将基础服务类给管理起来
@ComponentScan("cn.yuyake")
public class Entrance {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Entrance.class);
		OutSide os1 = applicationContext.getBean(OutSide.class);
		os1.say();
		OutSide os2 = (OutSide) applicationContext.getBean("cn.yuyake.aspects.OutSide"); // 这里必须全名才能获取到
		os2.say();
	}

	public static void main1(String[] args) {
		// System.out.println("gek");
		String xmlPath = "D:\\Pract\\Spring-Framework\\spring-demo\\src\\main\\resources\\spring\\spring-config.xml";
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(xmlPath);
		WelcomeService welcomeService = (WelcomeService) applicationContext.getBean("welcomeService");
		welcomeService.sayHello("yuyake");
	}

	public static void main2(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Entrance.class);
		String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			System.out.println(beanDefinitionName);
		}
        // WelcomeService welcomeService = (WelcomeService) applicationContext.getBean("welcomeServiceImpl");
		// welcomeService.sayHello("yuyake");
		WelcomeController welcomeController = (WelcomeController) applicationContext.getBean("welcomeController");
		welcomeController.handleRequest();
	}

	public static void main3(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Entrance.class);
		System.out.println("================= test about AOP =================");
		HelloController helloController = applicationContext.getBean(HelloController.class);
		helloController.handleRequest();
		HiController hiController = applicationContext.getBean(HiController.class);
		hiController.handleRequest();
		((LittleUniverse) hiController).burningUp();
	}
}
