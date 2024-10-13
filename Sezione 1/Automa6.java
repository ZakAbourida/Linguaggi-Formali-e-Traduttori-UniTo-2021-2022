import java.util.Scanner;
public class Automa6 {
    public static boolean scan(String s){   // VERSIONE UNICO STATO FINALE
        int state = 0;
        for(int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            switch(state){
                case 0:
                    if(Character.isDigit(ch)){
                        if(ch % 2 == 0){
                            state = 2;
                        }else{
                            state = 1;
                        }
                    }else{
                        state = -1;
                    }
                    break;

                case 1:
                    if(Character.isDigit(ch)){
                        if(ch % 2 == 0){
                            state = 4;
                        }else{
                            state = 3;
                        }
                    }else{
                        state = -1;
                    }
                    break;

                case 2:
                    if(Character.isDigit(ch)){
                        if(ch % 2 == 0){
                            state = 5;
                        }else{
                            state = 6;
                        }
                    }else{
                        state = -1;
                    }
                    break;

                case 3:
                    if(Character.isDigit(ch)){
                        if(ch % 2 == 0){
                            state = 4;
                        }else{
                            state = 3;
                        }
                    }else if(ch >= 'L' && ch <= 'Z'){
                        state = 7;
                    }else{
                        state = -1;
                    }
                    break;

                case 4:
                    if(Character.isDigit(ch)){
                        if(ch % 2 == 0){
                            state = 5;
                        }else{
                            state = 6;
                        }
                    }else if(ch >= 'L' && ch <= 'Z'){
                        state = 7;
                    }else{
                        state = -1;
                    }
                    break;

                case 5:
                    if(Character.isDigit(ch)){
                        if(ch % 2 == 0){
                            state = 5;
                        }else{
                            state = 6;
                        }
                    }else if(ch >= 'A' && ch <= 'K'){
                        state = 7;
                    }else{
                        state = -1;
                    }
                    break;

                case 6:
                    if(Character.isDigit(ch)){
                        if(ch % 2 == 0){
                            state = 4;
                        }else{
                            state = 3;
                        }
                    }else if(ch >= 'A' && ch <= 'K'){
                        state = 7;
                    }else{
                        state = -1;
                    }
                    break;

                case 7:
                    if(Character.isLetter(ch)){
                        state = 7;
                    }else{
                        state = -1;
                    }
                    break;
            }
        }
        return state == 7;
    }
    public static void main(String[] args) {
        Scanner tastiera= new Scanner  (System.in);
        
        System.out.print("Inserire la Stringa--> ");
        String s = tastiera.nextLine();
        System.out.println(scan(s) ? "OK!" : "NOPE!");
    }
}
