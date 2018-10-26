package com.xf.jstorm.wordcount;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by xiaofeng on 2018/10/12
 * Description:
 */
public class PrintBolt  implements IRichBolt {

    private static final Logger LOG = LoggerFactory.getLogger(PrintBolt.class);

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

    }

    @Override
    public void execute(Tuple input) {
        String line = input.getStringByField("line");
        LOG.error("line:" + line);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}