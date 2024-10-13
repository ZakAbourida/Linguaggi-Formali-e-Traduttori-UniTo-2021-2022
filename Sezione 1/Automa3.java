import java.util.Scanner;
public class Automa3 {
    
    public static boolean scan(String s){

        int state = 0;
        
        for(int i = 0; i < s.length(); i++){

            char ch = s.charAt(i);

            switch(state){
                case 0:
                    if(Character.isDigit(ch)){
                        if(ch % 2 == 0)
                            state = 2;
                        else 
                            state = 1;
                    }else 
                        state = -1;
                    break;

                case 1:
                    if(Character.isDigit(ch)){
                        if(ch % 2 == 0)
                            state = 2;
                        else 
                            state = 1;
                    }else if(Character.isLetter(ch)){
                        if(ch >= 'L' && ch <= 'Z')
                            state = 3;
                    }
                    else
                        state = -1;
                    break;

                case 2:
                    if(Character.isDigit(ch)){
                        if(ch % 2 == 0)
                            state = 2;
                        else 
                            state = 1;
                    }else if(Character.isLetter(ch)){
                        if(ch >= 'A' && ch <= 'K')
                            state = 3;
                    }
                    else
                        state = -1;
                    break;

                case 3:
                    if(Character.isLetter(ch))
                        state = 3;
                    else 
                        state = -1;
                    break;

            }
        }
        return state == 3;
    }

    public static void main(String [] args){
        Scanner tastiera= new Scanner  (System.in);
        
        System.out.print("Inserire la Stringa--> ");
        String s = tastiera.nextLine();
        System.out.println(scan(s) ? "OK!" : "NOPE!");
    }
}
