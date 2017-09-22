package uk.camsw.bloom

sealed trait Contains[+A]

case class Possibly[A](value: A) extends Contains[A]

case object No extends Contains[Nothing]
