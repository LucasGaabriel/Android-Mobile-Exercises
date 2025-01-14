package org.example

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.readCSV
import org.jetbrains.kotlinx.dataframe.io.writeCSV
import java.io.BufferedWriter
import java.io.FileNotFoundException
import java.io.FileWriter
import java.util.*
import kotlin.system.exitProcess

class Loja(private val dirEntrada: String, private val dirSaida: String) {
    private val produtos = mutableListOf<Produto>()
    private var compras: Double = 0.0
    private var vendas: Double = 0.0

    fun gerenciarCompraEVenda() {
        salvarCompras()
        salvarVendas()
    }

    private fun salvarCompras() {
        val df = try {
            DataFrame.readCSV(
                "${this.dirEntrada}/compras.csv",
                parserOptions = ParserOptions(locale = Locale.US)
            )
        } catch (e: FileNotFoundException) {
            println("Arquivo \"compras.csv\" não encontrado em ${this.dirEntrada}.")
            exitProcess(1)
        }

        for (row in df.rows()) {
            val categoria = row["Categoria"] as String
            val nome = row["Nome do produto"] as String
            val precoCompra = row["Preço de compra"] as Double
            val precoVenda = row["Preço de venda"] as Double
            val codigo = row["Código"] as String
            val estoque = row["Quantidade"] as Int

            when (categoria) {
                "colecionavel" -> {

                    val tipoColecionavel = TipoColecionavel.valueOf(row["Tipo"].toString().uppercase())
                    val materialFabricacao = MaterialFabricacao.valueOf(
                        row["Material de fabricação"].toString().uppercase()
                    )
                    val tamanho: Int? = try {
                        row["Tamanho"].toString().toInt()
                    } catch (e: NumberFormatException) {
                        null
                    }
                    val relevancia = Relevancia.valueOf(row["Relevância"].toString().uppercase())

                    val colecionavel = Colecionavel(
                        nome,
                        precoCompra,
                        precoVenda,
                        codigo,
                        estoque,
                        tipoColecionavel,
                        materialFabricacao,
                        tamanho,
                        relevancia,
                    )

                    produtos.add(colecionavel)
                }

                "eletronico" -> {

                    val tipoEletronico = TipoEletronico.valueOf(
                        row["Tipo"].toString().replace("-", "").uppercase()
                    )
                    val versao = row["Versão"].toString().toInt()
                    val anoFabricacao = row["Ano de fabricação"].toString().toInt()

                    val eletronico = Eletronico(
                        nome, precoCompra, precoVenda, codigo, estoque, tipoEletronico, versao, anoFabricacao
                    )

                    produtos.add(eletronico)
                }

                "roupa" -> {

                    val tipoRoupa = TipoRoupa.valueOf(row["Tipo"].toString().uppercase())
                    val tamanhoRoupa = TamanhoRoupa.valueOf(row["Tamanho"].toString().uppercase())
                    val corPrimaria = row["Cor primaria"].toString()
                    val corSecundaria = row["Cor secundário"].toString()

                    val roupa = Roupa(
                        nome,
                        precoCompra,
                        precoVenda,
                        codigo,
                        estoque,
                        tipoRoupa,
                        tamanhoRoupa,
                        corPrimaria,
                        corSecundaria
                    )

                    produtos.add(roupa)
                }
            }

            this.compras += precoCompra * estoque
        }
    }

    private fun salvarVendas() {
        val df = try {
            DataFrame.readCSV("${this.dirEntrada}/vendas.csv")
        } catch (e: FileNotFoundException) {
            println("Arquivo \"vendas.csv\" não encontrado em ${this.dirEntrada}.")
            exitProcess(1)
        }

        for (row in df.rows()) {
            val codigo = row["Código"] as String
            val quantidade = row["Quantidade"] as Int

            val produto: Produto? = produtos.find { it.codigo == codigo }

            if (produto != null) {
                this.vendas += produto.precoVenda * quantidade
                produto.estoque -= quantidade
            }
        }
    }

    fun gerarEstoque() {
        gerarEstoqueGeral()
        gerarEstoqueCategorias()
    }

    private fun gerarEstoqueGeral() {
        var df = this.produtos.toDataFrame()

        df = df.select("codigo", "nome", "estoque")
        df = df.rename("estoque").into("quantidade")

        for (colum in df.columnNames()) {
            df = df.rename(colum).into(colum.uppercase())
        }

        try {
            df.writeCSV("${this.dirSaida}/estoque_geral.csv")
        } catch (e: FileNotFoundException) {
            println("Não foi possível salvar o arquivo \"estoque_geral.csv\" em ${this.dirSaida}.")
            exitProcess(1)
        }
    }

    private fun gerarEstoqueCategorias() {
        var roupas = 0
        var colecionaveis = 0
        var eletronicos = 0

        for (produto in this.produtos) {
            when (produto) {
                is Roupa -> roupas += produto.estoque
                is Colecionavel -> colecionaveis += produto.estoque
                is Eletronico -> eletronicos += produto.estoque
                else -> throw Error("(!) Tipo do Produto inesperado ao gerar Estoque separado por categorias.")
            }
        }

        val writer = try {
            BufferedWriter(FileWriter("${this.dirSaida}/estoque_categorias.csv"))
        } catch (e: FileNotFoundException) {
            println("Não foi possível salvar o arquivo \"estoque_categorias.csv\" em ${this.dirSaida}.")
            exitProcess(1)
        }

        val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT)

        csvPrinter.printRecord("CATEGORIA", "QUANTIDADE")
        csvPrinter.printRecord("ROUPA", roupas)
        csvPrinter.printRecord("COLECIONAVEL", colecionaveis)
        csvPrinter.printRecord("ELETRONICO", eletronicos)

        csvPrinter.close()
    }

    fun gerarBalancete() {
        val writer = try {
            BufferedWriter(FileWriter(this.dirSaida + "/balancete.csv"))
        } catch (e: FileNotFoundException) {
            println("Não foi possível salvar o arquivo \"balancete.csv\" em ${this.dirSaida}.")
            exitProcess(1)
        }

        val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT)

        csvPrinter.printRecord("COMPRAS", this.compras)
        csvPrinter.printRecord("VENDAS", this.vendas)
        csvPrinter.printRecord("BALANCETE", this.vendas - this.compras)

        csvPrinter.close()
    }

    fun gerarBusca() {
        val df = try {
            DataFrame.readCSV(
                "${this.dirEntrada}/busca.csv",
                parserOptions = ParserOptions(locale = Locale.US)
            )
        } catch (e: FileNotFoundException) {
            return
        }

        val buscaResultados = mutableListOf<Pair<Int, Int>>()

        for ((index, row) in df.rows().withIndex()) {
            val buscaAtual = row.toMap().filterValues { it != "-" } // Ignora os valores "-"
            var quantidadeEncontrada = 0

            for (produto in produtos) {
                var atendeCriterios = true

                // Verifica cada critério da busca com as propriedades do produto
                for ((coluna, valorBusca) in buscaAtual) {
                    when (coluna) {
                        "Categoria" -> {
                            val categoria = when (produto) {
                                is Colecionavel -> "colecionavel"
                                is Eletronico -> "eletronico"
                                is Roupa -> "roupa"
                                else -> throw Error("(!) Tipo do Produto inesperado.")
                            }
                            if (categoria != valorBusca) {
                                atendeCriterios = false
                                break
                            }
                        }

                        "Tipo" -> {
                            val tipoProduto = when (produto) {
                                is Colecionavel -> produto.tipo.toString().lowercase()
                                is Eletronico -> produto.tipo.toString().lowercase()
                                is Roupa -> produto.tipo.toString().lowercase()
                                else -> throw Error("(!) Tipo do Produto inesperado.")
                            }
                            if (tipoProduto != valorBusca.toString().replace("-", "")) {
                                atendeCriterios = false
                                break
                            }
                        }

                        "Tamanho" -> {
                            if (produto is Roupa && produto.tamanho.toString() != valorBusca) {
                                atendeCriterios = false
                                break
                            } else if (produto is Colecionavel && produto.tamanho?.toString() != valorBusca) {
                                atendeCriterios = false
                                break
                            }
                        }

                        "Cor primaria" -> {
                            if (produto is Roupa && produto.corPrimaria != valorBusca) {
                                atendeCriterios = false
                                break
                            }
                        }

                        "Cor secundário" -> {
                            if (produto is Roupa && produto.corSecundaria != valorBusca) {
                                atendeCriterios = false
                                break
                            }
                        }

                        "Versão" -> {
                            if (produto is Eletronico && produto.versao.toString() != valorBusca) {
                                atendeCriterios = false
                                break
                            }
                        }

                        "Ano de fabricação" -> {
                            if (produto is Eletronico && produto.anoFabricacao.toString() != valorBusca) {
                                atendeCriterios = false
                                break
                            }
                        }

                        "Material de fabricação" -> {
                            if (produto is Colecionavel && produto.material.toString() != valorBusca) {
                                atendeCriterios = false
                                break
                            }
                        }

                        "Relevância" -> {
                            if (produto is Colecionavel && produto.relevancia.toString() != valorBusca) {
                                atendeCriterios = false
                                break
                            }
                        }
                    }
                }

                if (atendeCriterios) {
                    quantidadeEncontrada += produto.estoque
                }
            }

            buscaResultados.add(Pair(index + 1, quantidadeEncontrada))
        }

        val writer = try {
            BufferedWriter(FileWriter("${this.dirSaida}/resultado_busca.csv"))
        } catch (e: FileNotFoundException) {
            println("Não foi possível salvar o arquivo \"resultado_busca.csv\" em ${this.dirSaida}.")
            exitProcess(1)
        }
        val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT)

        csvPrinter.printRecord("BUSCAS", "QUANTIDADE")
        for ((buscaIndex, quantidade) in buscaResultados) {
            csvPrinter.printRecord(buscaIndex, quantidade)
        }

        csvPrinter.close()
    }
}