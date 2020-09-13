fun getBracketList(bracketMask: String): MutableList<Pair<Char, Char>> {
    val bracketList = mutableListOf<Pair<Char, Char>>()
    if (bracketMask[0] == '1') {
        bracketList.add(Pair('(', ')'))
    }
    if (bracketMask[1] == '1') {
        bracketList.add(Pair('[', ']'))
    }
    if (bracketMask[2] == '1') {
        bracketList.add(Pair('{', '}'))
    }
    return bracketList
}

fun <T> rollList(l: List<T>): List<T> {
    return l.takeLast(1) + l.subList(0, l.size - 1)
}

fun solveRec(s: String, needMiddle: Boolean, needEdges: Boolean, bracketList: List<Pair<Char, Char>>): String {
    val (lbr, rbr) = bracketList[0]
    if (s.isEmpty()){
        return if (needMiddle) { "$lbr$rbr" } else { "" }
    }
    if (s.length == 1){
        return if (needEdges) { "$lbr$s$rbr" } else { s }
    }

    val inner = s[0] +
            solveRec(s.substring(1, s.length - 1), needMiddle, true, rollList(bracketList)) +
            s.takeLast(1)
    return if (needEdges) { "$lbr$inner$rbr" } else { inner }
}


/*
User should enter "true" after "Need middle: " or "Need edges: " if the option is needed or any other string otherwise.
After "Bracket mask '([{': " should go three symbols: '1' if you want corresponding bracket in resulting string and
any other symbol otherwise. There should be at least one '1'.
Finally, user should provide the string itself after "String: ".

Example:
Input:
Need middle: true
Need edges: false
Bracket mask '([{': 101
String: abcdefgh
Output: a{b(c{d()e}f)g}h

Attention!
User does not have explicit control over the order of brackets, although it's guaranteed that brackets appear cyclically.
 */

fun main() {
    print("Need middle: ")
    val needMiddle = readLine()!!.toBoolean()
    print("Need edges: ")
    val needEdges = readLine()!!.toBoolean()
    print("Bracket mask '([{': ")
    val bracketsMask = readLine()!!
    if (!bracketsMask.contains('1')) {throw Exception("Need at least one bracket type")}
    print("String: ")
    val s = readLine()!!
    println(solveRec(s, needMiddle, needEdges, getBracketList(bracketsMask)))
}