package org.example

enum class TipoEletronico {
    VIDEOGAME, JOGO, PORTATIL, OUTROS
}

class Eletronico(
    nome: String,
    precoCompra: Double,
    precoVenda: Double,
    codigo: String,
    estoque: Int,
    val tipo: TipoEletronico,
    val versao: Int,
    val anoFabricacao: Int
) : Produto(nome, precoCompra, precoVenda, codigo, estoque) {

    init {
        this.codigo = "E-${codigo.uppercase()}"
    }

}