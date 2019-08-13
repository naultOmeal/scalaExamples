package collections01

object PokeMain {

  def main(args: Array[String]): Unit = {
    val pd00 = Pokedex(Bulbasaur)
    println("Pokedex01   = " + pd00)
    val pd01 = Pokedex(Charmander)
    println("Pokedex02   = " + pd01)
    val pd02 = Pokedex(Bulbasaur, Squirtle)
    println("Pokedex03   = " + pd02)
    println("00 union 01 = " + (pd00 union pd01))
  }
}
