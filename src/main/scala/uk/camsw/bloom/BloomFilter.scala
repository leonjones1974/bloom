package uk.camsw.bloom


import cats.syntax.semigroup._
import com.roundeights.hasher.Algo
import com.roundeights.hasher.Algo._
import uk.camsw.bloom.HashFunction.boundedHash
import uk.camsw.bloom.Implicits._

import scala.collection.immutable.BitSet

case class BloomFilter[A] private[bloom](
                                             private[bloom] val fHash: Seq[String => Int],
                                             private[bloom] val slots: BitSet = BitSet()) {

  def :+(obj: A)(implicit k: Key[A]): BloomFilter[A] = {
    val key = k(obj)
    copy(slots = fHash.foldLeft(slots)((bs, f) => bs |+| BitSet(f(key))))
  }

  def find[B](key: B)(implicit k: Key[B]): Contains[B] = if (fHash.forall(hash => slots(hash(k(key))))) Possibly(key) else No
}

object BloomFilter {

  val HashingAlgos = Set(md5, crc32, sha1)

  def apply[A](size: Int, algos: Set[Algo]): BloomFilter[A] = {
    require(size > 0, "Size must be greater than zero")

    val fHash = algos.map(algo => boundedHash(algo, 0 until size))
    new BloomFilter[A](fHash.toSeq)
  }
}



