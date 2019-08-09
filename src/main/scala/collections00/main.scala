package collections00

object main {

  def main(args: Array[String]): Unit = {
    val rna00 = RNA(A, U, G, G, C)
    println(rna00)
    val rna01 = rna00 map { case A => U case b => b }
    println(rna01)
    val rna02 = rna00 ++ rna00
    println(rna02)
  }

}
