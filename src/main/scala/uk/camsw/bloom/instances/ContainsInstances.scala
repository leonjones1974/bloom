package uk.camsw.bloom.instances

import cats.Applicative
import uk.camsw.bloom.{Contains, No, Possibly}

trait ContainsInstances {

  implicit val containsInstances: Applicative[Contains] = new Applicative[Contains] {
    def pure[A](a: A): Contains[A] = Possibly(a)

    override def ap[A, B](ff: Contains[(A) => B])(fa: Contains[A]): Contains[B] = (ff, fa) match {
      case (Possibly(f), Possibly(a)) => Possibly(f(a))
      case _ => No
    }
  }
}

