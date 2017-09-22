package uk.camsw.bloom

import cats.Eq
import cats.instances.int._
import cats.instances.string._
import cats.instances.tuple._
import cats.laws.discipline.ApplicativeTests
import org.scalacheck.Arbitrary
import org.scalacheck.Gen.{const, frequency, resize, sized}
import org.scalatest.FunSuite
import org.typelevel.discipline.scalatest.Discipline
import uk.camsw.bloom.instances.contains._

class ContainsLaws extends FunSuite with Discipline {

  implicit def arbContains[T](implicit a: Arbitrary[T]): Arbitrary[Contains[T]] =
    Arbitrary(sized(n =>
      frequency(
        (n, resize(n / 2, Arbitrary.arbitrary[T]).map(Possibly(_))),
        (1, const(No)))))

  implicit def eqContains[T](implicit t: Eq[T]): Eq[Contains[T]] = new Eq[Contains[T]] {
    def eqv(x: Contains[T], y: Contains[T]): Boolean = (x, y) match {
      case (No, No) => true
      case (Possibly(a), Possibly(b)) => a == b
      case _ => false
    }
  }

  checkAll("Contains[Int]", ApplicativeTests[Contains].applicative[Int, Int, Int])
  checkAll("Contains[String]", ApplicativeTests[Contains].applicative[String, String, String])

}
