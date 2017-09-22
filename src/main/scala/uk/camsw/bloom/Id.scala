package uk.camsw.bloom

trait Id[A] {
  def apply(a: A): String
}

object Id {

  def apply[A](f: A => String): Id[A] = new Id[A] {
    override def apply(a: A): String = f(a)
  }

  implicit val stringKey: Id[String] = Id(identity)
  implicit val intKey: Id[Int] = Id(_.toString)

}
