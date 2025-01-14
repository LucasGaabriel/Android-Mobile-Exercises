package org.example

enum class TipoRoupa {
    CAMISA, MOLETON, ACESSORIO
}

enum class TamanhoRoupa {
    PP, P, M, G, GG, XG, XXG
}

class Roupa(
    nome: String,
    precoCompra: Double,
    precoVenda: Double,
    codigo: String,
    estoque: Int,
    val tipo: TipoRoupa,
    val tamanho: TamanhoRoupa,
    val corPrimaria: String,
    val corSecundaria: String
) : Produto(nome, precoCompra, precoVenda, codigo, estoque) {

    init {
        this.codigo = "R-${codigo.uppercase()}"
    }

}