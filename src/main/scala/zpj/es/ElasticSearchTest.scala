package zpj.es

import org.apache.http.HttpHost
import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.admin.indices.alias.Alias
import org.elasticsearch.action.admin.indices.create.{CreateIndexRequest, CreateIndexResponse}
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.{RestClient, RestHighLevelClient}
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.junit.{After, Test};

/**
  * Created by PerkinsZhu on 2018/6/26 14:47
  **/

class ElasticSearchTest {
  val client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));

  @After
  def closeClient(): Unit = {
    client.close()
  }

  @Test
  def createIndex(): Unit = {
    val request = new CreateIndexRequest("twitter");
    request.settings(Settings.builder()
      .put("index.number_of_shards", 3)
      .put("index.number_of_replicas", 2)
    )
    request.alias(
      new Alias("twitter_alias")
    );
    client.indices().createAsync(request, listener)
  }

  @Test
  def query(): Unit = {
    val builder = new SearchSourceBuilder()
    builder.query(QueryBuilders.matchAllQuery())
    //builder.aggregation(AggregationBuilders.terms("person").field("user").size(10));
    val request = new SearchRequest()
    request.source(builder)
    val response = client.search(request)
    response.getHits.forEach((data: SearchHit) => {
      data.getSourceAsMap.forEach((a, b) => {
        println(a + "----->" + b.toString)
      })
    })
  }

  @Test
  def addData(): Unit = {

  }


  val listener = new ActionListener[CreateIndexResponse]() {
    @Override
    def onResponse(createIndexResponse: CreateIndexResponse): Unit = {
      println(createIndexResponse.isShardsAcknowledged)
      println(createIndexResponse.isAcknowledged)
    }

    @Override
    def onFailure(e: Exception): Unit = {
      e.printStackTrace()
    }
  }
}
