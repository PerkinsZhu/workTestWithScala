package zpj.es

import com.sksamuel.elastic4s.ElasticsearchClientUri
import com.sksamuel.elastic4s.analyzers.StopAnalyzer
import com.sksamuel.elastic4s.bulk.BulkCompatibleDefinition
import com.sksamuel.elastic4s.http.{ElasticDsl, HttpClient}
import com.sksamuel.elastic4s.searches.SearchDefinition
import org.apache.http.HttpHost
import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.admin.indices.alias.Alias
import org.elasticsearch.action.admin.indices.create.{CreateIndexRequest, CreateIndexResponse}
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RestClientBuilder.RequestConfigCallback
import org.elasticsearch.client.{RestClient, RestHighLevelClient}
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.junit.{After, Test}
import play.api.libs.json.Json

import scala.concurrent.duration.Duration
import scala.util.{Failure, Success};

/**
  * Created by PerkinsZhu on 2018/6/26 14:47
  **/

class ElasticSearchTestWithJava {
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


import scala.concurrent.ExecutionContext.Implicits.global


object ElasticSearchTestWithScala extends ElasticDsl {
  //  使用文档：https://sksamuel.github.io/elastic4s/docs/document/index.html

  implicit val client = HttpClient.apply(ElasticsearchClientUri("127.0.0.1", 9200))

  implicit class SearchDefinitionOps(val definition: SearchDefinition) {
    def run()(implicit client: HttpClient) = client.execute(definition).map({
      case Left(ex) => println(ex.body)
      case Right(data) => println(data.body)
    })
  }


  /*val uri = ElasticsearchClientUri("elasticsearch://foo:1234,boo:9876?cluster.name=mycluster")
  val client = HttpClient.apply(uri)*/

  @After
  def closeClient(): Unit = {
    Thread.sleep(5000)
    client.close()
  }

  @Test
  def createIndex(): Unit = {
    //    val request = createIndex("scala")
    val request = createIndex("places") mappings (
      mapping("cities") as(
        keywordField("id"),
        textField("name") boost 4,
        textField("content") analyzer StopAnalyzer
      )
      )
    client.execute(request)
  }

  @Test
  def testSaveData(): Unit = {
    val request = indexInto("places" / "cities") id "jsonCN" fields(
      "name" -> "BeiJing",
      "country" -> "China",
      "continent" -> "Europe",
      "status" -> "Awesome"
    )
    client.execute(request)
  }

  @Test
  def testQuery(): Unit = {
    //各种查询语法见：https://github.com/sksamuel/elastic4s
    searchWithType("places" / "cities").query("London") start 0 limit 10 run
  }

  def main(args: Array[String]): Unit = {
    testQuery()
  }


  @Test
  def testGetById(): Unit = {
    //    val query  = get("cn").from("places" / "cities")
    val query = multiget(
      get("cn").from("places" / "cities"),
      get("jsonCN").from("places" / "cities")
    )
    client.execute(query).map({
      case Left(ex) => println(ex.body)
      case Right(data) => println(data.body)
    })
  }

}
