package xiaofeng;

import static org.junit.Assert.assertTrue;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private static TestingServer server;
    private static CuratorFramework client;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        server = new TestingServer(2181, true);
        server.start();

        client = CuratorFrameworkFactory.newClient("127.0.0.1",
                new ExponentialBackoffRetry(1000, 3));
        client.start();
    }

    @AfterClass
    public static void tearDownAfterClass() throws IOException {
        server.stop();
        client.close();
    }

    @Test
    public void testFoobar() throws Exception {
        System.out.println("client: " + client);
        client.create().forPath("/test", "test-data".getBytes());

        byte[] data = client.getData().forPath("/test");
        System.out.println("data: " + new String(data));
    }
}
