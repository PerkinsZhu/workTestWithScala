package basetest

import org.junit.Test

/**
 * Created by PerkinsZhu on 2018/7/18 10:42
 **/
class BaseTestKotlin {
    fun main(args: Array<String>) {
        println("hello world! i am kotlin.")
    }

    @Test
    fun testNull() {
        var a: String = "abc"
        println(a)
        var b: String? = "abc"
        b = null // ok

        if (b != null && b.length > 0)
            println("String of length ${b.length}")
        else
            println("Empty string")
        println(b?.length)
        println(b?.length ?: 10)
        val l = b!!.length  //这里将会抛出异常
        println(l)

    }
}
