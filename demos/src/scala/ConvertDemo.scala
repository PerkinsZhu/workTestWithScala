import CaseClassMapConverter._
object ConvertDemo extends App {

  def ccToMap[C: CaseClassMapConverter](c: C): Map[String,Any] =
    implicitly[CaseClassMapConverter[C]].toMap(c)

  case class Person(name: String, age: Int)
  case class Car(make: String, year: Int, manu: String)

  val civic = Car("Civic",2016,"Honda")
  //println(ccToMap[Person](Person("john",18)))
  //println(ccToMap[Car](civic))

  def mapTocc[C: CaseClassMapConverter](m: Map[String,Any]) =
    implicitly[CaseClassMapConverter[C]].fromMap(m)

  val mapJohn = ccToMap[Person](Person("john",18))
  val mapCivic = ccToMap[Car](civic)
  println(mapTocc[Person](mapJohn))
  println(mapTocc[Car](mapCivic))

}