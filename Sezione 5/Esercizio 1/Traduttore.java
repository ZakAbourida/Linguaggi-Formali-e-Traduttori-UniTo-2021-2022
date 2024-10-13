
// javac Translator.java
// java Translator
// java -jar jasmin.jar Output.j
// java Output
import java.io.*;

public class Traduttore {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count = 0;

    public Traduttore(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }
//=======================================================================================================
    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }
//=======================================================================================================
    void error(String s) {
        throw new Error("near line " + lex.line + ": " + s);
    }
//=======================================================================================================
    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF)
                move();
        } else
            error("syntax error");
    }
//=======================================================================================================
    public void prog() {
        statlist();
        match(Tag.EOF);
        try {
            code.toJasmin();
        } catch (java.io.IOException e) {
            System.out.println("IO error\n");
        }
    }
//=======================================================================================================
    private void statlist() {
        stat();
        statlistp();
    }
//=======================================================================================================
    private void statlistp() {
        switch (look.tag) {
            case ';':
                match(';');
                stat();
                statlist();
                break;

            case Tag.EOF:
                break;
        }
    }
//=======================================================================================================
    private void stat() {
        switch (look.tag) {
            case Tag.ASSIGN: 
                match(Tag.ASSIGN);
                expr();
                match(Tag.TO);
                idlist(Tag.ASSIGN);
                code.emit(OpCode.pop);  // per come abbiamo implementato assign,
                break;                  // duplichiamo una volta in piu' la cima, quindi in fine facciamo una pop

            case Tag.PRINT:
                match(Tag.PRINT);
                match('(');
                exprlist(OpCode.invokestatic);
                match(')');
                break;

            case Tag.READ:
                match(Tag.READ);
                match('(');
                idlist(Tag.READ);
                match(')');
                break;

            case Tag.WHILE: {
                match(Tag.WHILE);
                match('(');
                // crea le label
                int wtrue = code.newLabel();
                int wfalse = code.newLabel();
                int wstart = code.newLabel();
                code.emitLabel(wstart); // label viene emessa nel codice Output.j 
                bexpr(wtrue, wfalse);
                match(')');
                code.emitLabel(wtrue);
                stat();
                code.emit(OpCode.GOto, wstart);
                code.emitLabel(wfalse); // label uscita dal ciclo
                break;
            }

            case Tag.IF: {
                match(Tag.IF);
                match('(');
                int ifTrue = code.newLabel();
                int ifEnd = code.newLabel();
                int ifElse = code.newLabel();
                bexpr(ifTrue, ifElse);
                match(')');
                code.emitLabel(ifTrue);
                stat();
                code.emit(OpCode.GOto, ifEnd);
                doubleif(ifEnd, ifElse);
                break;
            }

            case '{':
                match('{');
                statlist();
                match('}');
                break;

        }
    }
//=======================================================================================================
    private void doubleif(int endlabel, int elselabel) {    // produzione che rende la grammatica LL(1), non fattorizzabile
        switch (look.tag) {
            case Tag.END:
                code.emitLabel(endlabel);
                match(Tag.END);
                break;

            case Tag.ELSE:
                code.emitLabel(elselabel);
                match(Tag.ELSE);
                stat();
                code.emit(OpCode.GOto, endlabel);
                match(Tag.END);
                code.emitLabel(endlabel);
        }
    }
//=======================================================================================================
    private void idlist(int i) {                                        // usiamo i per distinguere read da assign
        if (look.tag == Tag.ID) {                                       // controlla se una variabile esiste nella Symbol Table
            int id_addr = st.lookupAddress(((Word) look).lexeme);       // indirizzo di Tag.ID
            if (id_addr == -1){
                id_addr = count;
                st.insert(((Word) look).lexeme, count++);               // altrimenti la inserisce
            }
            match(Tag.ID);
            
            if (Tag.READ == i) {
                code.emit(OpCode.invokestatic, 0);
                code.emit(OpCode.istore, id_addr); //
            }
    
            if (Tag.ASSIGN == i) {                                      //assign multiplo
                code.emit(OpCode.dup);                                  // caso in cui ci sono piu variabili a cui assegnare un valore
                code.emit(OpCode.istore, id_addr);                      // nel caso di assegnazione singola viene fatto il pop dallo stack del duplicato
            }
            idlistp(i);
        }
    }
//=======================================================================================================
    private void idlistp(int i) {   
        switch (look.tag) {
            case ',':
                match(',');
                idlist(i);
                break;
            default:
                break;
        }

    }
//=======================================================================================================
    private void bexpr(int bexprtrue, int bexprnext) {                          // boolean expression
        switch (look.tag) {
            case Tag.RELOP: {
                String rel = ((Word) look).lexeme;                              //sfruttiamo la struttura Word che ha la lexeme di tipo Sstring
                match(Tag.RELOP);
                expr();
                expr();
                switch (rel) {                                                  // switch sugli op. relazionali
                    case ">":
                        code.emit(OpCode.if_icmpgt, bexprtrue);                 // per ogni rel viene emesso opcode
                        break;
                    case "<":
                        code.emit(OpCode.if_icmplt, bexprtrue);
                        break;
                    case "==":
                        code.emit(OpCode.if_icmpeq, bexprtrue);
                        break;
                    case ">=":
                        code.emit(OpCode.if_icmpge, bexprtrue);
                        break;
                    case "<=":
                        code.emit(OpCode.if_icmple, bexprtrue);
                        break;
                    case "<>":
                        code.emit(OpCode.if_icmpne, bexprtrue);
                        break;
                }
            }
                code.emit(OpCode.GOto, bexprnext); 
                break;    
        
           
        }
    }
//=======================================================================================================
    private void expr() {
        switch (look.tag) {
            case '+':
                match('+');
                match('(');
                exprlist(OpCode.iadd);
                match(')');
                break;

            case '-':
                match('-');
                expr();
                expr();
                code.emit(OpCode.isub);
                break;
                
            case '/':
                match('/');
                expr();
                expr();
                code.emit(OpCode.idiv);
                break;

            case '*':
                match('*');
                match('(');
                exprlist(OpCode.imul);
                match(')');
                break;

            case Tag.NUM:
                code.emit(OpCode.ldc, ((NumberTok) look).n);
                match(Tag.NUM);
                break;

            case Tag.ID:
                if(st.lookupAddress(((Word) look).lexeme)==-1)
                    error("Identificatore non inizializzato");
                code.emit(OpCode.iload, st.lookupAddress(((Word) look).lexeme));
                match(Tag.ID);
                break;
        }
    }
//=======================================================================================================
    private void exprlist(OpCode opcode) {                                              // print risultati espressioni
        switch (look.tag) {
            case '+':
                expr();
                if (opcode == OpCode.invokestatic)                                  // opcode == print-read
                    code.emit(OpCode.invokestatic, 1);
                exprlistp(opcode);
                break;

            case '-':
                expr();
                if (opcode == OpCode.invokestatic)
                    code.emit(OpCode.invokestatic, 1);
                exprlistp(opcode);
                break;

            case '*':
                expr();
                if (opcode == OpCode.invokestatic)
                    code.emit(OpCode.invokestatic, 1);
                exprlistp(opcode);
                break;

            case '/':
                expr();
                if (opcode == OpCode.invokestatic)
                    code.emit(OpCode.invokestatic, 1);
                exprlistp(opcode);
                break;

            case Tag.NUM:
                expr();
                if (opcode == OpCode.invokestatic)
                    code.emit(OpCode.invokestatic, 1);
                exprlistp(opcode);
                break;

            case Tag.ID:
                expr();
                if (opcode == OpCode.invokestatic)
                    code.emit(OpCode.invokestatic, 1);
                exprlistp(opcode);
                break;
                
            default:
                error("exprlist error");
                break;
        }
    }
//=======================================================================================================
    public void exprlistp(OpCode opcode) {
        switch (look.tag) {
            case ',':
                match(',');
                expr();
                if (opcode == OpCode.invokestatic)
                    code.emit(OpCode.invokestatic, 1);
                else
                    code.emit(opcode);
                exprlistp(opcode);
                break;

            default:
                break;
        }
    }
//=======================================================================================================
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "Input.lft";
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            Traduttore trad = new Traduttore(lex, br);
            trad.prog();
            System.out.println("Input OK");
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
