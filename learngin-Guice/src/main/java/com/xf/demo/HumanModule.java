package com.xf.demo;

import com.google.inject.AbstractModule;

/**
 * Created by xiao on 2019/1/18.
 */
public class HumanModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Person.class).asEagerSingleton();
	}
}
