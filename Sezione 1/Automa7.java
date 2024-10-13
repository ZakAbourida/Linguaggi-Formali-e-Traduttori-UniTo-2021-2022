import java.util.Scanner;

public class Automa7 {

    
    public static boolean scan(String t){
        String s = "Zakaria";
        int state = 0;
        int i = 0;
        
        if(s.length() != t.length())
            return false;
        
            
        
        while(state >= 0 && i < s.length()){
            char ch1 = s.charAt(i);
            char ch2 = t.charAt(i++);
            switch(state){
                case 0: if(ch2 == ch1)
                            state = 1;
                        else
                            state = 2;
                    break;
                
                case 1: if(ch2 != ch1)
                            state = 2;
                    break;
                
                case 2: if(ch2 != ch1)
                            state = -1;
                    break;
            }
        }
        return state == 2 || state == 1 ;
    }
    
    
    
    
    public static void main(String[] args) {
        Scanner tastiera = new Scanner (System.in);
        System.out.println("Stringa di base: Zakaria");
        System.out.print("Inserire Stringa da analizzare--> ");
        String t = tastiera.nextLine();
        System.out.println(scan(t) ? "OK!" : "NOPE: Errore nella stringa!!");   
    }
}