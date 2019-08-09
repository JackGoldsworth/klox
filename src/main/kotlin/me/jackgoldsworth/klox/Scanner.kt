package me.jackgoldsworth.klox

class Scanner(val source: String) {

    private val tokens = mutableListOf<Token>()

    private var start = 0
    private var current = 0
    private var line = 1

    fun scanTokens(): List<Token> {
        while (!isEndOfFile()) {
            start = current;
            scanToken()
        }
        tokens.add(Token(TokenType.EOF, "", null, line))
        return tokens
    }

    private fun scanToken() {
        val char = advance()
        when(char) {
            '(' -> addToken(TokenType.LEFT_PAREN)
            ')' -> addToken(TokenType.RIGHT_PAREN)
            '{' -> addToken(TokenType.LEFT_BRACE)
            '}' -> addToken(TokenType.RIGHT_BRACE)
            ',' -> addToken(TokenType.COMMA)
            '.' -> addToken(TokenType.DOT)
            '-' -> addToken(TokenType.MINUS)
            '+' -> addToken(TokenType.PLUS)
            ';' -> addToken(TokenType.SEMICOLON)
            '*' -> addToken(TokenType.STAR)
            else -> KLox.error(line, "Unexpected character.", "")
        }
    }

    private fun advance(): Char {
        current++
        return source[current - 1]
    }

    private fun addToken(type: TokenType) {
        addToken(type, null)
    }

    private fun addToken(type: TokenType, literal: Any?) {
        val text = source.substring(start, current)
        tokens.add(Token(type, text, literal ?: "", line))
    }

    private fun isEndOfFile(): Boolean {
        return current >= source.length
    }
}