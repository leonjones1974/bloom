package uk.camsw.bloom

sealed trait Contains

case object Possibly extends Contains

case object No extends Contains

sealed trait ContainsA[+A]

case class PossiblyA[A](value: A) extends ContainsA[A]

case object NoA extends ContainsA[Nothing]
