package collections00

import scala.collection.IndexedSeqLike
import collection.mutable.{Builder, ArrayBuffer}
import collection.generic.CanBuildFrom

// IndexedSeqLike[Base, RNA2] takes Bases and builds an RNA2
final class RNA2 private (val groups: Array[Int], val length: Int) extends IndexedSeq[Base] with IndexedSeqLike[Base, RNA2]{
  import RNA2._

  // return a new builder that takes Bases and makes an RNA2
  override def newBuilder: Builder[Base, RNA2] = new ArrayBuffer[Base] mapResult fromSeq
  // mapResult transforms the ArrayBuffer of Base using the function fromSeq
  // This turns the arrayBuffer into a RNA2. This works since arrayBuffer -> Buffer -> Seq

  // This apply allows you to get the Base out of the structure
  def apply(idx: Int): Base= {
    if (idx < 0 || idx >= length){
      throw new IndexOutOfBoundsException
    }
    // shift the number inside the array to the right
    // by the amount to get the 2 bit rep at the right side
    // Pick out those two bits with the filter M
    // Covert to Base
    Base.fromInt(groups(idx / N) >> (idx % N * S) & M)
  }
}

object RNA2{
  // Number of bits needed to represent a group
  private val S: Int = 2
  // Number of groups that fit into an int
  private val N: Int = 32 / S  // for group of 2 this is 16 groups in an int
  // Bitmask to isolate a group
  private val M: Int = (1 << S) - 1  // this is base2: 11 for a groups size of 2

  def fromSeq(buf: Seq[Base]): RNA2 = {
    val groups = new Array[Int]((buf.length + N - 1) / N) // this ensures that we always have a length of 1 or greater
    for (i <- buf.indices){
      // Take a Base and convert it into an int.
      // Figure out the location in the Int this base needs to go
      // bitwise OR it into the Int at the right location
      groups(i / N) |= Base.toInt(buf(i)) << (i % N * S)
    }
    //This makes use of the fact that a private constructor of a class is visible in the class’s companion object.
    new RNA2(groups, buf.length)
  }

  def apply(bases: Base*): RNA2 = fromSeq(bases)
}
