public enum OpCode {    // enumerazione di tutte le istruzioni
    ldc, imul, ineg, idiv, iadd, dup, pop,//aggiunto il metodo pop per gestire l'assegnazione multipla
    isub, istore, ior, iand, iload,
    if_icmpeq, if_icmple, if_icmplt, if_icmpne, if_icmpge, 
    if_icmpgt, ifne, GOto, invokestatic, label 
}