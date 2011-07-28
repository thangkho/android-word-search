package pk.dl.jk.wordsearch;

//An inner class that represents a node in the String[] list.
//  The public variables are accessed by the String[]List class.
public class WordNode {
	
       public String[] word;
       private String letter;
       public WordNode next;
       public int index;

       //--------------------------------------------------------------
       //  Sets up the node
       //--------------------------------------------------------------
       public WordNode (String[] c, String l)
       {
          word = c;
          setLetter(l);
          next = null;
          setIndex(0);
       }
       
       public void setIndex(int i)
       {
           index = i;
       }
       
       public int getIndex()
       {
           return index;
       }

     public void setLetter(String l) {
         letter = l;
     }
          
     public String getLetter() {
         return letter;
     }
}
