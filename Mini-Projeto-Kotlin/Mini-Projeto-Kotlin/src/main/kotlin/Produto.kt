package org.example

open class Produto(
    var nome: String,
    val precoCompra: Double,
    val precoVenda: Double,
    var codigo: String,
    var estoque: Int = 0
) {
    init {
        this.nome = this.nome.uppercase()
    }
}