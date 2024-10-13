import java.util.Scanner;
public class Automa10 {
    public static boolean scan(String s){
        int state = 0; 
        for(int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            switch(state){
                case 0:
                    if(ch == 'a' || ch == '*'){
                        state = 1;
                    }else if(ch == '/'){
                        state = 2;
                    }else{
                        state = -1;
                    }
                    break;

                case 1:
                    if(ch == 'a' || ch == '*'){
                        state = 1;
                    }else if(ch == '/'){
                        state = 2;
                    }else{
                        state = -1;
                    }
                    break;

                case 2:
                    if(ch == 'a'){
                        state = 1;
                    }else if(ch == '*'){
                        state = 3;
                    }else if(ch == '/'){
                        state = 2;
                    }else{
                        state = -1;
                    }
                    break;

                case 3:
                    if(ch == 'a' || ch == '/'){
                        state = 3;
                    }else if(ch == '*'){
                        state = 4;
                    }else{
                        state = -1;
                    }
                    break;

                case 4:
                    if(ch == 'a'){
                        state = 3;
                    }else if(ch == '/'){
                        state = 5;
                    }else if(ch == '*'){
                        state = 4;
                    }else{
                        state = -1;
                    }
                    break;

                case 5:
                    if(ch == 'a' || ch == '*'){
                        state = 5;
                    }else if(ch == '/'){
                        state = 6;
                    }else{
                        state = -1;
                    }
                    break;

                case 6:
                    if(ch == 'a'){
                        state = 5;
                    }else if(ch == '*'){
                        state = 3;
                    }else if(ch == '/'){
                        state = 6;
                    }else{
                        state = 1;
                    }
                    break;
            }
        }
        return state == 1 || state == 2 || state == 5 || state == 6;
    }
    public static void main(String [] args){
        Scanner tastiera= new Scanner  (System.in);
        
        System.out.print("Inserire la Stringa--> ");
        String s = tastiera.nextLine();
        System.out.println(scan(s) ? "OK!" : "NOPE!");
    }
}
