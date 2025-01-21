package com.lucascosta.catsanddogs.repository

import kotlin.random.Random

class CuriositiesRepository {
    // Classe para simular o acesso a uma API ou acesso a um Banco de Dados
    object Curiosities {
        val catCuriosities: Array<String> = arrayOf(
            "Os gatos passam cerca de 70% do tempo dormindo.",
            "Podem girar as orelhas em 180°.",
            "Ronronam para se acalmar, além de expressar felicidade.",
            "Seu nariz tem um padrão único, como uma impressão digital.",
            "Convivem com humanos há mais de 9.000 anos.",
            "Não sentem o gosto doce.",
            "Podem saltar até seis vezes o comprimento do próprio corpo.",
            "O cérebro dos gatos é 90% semelhante ao dos humanos.",
            "Se esfregam nas pessoas para marcar território.",
            "Têm bigodes sensíveis que os ajudam a medir espaços."
        )

        val dogCuriosities: Array<String> = arrayOf(
            "O faro deles pode ser até 100.000 vezes mais potente que o humano.",
            "São capazes de entender até 250 palavras e gestos.",
            "Os cães suam apenas pelas patas.",
            "O focinho molhado ajuda a captar cheiros melhor.",
            "O DNA dos cães é 99,9% semelhante ao dos lobos.",
            "Alguns cães podem detectar doenças, como câncer e diabetes.",
            "Latem em diferentes tons para expressar emoções.",
            "Os filhotes nascem surdos e cegos.",
            "Cada cão tem uma pegada única no nariz.",
            "Abanam o rabo para mostrar emoções diferentes."
        )
    }

    fun getCatCuriosity(): String {
        return Curiosities.catCuriosities[Random.nextInt(10)]
    }

    fun getDogCuriosity(): String {
        return Curiosities.dogCuriosities[Random.nextInt(10)]
    }
}