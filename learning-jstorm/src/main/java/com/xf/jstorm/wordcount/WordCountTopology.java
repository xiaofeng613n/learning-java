package com.xf.jstorm.wordcount;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

import java.util.concurrent.TimeUnit;

/**
 * Created by xiao on 2018/7/1.
 */
public class WordCountTopology {

	private static final String WORD_READER_SPOUT_ID = "word-reader";
	private static final String WORD_NORMALIZER_BOLT_ID = "word-normalizer";
	private static final String WORD_COUNTER_BOLT_ID = "word-counter";

	public static void main(String[] args) throws InterruptedException {

		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout(WORD_READER_SPOUT_ID, new WordReader());

		builder.setBolt(WORD_NORMALIZER_BOLT_ID, new WordNormalizer(),1)
				.shuffleGrouping(WORD_READER_SPOUT_ID);

		builder.setBolt(WORD_COUNTER_BOLT_ID, new WordCounter(),1)
				.fieldsGrouping(WORD_NORMALIZER_BOLT_ID,new Fields("word"));

		Config conf = new Config();
		//conf.put("filePath","D:\\IdeaSpace\\learning-java\\learning-jstorm\\src\\main\\resources\\word.txt");
		conf.put("filePath","d:/text.txt");

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("WordCountTopology",conf,builder.createTopology());

		TimeUnit.SECONDS.sleep(30);

		cluster.shutdown();
	}
}
