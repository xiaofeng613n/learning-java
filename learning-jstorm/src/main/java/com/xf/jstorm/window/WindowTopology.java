package com.xf.jstorm.window;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.xf.jstorm.wordcount.WordCounter;
import com.xf.jstorm.wordcount.WordNormalizer;
import com.xf.jstorm.wordcount.WordReader;

import java.util.concurrent.TimeUnit;

/**
 * Created by xiaofeng on 2018/7/2
 * Description:
 */
public class WindowTopology {

    private static final String BIZ_SPOUT = "biz-spout";
    private static final String DB_BOLT = "db-bolt";

    public static void main(String[] args) {

//        TopologyBuilder builder = new TopologyBuilder();
//
//        builder.setSpout(WORD_READER_SPOUT_ID, new BizSpout());
//
//        builder.setBolt(WORD_NORMALIZER_BOLT_ID, new WordNormalizer(),1)
//                .shuffleGrouping(WORD_READER_SPOUT_ID);
//
//        builder.setBolt(WORD_COUNTER_BOLT_ID, new WordCounter(),1)
//                .fieldsGrouping(WORD_NORMALIZER_BOLT_ID,new Fields("word"));
//
//        Config conf = new Config();
//        //conf.put("filePath","D:\\IdeaSpace\\learning-java\\learning-jstorm\\src\\main\\resources\\word.txt");
//        conf.put("filePath","d:/text.txt");
//
//        LocalCluster cluster = new LocalCluster();
//        cluster.submitTopology("WordCountTopology",conf,builder.createTopology());
//
//        TimeUnit.SECONDS.sleep(30);
//
//        cluster.shutdown();
    }
}