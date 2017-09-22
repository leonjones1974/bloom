package uk.camsw.bloom

sealed trait Contains[+A]

case class Possibly[A](value: A) extends Contains[A]

case object No extends Contains[Nothing]

object Contains {
  def possibly[A](value: A): Contains[A] = Possibly(value)
  def no[A] = No
}
