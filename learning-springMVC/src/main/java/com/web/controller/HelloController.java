package com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xiao on 2019/1/1.
 */
@Controller
//@RequestMapping(value = "/hello")
public class HelloController {


	@RequestMapping(value = "/hi",method = RequestMethod.GET)
	@ResponseBody
	public String hi(String name) {
		return "hi," + name;
	}
}
