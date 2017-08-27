package uk.camsw.bloom


import com.roundeights.hasher.Algo
import com.roundeights.hasher.Algo._
import uk.camsw.bloom.Key.Key

import scala.collection.immutable.BitSet
import BitSetImplicits._
import uk.camsw.bloom.HashFunction.boundedHash

sealed trait Contains

case object Possibly extends Contains

case object No extends Contains


case class BloomFilter private[bloom](private[bloom] val fHash: Seq[String => Int],
                                      private[bloom] val slots: BitSet = BitSet()) {

  def +[A](obj: A)(implicit k: Key[A]): BloomFilter = {
    val key = k(obj)
    copy(slots = fHash.foldLeft(slots)((xs, f) => xs.combine(BitSet(f(key)))))
  }

  def contains(key: String): Contains = if (fHash.forall(f => slots(f(key)))) Possibly else No
}

object BloomFilter {

  val HashingAlgos = Set(md5, crc32, sha1)

  def apply(size: Int, algos: Set[Algo]): BloomFilter = {
    val fHash = algos.map(algo => boundedHash(algo, 0 until size))
    new BloomFilter(fHash.toSeq)
  }
}



