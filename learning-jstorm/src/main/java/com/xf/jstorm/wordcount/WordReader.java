package com.xf.jstorm.wordcount;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiao on 2018/6/30.
 */
public class WordReader implements IRichSpout {

	private static final Logger LOG = LoggerFactory.getLogger(WordReader.class);

	public static final long serialVersionUID = 1L;

	private SpoutOutputCollector collector;

	private FileReader fileReader;
	private boolean completed = false;


	@Override
	public void open(Map conf, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
		final String filePath = (String) conf.get("filePath");

		try {
			this.fileReader = new FileReader(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LOG.error("file not found:{}",filePath);
		}

		this.collector = spoutOutputCollector;
	}

	@Override
	public void nextTuple() {
		if( completed){
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}

		BufferedReader reader = new BufferedReader(fileReader);
		String line;
		try {
			while ( ( line = reader.readLine()) != null){
				this.collector.emit(new Values(line),line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			completed = true;
//			try {
//				reader.close();
//				fileReader.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("line"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}


	@Override
	public void close() {

	}

	@Override
	public void activate() {

	}

	@Override
	public void deactivate() {

	}

	@Override
	public void ack(Object msgId) {
		LOG.info("OK:{}",msgId);
	}

	@Override
	public void fail(Object msgId) {
		LOG.error("FAIL:{}",msgId);
	}
}
