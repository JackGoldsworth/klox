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
        when(advance()) {
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
            '!' -> addToken(if(match('=')) TokenType.BANG_EQUAL else TokenType.BANG)
            '=' -> addToken(if(match('=')) TokenType.EQUAL_EQUAL else TokenType.EQUAL)
            '<' -> addToken(if(match('=')) TokenType.LESS_EQUAL else TokenType.EQUAL)
            '>' -> addToken(if(match('=')) TokenType.GREATER_EQUAL else TokenType.EQUAL)
            '/' -> if(match('/')) while(peek() != '\n' && !isEndOfFile()) advance() else addToken(TokenType.SLASH)
            else -> KLox.error(line, "Unexpected character.", "")
        }
    }

    private fun peek(): Char {
        if(this.isEndOfFile()) {
            return '\u0000'
        }
        return source[current]
    }

    private fun match(expected: Char): Boolean {
        if(isEndOfFile()) {
            return false
        }
        if(source[current] != expected) {
            return false
        }
        return true
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