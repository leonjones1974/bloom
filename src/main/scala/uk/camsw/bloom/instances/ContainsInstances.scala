package uk.camsw.bloom.instances

import cats.Applicative
import uk.camsw.bloom.{ContainsA, NoA, PossiblyA}

trait ContainsInstances {

  implicit val containsInstances: Applicative[ContainsA] = new Applicative[ContainsA] {
    def pure[A](a: A): ContainsA[A] = PossiblyA(a)

    override def ap[A, B](ff: ContainsA[(A) => B])(fa: ContainsA[A]): ContainsA[B] = (ff, fa) match {
      case (PossiblyA(f), PossiblyA(a)) => PossiblyA(f(a))
      case _ => NoA
    }
  }
}

