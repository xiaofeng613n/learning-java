package com.xf.demo;

import com.google.inject.Inject;

import java.util.Map;

/**
 * Created by xiao on 2019/1/18.
 */
public class Person {

	private IAnimal iAnimal;
	private ITool iTool;
	private Map<String, String> map;


	@Inject
	public Person(IAnimal iAnimal, ITool iTool, Map<String, String> map) {
		this.iAnimal = iAnimal;
		this.iTool = iTool;
		this.map = map;
	}

	public void startwork() {
		iTool.doWork();
		iAnimal.work();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println("map:" + entry.getKey() + " value" + entry.getValue());
		}
	}
}
