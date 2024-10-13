import java.io.*;
public class Parser {
    /*
     *  analizzatore sintattico che: 
     *      - controlla se la successione di token
            - generata dal lexer
            - rispetta la grammatica implementata.
     */
    private Lexer lex;                          
    private BufferedReader pbr;
    private Token look;
    public static String s;

    public Parser(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }
//=================================================================================
    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }
//=================================================================================
    void error(String s) {
        throw new Error("near line " + lex.line + ": " + s);
    }
//=================================================================================
    void match(int t) {         // verifica la corrispondenz tra il tag ed il simbolo corrente
        if (look.tag == t) {
            if (look.tag != Tag.EOF) move();
        } else error("syntax error");
    }
//=================================================================================
void prog(){
    statlist();
    match(Tag.EOF);
}
//=================================================================================
void statlist(){
    stat();
    statlistp();
}
//=================================================================================
void statlistp(){
    switch(look.tag){
        case ';':   match(';');
                    stat();
                    statlist();
                break;
        default:
            //CASO EPS
                break;
    }
}
//=================================================================================
/*caso degli identificatori simil-java, dove ogni case (tag) ha una
  differente procedura determinata dalla propria produzione           */
void stat(){
    switch(look.tag){
        case Tag.ASSIGN:    match(Tag.ASSIGN);
                            expr();
                            match(Tag.TO);
                            idlist();
                        break;
        case Tag.PRINT:     match(Tag.PRINT);
                            match('(');
                            exprlist();
                            match(')');
                        break;
                        
        case Tag.READ:      match(Tag.READ);
                            match('(');
                            idlist();
                            match(')');
                        break;
                        
        case Tag.WHILE:     match(Tag.WHILE);
                            match('(');
                            bexpr();
                            match(')');
                            stat();
                        break;
                        
        case Tag.IF:    match(Tag.IF);
                        match('(');
                        bexpr();
                        match(')');
                        stat();
                        statIF();
                    break;
        
        case '{':   match('{');
                    statlist();
                    match('}');
                break;
    }
}
//=================================================================================
//metodo aggiuntivo per risoluzione ambiguità tramite fattorizzazione
void statIF(){
    switch(look.tag){
        case Tag.END:   match(Tag.END);
                    break;
        case Tag.ELSE:  match(Tag.ELSE);
                        stat();
                        match(Tag.END);
                    break;
    }
}
//=================================================================================
void idlist(){
    match(Tag.ID);
    idlistp();
}
//=================================================================================
void idlistp(){
    switch(look.tag){
        case ',':   match(',');
                    match(Tag.ID);
                    idlistp();
                break;
        default:
                //CASO EPS
                break;
    }
}
//=================================================================================
void bexpr(){
    match(Tag.RELOP);
    expr();
    expr();
}
//=================================================================================
void expr(){
    switch(look.tag){
        case '+':   match('+');
                    match('(');
                    exprlist();
                    match(')');
                break;
        case '-':   match('-');
                    expr();
                    expr();
                break;
        case '*':   match('*');
                    match('(');
                    exprlist();
                    match(')');
                break;
        case '/':   match('/');
                    expr();
                    expr();
                break;
        case Tag.NUM:   match(Tag.NUM);
                    break;
        case Tag.ID:    match(Tag.ID);
                    break;
    }
}
//=================================================================================
void exprlist(){
    expr();
    exprlistp();
}
//=================================================================================
void exprlistp(){
    switch(look.tag){
        case ',':   match(',');
                    expr();
                    exprlistp();
                break;
        default:
            //CASO EPS  
                break;
    }
}
//=================================================================================
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "ProvaParser.lft";
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.prog();
            System.out.println("Input OK!!");
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}