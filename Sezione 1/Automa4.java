import java.util.Scanner;
public class Automa4 {
    
    public static boolean scan(String s){

        int state = 0;

        for(int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);

            switch(state){
                case 0:
                    if(ch == ' '){
                        state = 0;
                    }else if(Character.isDigit(ch)){
                        if(ch % 2 == 0)
                            state = 2;
                        else 
                            state = 1;
                    }else 
                        state = -1;
                    break;

                case 1:
                    if(ch == ' '){
                        state = 3;
                    }else if(Character.isDigit(ch)){
                        if(ch % 2 == 0){
                            state = 2;
                        }else{
                            state = 1;
                        }
                    }else if(Character.isLetter(ch)){
                        if(ch >= 'L' && ch <= 'Z')
                            state = 5;
                    }else{
                        state = -1;
                    }
                    break;

                case 2:
                    if(ch == ' '){
                        state = 4;
                    }else if(Character.isDigit(ch)){
                        if(ch % 2 == 0){
                            state = 2;
                        }else{
                            state = 1;
                        }
                    }else if(Character.isLetter(ch)){
                        if(ch >= 'A' && ch <= 'K')
                            state = 5;
                    }else{
                        state = -1;
                    }
                    break;

                case 3:
                    if(ch == ' '){
                        state = 3;
                    }else if(Character.isLetter(ch)){
                        if(ch >= 'L' && ch <= 'Z'){
                            state = 5;
                        }
                    }
                    else{
                        state = -1;
                    }
                    break;

                case 4:
                    if(ch == ' '){
                        state = 4;
                    }else if(Character.isLetter(ch)){
                        if(ch >= 'A' && ch <= 'K'){
                            state = 5;
                        }
                    }
                    else{
                        state = -1;
                    }
                    break;

                case 5:
                    if(ch == ' '){
                        state = 6;
                    }else if(ch >= 'a' && ch <= 'z'){
                        state = 5;
                    }
                    else{
                        state = -1;
                    }
                    break;

                case 6:
                    if(ch == ' '){
                        state = 6;
                    }else if(ch >= 'A' && ch <= 'Z'){
                        state = 5;
                    }
                    else{
                        state = -1;
                    }
                    break;
            }
        }
        return state == 6 || state == 5;
    }
    public static void main(String [] args){
        Scanner tastiera= new Scanner  (System.in);
        
        System.out.print("Inserire la Stringa--> ");
        String s = tastiera.nextLine();
        System.out.println(scan(s) ? "OK!" : "NOPE!");
    }
}
