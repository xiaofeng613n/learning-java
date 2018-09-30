package xiaofeng;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by xiaofeng on 2017/10/10
 * Description:
 */
public class KafkaTest
{
	public static void main(String[] args) throws ExecutionException, InterruptedException
	{
		testProducer();
		//testConsumer();
	}

	public static void testProducer() throws ExecutionException, InterruptedException
	{
//		Properties props = new Properties();
//		props.put("bootstrap.servers", "10.40.6.151:9092,10.40.6.152:9092,10.40.6.153:9092");//10.33.4.231:9092
//		props.put("acks", "all");
//		props.put("retries", 0);
//		props.put("batch.size", 16384);
//		props.put("linger.ms", 1);
//		props.put("buffer.memory", 33554432);
//		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Properties kafkaProducerProperties = new Properties();
		kafkaProducerProperties.put("acks", "all");
		kafkaProducerProperties.put("retries", 0);
		kafkaProducerProperties.put("batch.size", 16384);
		kafkaProducerProperties.put("linger.ms", 1);
		kafkaProducerProperties.put("buffer.memory", 33554432);
		kafkaProducerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProducerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProducerProperties.put("bootstrap.servers", "10.40.6.151:9092,10.40.6.152:9092,10.40.6.153:9092");

		Producer<String, String> producer = new KafkaProducer<>(kafkaProducerProperties);
		for(int i = 0; i < Integer.MAX_VALUE; i++)
		{
//			String topic =  ( i % 2 ) == 0 ? "topic1" : "topic2";
			String topic = "app2";
			Future<RecordMetadata> send = null;
			try{
				send = producer.send(new ProducerRecord<String, String>(topic, Integer.toString(i), Integer.toString(i)), new Callback() {
					@Override
					public void onCompletion(RecordMetadata metadata, Exception exception) {
						if( exception != null ){
							System.out.println(exception);
						}
					}
				});
			} catch (Exception e) {
				System.out.println(e);
			}
//			System.out.println("offset:" +send.get().offset());
//			System.out.println("partition:" + send.get().partition());
//			producer.flush();
//			Thread.sleep(1000);
		}

		producer.close();
	}

	public static void testConsumer()
	{

		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.1.108:9093");//10.33.4.231:9092
		props.put("group.id", "groupC");
//		props.put("enable.auto.commit", "true");
//		props.put("auto.commit.interval.ms", "1000");
		props.put("auto.offset.reset", "earliest");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Arrays.asList("topicOfFlume")); //testFromFlume
		while (true)
		{
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records)
			{
				System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
			}
		}
	}
}