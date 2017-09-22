package uk.camsw.bloom.syntax

import cats.syntax.{OptionIdOps, OptionOps}
import uk.camsw.bloom.{Contains, ContainsA}

trait ContainsSyntax {
  final def no[A]: ContainsA[A] = ???

  implicit final def possiblySyntax[A](a: ContainsA[A]): ContainsOps[A] = new ContainsOps(a)

}

class ContainsOps[A](val a: ContainsA[A]) extends AnyVal {


}
