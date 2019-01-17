package com.xf.demo;

import com.google.inject.Injector;

/**
 * Created by xiao on 2019/1/18.
 */
public class Main {

	public static void main(String[] args) {
		CustomModuleBuilder moduleBuilder = new CustomModuleBuilder();
		moduleBuilder.add(new ToolModule());
		moduleBuilder.add(new HumanModule());
		Injector injector = moduleBuilder.createInjector();
		Person person = injector.getInstance(Person.class);
		person.startwork();
	}
}
