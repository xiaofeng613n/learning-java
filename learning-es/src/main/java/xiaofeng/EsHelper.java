package xiaofeng;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xiaofeng on 2018/11/15
 * Description:
 */
public class EsHelper {

    private static TransportClient client;

    public static TransportClient createClient() {
        Settings settings = Settings.builder()
                .put("client.transport.sniff", false)
                .put("client.transport.ignore_cluster_name", true)
                .build();

        client = new PreBuiltTransportClient(settings);

        List<String> ips = Arrays.asList("10.40.6.129","10.40.6.139","10.40.6.144");

        int port = 9300;
        for(String ip : ips){
            try {
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }
        return client;
    }


    public static BulkProcessor buildBulkProcessor() {
        if ( client == null ) {
            client = createClient();
        }
        BulkProcessor bulkProcessor = BulkProcessor.builder(
                client,
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long l, BulkRequest bulkRequest) {
//                        System.out.println(bulkRequest.numberOfActions());
                    }

                    @Override
                    public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                        if( bulkResponse.hasFailures() ){
//                            System.out.println("error");
                        }
                    }

                    @Override
                    public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
                        if (throwable != null) {
//                            System.out.println("exception");
                        }
                    }
                })
                .setBulkActions(1000)
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setConcurrentRequests(1)
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();

        return bulkProcessor;
    }
}

