import java.util.Scanner;
public class Automa2 {
    
    public static boolean scan(String s){
        
        int state = 0;

        for (int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);

            switch(state){

                case 0:
                    if(Character.isLetter(ch))
                        state = 1;
                    else if(ch == '_')
                        state = 2;
                    else 
                        state = -1;
                    break;

                case 1:
                    if(Character.isDigit(ch) || Character.isLetter(ch) || ch == '_')
                        state = 1;
                    else 
                        state = -1;
                    break;

                case 2:
                    if(ch == '_')
                        state = 2;
                    else if(Character.isDigit(ch) || Character.isLetter(ch))
                        state = 1;
                    else 
                        state = -1;
                    break;      
            }
        }
        return state == 1;
    }

    public static void main (String [] args){
        Scanner tastiera= new Scanner  (System.in);
        
        System.out.print("Inserire la Stringa--> ");
        String s = tastiera.nextLine();
        System.out.println(scan(s) ? "OK!" : "NOPE!");
    }
}