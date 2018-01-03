/*
 * Copyright 2017 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zpj.database.mongodbDriver

import org.mongodb.scala._
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Sorts._
import org.mongodb.scala.model.Updates._
import zpj.baseTest.BaseTest.MyEnum

object QuickTourCaseClass {

  def main(args: Array[String]): Unit = {
    object MyEnum extends Enumeration{
      type MyEnumType = Value
      val AA= Value
    }

    object PPS {
      def apply(firstName: String, lastName: String): PPS = PPS(new ObjectId(), firstName, lastName);
    }
    case class PPS(_id: ObjectId, firstName: String, lastName: String)
    import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
    import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
    import org.mongodb.scala.bson.codecs.Macros._
//    val codecRegistry = fromRegistries(fromProviders(classOf[MyEnum.Value],classOf[PPS]), DEFAULT_CODEC_REGISTRY)
    val codecRegistry = fromRegistries(fromProviders(classOf[PPS]), DEFAULT_CODEC_REGISTRY)

    val mongoClient: MongoClient = if (args.isEmpty) MongoClient() else MongoClient(args.head)

    val database: MongoDatabase = mongoClient.getDatabase("test").withCodecRegistry(codecRegistry)
    val collection: MongoCollection[PPS] = database.getCollection("person1")
    collection.drop().subscribe(MongodbDriverTool.observer[Completed])

    val person: PPS = PPS("Ada", "Lovelace")

    collection.insertOne(person).subscribe(MongodbDriverTool.observer[Completed])
    mongoClient.close()
    Thread.sleep(2000)
  }

}
