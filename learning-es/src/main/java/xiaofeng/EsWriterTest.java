package xiaofeng;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.util.Map;

/**
 * Created by xiaofeng on 2018/11/15
 * Description:
 */
public class EsWriterTest {

    public static void main(String[] args) {
        test();

    }


    public static void test(){
        for (int i = 0; i < 4; i ++ ) {
            System.out.println("start thread " + i);
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    Map<String,Object> sourceLog = buildCDNLog();
                    sourceLog.put("_collectTime","2018-11-15 09:44:47");
                    write("cdn-lot7", sourceLog);
                }
            });
            thread1.start();
        }

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void write(String index, Map<String, Object> sourceLog) {
        BulkProcessor processor = EsHelper.buildBulkProcessor();

        while (true) {
            for (int i = 0; i < 2000; i++) {
                processor.add(Requests
                        .indexRequest(index)
                        .type("elog")
                        .id(UUIDHexGenerator.generate())
                        .source(sourceLog).waitForActiveShards(3));
            }
            processor.flush();
        }

    }

    public static Map<String, Object> buildCDNLog() {
        String logStr = "{\n" +
                "\t\"_domain\": \"cdn-log\",\n" +
                "\t\"reqhdr_cookie\": \"AKAM_CLIENTID=0b3a6ed662e0aec04de4a8dd2918ba7d; _gcl_au=1.1.1145944660.1542272521; _ga=GA1.2.900238748.1542272522; _gid=GA1.2.327604601.1542272522; gb_lang=it; gb_pipeline=GBIT; gb_isNewUser=noLogin; ak_bmsc=F63FF59DB0DA6FA9E1087969140DD4DA5C7B653CB1570000FF35ED5B0AC24E3B~plytNqiq7hnkz7XGphLVV53B3lysSGumJESPpWNcCb/ JPleXj9320KqlsrfyuhkRJ9HNaaB2907fGxNLMmRjKMX0wQH4sTaQKHNzzdYdHGI/jNoUjkn/udp5Lff2XRnP3y3vFoh9XkqGjJUY3AGV0UgKnG3VvKtLOxPEMCqjVpcDyCFSrM795So 73yDwp/a58O8UsB78iBA7iA0xAdtgP3BacL2YcHrFPjrRnYLPcl/ImlG4/3eJV0otLEa6vfnF; cdn_countryCode=IT; gb_countryCode=IT; gb_currencyCode=EUR; _fbp=fb.1.1542272528272.2068409987; WEBF_guid=0b3a6ed662e0aec04de4a8dd2918ba7d_1542272531; landingUrl=https://it.gearbest.com/cellulari-e-smartphone-c_1; od=prcvjmvdxlia1542272533866; osr_referrer=https://www.google.it/; osr_landing=https://it.gearbest.com/cellulari-e-smartphone-c_11293/; globalegrow_user_id=f80625df-5fc6-afee-a931-35aa8888b198; globalegrowbigdata2018_globalegrow_session_id=3d1e9965-107c-ad80-90d4-ea3c311e4f71; _dc_gtm_UA-89413560-1=1; ORIGINDC=4; ORIGINDCPC=4; _ngroup=[{\\\"tid\\\":5,\\\"v\\\":[{\\\"n\\\":\\\"source\\\",\\\"v\\\":\\\"www.google.it\\\"},{\\\"n\\\":\\\"medium\\\",\\\"v\\\":\\\"referrer\\\"}],\\\"lt\\\":1542272613,\\\"ct\\\":1542272521}]; bm_sv=9D3C9CB075813A17F36B2AFE4CB8B177~CoE3ynSPAV3F9dLUEvkvaSIT8cTV20 2ZQrmzqU 4TNMYm8gUmG6WYWRVLmvv5F71iomJ7ZTLdJidCtK6H9A2sYfViD1xx0oEAz1v65GiJLHYz4xZUiFSYCurjIJSXCJ8qKZj1WNJCtCpoxF/Rdmjn5mwOg/Pt0CWUiGJtI4Vyk=; WEBF_predate=1542272636; globalegrowbigdata2018_globalegrow_session_id_3d1e9965-107c-ad80-90d4-ea3c311e4f71=false\",\n" +
                "\t\"message_bytes\": \"656\",\n" +
                "\t\"netperf_lastbyte\": \"1\",\n" +
                "\t\"resphdr_date\": \"Thu, 15 Nov 2018 09:03:58 GMT\",\n" +
                "\t\"content_wafwarnslrs\": \"\",\n" +
                "\t\"type\": \"cloud_monitor\",\n" +
                "\t\"resphdr_lastmod\": \"Fri, 09 Nov 2018 11:58:43 GMT\",\n" +
                "\t\"reqhdr_accenc\": \"gzip, deflate, br\",\n" +
                "\t\"content_akamclientid\": \"0b3a6ed662e0aec04de4a8dd2918ba7d\",\n" +
                "\t\"content_wafdenyrules\": \"\",\n" +
                "\t\"message_protover\": \"2.0\",\n" +
                "\t\"message_reqquery\": \"v=gb0.2.1\",\n" +
                "\t\"reqhdr_acclang\": \"it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7\",\n" +
                "\t\"id\": \"1cf5b685bed367ed257b117-a\",\n" +
                "\t\"netperf_firstbyte\": \"1\",\n" +
                "\t\"message_reqmethod\": \"GET\",\n" +
                "\t\"format\": \"default\",\n" +
                "\t\"reqhdr_referer\": \"https://it.gearbest.com/sw.js?v=gb0.2.1\",\n" +
                "\t\"message_reqhost\": \"it.gearbest.com\",\n" +
                "\t\"version\": \"1.0\",\n" +
                "\t\"message_reqpath\": \"/sw.js\",\n" +
                "\t\"resphdr_etag\": \"W/\\\"5be57673-5ba\\\"\",\n" +
                "\t\"content_trueclientasnum\": \"12874\",\n" +
                "\t\"content_wafwarnrules\": \"\",\n" +
                "\t\"message_status\": \"200\",\n" +
                "\t\"resphdr_alloworigin\": \"*\",\n" +
                "\t\"message_cliip\": \"93.61.75.4\",\n" +
                "\t\"network_network\": \"fastweb\",\n" +
                "\t\"_collectTime\": \"2018-11-15 09:03:59\",\n" +
                "\t\"resphdr_conn\": \"keep-alive\",\n" +
                "\t\"content_wafwarndata\": \"\",\n" +
                "\t\"network_asnum\": \"12874\",\n" +
                "\t\"netperf_lastmilertt\": \"407\",\n" +
                "\t\"message_reqport\": \"443\",\n" +
                "\t\"message_ua_text\": \"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36\",\n" +
                "\t\"message_proto\": \"https\",\n" +
                "\t\"resphdr_setcookie\": \"\",\n" +
                "\t\"geo_region\": \"\",\n" +
                "\t\"_ip\": \"127.0.0.1\",\n" +
                "\t\"content_wafwarntags\": \"\",\n" +
                "\t\"netperf_asnum\": \"12874\",\n" +
                "\t\"netperf_edgeip\": \"92.123.101.60\",\n" +
                "\t\"content_trueclientlatitude\": \"45.47\",\n" +
                "\t\"message_resplen\": \"656\",\n" +
                "\t\"network_networktype\": \"\",\n" +
                "\t\"geo_city\": \"MILANO\",\n" +
                "\t\"full_request\": \"/sw.js?v=gb0.2.1\",\n" +
                "\t\"content_trueclientip\": \"93.61.75.4\",\n" +
                "\t\"message_sslver\": \"TLSv1.2\",\n" +
                "\t\"network_edgeip\": \"92.123.101.60\",\n" +
                "\t\"message_respct\": \"application/javascript\",\n" +
                "\t\"netperf_cachestatus\": \"2\",\n" +
                "\t\"start\": \"1542272638.314\",\n" +
                "\t\"biz_referer_2\": \"it.gearbest.com\",\n" +
                "\t\"biz_referer_3\": \"sw.js?v=gb0.2.1\",\n" +
                "\t\"resphdr_contenc\": \"gzip\",\n" +
                "\t\"cp\": \"568430\",\n" +
                "\t\"content_wafdenydata\": \"\",\n" +
                "\t\"log_time\": \"2018-11-15 09:03:58\",\n" +
                "\t\"geo_long\": \"9.20\",\n" +
                "\t\"reqhdr_cachectl\": \"no-cache\",\n" +
                "\t\"content_lastmilebw\": \"5000\",\n" +
                "\t\"content_trueclientlongitude\": \"9.20\",\n" +
                "\t\"geo_lat\": \"45.47\",\n" +
                "\t\"geo_country\": \"IT\",\n" +
                "\t\"netperf_downloadtime\": \"4\",\n" +
                "\t\"message_fwdhost\": \"it.gearbest.com\",\n" +
                "\t\"message_ua\": \"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36\"\n" +
                "}";

        return JSON.parseObject(logStr,Map.class);
    }

}