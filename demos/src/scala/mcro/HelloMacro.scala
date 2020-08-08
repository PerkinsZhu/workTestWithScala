package mcro

object HelloMacro extends App {

  import LibraryMacros._

  greeting("john")
  tell("john")

}