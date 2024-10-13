import java.util.*;

public class SymbolTable {

     Map <String, Integer> OffsetMap = new HashMap <String,Integer>(); //struttura a tabella dove vengono salvate le variabili e i corripettivi valori

	public void insert( String s, int address ) {//inserisce nuova variabile nella tabella 
            if( !OffsetMap.containsValue(address) ) 
                OffsetMap.put(s,address);
            else 
                throw new IllegalArgumentException("Reference to a memory location already occupied by another variable");
	}

	public int lookupAddress ( String s ) { //controlla l'esistenza di una variabile nella tabella 
            if( OffsetMap.containsKey(s) )  //ritorna il suo index nella tabella,altrimenti -1 se non è stata inserita 
                return OffsetMap.get(s);
            else
                return -1;
	}
}