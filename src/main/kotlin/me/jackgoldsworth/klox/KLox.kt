package me.jackgoldsworth.klox

import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess

object KLox {

    var hadError: Boolean = false

    @JvmStatic
    fun main(args: Array<String>) {
        when {
            args.isEmpty() -> {
                println("Usage: klox [script]")
                exitProcess(64)
            }
            args.size == 1 -> {
                runFile(args[0])
            }
        }
    }

    private fun runFile(filePath: String) {
        val bytes = Files.readAllBytes(Paths.get(filePath))
        run(String(bytes, Charset.defaultCharset()))
        if(this.hadError) {
            exitProcess(65)
        }
    }

    private fun run(source: String) {
        val scanner = Scanner(source)
        val tokens = scanner.scanTokens()

        for (token in tokens) {
            println(token)
        }
    }

    @JvmStatic
    fun error(line: Int, message: String, where: String) {
        this.hadError = true
        System.err.println("[Line $line] Error $where: $message")
    }
}