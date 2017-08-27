package uk.camsw.bloom

object Key {

  type Key[A] = A => String

  implicit val stringKey: Key[String] = identity

}
