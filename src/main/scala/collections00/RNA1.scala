package collections00

// Tacking the private on to this class makes it a primary constructor that is private.
// This helps to enforce the singleton pattern and prevents new objects from being created outside of the local file.
// The new instance of the object can then be accessed via the companion object and a getInstance() method.
final class RNA1 private (val groups: Array[Int], val length: Int) extends IndexedSeq[Base]{
  // This import loads everything into this class from from the companion object
  import RNA1._

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

object RNA1{
  // Number of bits needed to represent a group
  private val S: Int = 2
  // Number of groups that fit into an int
  private val N: Int = 32 / S  // for group of 2 this is 16 groups in an int
  // Bitmask to isolate a group
  private val M: Int = (1 << S) - 1  // this is base2: 11 for a groups size of 2

  def fromSeq(buf: Seq[Base]): RNA1 = {
    val groups = new Array[Int]((buf.length + N - 1) / N) // this ensures that we always have a length of 1 or greater
    for (i <- buf.indices){
      // Take a Base and convert it into an int.
      // Figure out the location in the Int this base needs to go
      // bitwise OR it into the Int at the right location
      groups(i / N) |= Base.toInt(buf(i)) << (i % N * S)
    }
    //This makes use of the fact that a private constructor of a class is visible in the class’s companion object.
    new RNA1(groups, buf.length)
  }

  def apply(bases: Base*): RNA1 = fromSeq(bases)
}