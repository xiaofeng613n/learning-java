package com.xf.demo;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by xiao on 2019/1/18.
 */
public class CustomModuleBuilder implements Iterable<Module> {

	private final List<Module> modules = new ArrayList<>();

	public CustomModuleBuilder add(Module... newModules) {
		for (Module module : newModules) {
			modules.add(module);
		}
		return this;
	}

	@Override
	public Iterator<Module> iterator() {
		return modules.iterator();
	}

	public Injector createInjector() {
		Injector injector = Guice.createInjector(modules);
		return injector;
	}


}

