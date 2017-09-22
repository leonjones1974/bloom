package uk.camsw.bloom.syntax

import uk.camsw.bloom.{Contains, No, Possibly}

trait ContainsSyntax {
  def no[A]: Contains[A] = No
  def possibly[A](value: A): Contains[A] = Possibly(value)

  implicit final def possiblyIdSyntax[A](a: A): ContainsIdOps[A] = new ContainsIdOps(a)
}

class ContainsIdOps[A](val a: A) extends AnyVal {

  def possibly = Possibly(a)

}
