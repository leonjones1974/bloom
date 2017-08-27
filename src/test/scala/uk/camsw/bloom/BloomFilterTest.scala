package uk.camsw.bloom

import org.scalacheck.Gen

import scala.util.Random

class BloomFilterTest extends PropertyDrivenTest with CustomMatchers {

  def randomString(maxLength: Int): String =
    math.abs(Random.nextInt(Int.MinValue + 1 max maxLength)).toString

  val emptyFilter = BloomFilter()

  def randomStrings = for {
    n <- Gen.choose(0, 50)
    s <- Gen.listOfN(n, Gen.alphaChar).map(_.mkString)
  } yield s

  property("the filter should indicate possible inclusion for all added values") {
    forAll(emptyFilter, randomStrings) { (f, s) =>
      (emptyFilter + s) should possiblyContain(s)
    }
  }
}
