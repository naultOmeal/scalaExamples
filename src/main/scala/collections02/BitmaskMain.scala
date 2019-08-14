package collections02

object BitmaskMain {
  def main(args: Array[String]): Unit = {
    val b00 = Bitmask(length = 4,0,1,2,3)
    val b01 = Bitmask(length = 4,3)
    println("b00: 1111     -> " + b00)
    println("b01: 0001     -> " +  b01)
    println("b00 union b01 -> " + (b00 union b01))
    println("b00 diff b01  -> " + (b00 diff b01))
    println("b01 diff b00  -> " + (b01 diff b00))
    println("b00 inter b01 -> " + (b00 intersect b01))
    println("b01 inter b00 -> " + (b01 intersect b00))
  }
}
