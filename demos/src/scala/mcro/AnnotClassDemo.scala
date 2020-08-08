package mcro
object AnnotClassDemo extends App {
  trait Animal {
    val name: String
//    def sayHello:Unit
  }
  @TalkingAnimal("wangwang")
  case class Dog(val name: String) extends Animal

  @TalkingAnimal("miaomiao")
  case class Cat(val name: String) extends Animal

  //@TalkingAnimal("")
  //case class Carrot(val name: String)
  //Error:(12,2) Annotation TalkingAnimal only apply to Animal inherited! @TalingAnimal
  Dog("Goldy").sayHello
  Cat("Kitty").sayHello

}