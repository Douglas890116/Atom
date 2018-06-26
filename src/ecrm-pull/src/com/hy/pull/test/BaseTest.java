package com.hy.pull.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * 单元测试基类
 * 创建日期 2016-09-05
 * @author temdy
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:resources/*-context.xml"})
public class BaseTest{
}
