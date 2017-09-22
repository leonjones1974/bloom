package uk.camsw.bloom


import cats.syntax.semigroup._
import com.roundeights.hasher.Algo
import com.roundeights.hasher.Algo._
import uk.camsw.bloom.HashFunction.boundedHash
import uk.camsw.bloom.Implicits._

import scala.collection.immutable.BitSet

case class BloomFilter[A, K] private[bloom](
                                             private[bloom] val fHash: Seq[String => Int],
                                             private[bloom] val slots: BitSet = BitSet())(implicit vh: Hashable[A], kh: Hashable[K]) {

  def :+(obj: A): BloomFilter[A, K] = {
    val key = vh(obj)
    copy(slots = fHash.foldLeft(slots)((bs, f) => bs |+| BitSet(f(key))))
  }

  def find(key: K): Contains[K] = if (fHash.forall(hash => slots(hash(kh(key))))) Possibly(key) else No
}

object BloomFilter {

  val HashingAlgos = Set(md5, crc32, sha1)

  def apply[A, K](size: Int, algos: Set[Algo])(implicit vh: Hashable[A], kh: Hashable[K]): BloomFilter[A, K] = {
    require(size > 0, "Size must be greater than zero")

    val fHash = algos.map(algo => boundedHash(algo, 0 until size))
    new BloomFilter[A, K](fHash.toSeq)
  }
}



