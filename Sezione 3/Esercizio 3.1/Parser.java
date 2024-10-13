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
    public void start() {
        expr();
        match(Tag.EOF);
    }
//=================================================================================
    private void expr() {
        term();
        exprp();
    }
//=================================================================================
    private void exprp() {  
        switch (look.tag) {
            case '+':
                match('+');
                term();
                exprp();
                break; 

            case'-':
                match('-');
                term();
                exprp();
                break; 

            default:    //caso eps
                break;
        }
    }
//=================================================================================
    private void term() {
        fact();
        termp();
    }
//=================================================================================
    private void termp() {
        switch (look.tag) {
            case '*':
                match('*');
                fact();
                termp();
                break;

            case '/':
                match('/');
                fact();
                termp();
                break;

            default:    //caso eps
                break;    
        }
    }
//=================================================================================
    private void fact() {
		switch(look.tag){
            case Tag.NUM:   match(Tag.NUM);
                        break;
            case Tag.ID:    match(Tag.ID);
                        break;
            case '(':       match('(');
                            expr();
                            match(')');
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
            parser.start();
            System.out.println("Input OK!!");
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
    

