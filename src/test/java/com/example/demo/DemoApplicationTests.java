package com.example.demo;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReUtil;
import com.example.demo.dao.UserDao;
import com.example.demo.service.UserService;
import com.example.demo.tools.Check;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	UserService userservice;


	@Test
	void test() {
		System.out.println("hello");
		DateTime t = DateUtil.parse("2023-6-10 20:23:10");
		String s = DateUtil.format(t,"dd");
		System.out.println(s);
	}
}
