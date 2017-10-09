package zpj.collection.list

/**
  * Created by PerkinsZhu on 2017/9/27 16:15. 
  */
class List[T] {
  case class Node(head: T, tail: List[T])
}

object List {
  def apply[T](node: T*): List[T] = new List()
}