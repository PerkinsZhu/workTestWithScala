package zpj.playmong

import cn.playscala.mongo.annotations.Entity

/**
  * Created by PerkinsZhu on 2018/6/9 16:19
  **/
case class Beans()


case class Person(_id: String, name: String, age: Option[Int])

@Entity("common-article")
case class Article(_id: String, title: String, content: String, authorId: List[String])

@Entity("common-author")
case class Author(_id: String, name: String)