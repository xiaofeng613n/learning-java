package com.xf.jstorm.wordcount;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * Created by xiao on 2018/6/30.
 */
public class WordNormalizer implements IRichBolt{

	private OutputCollector collector;

	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.collector = outputCollector;
	}

	@Override
	public void execute(Tuple input) {
//		input.getStringByField("line");
		String sentence = input.getString(0);
		String [] words = sentence.split(" ");
		for (String word : words) {
			word = word.trim();
			List<Tuple> anchors = Lists.newArrayList(input);
			this.collector.emit(anchors,new Values(word));
 		}
 		this.collector.ack(input);
	}

	@Override
	public void cleanup() {

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("word"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
