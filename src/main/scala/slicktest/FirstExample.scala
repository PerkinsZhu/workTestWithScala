package slicktest


//#imports
// Use H2Profile to connect to an H2 database
import slick.jdbc.PostgresProfile.api._
import slicktest.bean.{Coffees, Suppliers}

import scala.concurrent.ExecutionContext.Implicits.global
//#imports
import scala.concurrent.Await
import scala.concurrent.duration.Duration

import scala.collection.mutable.ArrayBuffer

/**
  * A simple example that uses statically typed queries against an in-memory
  * H2 database. The example data comes from Oracle's JDBC tutorial at
  * http://docs.oracle.com/javase/tutorial/jdbc/basics/tables.html.
  */
object FirstExample extends App {
  val lines = new ArrayBuffer[Any]()
  def println(s: Any) = lines += s

  val suppliers = TableQuery[Suppliers]
  val coffees = TableQuery[Coffees]

  // Connect to the database and execute the following block within a session
  //#setup
  val db = Database.forURL("jdbc:postgresql://127.0.0.1/test","postgres",password = "jinzhao",driver = "org.postgresql.Driver",executor = AsyncExecutor("test1", numThreads=10, queueSize=1000))
  //可从配置文件中读取链接信息
//    .forConfig("postgres")
  try {
    // ...
    //#setup

    //#create
    val setup = DBIO.seq(
      // Create the tables, including primary and foreign keys
      (suppliers.schema ++ coffees.schema).create,
      //两种插入数据的方式
      // Insert some suppliers
      suppliers += (101, "Acme, Inc.",      "99 Market Street", "Groundsville", "CA", "95199"),
      suppliers += ( 49, "Superior Coffee", "1 Party Place",    "Mendocino",    "CA", "95460"),
      suppliers += (150, "The High Ground", "100 Coffee Lane",  "Meadows",      "CA", "93966"),
      // Equivalent SQL code:
      // insert into SUPPLIERS(SUP_ID, SUP_NAME, STREET, CITY, STATE, ZIP) values (?,?,?,?,?,?)

      // Insert some coffees (using JDBC's batch insert feature, if supported by the DB)
      coffees ++= Seq(
        ("Colombian",         101, 7.99, 0, 0),
        ("French_Roast",       49, 8.99, 0, 0),
        ("Espresso",          150, 9.99, 0, 0),
        ("Colombian_Decaf",   101, 8.99, 0, 0),
        ("French_Roast_Decaf", 49, 9.99, 0, 0)
      )
      // Equivalent SQL code:
      // insert into COFFEES(COF_NAME, SUP_ID, PRICE, SALES, TOTAL) values (?,?,?,?,?)
    )

    val setupFuture = db.run(setup)
    //#create
    val resultFuture = setupFuture.flatMap { _ =>

      //#readall
      // Read all coffees and print them to the console
      println("Coffees:")
      db.run(coffees.result).map(_.foreach {
        case (name, supID, price, sales, total) =>
          println("  " + name + "\t" + supID + "\t" + price + "\t" + sales + "\t" + total)
      })
      // Equivalent SQL code:
      // select COF_NAME, SUP_ID, PRICE, SALES, TOTAL from COFFEES
      //#readall

    }.flatMap { _ =>

      //#projection
      // Why not let the database do the string conversion and concatenation?
      //#projection
      println("Coffees (concatenated by DB):")
      //#projection
      val q1 = for(c <- coffees)
        yield LiteralColumn("  ") ++ c.name ++ "\t" ++ c.supID.asColumnOf[String] ++
          "\t" ++ c.price.asColumnOf[String] ++ "\t" ++ c.sales.asColumnOf[String] ++
          "\t" ++ c.total.asColumnOf[String]
      // The first string constant needs to be lifted manually to a LiteralColumn
      // so that the proper ++ operator is found

      // Equivalent SQL code:
      // select '  ' || COF_NAME || '\t' || SUP_ID || '\t' || PRICE || '\t' SALES || '\t' TOTAL from COFFEES

      db.stream(q1.result).foreach(println)
      //#projection

    }.flatMap { _ =>

      //#join
      // Perform a join to retrieve coffee names and supplier names for
      // all coffees costing less than $9.00
      //#join
      println("Manual join:")
      //#join
      val q2 = for {
        c <- coffees if c.price < 9.0
        s <- suppliers if s.id === c.supID
      } yield (c.name, s.name)
      // Equivalent SQL code:
      // select c.COF_NAME, s.SUP_NAME from COFFEES c, SUPPLIERS s where c.PRICE < 9.0 and s.SUP_ID = c.SUP_ID
      //#join
      db.run(q2.result).map(_.foreach(t =>
        println("  " + t._1 + " supplied by " + t._2)
      ))

    }.flatMap { _ =>

      // Do the same thing using the navigable foreign key
      println("Join by foreign key:")
      //#fkjoin
      val q3 = for {
        c <- coffees if c.price < 9.0
        s <- c.supplier
      } yield (c.name, s.name)
      // Equivalent SQL code:
      // select c.COF_NAME, s.SUP_NAME from COFFEES c, SUPPLIERS s where c.PRICE < 9.0 and s.SUP_ID = c.SUP_ID
      //#fkjoin

      db.run(q3.result).map(_.foreach { case (s1, s2) => println("  " + s1 + " supplied by " + s2) })

    }
    Await.result(resultFuture, Duration.Inf)
    lines.foreach(Predef.println _)
    //#setup
  } finally db.close
  //#setup
}