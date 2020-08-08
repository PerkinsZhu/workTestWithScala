package scalameta

object MacroAnnotDemo extends App {

  @Greetings object Greet {
    def add(x: Int, y: Int) = println(x + y)
  }

/*  Greet.sayHello("John")
  Greet.add(1,2)*/
}