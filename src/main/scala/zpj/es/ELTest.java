package zpj.es;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.client.transport.TransportClient;

public class ELTest {
    //    用于提供单例的TransportClient BulkProcessor
    static public TransportClient tclient = null;
    static BulkProcessor staticBulkProcessor = null;

    public static void main(String args[]) {

//        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new TransportAddress(InetAddress.getByName("host1"), 9300)).addTransportAddress(new TransportAddress(InetAddress.getByName("host2"), 9300));


    }

}
