package com.xf.jstorm.wordcount;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiao on 2018/6/30.
 */
public class WordCounter implements IRichBolt{

	private static final Logger LOG = LoggerFactory.getLogger(WordCounter.class);

	private Integer id;
	private String name;

	private Map<String,Integer> counters;
	private OutputCollector collector;

	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.collector = outputCollector;
		this.counters = new HashMap<>();
		this.name = topologyContext.getThisComponentId();
		this.id = topologyContext.getThisTaskId();
	}

	@Override
	public void execute(Tuple input) {
		String word = input.getString(0);
		Integer counter = counters.get(word);
		if (counter == null) {
			counter = new Integer(0);
			counters.put(word,counter);
		}
		counters.put(word,counter + 1);

		this.collector.ack(input);
	}

	@Override
	public void cleanup() {
		LOG.info("ending word count:");
		for (Map.Entry<String, Integer> entry : counters.entrySet()) {
			LOG.info("{}:{}",entry.getKey(),entry.getValue());
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
