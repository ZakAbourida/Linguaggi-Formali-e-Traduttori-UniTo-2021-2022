import java.io.*; 
public class Lexer {
    /**
     *  analizzatore lessicale che genera i token appartenenti ad una determinata sintassi
        ogni token corrisponde ad un frammento della sintassi java
    */ 

    public static int line = 1;
    private char peek = ' '; // carattere corrente

    //metodo per scorrere la stringa in input
    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1;                           // ERROR
        }
    }

    // Scanner linea / carattere
    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r'  || peek == '/' ) {
            if (peek == '/') {                                                              //primo carrattere '/'
                readch(br);                                                                 //prende il secondo carattere
                if (peek == '*') {                                                          //controllo se il nuovo carattere è '*'
                    readch(br);
                    boolean closed = true;      
                    boolean star = false;                                            //INIZIO COMMENTO
                    while (closed && peek != (char) -1) {                                       //finchè non c'è la chiusura commenti, non tokenizzerà più niente
                        if (peek == '*')
                            star = true;
                        if(peek == '/' && star)
                            closed = false;
                        if(peek == '\n')
                            line++;
                        readch(br);
                    }
                    if (closed == true) {
                        System.out.println("Il commento è stato chiuso in maniera errata.");
                        return null;
                    }
                    return lexical_scan(br);
                }else if (peek == '/'){
                    while (peek != '\n' && peek != (char) -1){
                        readch(br);
                    }
                }else
                    return Token.div;
            }
           
        if (peek == '\n')
            line++;
        readch(br);
    }
        
        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;

            case '(':
                peek = ' ';
                return Token.lpt;

            case ')':
                peek = ' ';
                return Token.rpt;

            case '{':
                peek = ' ';
                return Token.lpg;

            case '}':
                peek = ' ';
                return Token.rpg;

            case '+':
                peek = ' ';
                return Token.plus;

            case '-':
                peek = ' ';
                return Token.minus;

            case '*':
                peek = ' ';
                return Token.mult;

            case '/':
                peek = ' ';
                return Token.div;
                
            case ';':
                peek = ' ';
                return Token.semicolon;

            case ',':
                peek = ' ';
                return Token.comma;
	
            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character"
                            + " after & : "  + peek );
                    return null;
                }
                
            case '|':
                readch(br);
                if(peek == '|'){
                    peek = ' ';
                    return Word.or;
                }else{
                    System.err.println("Erroneous character"
                            + " after & : "  + peek );
                    return null;
                }

            case '<':
                readch(br);
                switch(peek){
                    case '>':
                        peek = ' ';
                        return Word.ne;
                    
                    case '=':
                        peek = ' ';
                        return Word.le;

                    default:
                        peek = ' ';
                        return Word.lt;
                }

            case '>':
                readch(br);
                switch(peek){
                    case '=':
                        peek = ' ';
                        return Word.ge;

                    default:
                        peek = ' ';
                        return Word.gt;
                }

            case '=':
                readch(br);
                if(peek == '='){
                    peek = ' ';
                    return Word.eq;
                }else{
                    System.err.println("Erroneous character" + " after & : "  + peek );
                    return null;
                }
         
            case (char)-1:
                return new Token(Tag.EOF);

            default:
                if (Character.isLetter(peek) || peek == '_') {
                    String s = "";
                    while(Character.isLetter(peek) || peek == '_' || Character.isDigit(peek)){ 
                        s = s + peek;
                        readch(br);
                    }
                    
                    /*
                     * confrontiamo s con le word della sintassi, 
                     * se c'e' corrispondenza:      return Word, 
                     *                              altrimenti crea un nuovo ID
                     */

                    if(s.compareTo("assign") == 0){
                        return Word.assign;
                    }else if(s.compareTo("to") == 0){
                        return Word.to;
                    }else if(s.compareTo("if") == 0){
                        return Word.iftok;
                    }else if(s.compareTo("else") == 0){
                        return Word.elsetok;
                    }else if(s.compareTo("while") == 0){
                        return Word.whiletok;
                    }else if(s.compareTo("begin") == 0){
                        return Word.begin;
                    }else if(s.compareTo("end") == 0){
                        return Word.end;
                    }else if(s.compareTo("print") == 0){
                        return Word.print;
                    }else if(s.compareTo("read") == 0){
                        return Word.read;
                    }else{
                        return new Word(Tag.ID, s);
                    }
                 
                } else if (Character.isDigit(peek)) {
                    /*
                     *  Bisogna convertire char in int:
                            -uso n per ottenere il valore corrente (0 == 48 in ascii)
                            -moltiplico il totale x10 e aggiungo la cifra n
                            -leggo next char fin quando ci sono cifre da leggere
                     */
                    int sum = 0;
                    while(Character.isDigit(peek) && !Character.isLetter(peek) && peek != '_'){
                        int n = (int) peek - 48;
                        sum = sum*10 + n;
                        readch(br);
                    }
                    
                    return new NumberTok(Tag.NUM, sum); 

                } else {
                        System.err.println("Erroneous character: " 
                                + peek );
                        return null;
                }
         }
    }
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "prova.lft";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            }while(tok.tag != Tag.EOF);
                br.close();
        }catch(IOException e){
            e.printStackTrace();
        }    
    }

}