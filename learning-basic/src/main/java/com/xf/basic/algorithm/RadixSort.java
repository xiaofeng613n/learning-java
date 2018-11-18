package com.xf.basic.algorithm;

import java.util.List;

/**
 * Created by xiao on 2018/11/18.
 */
public class RadixSort {

	private List<Integer> [] radixList;

	public void sort(List<Integer> inputList , int i ) {
		for (Integer input : inputList) {
			int readixValue = getRadixValue(input,i);
			List<Integer> valueList = radixList[readixValue];
			valueList.add(input);
		}

	}

	private int getRadixValue(int input, int i) {
		return i;
	}
}
