package uk.camsw.bloom

trait Key[A] {
  def apply(a: A): String
}

object Key {

  def apply[A](f: A => String): Key[A] = new Key[A] {
    override def apply(a: A): String = f(a)
  }

  implicit val stringKey: Key[String] = Key(identity)
  implicit val intKey: Key[Int] = Key(_.toString)

}
