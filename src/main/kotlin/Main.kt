import com.sun.source.tree.ExpressionTree
import java.math.BigInteger


open class MathematicalSymbol(val value: String) : Cloneable {

    var expressionTreeSet: MutableSet<ExpressionTree> = mutableSetOf()

    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (other is MathematicalSymbol) {
            return value == other.value
        }
        return false
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun clone(): MathematicalSymbol {
        return MathematicalSymbol(value)
    }


    companion object {
        val PLUS = MathematicalSymbol("+")
        val MINUS = MathematicalSymbol("-")
        val MULTIPLY = MathematicalSymbol("*")
        val DIVIDE = MathematicalSymbol("/")
        val POWER = MathematicalSymbol("^")
        val OPEN_BRACKET = MathematicalSymbol("(")
        val CLOSE_BRACKET = MathematicalSymbol(")")
    }

    // parse string to mathematical symbol
    fun charToToken(char: Char): MathematicalSymbol {
        return when (char) {
            '+' -> PLUS
            '-' -> MINUS
            '*' -> MULTIPLY
            '/' -> DIVIDE
            '^' -> POWER
            '(' -> OPEN_BRACKET
            ')' -> CLOSE_BRACKET
            else -> throw IllegalArgumentException("Unknown token: $char")
        }
    }

    // parse string to expression tree
    fun parseExpression(expression: String): ExpressionTree {
        val tokens = expression.split(" ")
        val stack = mutableListOf<MathematicalSymbol>()
        for (token in tokens) {
            if (token.length == 1) {
                val char = token[0]
                if (char.isDigit()) {
                    stack.add(MathematicalSymbol(char.toString()))
                } else {
                    stack.add(charToToken(char))
                }
            } else {
                stack.add(MathematicalSymbol(token))
            }
        }
        return parseExpression(stack)
    }

    // parse mathematical symbol to expression tree
    fun parseExpression(tokens: MutableList<MathematicalSymbol>): ExpressionTree {
        val stack = mutableListOf<MathematicalSymbol>()
        for (token in tokens) {
            if (token == MathematicalSymbol.CLOSE_BRACKET) {
                val expression = mutableListOf<MathematicalSymbol>()
                while (stack.last() != MathematicalSymbol.OPEN_BRACKET) {
                    expression.add(stack.removeAt(stack.lastIndex))
                }
                stack.removeAt(stack.lastIndex)
                stack.add(parseExpression(expression))
            } else {
                stack.add(token)
            }
        }
        return parseExpression(stack, MathematicalSymbol.PLUS, MathematicalSymbol.MINUS)
    }

    // parse mathematical symbol to expression tree
    fun parseExpression(tokens: MutableList<MathematicalSymbol>, vararg operators: MathematicalSymbol): ExpressionTree {
        val stack = mutableListOf<MathematicalSymbol>()
        for (token in tokens) {
            if (token in operators) {
                val expression = mutableListOf<MathematicalSymbol>()
                while (stack.isNotEmpty() && stack.last() !in operators) {
                    expression.add(stack.removeAt(stack.lastIndex))
                }
                stack.add(parseExpression(expression, *operators))
            } else {
                stack.add(token)
            }
        }
        return parseExpression(stack, MathematicalSymbol.MULTIPLY, MathematicalSymbol.DIVIDE, MathematicalSymbol.POWER)
    }

    // parse mathematical symbol to expression tree
    fun parseExpression(tokens: MutableList<MathematicalSymbol>, vararg operators: MathematicalSymbol): ExpressionTree {
        val stack = mutableListOf<MathematicalSymbol>()
        for (token in tokens) {
            if (token in operators) {
                val expression = mutableListOf<MathematicalSymbol>()
                while (stack.isNotEmpty() && stack.last() !in operators) {
                    expression.add(stack.removeAt(stack.lastIndex))
                }
                stack.add(parseExpression(expression, *operators))
            } else {
                stack.add(token)
            }
        }
        return parseExpression(stack, MathematicalSymbol.MULTIPLY, MathematicalSymbol.DIVIDE, MathematicalSymbol.POWER)
    }
}


// Language: kotlin
// Path: src/main/kotlin/Main.kt
// Create a function: LEDigitPrinter
// Requirement 1: Accept a bigint as input
// Requirement 2: Print the digits of the bigint in Little Endian order
// End of requirements
fun leDigitPrinter(input: BigInteger) {
    println(input.toString().toCharArray().reversed().joinToString(""))
}

// Language: kotlin
// Path: src/main/kotlin/Main.kt
// Create a function: BEDigitPrinter
// Requirement 1: Accept a bigint as input
// Requirement 2: Print the digits of the bigint in Big Endian order
// End of requirements
fun beDigitPrinter(input: BigInteger) {
    println(input.toString().toCharArray().joinToString(""))
}


fun main(args: Array<String>) {

    // Variable myBigInt: BigInteger <- 1234567890
    val myBigInt = BigInteger("1234567890")

    // Call LEDigitPrinter with myBigInt as input
    leDigitPrinter(myBigInt)
    beDigitPrinter(myBigInt)

    // Print the bytecode of the function LEDigitPrinter


    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}
