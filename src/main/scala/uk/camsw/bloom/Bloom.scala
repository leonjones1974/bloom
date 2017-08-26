package uk.camsw.bloom


import cats.Monoid

import scala.collection.BitSet

case class Bloom(slots: BitSet) {

}

object Bloom {

  implicit val bitSetMonoid = new Monoid[BitSet] {
    val empty: BitSet = BitSet()

    override def combine(x: BitSet, y: BitSet): BitSet = x | y
  }

  implicit class BitSetPimp(bs1: BitSet) {
    def combine(bs2: BitSet): BitSet = bitSetMonoid.combine(bs1, bs2)
  }
}


