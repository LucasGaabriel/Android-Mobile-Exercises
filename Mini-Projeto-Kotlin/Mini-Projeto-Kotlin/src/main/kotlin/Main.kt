package org.example

import kotlin.system.exitProcess

fun main(args: Array<String>) {

    if (args.size != 2) {
        println("Os diretórios de entrada e saída não foram passados!")
        exitProcess(1)
    }

    val dirEntrada = args[0]
    val dirSaida = args[1]

    val loja = Loja(dirEntrada, dirSaida)

    // 1º - Gerenciamento de Compra e Venda
    loja.gerenciarCompraEVenda()

    // 2º - Gerenciamento de Estoque
    loja.gerarEstoque()

    // 3º - Balancete
    loja.gerarBalancete()

    // 4º - Sistema de Busca Opcional (Só ocorre se na pasta de entrada existir o arquivo 'busca.csv')
    loja.gerarBusca()
}