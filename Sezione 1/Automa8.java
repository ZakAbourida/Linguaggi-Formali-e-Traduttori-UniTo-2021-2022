import java.util.Scanner;
public class Automa8 {
    public static boolean scan(String s){
        int state = 0;
        for(int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            switch(state){
                case 0:
                    if(Character.isDigit(ch)){
                        state = 1;
                    }else if(ch == '+' || ch == '-'){
                        state = 2;
                    }else if(ch == '.'){
                        state = 3;
                    }else{
                        state = -1;
                    }
                    break;

                case 1:
                    if(Character.isDigit(ch)){
                        state = 1;
                    }else if(ch == 'e'){
                        state = 5;
                    }else if(ch == '.'){
                        state = 3;
                    }else{
                        state = -1;
                    }
                    break;

                case 2:
                    if(Character.isDigit(ch)){
                        state = 1;
                    }else if(ch == '.'){
                        state = 3;
                    }else{
                        state = -1;
                    }
                    break;

                case 3:
                    if(Character.isDigit(ch)){
                        state = 4;
                    }else{
                        state = -1;
                    }
                    break;

                case 4:
                    if(Character.isDigit(ch)){
                        state = 4;
                    }else if(ch == 'e'){
                        state = 5;
                    }else{
                        state = -1;
                    }
                    break;

                case 5:
                    if(Character.isDigit(ch)){
                        state = 7;
                    }else if(ch == '+' || ch == '-'){
                        state = 6;
                    }else{
                        state = -1;
                    }
                    break;

                case 6:
                    if(Character.isDigit(ch)){
                        state = 7;
                    }else{
                        state = -1;
                    }
                    break;

                case 7:
                    if(Character.isDigit(ch)){
                        state = 7;
                    }else if(ch == '.'){
                        state = 8;
                    }else{
                        state = -1;
                    }
                    break;

                case 8:
                    if(Character.isDigit(ch)){
                        state = 9;
                    }else{
                        state = -1;
                    }
                    break;

                case 9:
                    if(Character.isDigit(ch)){
                        state = 9;
                    }else{
                        state = -1;
                    }
                    break;
            }
        }
        return state == 1 || state == 4 || state == 7 || state == 9;
    }
    public static void main(String [] args){
        Scanner tastiera= new Scanner  (System.in);
        
        System.out.print("Inserire la Stringa--> ");
        String s = tastiera.nextLine();
        System.out.println(scan(s) ? "OK!" : "NOPE!");
    }
}
