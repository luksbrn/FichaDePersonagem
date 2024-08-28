import models.CharacterClass
import models.Race
import models.SubRace

fun main() {
    println("Bem-vindo ao criador de fichas de D&D!")

    val atributos = mutableMapOf(
        "Força" to 10,
        "Constituição" to 10,
        "Destreza" to 10,
        "Sabedoria" to 10,
        "Inteligência" to 10,
        "Carisma" to 10
    )

    println("Escolha sua raça:")
    Race.values().forEachIndexed { index, race -> println("${index + 1}. ${race.name}") }
    val escolhaRaca = readLine()?.toIntOrNull() ?: 1
    val racaEscolhida = Race.values()[escolhaRaca - 1]

    racaEscolhida.bonuses.forEach { (atributo, bonus) ->
        atributos[atributo] = atributos[atributo]!! + bonus
    }

    val subRacaEscolhida: SubRace? = if (racaEscolhida.subRaces != null) {
        println("Escolha sua sub-raça:")
        racaEscolhida.subRaces.forEachIndexed { index, subRace -> println("${index + 1}. ${subRace.name}") }
        val escolhaSubRaca = readLine()?.toIntOrNull() ?: 1
        racaEscolhida.subRaces[escolhaSubRaca - 1]
    } else {
        null
    }

    subRacaEscolhida?.bonus?.forEach { (atributo, bonus) ->
        atributos[atributo] = atributos[atributo]!! + bonus
    }

    println("Escolha sua classe:")
    CharacterClass.values().forEachIndexed { index, characterClass -> println("${index + 1}. ${characterClass.name}") }
    val escolhaClasse = readLine()?.toIntOrNull() ?: 1
    val classeEscolhida = CharacterClass.values()[escolhaClasse - 1]

    classeEscolhida.bonus.forEach { (atributo, bonus) ->
        atributos[atributo] = atributos[atributo]!! + bonus
    }

    var pontosDisponiveis = 27
    while (pontosDisponiveis > 0) {
        println("Você tem $pontosDisponiveis pontos para distribuir.")
        println("Atributos atuais: $atributos")
        println("Escolha um atributo para aumentar:")
        atributos.keys.forEachIndexed { index, atributo -> println("${index + 1}. $atributo") }
        val escolhaAtributo = readLine()?.toIntOrNull() ?: 1
        val atributoEscolhido = atributos.keys.toList()[escolhaAtributo - 1]

        println("Quantos pontos deseja adicionar em $atributoEscolhido? (Máximo $pontosDisponiveis)")
        val pontos = readLine()?.toIntOrNull() ?: 0

        val novoValor = atributos[atributoEscolhido]!! + pontos
        if (pontos in 1..pontosDisponiveis && novoValor <= 20) {
            atributos[atributoEscolhido] = novoValor
            pontosDisponiveis -= pontos
        } else {
            if (novoValor > 20) {
                println("O atributo $atributoEscolhido não pode ultrapassar 20 pontos.")
            } else {
                println("Número inválido de pontos.")
            }
        }
    }



    println("Distribuição de pontos concluída.")
    println("Digite OK para confirmar ou N para reiniciar.")
    val confirmacao = readLine()
    if (confirmacao?.uppercase() == "N") {
        main()
        return
    }


    println("Digite o nome do seu personagem:")
    val nome = readLine() ?: "Personagem Sem Nome"
    println("Digite uma breve descrição do seu personagem:")
    val descricao = readLine() ?: "Sem descrição"


    println("Ficha do Personagem:")
    println("Nome: $nome")
    println("Raça: ${racaEscolhida.name} ${subRacaEscolhida?.name ?: ""}")
    println("Classe: ${classeEscolhida.name}")
    println("Atributos: $atributos")
    println("Descrição: $descricao")
}

