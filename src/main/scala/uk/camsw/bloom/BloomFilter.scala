package uk.camsw.bloom


import com.roundeights.hasher.Algo
import com.roundeights.hasher.Algo._
import uk.camsw.bloom.HashFunction.boundedHash
import uk.camsw.bloom.Key.Key
import cats.syntax.semigroup._
import Implicits._

import scala.collection.immutable.BitSet


/**
  * TODO: Replace these with applicatives, such that we can create a list of Id's to retrieve from store
  */
sealed trait Contains

case object Possibly extends Contains

case object No extends Contains


case class BloomFilter[A] private[bloom](private[bloom] val fHash: Seq[String => Int],
                                      private[bloom] val slots: BitSet = BitSet()) {

  def +(obj: A)(implicit k: Key[A]): BloomFilter[A] = {
    val key = k(obj)
    copy(slots = fHash.foldLeft(slots)((bs, f) => bs |+| BitSet(f(key))))
  }

  def contains[B](key: B)(implicit k: Key[B]): Contains = if (fHash.forall(f => slots(f(k(key))))) Possibly else No
}

object BloomFilter {

  val HashingAlgos = Set(md5, crc32, sha1)

  def apply[A](size: Int, algos: Set[Algo]): BloomFilter[A] = {
    require(size > 0, "Size must be greater than zero")

    val fHash = algos.map(algo => boundedHash(algo, 0 until size))
    new BloomFilter[A](fHash.toSeq)
  }
}



