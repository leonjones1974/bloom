package uk.camsw.bloom


import uk.camsw.bloom.Key.Key

import scala.collection.immutable.BitSet

sealed trait Contains
case object Possibly extends Contains
case object No extends Contains

trait BloomFilter[K] {

  def +[A](obj: A)(implicit k: Key[A]): BloomFilter[K]

  def contains(key: String): Contains

}

object BloomFilter {
  import BitSetImplicits._
  def apply(slots: BitSet = BitSet()): BloomFilter[Int] = new BloomFilter[Int] {

    def tempHash(key: String): Int =
      math.abs(key.hashCode % 1000)

    override def +[A](obj: A)(implicit k: Key[A]): BloomFilter[Int] = {
      val key = k(obj)
      apply(slots.combine(BitSet(tempHash(key))))
    }

    override def contains(key: String): Contains = if (slots(tempHash(key))) Possibly else No
  }
}



