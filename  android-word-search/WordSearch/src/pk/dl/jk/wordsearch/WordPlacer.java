package pk.dl.jk.wordsearch;
import java.util.*;
import java.lang.String;

import android.util.Log;
/**
 * Tests the Methods of the Linked List class
 * 
 * @author Donnell LeBlanc
 * @version 7/18/11
 */

public class WordPlacer {
	public Random rand = new Random();
	private final int NORTH = 1;
	private final int WEST = 2;
	private final int EAST = 3;
	private final int SOUTH = 4;
	private final int NORTHWEST = 5;
	private final int NORTHEAST = 6;
	private final int SOUTHWEST = 7;
	private final int SOUTHEAST = 8;
	private String[] alreadyUsed = new String[35];
    //Use determines how many times we should try and attempt to place a group of words into the grid
	private int use = -1;
	private boolean failed = false;
    
    
    public boolean getNotIntheGrid(){
    	return failed;
    }
    
    public void setBackFalse(){
    	failed = false;
    }

    //Fills the grid with random characters
    public void addRandomChars(String[][] myGrid){
    	 String[] alpha = { "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","a","e","i","o","u" };
 
         for(int i = 0; i < 10; i++){
             for(int k = 0; k < 10; k++){
                 if(myGrid[i][k].equalsIgnoreCase("*")){
                     int num = rand.nextInt(31);
                     myGrid[i][k] = alpha[num];
                 }
             }
         }
    }
    
    
    /**Places words read from a text file into a link list that stores String arrays */
    //public LinkedList placeInList(String[] wordArray, int level)
    public LinkedList placeInList(Object[] aWordArray, int level)

    {
    	String[] wordArray = new String[aWordArray.length];
    	for(int i = 0; i < aWordArray.length; i++){
    		
    		wordArray[i] = (String)aWordArray[i];
    		Log.e("IN WORD PLACER FOR LOOP", " CONVERTING TO STRING " + (String)aWordArray[i]);
    	}
        LinkedList connectedWords = new LinkedList();
        String[] pairs;
        String let = null;        //the letter that the words have in common, also stored in linked list
        int c = 0;
        boolean finished = false;
        
        
            //depending on the level it determines how many words can be connected to each other
            switch(level){
            
                 case 1:
               
                 //Loops through each word in the String array read from the text file
                 for(int i = 0; i < wordArray.length; i++)
                 {
                     //if the level is Easy, up to four words can be connected
                     pairs = new String[4];
                     int  count = 0;
                     
                     
                        //This adds the four words that connect to a String array
                        while( c < 3 && finished == false){
                             
                                 if(wordArray[i] != null){
                                     
                                     //The word that is being compared to other words
                                     String alikeWord = wordArray[i].toLowerCase();
                                     boolean foundOne = false;
                                         
                                         //Loops through each letter in the word
                                         //The first time it finds a word that matches it does this
                                         while(count < alikeWord.length() && foundOne == false){
                                             //This is the character from the word that will be checked in the other words
                                             Character firstChar = (Character)alikeWord.charAt(count);
                                             String newChar = firstChar.toString();
                    
                                             //Loops through each of the remaining words in the list and adds words that contain the same character to the String array
                                            int newCount = i+1;
                                            boolean stopIt = false;
                                            while(newCount < wordArray.length && stopIt == false){
                                                 if(wordArray[newCount] != null && c != 2){
                                                      if(wordArray[newCount].toLowerCase().contains(newChar))
                                                      {
                                                          //Puts the next two words into list
                                                          pairs[0] = wordArray[i];
                                                          pairs[1] = wordArray[newCount];
                                                          let = newChar;
                                                          wordArray[newCount] = null;     //so that words aren't repeated in the linked list, this is set to null
                                                          c = 2;
                                                          foundOne = true;
                                                          stopIt = true;
                                                      }
                                                 }
                                             newCount++;
                                             }
                                             count++;
                                         }
                                         
                                         
                          
                                         //The rest of the times it does this
                                         if(foundOne == true){
                                             //This is the character from the word that will be checked in the other words
                                             String newChar = let;
                    
                                             //Loops through each of the remaining words in the list and adds words that contain the same character to the String array
                                             int newCount = i+1;
                                             boolean stopIt = false;
                                             while(newCount < wordArray.length && stopIt == false){
                                                 if(wordArray[newCount] != null && c <=3){
                                                      if(wordArray[newCount].toLowerCase().contains(newChar))
                                                      {
                                                          //Puts the next word into list
                                                          pairs[c] = wordArray[newCount];
                                                          wordArray[newCount] = null;     //so that words aren't repeated in the linked list, this is set to null
                                                          c++;
                                                          if(c == 4){
                                                              stopIt = true;
                                                             }
                                                      }
                                                 }
                                              newCount++;
                                             }
                                         }
                                       
                                         //If there are no matching words in the array then it just adds the word to the list
                                         if(foundOne == false){
                                             pairs[0] = wordArray[i];
                                         }                           
                                 }   
                                 
              
                             //if there are less than four words in the list that contain the same letter it ends the while loop
                             int checker = 0;
                             while(checker < pairs.length){
                                if(pairs[checker] == null ){
                                    finished = true;
                                }
                                checker++;
                             } 
                        }
                        //End of first while loop//
                        
                        
                     //Adds the connected words to the linked list
                     if(pairs[0] != null){
                         connectedWords.add(pairs, let);
                     }
                     finished = false;
                     //let = null;
                     //Sets the value of the count for the pairs of words back to zero
                     c = 0;  
                 }
                         
             
            break;
            
            case 2:
              
                  //Loops through each word in the String array read from the text file
                 for(int i = 0; i < wordArray.length; i++)
                 {
                     //if the level is Medium, up to three words can be connected
                     pairs = new String[3];
                     int  count = 0;
                     
                     
                        //This adds the three words that connect to a String array
                        while( c < 2 && finished == false){
                             
                                 if(wordArray[i] != null){
                                     
                                     //The word that is being compared to other words
                                     String alikeWord = wordArray[i].toLowerCase();
                                     boolean foundOne = false;
                                         
                                         //Loops through each letter in the word
                                         //The first time it finds a word that matches it does this
                                         while(count < alikeWord.length() && foundOne == false){
                                             //This is the character from the word that will be checked in the other words
                                             Character firstChar = (Character)alikeWord.charAt(count);
                                             String newChar = firstChar.toString();
                    
                                             //Loops through each of the remaining words in the list and adds words that contain the same character to the String array
                                            int newCount = i+1;
                                            boolean stopIt = false;
                                            while(newCount < wordArray.length && stopIt == false){
                                                 if(wordArray[newCount] != null && c != 2){
                                                      if(wordArray[newCount].toLowerCase().contains(newChar))
                                                      {
                                                          //Puts the next two words into list
                                                          pairs[0] = wordArray[i];
                                                          pairs[1] = wordArray[newCount];
                                                          let = newChar;
                                                          wordArray[newCount] = null;     //so that words aren't repeated in the linked list, this is set to null
                                                          c = 2;
                                                          foundOne = true;
                                                          stopIt = true;
                                                      }
                                                 }
                                             newCount++;
                                             }
                                             count++;
                                         }
                                         
                                         
                          
                                         //The rest of the times it does this
                                         if(foundOne == true){
                                             //This is the character from the word that will be checked in the other words
                                             String newChar = let;
                    
                                             //Loops through each of the remaining words in the list and adds words that contain the same character to the String array
                                             int newCount = i+1;
                                             boolean stopIt = false;
                                             while(newCount < wordArray.length && stopIt == false){
                                                 if(wordArray[newCount] != null){
                                                      if(wordArray[newCount].toLowerCase().contains(newChar))
                                                      {
                                                          //Puts the next word into list
                                                          pairs[c] = wordArray[newCount];
                                                          wordArray[newCount] = null;     //so that words aren't repeated in the linked list, this is set to null
                                                          stopIt = true; 
                                                      }
                                                 }
                                              newCount++;
                                             }
                                         }
                                       
                                         //If there are no matching words in the array then it just adds the word to the list
                                         if(foundOne == false){
                                             pairs[0] = wordArray[i];
                                         }                           
                                 }   
                                 
              
                             //if there are less than four words in the list that contain the same letter it ends the while loop
                             int checker = 0;
                             while(checker < pairs.length){
                                if(pairs[checker] == null ){
                                    finished = true;
                                }
                                checker++;
                             } 
                        }
                        //End of first while loop//
                        
                        
                     //Adds the connected words to the linked list
                     if(pairs[0] != null){
                         connectedWords.add(pairs, let);
                     }
                     finished = false;
                     //let = null;
                     //Sets the value of the count for the pairs of words back to zero
                     c = 0;  
                 }
                         
            break;
         
            case 3:
            
                  //Loops through each word in the String array read from the text file
                 for(int i = 0; i < wordArray.length; i++)
                 {
                     //if the level is Easy, up to four words can be connected
                     pairs = new String[2];
                     int  count = 0;
                     
                     
                        //This adds the four words that connect to a String array
                        while( c < 1 && finished == false){
                             
                                 if(wordArray[i] != null){
                                     
                                     //The word that is being compared to other words
                                     String alikeWord = wordArray[i].toLowerCase();
                                     boolean foundOne = false;
                                         
                                         //Loops through each letter in the word
                                         //The first time it finds a word that matches it does this
                                         while(count < alikeWord.length() && foundOne == false){
                                             //This is the character from the word that will be checked in the other words
                                             Character firstChar = (Character)alikeWord.charAt(count);
                                             String newChar = firstChar.toString();
                    
                                             //Loops through each of the remaining words in the list and adds words that contain the same character to the String array
                                            int newCount = i+1;
                                            boolean stopIt = false;
                                            while(newCount < wordArray.length && stopIt == false){
                                                 if(wordArray[newCount] != null && c != 2){
                                                      if(wordArray[newCount].toLowerCase().contains(newChar))
                                                      {
                                                          //Puts the next two words into list
                                                          pairs[0] = wordArray[i];
                                                          pairs[1] = wordArray[newCount];
                                                          let = newChar;
                                                          wordArray[newCount] = null;     //so that words aren't repeated in the linked list, this is set to null
                                                          c = 2;
                                                          foundOne = true;
                                                          stopIt = true;
                                                      }
                                                 }
                                             newCount++;
                                             }
                                             count++;
                                         }
                                         
                                       
                                         //If there are no matching words in the array then it just adds the word to the list
                                         if(foundOne == false){
                                             pairs[0] = wordArray[i];
                                         }                           
                                 }   
                                 
              
                             //if there are less than four words in the list that contain the same letter it ends the while loop
                             int checker = 0;
                             while(checker < pairs.length){
                                if(pairs[checker] == null ){
                                    finished = true;
                                }
                                checker++;
                             } 
                        }
                        //End of first while loop//
                        
                        
                     //Adds the connected words to the linked list
                     if(pairs[0] != null){
                         connectedWords.add(pairs, let);
                     }
                     finished = false;
                     //let = null;
                     //Sets the value of the count for the pairs of words back to zero
                     c = 0;  
                 }
                         
            break;
            
           }
        //End of Switch statement //   
       
        return connectedWords;
     }
    
    
    
//Places the words in the grid    
public void placeInGrid(String[] words, String let, String[][] grid, int newX, int newY, int level, boolean inUse){
        
        int xPos;
        int yPos;
        
        if(inUse == false){ //Base Case
            xPos = newX;
            yPos = newY;
            int dir;
            boolean fits = true;
            
            
            try{
            switch(level){
                
                case 1:
                    dir = rand.nextInt(2) + 1;
                    
                        switch(dir){
                
                            case NORTH:
                            
                                //if the first word can be placed in the grid, then begin loop
                                if((words[0].length() - 1 + yPos) <= 9){
                                    int count = 0;
                                    int count2 = 0;
                                    int count3 = 0;
                                    int count4 = 0;
                                    int location = 0;
                                    if(let != null){
                                    char temp = let.charAt(0);
                                    
                                    //the location of where the matching character is
                                    location = words[0].indexOf(temp);
                                    }
                                    
                                    //Checks to see if the first location is occupied
                                    //If the location is occupied, but the same character is supposed to be placed there then it continues
                                    //with the loop and places the word anyway
                                    //dir = NORTH
                                    while(count < words[0].length() && fits == true){
                                        
                                        if(!(grid[xPos][yPos + count].equalsIgnoreCase("*")) && 
                                                !(grid[xPos][yPos + count].equalsIgnoreCase(Character.toString(words[0].charAt(count))))){
                                            
                                                fits = false;
                                        }
                                        
                                        count++;
                                    }
                                    
                                    
                                    //if there is a second word connected, dir = WEST
                                    if(words[1] != null){
                                        char temp2 = let.charAt(0);
                                        //where the letter is located in the second word
                                        int location2 = words[1].indexOf(temp2);
                                        
                                        //checks to see if the second word fits in the grid
                                        if((xPos - location2) >= 0 && (xPos + ((words[1].length()-1) - location2)) <= 9){
                                                 while(count2 < words[1].length() && fits == true){
                                                     location2 = words[1].indexOf(temp2) - count2;
                                            
                                                     if(!(grid[xPos - location2][yPos + location].equalsIgnoreCase("*")) &&
                                                             !(grid[xPos - location2][yPos + location].equalsIgnoreCase(Character.toString(words[1].charAt(count2))))){
                                                            
                                                                    fits = false;
                                                     }

                                                        count2++;
                                                    }
                                        }else{
                                            fits = false;
                                        }
                                    }
                                    
                                    
                                    //if there is a third word connected, dir = NORTHWEST
                                    if(words[2] != null){
                                        char temp2 = let.charAt(0);
                                        //where the letter is located in the third word
                                        int location2 = words[2].indexOf(temp2);
                                        
                                        //checks to see if the third word fits in the grid
                                        if((xPos - location2) >= 0 && (xPos + ((words[2].length()-1) - location2)) <= 9 && (yPos - location2) >= 0 && (yPos + ((words[2].length()-1) - location2)) <= 9){
                                                while(count3 < words[2].length() && fits == true){
                                                    location2 = words[2].indexOf(temp2) - count3;

                                                    if(!(grid[xPos - location2][yPos + location - location2].equalsIgnoreCase("*")) &&
                                                            !(grid[xPos - location2][yPos + location - location2].equalsIgnoreCase(Character.toString(words[2].charAt(count3))))){
                                                            
                                                                fits = false;
                                                    }   
                                                    
                                                    count3++;
                                                }
                                        }else{
                                            fits = false;
                                        }
                                    }
                                    
                                    
                                    //if there is a fourth word connected, dir = NORTHEAST
                                    if(words[3] != null){
                                        char temp2 = let.charAt(0);
                                        //where the letter is located in the fourth word
                                        int location2 = words[3].indexOf(temp2);
                                        
                                        //checks to see if the fouth word fits in the grid
                                        if((xPos + location + location2) <= 9 && (xPos + location - ((words[3].length() - 1) - location2)) >= 0 
                                                && (yPos + ((words[3].length() - 1) - location2)) <= 9 && (yPos - location2) >= 0){
                                                while(count4 < words[3].length() && fits == true){
                                                    location2 = words[3].indexOf(temp2) - count4;
                                                
                                                    
                                                    if(!(grid[xPos + location2][yPos + location - location2].equalsIgnoreCase("*")) &&
                                                            !(grid[xPos + location2][yPos + location - location2].equalsIgnoreCase(Character.toString(words[3].charAt(count4))))){
                                                            
                                                                    fits = false;
                                                    }       
                                                   
                                                    count4++;
                                                }
                                        }else{
                                            fits = false;
                                        }
                                    }
                                    
                                    
                                    
                                    //if the words actually fits then put them into the grid
                                    if(fits == true){
                                        //Resets the counts
                                        count = 0;
                                        count2 = 0;
                                        count3 = 0;
                                        count4 = 0;
                                        
                                            //Puts the first word in, dir = NORTH
                                            while(count < words[0].length()){
                                                grid[xPos][yPos + count] = Character.toString(words[0].charAt(count));
                                                count++;
                                            }
                                             
                                            //If there's a second word, it puts that one in, dir = WEST
                                            if(words[1] != null){
                                                char temp2 = let.charAt(0);
                                                //where the letter is located in the second word
                                                int location2 = words[1].indexOf(temp2);
                                                
                                                while(count2 < words[1].length()){
                                                    location2 = words[1].indexOf(temp2) - count2;
                                                    
                                                    grid[xPos - location2][yPos + location] = Character.toString(words[1].charAt(count2));
                                                    
                                                    count2++;
                                                }
                                            }
                                            
                                            
                                            //If there's a third word, it puts that one in, dir = NORTHWEST 
                                            if(words[2] != null){
                                                char temp2 = let.charAt(0);
                                                //where the letter is located in the third word
                                                int location2 = words[2].indexOf(temp2);
                                                
                                                while(count3 < words[2].length()){
                                                    location2 = words[2].indexOf(temp2) - count3;
                                                    
                                                    grid[xPos - location2][yPos + location - location2] = Character.toString(words[2].charAt(count3));
                                                
                                                    count3++;
                                                }
                                            }
                                            
                                            //If there's a fourth word, it puts that one in, dir = NORTHEAST 
                                            if(words[3] != null){
                                                char temp2 = let.charAt(0);
                                                //where the letter is located in the fourth word
                                                int location2 = words[3].indexOf(temp2);
                                                
                                                while(count4 < words[3].length()){
                                                    location2 = words[3].indexOf(temp2) - count4;
                                                    
                                                    grid[xPos + location2][yPos + location - location2] = Character.toString(words[3].charAt(count4));
                                                
                                                    count4++;
                                                }
                                            }
                                            
                                            
                                            //Emptys the alreadyUsed array so the next list of words can be put in to the placeInGrid() method
                                            use = -1;
                                            alreadyUsed = null;
                                            alreadyUsed = new String[35];
                                    }
                                    //End of the loop that puts the individual characters from each word into the grid
                                    
                                    
                                    //If any of the locations are occupied, then a new location is choosen
                                    if(fits == false){
                                        placeInGrid(words, let, grid, xPos, yPos, level, true); 
                                    }
                                   
                                    
                                }else{
                                    
                                    placeInGrid(words, let, grid, xPos, yPos, level, true); 
                                }
                                //End of the first if, where it checks to see if it can fit in the grid///
                            break;
                
                            
                            case WEST:
                            
                                //if the first word can be placed in the grid, then begin loop
                                if((words[0].length() - 1 + xPos) <= 9){
                                    int count = 0;
                                    int count2 = 0;
                                    int count3 = 0;
                                    int count4 = 0;
                                    int location = 0;
                                    if(let != null){
                                    char temp = let.charAt(0);
                                    
                                    //the location of where the matching character is
                                    location = words[0].indexOf(temp);
                                    }
                                    
                                    //Checks to see if the first location is occupied
                                    //If the location is occupied, but the same character is supposed to be placed there then it continues
                                    //with the loop and places the word anyway
                                    //dir = WEST
                                    while(count < words[0].length() && fits == true){
                                        
                                        if(!(grid[xPos + count][yPos].equalsIgnoreCase("*")) &&
                                            !(grid[xPos + count][yPos].equalsIgnoreCase(Character.toString(words[0].charAt(count))))){
                                               
                                                fits = false;               
                                        }
                                        
                                        count++;
                                    }
                                    
                                    
                                    //if there is a second word connected, dir = NORTH
                                    if(words[1] != null){
                                        char temp2 = let.charAt(0);;
                                        //where the letter is located in the second word
                                        int location2 = words[1].indexOf(temp2);
                                        
                                        //checks to see if the second word fits in the grid
                                        if((yPos - location2) >= 0 && (yPos + ((words[1].length()-1) - location2)) <= 9){
                                                 while(count2 < words[1].length() && fits == true){
                                                     location2 = words[1].indexOf(temp2) - count2;
                                            
                                                     if(!(grid[xPos + location][yPos - location2].equalsIgnoreCase("*")) &&
                                                             !(grid[xPos + location][yPos - location2].equalsIgnoreCase(Character.toString(words[1].charAt(count2))))){
                                                             
                                                                    fits = false;
                                                     } 
                                       
                                                     count2++;
                                                 }       
                                        }else{
                                            fits = false;
                                        }
                                    }
                                    
                                    
                                    //if there is a third word connected, dir = NORTHWEST
                                    if(words[2] != null){
                                        char temp2 = let.charAt(0);
                                        //where the letter is located in the third word
                                        int location2 = words[2].indexOf(temp2);
                                        
                                        //checks to see if the third word fits in the grid
                                        if((xPos - location2) >= 0 && (xPos + ((words[2].length()-1) - location2)) <= 9 && (yPos - location2) >= 0 && (yPos + ((words[2].length()-1) - location2)) <= 9){
                                                while(count3 < words[2].length() && fits == true){
                                                    location2 = words[2].indexOf(temp2) - count3;
                                                     
                                                    if(!(grid[xPos - location2][yPos + location - location2].equalsIgnoreCase("*")) &&
                                                            !(grid[xPos - location2][yPos + location - location2].equalsIgnoreCase(Character.toString(words[2].charAt(count3))))){
                                                            
                                                                    fits = false;
                                                    }

                                                    count3++;
                                                }
                                        }else{
                                            fits = false;
                                        }
                                    }
                                    
                                    
                                    //if there is a fourth word connected, dir = NORTHEAST
                                    if(words[3] != null){
                                        char temp2 = let.charAt(0);
                                        //where the letter is located in the fourth word
                                        int location2 = words[3].indexOf(temp2);
                                        
                                        //checks to see if the fouth word fits in the grid
                                        if((xPos + location + location2) <= 9 && (xPos + location - ((words[3].length() - 1) - location2)) >= 0 
                                                && (yPos + ((words[3].length() - 1) - location2)) <= 9 && (yPos - location2) >= 0){
                                                while(count4 < words[3].length() && fits == true){
                                                    location2 = words[3].indexOf(temp2) - count4;
                                                
                                                    if(!(grid[xPos + location2][yPos + location - location2].equalsIgnoreCase("*")) &&
                                                            !(grid[xPos + location2][yPos + location - location2].equalsIgnoreCase(Character.toString(words[3].charAt(count4))))){
                                                                
                                                            fits = false;
                                                    }           
                                                    
                                                    count4++;
                                                }
                                        }else{
                                            fits = false;
                                        }
                                    }
                                    
                                    
                                    
                                    //if the words actually fits then put them into the grid
                                    if(fits == true){
                                        //Resets the counts
                                        count = 0;
                                        count2 = 0;
                                        count3 = 0;
                                        count4 = 0;
                                        
                                            //Puts the first word in, dir = WEST
                                            while(count < words[0].length()){
                                                grid[xPos + count][yPos] = Character.toString(words[0].charAt(count));
                                                count++;
                                            }
                                             
                                            //If there's a second word, it puts that one in, dir = NORTH
                                            if(words[1] != null){
                                                char temp2 = let.charAt(0);
                                                //where the letter is located in the second word
                                                int location2 = words[1].indexOf(temp2);
                                                
                                                while(count2 < words[1].length()){
                                                    location2 = words[1].indexOf(temp2) - count2;
                                                    
                                                    grid[xPos + location][yPos - location2] = Character.toString(words[1].charAt(count2));
                                                    
                                                    count2++;
                                                }
                                            }
                                            
                                            //If there's a third word, it puts that one in, dir = NORTHWEST 
                                            if(words[2] != null){
                                                char temp2 = let.charAt(0);
                                                //where the letter is located in the third word
                                                int location2 = words[2].indexOf(temp2);
                                                
                                                while(count3 < words[2].length()){
                                                    location2 = words[2].indexOf(temp2) - count3;
                                                    
                                                    grid[xPos - location2][yPos + location - location2] = Character.toString(words[2].charAt(count3));
                                                
                                                    count3++;
                                                }
                                            }
                                            
                                            //If there's a fourth word, it puts that one in, dir = NORTHEAST 
                                            if(words[3] != null){
                                                char temp2 = let.charAt(0);
                                                //where the letter is located in the fourth word
                                                int location2 = words[3].indexOf(temp2);
                                                
                                                while(count4 < words[3].length()){
                                                    location2 = words[3].indexOf(temp2) - count4;
                                                    
                                                    grid[xPos + location2][yPos + location - location2] = Character.toString(words[3].charAt(count4));
                                                
                                                    count4++;
                                                }
                                            }
                                            
                                            
                                            //Emptys the alreadyUsed array so the next list of words can be put in to the placeInGrid() method
                                            use = -1;
                                            alreadyUsed = null;
                                            alreadyUsed = new String[35];
                                    }
                                    //End of the loop that puts the individual characters from each word into the grid
                                    
                                    
                                    //If any of the locations are occupied, then a new location is choosen
                                    if(fits == false){
                                        placeInGrid(words, let, grid, xPos, yPos, level, true); 
                                    }

                                }else{
                                    
                                    placeInGrid(words, let, grid, xPos, yPos, level, true); 
                                }
                                //End of the first if, where it checks to see if it can fit in the grid///
                            break;
                        }
                        ///End of directional Switch Statement///
                        
                break;
                ///Level 1 Done///
                
                        
                case 2:
                    dir = rand.nextInt(2) + 3;       
                    
                    switch(dir){
                            case SOUTH:
                                
                                //if the first word can be placed in the grid, then begin loop
                                if((words[0].length() - 1 + yPos) <= 9){
                                    int count = 0;
                                    int count2 = 0;
                                    int count3 = 0;
                                    int location = 0;
                                    if(let != null){
                                    char temp = let.charAt(0);
                                    
                                    //the location of where the matching character is
                                    location =  (words[0].length() - 1) - words[0].indexOf(temp);
                                    }
                                    
                                    //Checks to see if the first location is occupied
                                    //If the location is occupied, but the same character is supposed to be placed there then it continues
                                    //with the loop and places the word anyway
                                    //dir = SOUTH
                                    while(count < words[0].length() && fits == true){
                                        
                                        if(!(grid[xPos][yPos + count].equalsIgnoreCase("*")) && 
                                                !(grid[xPos][yPos + count].equalsIgnoreCase(Character.toString(words[0].charAt((words[0].length() - 1) - count))))){
                                            
                                                fits = false;
                                        }
                                        
                                        count++;
                                    }
                                    
                                    
                                    //if there is a second word connected, dir = EAST
                                    if(words[1] != null){
                                        char temp2 = let.charAt(0);
                                        //where the letter is located in the second word
                                        int location2 = (words[1].length() - 1) - words[1].indexOf(temp2);
                                        
                                        //checks to see if the second word fits in the grid
                                        if((xPos - location2) >= 0 && (xPos + ((words[1].length()-1) - location2)) <= 9){
                                                 while(count2 < words[1].length() && fits == true){
                                                     location2 = (words[1].length() - 1) - words[1].indexOf(temp2) - count2;
                                            
                                                     if(!(grid[xPos - location2][yPos + location].equalsIgnoreCase("*")) &&
                                                             !(grid[xPos - location2][yPos + location].equalsIgnoreCase(Character.toString(words[1].charAt((words[1].length() - 1) - count2))))){
                                                            
                                                                    fits = false;
                                                     }

                                                        count2++;
                                                    }
                                        }else{
                                            fits = false;
                                        }
                                    }
                                    
                                    
                                    //if there is a third word connected, dir = NORTHWEST
                                    if(words[2] != null){
                                        char temp2 = let.charAt(0);
                                        //where the letter is located in the third word
                                        int location2 = words[2].indexOf(temp2);
                                        
                                        //checks to see if the third word fits in the grid
                                        if((xPos - location2) >= 0 && (xPos + ((words[2].length()-1) - location2)) <= 9 && (yPos - location2) >= 0 && (yPos + ((words[2].length()-1) - location2)) <= 9){
                                                while(count3 < words[2].length() && fits == true){
                                                    location2 = words[2].indexOf(temp2) - count3;

                                                    if(!(grid[xPos - location2][yPos + location - location2].equalsIgnoreCase("*")) &&
                                                            !(grid[xPos - location2][yPos + location - location2].equalsIgnoreCase(Character.toString(words[2].charAt(count3))))){
                                                            
                                                                fits = false;
                                                    }   
                                                    
                                                    count3++;
                                                }
                                        }else{
                                            fits = false;
                                        }
                                    }
                                    
                                    
                                    //if the words actually fits then put them into the grid
                                    if(fits == true){
                                        //Resets the counts
                                        count = 0;
                                        count2 = 0;
                                        count3 = 0;
                                        
                                            //Puts the first word in, dir = SOUTH
                                            while(count < words[0].length()){
                                                grid[xPos][yPos + count] = Character.toString(words[0].charAt((words[0].length() - 1) - count));
                                                count++;
                                            }
                                             
                                            //If there's a second word, it puts that one in, dir = EAST
                                            if(words[1] != null){
                                                char temp2 = let.charAt(0);
                                                //where the letter is located in the second word
                                                int location2 = (words[1].length() - 1) - words[1].indexOf(temp2);
                                                
                                                while(count2 < words[1].length()){
                                                    location2 = (words[1].length() - 1) - words[1].indexOf(temp2) - count2;
                                                    
                                                    grid[xPos - location2][yPos + location] = Character.toString(words[1].charAt((words[1].length() - 1) - count2));
                                                    
                                                    count2++;
                                                }
                                            }
                                            
                                            
                                            //If there's a third word, it puts that one in, dir = NORTHWEST 
                                            if(words[2] != null){
                                                char temp2 = let.charAt(0);
                                                //where the letter is located in the third word
                                                int location2 = words[2].indexOf(temp2);
                                                
                                                while(count3 < words[2].length()){
                                                    location2 = words[2].indexOf(temp2) - count3;
                                                    
                                                    grid[xPos - location2][yPos + location - location2] = Character.toString(words[2].charAt(count3));
                                                
                                                    count3++;
                                                }
                                            }
                                       
                                            
                                            //Emptys the alreadyUsed array so the next list of words can be put in to the placeInGrid() method
                                            use = -1;
                                            alreadyUsed = null;
                                            alreadyUsed = new String[35];
                                    }
                                    //End of the loop that puts the individual characters from each word into the grid
                                    
                                    
                                    //If any of the locations are occupied, then a new location is choosen
                                    if(fits == false){
                                        placeInGrid(words, let, grid, xPos, yPos, level, true); 
                                    }
                                   
                                    
                                }else{
                                    
                                    placeInGrid(words, let, grid, xPos, yPos, level, true); 
                                }
                                //End of the first if, where it checks to see if it can fit in the grid///
                            break;
                
                            
                            case EAST:
                                
                                //if the first word can be placed in the grid, then begin loop
                                if((words[0].length() - 1 + xPos) <= 9){
                                    int count = 0;
                                    int count2 = 0;
                                    int count3 = 0;
                                    int location = 0;
                                    if(let != null){
                                    char temp = let.charAt(0);
                                    
                                    //the location of where the matching character is
                                    location = (words[0].length() - 1) - words[0].indexOf(temp);
                                    }
                                    
                                    //Checks to see if the first location is occupied
                                    //If the location is occupied, but the same character is supposed to be placed there then it continues
                                    //with the loop and places the word anyway
                                    //dir = EAST
                                    while(count < words[0].length() && fits == true){
                                        
                                        if(!(grid[xPos + count][yPos].equalsIgnoreCase("*")) &&
                                            !(grid[xPos + count][yPos].equalsIgnoreCase(Character.toString(words[0].charAt((words[0].length() - 1) - count))))){
                                               
                                                fits = false;               
                                        }
                                        
                                        
                                        count++;
                                    }
                                    
                                    
                                    //if there is a second word connected, dir = SOUTH
                                    if(words[1] != null){
                                        char temp2 = let.charAt(0);;
                                        //where the letter is located in the second word
                                        int location2 = (words[1].length() - 1) - words[1].indexOf(temp2);
                                        
                                        //checks to see if the second word fits in the grid
                                        if((yPos - location2) >= 0 && (yPos + ((words[1].length()-1) - location2)) <= 9){
                                                 while(count2 < words[1].length() && fits == true){
                                                     location2 = (words[1].length() - 1) - words[1].indexOf(temp2) - count2;
                                            
                                                     if(!(grid[xPos + location][yPos - location2].equalsIgnoreCase("*")) &&
                                                             !(grid[xPos + location][yPos - location2].equalsIgnoreCase(Character.toString(words[1].charAt((words[1].length() - 1) - count2))))){
                                                             
                                                                    fits = false;
                                                     } 
                                                     
                                                     count2++;
                                                 }       
                                        }else{
                                            fits = false;
                                        }
                                    }
                                    
                                    
                                    //if there is a third word connected, dir = NORTHEAST
                                    if(words[2] != null){
                                        char temp2 = let.charAt(0);
                                        //where the letter is located in the third word
                                        int location2 = words[2].indexOf(temp2);
                                        
                                        //checks to see if the third word fits in the grid
                                        if((xPos + location + location2) <= 9 && (xPos + location - ((words[2].length() - 1) - location2)) >= 0 
                                                && (yPos + ((words[2].length() - 1) - location2)) <= 9 && (yPos - location2) >= 0){
                                                while(count3 < words[2].length() && fits == true){
                                                    location2 = words[2].indexOf(temp2) - count3;
                                                
                                                    
                                                    if(!(grid[xPos + location2 + location][yPos - location2].equalsIgnoreCase("*")) &&
                                                            !(grid[xPos + location2 + location][yPos - location2].equalsIgnoreCase(Character.toString(words[2].charAt(count3))))){
                                                            
                                                                    fits = false;
                                                    }       
                                                   
                                                    count3++;
                                                }
                                        }else{
                                            fits = false;
                                        }
                                    }
                                    
                                    
                                    
                                    //if the words actually fits then put them into the grid
                                    if(fits == true){
                                        //Resets the counts
                                        count = 0;
                                        count2 = 0;
                                        count3 = 0;
                                        
                                            //Puts the first word in, dir = EAST
                                            while(count < words[0].length()){
                                                grid[xPos + count][yPos] = Character.toString(words[0].charAt((words[0].length() - 1) - count));
                                                count++;
                                            }
                                             
                                            //If there's a second word, it puts that one in, dir = SOUTH
                                            if(words[1] != null){
                                                char temp2 = let.charAt(0);
                                                //where the letter is located in the second word
                                                int location2 = (words[1].length() - 1) - words[1].indexOf(temp2);
                                                
                                                while(count2 < words[1].length()){
                                                    location2 = (words[1].length() - 1) - words[1].indexOf(temp2) - count2;
                                                    
                                                    grid[xPos + location][yPos - location2] = Character.toString(words[1].charAt((words[1].length() - 1) - count2));
                                                    
                                                    count2++;
                                                }
                                            }
                                            
                                            
                                            //If there's a third word, it puts that one in, dir = NORTHEAST 
                                            if(words[2] != null){
                                                char temp2 = let.charAt(0);
                                                //where the letter is located in the third word
                                                int location2 = words[2].indexOf(temp2);
                                                
                                                while(count3 < words[2].length()){
                                                    location2 = words[2].indexOf(temp2) - count3;
                                                    
                                                    grid[xPos + location2 + location][yPos - location2] = Character.toString(words[2].charAt(count3));
                                                
                                                    count3++;
                                                }
                                            }
                                            
                                           
                                            //Emptys the alreadyUsed array so the next list of words can be put in to the placeInGrid() method
                                            use = -1;
                                            alreadyUsed = null;
                                            alreadyUsed = new String[35];
                                    }
                                    //End of the loop that puts the individual characters from each word into the grid
                                    
                                    
                                    //If any of the locations are occupied, then a new location is choosen
                                    if(fits == false){
                                        placeInGrid(words, let, grid, xPos, yPos, level, true); 
                                    }

                                }else{
                                    placeInGrid(words, let, grid, xPos, yPos, level, true); 
                                }
                                //End of the first if, where it checks to see if it can fit in the grid///
                            break;

                        }
                        ///End of directional Switch Statement///
                        
                break;
                ///Level 2 Done///
                
                
                case 3:
                int selective = rand.nextInt(3) + 1;
                    dir = 0;
                    
                    //Makes it so that there is a higher chance that the word will be placed diagonally
                    if(selective == 1){
                        dir = rand.nextInt(2) + 3; 
                    }else if(selective == 2 || selective == 3){
                        dir = rand.nextInt(2) + 7;
                
                    } 
                    
                        switch(dir){
                            
                            case SOUTH:
                            
                                //if the first word can be placed in the grid, then begin loop
                                if((words[0].length() - 1 + yPos) <= 9){
                                    int count = 0;
                                    int count2 = 0;
                                    int location = 0;
                                    if(let != null){
                                    char temp = let.charAt(0);
                                    
                                    //the location of where the matching character is
                                    location =  (words[0].length() - 1) - words[0].indexOf(temp);
                                    }
                                    
                                    //Checks to see if the first location is occupied
                                    //If the location is occupied, but the same character is supposed to be placed there then it continues
                                    //with the loop and places the word anyway
                                    //dir = SOUTH
                                    while(count < words[0].length() && fits == true){
                                        
                                        if(!(grid[xPos][yPos + count].equalsIgnoreCase("*")) && 
                                                !(grid[xPos][yPos + count].equalsIgnoreCase(Character.toString(words[0].charAt((words[0].length() - 1) - count))))){
                                            
                                                fits = false;
                                        }
                                        
                                        count++;
                                    }
                                    
                                    
                                    //if there is a second word connected, dir = EAST
                                    if(words[1] != null){
                                        char temp2 = let.charAt(0);
                                        //where the letter is located in the second word
                                        int location2 = (words[1].length() - 1) - words[1].indexOf(temp2);
                                        
                                        //checks to see if the second word fits in the grid
                                        if((xPos - location2) >= 0 && (xPos + ((words[1].length()-1) - location2)) <= 9){
                                                 while(count2 < words[1].length() && fits == true){
                                                     location2 = (words[1].length() - 1) - words[1].indexOf(temp2) - count2;
                                            
                                                     if(!(grid[xPos - location2][yPos + location].equalsIgnoreCase("*")) &&
                                                             !(grid[xPos - location2][yPos + location].equalsIgnoreCase(Character.toString(words[1].charAt((words[1].length() - 1) - count2))))){
                                                            
                                                                    fits = false;
                                                     }

                                                        count2++;
                                                    }
                                        }else{
                                            fits = false;
                                        }
                                    }
                                    
                                    
                                    
                                    //if the words actually fits then put them into the grid
                                    if(fits == true){
                                        //Resets the counts
                                        count = 0;
                                        count2 = 0;
                                        
                                            //Puts the first word in, dir = SOUTH
                                            while(count < words[0].length()){
                                                grid[xPos][yPos + count] = Character.toString(words[0].charAt((words[0].length() - 1) - count));
                                                count++;
                                            }
                                             
                                            //If there's a second word, it puts that one in, dir = EAST
                                            if(words[1] != null){
                                                char temp2 = let.charAt(0);
                                                //where the letter is located in the second word
                                                int location2 = (words[1].length() - 1) - words[1].indexOf(temp2);
                                                
                                                while(count2 < words[1].length()){
                                                    location2 = (words[1].length() - 1) - words[1].indexOf(temp2) - count2;
                                                    
                                                    grid[xPos - location2][yPos + location] = Character.toString(words[1].charAt((words[1].length() - 1) - count2));
                                                    
                                                    count2++;
                                                }
                                            }
                                            
                                            
                                            //Emptys the alreadyUsed array so the next list of words can be put in to the placeInGrid() method
                                            use = -1;
                                            alreadyUsed = null;
                                            alreadyUsed = new String[35];
                                    }
                                    //End of the loop that puts the individual characters from each word into the grid
                                    
                                    
                                    //If any of the locations are occupied, then a new location is choosen
                                    if(fits == false){
                                        placeInGrid(words, let, grid, xPos, yPos, level, true); 
                                    }

                                    
                                }else{
                                    
                                    placeInGrid(words, let, grid, xPos, yPos, level, true); 
                                }
                                //End of the first if, where it checks to see if it can fit in the grid///
                            break;
                
                            
                            case EAST:
                            
                                //if the first word can be placed in the grid, then begin loop
                                if((words[0].length() - 1 + xPos) <= 9){
                                    int count = 0;
                                    int count2 = 0;
                                    int location = 0;
                                    if(let != null){
                                    char temp = let.charAt(0);
                                    
                                    //the location of where the matching character is
                                    location = (words[0].length() - 1) - words[0].indexOf(temp);
                                    }
                                    
                                    //Checks to see if the first location is occupied
                                    //If the location is occupied, but the same character is supposed to be placed there then it continues
                                    //with the loop and places the word anyway
                                    //dir = EAST
                                    while(count < words[0].length() && fits == true){
                                        
                                        if(!(grid[xPos + count][yPos].equalsIgnoreCase("*")) &&
                                            !(grid[xPos + count][yPos].equalsIgnoreCase(Character.toString(words[0].charAt((words[0].length() - 1) - count))))){
                                               
                                                fits = false;               
                                        }
                                        
                                        count++;
                                    }
                                    
                                    
                                    //if there is a second word connected, dir = SOUTH
                                    if(words[1] != null){
                                        char temp2 = let.charAt(0);;
                                        //where the letter is located in the second word
                                        int location2 = (words[1].length() - 1) - words[1].indexOf(temp2);
                                        
                                        //checks to see if the second word fits in the grid
                                        if((yPos - location2) >= 0 && (yPos + ((words[1].length()-1) - location2)) <= 9){
                                                 while(count2 < words[1].length() && fits == true){
                                                     location2 = (words[1].length() - 1) - words[1].indexOf(temp2) - count2;
                                            
                                                     if(!(grid[xPos + location][yPos - location2].equalsIgnoreCase("*")) &&
                                                             !(grid[xPos + location][yPos - location2].equalsIgnoreCase(Character.toString(words[1].charAt((words[1].length() - 1) - count2))))){
                                                             
                                                                    fits = false;
                                                     } 
                                       
                                                     count2++;
                                                 }       
                                        }else{
                                            fits = false;
                                        }
                                    }
                                    
                                    
                                    
                                    //if the words actually fits then put them into the grid
                                    if(fits == true){
                                        //Resets the counts
                                        count = 0;
                                        count2 = 0;
                                        
                                            //Puts the first word in, dir = EAST
                                            while(count < words[0].length()){
                                                grid[xPos + count][yPos] = Character.toString(words[0].charAt((words[0].length() - 1) - count));
                                                count++;
                                            }
                                             
                                            //If there's a second word, it puts that one in, dir = SOUTH
                                            if(words[1] != null){
                                                char temp2 = let.charAt(0);
                                                //where the letter is located in the second word
                                                int location2 = (words[1].length() - 1) - words[1].indexOf(temp2);
                                                
                                                while(count2 < words[1].length()){
                                                    location2 = (words[1].length() - 1) - words[1].indexOf(temp2) - count2;
                                                    
                                                    grid[xPos + location][yPos - location2] = Character.toString(words[1].charAt((words[1].length() - 1) - count2));
                                                    
                                                    count2++;
                                                }
                                            }
                                            
                                            
                                            //Emptys the alreadyUsed array so the next list of words can be put in to the placeInGrid() method
                                            use = -1;
                                            alreadyUsed = null;
                                            alreadyUsed = new String[35];
                                    }
                                    //End of the loop that puts the individual characters from each word into the grid
                                    
                                    
                                    //If any of the locations are occupied, then a new location is choosen
                                    if(fits == false){
                                        placeInGrid(words, let, grid, xPos, yPos, level, true); 
                                    }

                                }else{
                                    placeInGrid(words, let, grid, xPos, yPos, level, true); 
                                }
                                //End of the first if, where it checks to see if it can fit in the grid///
                            break;
                            

                            case SOUTHEAST:
          
                                    //if the first word can be placed in the grid, then begin loop
                                    if(((words[0].length() - 1) + xPos) <= 9 && ((words[0].length() - 1) + yPos) <= 9){
                                        int count = 0;
                                        int count2 = 0;
                                        int location = 0;
                                        if(let != null){
                                            char temp = let.charAt(0);
                                    
                                            //the location of where the matching character is
                                            location = (words[0].length() - 1) - words[0].indexOf(temp);
                                        }
                                        
                                        //Checks to see if the first location is occupied
                                        //If the location is occupied, but the same character is supposed to be placed there then it continues
                                        //with the loop and places the word anyway
                                        //dir = SOUTHEAST        
                                        while(count < words[0].length() && fits == true){
                                                
                                            if(!(grid[xPos + count][yPos + count].equalsIgnoreCase("*")) &&
                                                    !(grid[xPos + count][yPos + count].equalsIgnoreCase(Character.toString(words[0].charAt((words[0].length() - 1) - count))))){
                                                                
                                                            fits = false;
                                            }           
                                                    
                                            count++;
                                        }
                                        
                                        //if there is a second word connected, dir = SOUTHWEST
                                        if(words[1] != null){
                                        char temp2 = let.charAt(0);
                                        //where the letter is located in the second word
                                        int location2 = (words[1].length() - 1) - words[1].indexOf(temp2);
                                        
                                        //checks to see if the second word fits in the grid
                                            if((xPos + location + location2) <= 9 && (xPos  + location - ((words[1].length()-1) - location2)) >= 0 
                                                    && (yPos + location +((words[1].length()-1) - location2)) <= 9 && (yPos + location - location2) >= 0){
                                                  while(count2 < words[1].length() && fits == true){
                                                      location2 = (words[1].length() - 1) - words[1].indexOf(temp2) - count2;
                                                
                                                    
                                                    if(!(grid[xPos + location + location2][yPos + location - location2].equalsIgnoreCase("*")) &&
                                                            !(grid[xPos + location + location2][yPos + location - location2].equalsIgnoreCase(Character.toString(words[1].charAt((words[1].length() - 1) - count2))))){
                                                            
                                                                    fits = false;
                                                    }       
                                                   
                                                    count2++;
                                                  }
                                                }else{
                                                    fits = false;
                                                }
                                        }
                                        
                                        if(fits == true){
                                        //Resets the counts
                                        count = 0;
                                        count2 = 0;
                                        
                                            //Puts the first word in, dir = SOUTHEAST
                                            while(count < words[0].length()){
                                                grid[xPos + count][yPos + count] = Character.toString(words[0].charAt((words[0].length() - 1) - count));
                                                count++;
                                            }
                                        
                                            //If there's a second word, it puts that one in, dir = SOUTHWEST
                                            if(words[1] != null){
                                                char temp2 = let.charAt(0);
                                                //where the letter is located in the second word
                                                int location2 = (words[1].length() - 1) - words[1].indexOf(temp2);
                                                
                                                while(count2 < words[1].length()){
                                                    location2 = (words[1].length() - 1) - words[1].indexOf(temp2) - count2;
                                                    
                                                    grid[xPos + location + location2][yPos + location - location2] = Character.toString(words[1].charAt((words[1].length() - 1) - count2));
                                                
                                                    count2++;
                                                }
                                            }
                                        
   
                                        //Emptys the alreadyUsed array so the next list of words can be put in to the placeInGrid() method
                                            use = -1;
                                            alreadyUsed = null;
                                            alreadyUsed = new String[35];
                                       }
                                       //End of the loop that puts the individual characters from each word into the grid
                                                
                                                
                                        //If any of the locations are occupied, then a new location is choosen
                                        if(fits == false){
                                            placeInGrid(words, let, grid, xPos, yPos, level, true); 
                                        }
                                     
                                    }else{
                                            placeInGrid(words, let, grid, xPos, yPos, level, true); 
                                    }
                                    //End of the first if, where it checks to see if it can fit in the grid///
                            break;
                            
                            
                            case SOUTHWEST:

                                    //if the first word can be placed in the grid, then begin loop
                                    if((xPos - (words[0].length() - 1)) >= 0 && ((words[0].length() - 1) + yPos) <= 9){
                                        int count = 0;
                                        int count2 = 0;
                                        int location = 0;
                                        if(let != null){
                                            char temp = let.charAt(0);
                                    
                                            //the location of where the matching character is
                                            location = (words[0].length() - 1) - words[0].indexOf(temp);
                                        }
                                        
                                        //Checks to see if the first location is occupied
                                        //If the location is occupied, but the same character is supposed to be placed there then it continues
                                        //with the loop and places the word anyway
                                        //dir = SOUTHWEST  
                                        while(count < words[0].length() && fits == true){
                                                
                                            if(!(grid[xPos - count][yPos + count].equalsIgnoreCase("*")) &&
                                                    !(grid[xPos - count][yPos + count].equalsIgnoreCase(Character.toString(words[0].charAt((words[0].length() - 1) - count))))){
                                                                
                                                            fits = false;
                                            }           
                                                    
                                            count++;
                                        }
                                        
                                        //if there is a second word connected, dir = SOUTHEAST
                                        if(words[1] != null){
                                            char temp2 = let.charAt(0);
                                            //where the letter is located in the second word
                                            int location2 = (words[1].length() - 1) - words[1].indexOf(temp2);
                                        
                                            //checks to see if the third word fits in the grid
                                            if((xPos  - location - location2) >= 0 && (xPos  - location + ((words[1].length()-1) - location2)) <= 9
                                                    && (yPos + location - location2) >= 0 && (yPos + location + ((words[1].length()-1) - location2)) <= 9){
                                                while(count2 < words[1].length() && fits == true){
                                                    location2 = (words[1].length() - 1) - words[1].indexOf(temp2) - count2;

                                                    if(!(grid[xPos - location - location2][yPos + location - location2].equalsIgnoreCase("*")) &&
                                                            !(grid[xPos - location - location2][yPos + location - location2].equalsIgnoreCase(Character.toString(words[1].charAt((words[1].length() - 1) - count2))))){
                                                            
                                                                fits = false;
                                                    }   
                                                    
                                                    count2++;
                                                }
                                            }else{
                                                fits = false;
                                            }
                                        }
                                        
                                        
                                        
                                       if(fits == true){
                                        //Resets the counts
                                        count = 0;
                                        count2 = 0;
                                        
                                            //Puts the first word in, dir = SOUTHWEST
                                            while(count < words[0].length()){
                                                grid[xPos - count][yPos + count] = Character.toString(words[0].charAt((words[0].length() - 1) - count));
                                                count++;
                                            }
                                        
                                            //If there's a second word, it puts that one in, dir = SOUTHEAST
                                            if(words[1] != null){
                                                char temp2 = let.charAt(0);
                                                //where the letter is located in the second word
                                                int location2 = (words[1].length() - 1) - words[1].indexOf(temp2);
                                                
                                                while(count2 < words[1].length()){
                                                    location2 = (words[1].length() - 1) - words[1].indexOf(temp2) - count2;
                                                    
                                                    grid[xPos - location - location2][yPos + location - location2] = Character.toString(words[1].charAt((words[1].length() - 1) - count2));
                                                
                                                    count2++;
                                                }
                                            }
                                        

                                        //Emptys the alreadyUsed array so the next list of words can be put in to the placeInGrid() method
                                            use = -1;
                                            alreadyUsed = null;
                                            alreadyUsed = new String[35];
                                       }
                                       //End of the loop that puts the individual characters from each word into the grid
                                                
                                                
                                        //If any of the locations are occupied, then a new location is choosen
                                        if(fits == false){
                                            placeInGrid(words, let, grid, xPos, yPos, level, true); 
                                        }
                                     
                                    }else{
                                            placeInGrid(words, let, grid, xPos, yPos, level, true); 
                                    }
                                    //End of the first if, where it checks to see if it can fit in the grid///
                            break;
                            
                        }
                        ///End of directional Switch Statement///

                break;
                ///Level 3 Done///
                
            }
            ////End of Level Switch statement////
        }catch(Exception exception){
            
            use = -1;
            alreadyUsed = null;
            alreadyUsed = new String[35];
            failed = true;
            Log.e("ERROR", "ERROR IN CODE WORD PLACER: " + exception.toString());
			exception.printStackTrace();
            return;
        }
        //End of Try-Catch, incase the placeInGrid() method doesn't work
            
        }else if(inUse == true){    //Recursive Case//
            
            //Stores the last locations
            xPos = newX;
            yPos = newY;
            use++;
            int count = 0;
            
             //if the all locations are used then empty the String array, and stop the method
            if(use >= 34){
                use = -1;
                alreadyUsed = null;
                alreadyUsed = new String[35];
                failed = true;
                
                return;
            }else{
                //Puts the last locations that was used into the alreadyUsed String array
                alreadyUsed[use] = Integer.toString(xPos) + Integer.toString(yPos);
            }
            
            
            //Chooses new random location    
            xPos = rand.nextInt(10);
            yPos = rand.nextInt(10);
            String newXY = Integer.toString(xPos) + Integer.toString(yPos);
             
            //Checks to see if the new location is in the array, if it is then it chooses a new random location that hasn't been choosen
            while(alreadyUsed[count] != null){
                  if(newXY.equalsIgnoreCase(alreadyUsed[count])){
                      xPos = rand.nextInt(10);
                      yPos = rand.nextInt(10); 
                      newXY = Integer.toString(xPos) + Integer.toString(yPos);
                      count = -1;
                    }
                    
                  count++;    
            }
            
            //trys to place words into the new location in the grid
            placeInGrid(words, let, grid, xPos, yPos, level, false); 
        }
        //End of the recursive case//
    }
    
}
