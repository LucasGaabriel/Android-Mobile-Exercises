package org.example

enum class TipoColecionavel {
    LIVRO, BONECO, OUTROS
}

enum class MaterialFabricacao {
    PAPEL, PLASTICO, ACO, MISTURADO, OUTROS
}

enum class Relevancia {
    COMUM, MEDIO, RARO, RARISSIMO
}

class Colecionavel(
    nome: String,
    precoCompra: Double,
    precoVenda: Double,
    codigo: String,
    estoque: Int,
    val tipo: TipoColecionavel,
    val material: MaterialFabricacao,
    val tamanho: Int?,
    val relevancia: Relevancia
) : Produto(nome, precoCompra, precoVenda, codigo, estoque) {

    init {
        this.codigo = "C-${codigo.uppercase()}"
    }

}