package uk.camsw.bloom

trait Hashable[A] {
  def apply(a: A): String
}

object Hashable {

  def apply[A](f: A => String): Hashable[A] = new Hashable[A] {
    override def apply(a: A): String = f(a)
  }

  implicit val stringKey: Hashable[String] = Hashable(identity)
  implicit val intKey: Hashable[Int] = Hashable(_.toString)

}
