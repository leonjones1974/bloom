package uk.camsw.bloom.syntax

import uk.camsw.bloom.Contains

trait ContainsSyntax {
  final def no[A]: Contains[A] = ???

  implicit final def possiblySyntax[A](a: Contains[A]): ContainsOps[A] = new ContainsOps(a)

}

class ContainsOps[A](val a: Contains[A]) extends AnyVal {


}
