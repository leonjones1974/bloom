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

  implicit def arbContains[T](implicit a: Arbitrary[T]): Arbitrary[ContainsA[T]] =
    Arbitrary(sized(n =>
      frequency(
        (n, resize(n / 2, Arbitrary.arbitrary[T]).map(PossiblyA(_))),
        (1, const(NoA)))))

  implicit def eqContains[T](implicit t: Eq[T]): Eq[ContainsA[T]] = new Eq[ContainsA[T]] {
    def eqv(x: ContainsA[T], y: ContainsA[T]): Boolean = (x, y) match {
      case (NoA, NoA) => true
      case (PossiblyA(a), PossiblyA(b)) => a == b
      case _ => false
    }
  }

  checkAll("Contains[Int]", ApplicativeTests[ContainsA].applicative[Int, Int, Int])
  checkAll("Contains[String]", ApplicativeTests[ContainsA].applicative[String, String, String])

}
