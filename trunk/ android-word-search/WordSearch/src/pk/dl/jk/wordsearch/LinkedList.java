package pk.dl.jk.wordsearch;

public class LinkedList {
	private WordNode list;
    /**
     * Constructor for objects of class LinkedList
     */
    public LinkedList()
    {
        list = null;
    }
    
    public boolean isEmpty()
    {
        if(list != null){
            return false;
        }else
        return true;
    }
   
   //Adds a String[] to the list
   public void add(String[] s, String l)
   {

      WordNode node = new WordNode(s,l);
      WordNode current;

      if (list == null){
         list = node;
      }else{
         current = list;
         while(current.next != null){
            current = current.next;
        }
         //Sets the new words index to one more than the last index
         node.setIndex(current.getIndex() + 1);
         current.next = node;
      }
   }
   
   //Removes a String[] from the list specified by its name
   public void delete(String[] wn)
   {
       String[] wordName = wn;
       WordNode temp;
       WordNode previous;
       WordNode current;
       int deleteIndex;  //stores the index of the WordNode thats going to be deleted
       
       temp = list;
       previous = list;
       
       //finds the WordNode with the same name
       while(!(temp.word == wordName)){
            temp = temp.next;    
       }
       
       //gets the index of the String[] thats going to be deleted
       deleteIndex = temp.getIndex();
            
            if(deleteIndex != 0){
                    
                //finds the WordNode located before the one thats going to be deleted
                while(previous.getIndex() != deleteIndex-1){
                    previous = previous.next;    
                }
                
                //sets the previous WordNodes next to the second next and sets the word thats going to be deleted to null
                previous.next = temp.next;
                temp = null;
                current = previous;
       
                    //Recalculates the new indexes of the list
                    while(current.next != null){
                        int lastIndex = current.getIndex();
                        current = current.next;
                        current.setIndex(lastIndex + 1);
                    }
                    
          //if the word thats to be deleted is the first one then it is removed from the list         
          }else if(deleteIndex == 0){
              list = list.next;
              
              if(list != null){
                  list.setIndex(0);
                  current = list;
              
                    //Recalculates the new indexes of the list
                         while(current.next != null){
                             int lastIndex = current.getIndex();
                             current = current.next;
                             current.setIndex(lastIndex + 1);
                            }
              }
          }
   }
   
   //Used to determine if the WordNode that the user selected is the list
   public boolean Search(String[] target)
   {
        boolean found = false;
        WordNode temp;
        
        temp = list;
    
        while(temp != null)
        {
            if(temp.word == target)
            {
                found = true;
            }
            temp = temp.next;
        }
     
        return found;
   }
   
   //Returns all the String[]s in the list
   public String toString ()
   {
      String result = "";  
             
      WordNode current = list;

      while (current != null)
      {
        for(int i = 0; i < current.word.length; i++){
             
         result +=  " " + '\n' + " " + current.word[i] ;
         
       }
         result += " " + '\n' +" " + current.getLetter()+ '\n';
         current = current.next;
      }

      return result;
   }
   
   public WordNode allTheOtherKids(int value)
   {
       WordNode result = null;
       //Converts value to the last index of the String array that we are searching for
       int convert = value - 1;
       
       WordNode current = list;
       
       while(current != null)
       {
           if(value != current.word.length){
               if(current.word[convert] != null && current.word[value] == null){
                    result = new WordNode(current.word, current.getLetter());
                    current = null;
                }
            }else if(value == current.word.length){
                if(current.word[convert] != null){
                    result = new WordNode(current.word, current.getLetter());
                    current = null;
                }
            }
            if(current != null){
            current = current.next;
            }
       }
       
       if(result != null){ 
           delete(result.word);
       }   
                            
       return result;
   }
}
