package com.xf.demo;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

/**
 * Created by xiao on 2019/1/18.
 */
public class ToolModule extends AbstractModule {

	@Override
	protected void configure() {
		//是将接口与其具体实现绑定起来
		bind(IAnimal.class).to(IAnimalImpl.class);
		bind(ITool.class).to(IToolImpl.class);

		//Map的绑定
		MapBinder<String, String> mapBinder = MapBinder.newMapBinder(binder(), String.class, String.class);
		mapBinder.addBinding("test1").toInstance("test1");
		mapBinder.addBinding("test2").toInstance("test2");
	}
}
