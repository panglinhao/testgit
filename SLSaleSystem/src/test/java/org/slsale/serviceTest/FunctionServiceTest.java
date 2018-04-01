package org.slsale.serviceTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slsale.mapper.FunctionMapper;
import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;
import org.slsale.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath*:applicationContext-mybatis.xml"})
public class FunctionServiceTest {

	@Test
	public void testGetMainFunctionList() {
		ApplicationContext atc = new ClassPathXmlApplicationContext("classpath:applicationContext-mybatis.xml");
		FunctionMapper functionMapper = atc.getBean(FunctionMapper.class);
		Authority authority = new Authority();
		authority.setRoleId(1);
		List<Function> mainFunctionList = functionMapper.getMainFunctionList(authority);
		System.out.println(mainFunctionList);
	}

	@Test
	public void testGetSubFunctionList() {
		
	}

}
