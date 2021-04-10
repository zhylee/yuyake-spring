package cn.yuyake;

import cn.yuyake.controller.WelcomeController;
import cn.yuyake.service.WelcomeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * create by yeah on 2021/4/4 17:09
 */
@Configuration
@ComponentScan("cn.yuyake")
public class Entrance {

	public static void main1(String[] args) {
		// System.out.println("gek");
		String xmlPath = "D:\\Pract\\Spring-Framework\\spring-demo\\src\\main\\resources\\spring\\spring-config.xml";
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(xmlPath);
		WelcomeService welcomeService = (WelcomeService) applicationContext.getBean("welcomeService");
		welcomeService.sayHello("yuyake");
	}

	public static void main(String[] args) {
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
	// TODO review 6-5 6-12
}
