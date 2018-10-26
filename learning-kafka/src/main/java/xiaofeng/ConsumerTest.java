package xiaofeng;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by xiao on 2018/1/16.
 */
public class ConsumerTest {

	public static void main(String[] args) {
		final ConcurrentLinkedQueue<String> subscribedTopics = new ConcurrentLinkedQueue<>();

		Properties props = new Properties();
		props.put("bootstrap.servers", "10.40.6.151:9092,10.40.6.152:9092,10.40.6.153:9092");
		props.put("group.id", "my-group");
		props.put("auto.offset.reset", "earliest");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

		// 最开始的订阅列表：atopic、btopic
		consumer.subscribe(Arrays.asList("app10"));
		TopicPartition p = new TopicPartition("app9", 2);
		consumer.assign(Arrays.asList(p));
		consumer.seek(p, 	1);
		while (true) {
			ConsumerRecords<String, String> records = 		consumer.poll(2000); //表示每2秒consumer就有机会去轮询一下订阅状态是否需要变更
			// 本例不关注消息消费，因此每次只是打印订阅结果！
			records.forEach( record ->{

				System.err.println("consumer:" + record.key() + " " + record.value());
			});
			//System.out.println(consumer.subscription());
			if (!subscribedTopics.isEmpty()) {
				Iterator<String> iter = subscribedTopics.iterator();
				List<String> topics = new ArrayList<>();
				while (iter.hasNext()) {
					topics.add(iter.next());
				}
				subscribedTopics.clear();
				consumer.subscribe(topics); // 重新订阅topic
			}
		}
		// 本例只是测试之用，使用了while(true)，所以这里没有显式关闭consumer
//        consumer.close();
	}
}