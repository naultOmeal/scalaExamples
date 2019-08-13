package collections01

object PokeMain {

  def main(args: Array[String]): Unit = {
    val pd00 = Pokedex(Bulbasaur)
    val pd01 = Pokedex(Charmander)
    val pd02 = Pokedex(Bulbasaur, Squirtle)
    println("Pokedex01       = " + pd00)
    println("Pokedex02       = " + pd01)
    println("Pokedex03       = " + pd02)
    println("00 union 01     = " + (pd00 union pd01))
    println("00 diff 01      = " + (pd00 diff pd01))
    println("00 intersect 01 = " + (pd00 intersect pd01))
    println("00 intersect 02 = " + (pd00 intersect pd02))
    println("01 intersect 00 = " + (pd01 intersect pd00))
    println("02 intersect 00 = " + (pd02 intersect pd00))
  }
}
