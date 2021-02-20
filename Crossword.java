import java.io.*;
import java.util.*;




public class Crossword
{
    private  static char[][] Board;  
    private  static char[] alphabet;      // 26 characters
    private  static DictInterface D;      //dictionary
    
    private  static StringBuilder[] rowword; // rows array
    private  static StringBuilder[] colword;  //column array
    
    private static int [] rowIndex;          //array for recording the last index of '-' sign 
    private static int [] colIndex;
               
    
   
   private static HashMap<Character,Integer> Score; //for calualate the Score, and store points.
    
    
    private static boolean type=false;         // file type. false for no - sign in the board
                                               //  true for - sign in the board
    
    
    private static int line;                 //number of the rows and columns
     
    



    public static void main(String[] args) throws Exception
    {
        String word;
       
        String file1= args[0];        //Files for dictionary and board
        String file2 = args[1];
       
        Scanner dictionary=null;       
        Scanner testfile=null;
        Scanner grade=null;         //Scanner for dictionary board and letter points

      Score=new HashMap<>();       //Store letter points

        try
        {
             dictionary = new Scanner(new FileInputStream (file1));

        }
        catch(Exception e)
        {
            System.out.println("Dictionary does noe exist,Try another file");
            System.exit(0);
        }

        try
        {
            testfile = new Scanner(new File(file2));

        }
        catch(Exception e)
        {
            System.out.println("Board does noe exist.Try another file");
            System.exit(0);
        }


      try
     {
        grade = new Scanner(new FileInputStream("letterpoints.txt"));
        
    }
    catch(Exception e)
    {
        System.out.println("Score file does not exist");
            System.exit(0);

    }

    ///  Scan file and use try statment to avoid exceptions
    //


    while(grade.hasNext())
    {
        char character=grade.next().charAt(0);
        int score=grade.nextInt();

        Score.put(character,score);
    }
    // letter points

    grade.close();
        
        line = testfile.nextInt(); //get number of rows and columns
        
       
        Board =new char [line][line];   //create a board

        for(int i=0; i<line;i++)
        {
             String row = testfile.next();
             for(int j=0; j<line; j++)
            {
                
              
               Board[i][j]=row.charAt(j);  //get the Board

            }

        }
        testfile.close();
        
       
       alphabet=new char[26]; // get 26 characters

       for(int i=0;i<26;i++)
       alphabet[i]=(char)(97+i);// get 26 characters  
       //get 26 character;

       

       for(int i=0; i<line;i++)
        {
            for(int j=0; j<line; j++)
            {
                if(Board[i][j]=='-')
                {
                type=true;           //// get the type of the Board, 
                                        //whether there is a negative sign in the Board or not
                break;
                }
        }
    }


       
       
        D = new MyDictionary();

        while(dictionary.hasNext())
        {
            
            word=dictionary.nextLine();
            if(type)
            {
                if(word.length()<=line)      // if there is a negative sign in the board 
                {                             // add word that less or equal to number of lines
                D.add(word);
                }    
            }
            else
            {
                if(word.length()==line)       //if there no negative sign in the board
                {                            // add words that equal to the number of lines 
                D.add(word);
                }

            }
        }//  get the dictionary

        dictionary.close();

        
        

        
        
          

       
       

        rowword=new StringBuilder[line];      // row words array
        
        colword=new StringBuilder[line];      // colmun words array
        
        
      

        
        for(int i=0;i<line;i++)
        {
            
            rowword[i]=new StringBuilder();     //initialize rows and colmuns
            colword[i]=new StringBuilder();
        }
        
       
        
            rowIndex = new int [line];        //intitialize last index
            colIndex = new int[line];
            
            
            
            
            for(int i=0;i<line;i++)
            {
                rowIndex[i]=-1;           // Set intial value for no negative sign;
                colIndex[i]=-1;
            }
            
        
            
            solve(0,0);              // recursive method to solve the Crossword puzzle.


            

        System.out.println("No solution!");   // There is no solution in the Board


      /*  double finish = System.nanoTime();
        double delta=((finish-start)/1000000000);
        System.out.println("Time running the alogorithm : "+ (delta)+"s");      //caluate time
        */


       

    }



    public static void solve(int row,int col)
    {
    
    
  
      
    switch(Case(row,col)) // Determin case for + and character, -
    {
    case 0:             // + case
    for(int i=0;i<26;i++)     // For each character 
    {
          
        if(isValid(row,col,alphabet[i]))    // Determin whether is valid
            {
                rowword[row].append(alphabet[i]);      
                                                    
                colword[col].append(alphabet[i]);      // add character
              
                
                if(col<line-1)
               solve(row,col+1);            // Next square
               else if (row<line)
                solve(row+1,0);

                
                if(rowword[row].length()!=0)
                rowword[row].deleteCharAt(rowword[row].length()-1);    //delete a character for backtracking

            
                 if(colword[col].length()!=0)
                colword[col].deleteCharAt(colword[col].length()-1);

               
               }

               
        }

        break;
             


    case 1:  // small character case
    

    
    
    if(isValid(row,col,Board[row][col]))    // test whether it is valid
       { 
       
        
        
        rowword[row].append(Board[row][col]);   
                
        colword[col].append(Board[row][col]); // add character

        
        
     
             
        
             if(col<line-1)
               solve(row,col+1);
            else if(row<line-1)
            solve(row+1,0);                  // next character
                
                
            
               
          
            
          if(rowword[row].length()!=0)
          rowword[row].deleteCharAt(rowword[row].length()-1); //delete character

      
           if(colword[col].length()!=0)
          colword[col].deleteCharAt(colword[col].length()-1);
        }

        

           
            break;
       
      

                
        

    case 2:            //for negative sign
    
    if(isOk(row,col))     //test whether it is valid
       { 
       
        
       
        rowword[row].append('-');      // add negative sign to the board
        colword[col].append('-');

        int prerow=rowIndex[row];
        int precol=colIndex[col];      //previous row last index of a negative sign 
        
        rowIndex[row]=col;
        
        colIndex[col]=row;              // update the last index of a negative

      

       if(col<line-1)
      {
           solve(row,col+1);
      }
       else if(row<line-1)
       {                            //next character
           solve(row+1,0);
       }

       
       if(rowword[row].length()!=0)
          rowword[row].deleteCharAt(rowword[row].length()-1);   //delete character

      
        if(colword[col].length()!=0)
          colword[col].deleteCharAt(colword[col].length()-1);
 
        rowIndex[row]=prerow;       //back to previous last index
        
        colIndex[col]=precol;

       

       


     

     
      
          

      }
      break;

        
    }

}



public static boolean isOk(int row,int col)     //test whether the negative is valid.
{    
    
    if(row==0&&col==0)//if the negative sign is on the first square
     {
         return true; 
     }
    
    if(colIndex[col]!=-1&&rowIndex[row]!=-1) //if the negative signs next to the square
              return true;                   // return true
    
    
    
    StringBuilder word1=new StringBuilder();
             
    StringBuilder word2=new StringBuilder();

    

  
    if(rowIndex[row]!=-1)       //test whether there is a negative sign in row
    word1.append(rowword[row].substring(rowIndex[row]+1));    //get the substring
   else
    word1.append(rowword[row]); 
    
   if(colIndex[col]!=-1)        
    word2.append(colword[col].substring(colIndex[col]+1));  // //test whether there is a negative sign in column
    else
    word2.append(colword[col]);

    
    
    
    int choice1=D.searchPrefix(word1);
    
    int choice2=D.searchPrefix(word2);                  //get the number that show whther is a prefix or 

    

    
     
     
     if(row==0&&col<line)                        // if the negative sign is on the first row
    {
        if(choice1==3||choice1==2)
        return true;
    }
    
    if(row<line&&col==0)                      //if the negative sign is on the  first column
    {
        if(choice2==3||choice2==2)
        return true;
    
    }
   
    if(row<line-1&&col<line-1)               // if the negative sign is on the last row or last column
    {
        if ((choice1==3||choice1==2)&&(choice2==3||choice2==2))   ///and center of the board
        return true;
    }
    
    if(row==line-1&&col==line-1)    //if the negative sign is on the last square
    {
        rowword[row].append('-');
        colword[col].append('-');
        
        Print();
        System.exit(0);
    }
            
    return false;

    

}


    
public static int Case(int row,int col)    //identify which case + - and character
{
    if(Board[row][col]=='+')  
    return 0;
    else if(Board[row][col]=='-')
    return 2;
    else
    return 1;
    
}

   
public static boolean isValid(int row, int col,char w)   //test + sign and character 
    {
       StringBuilder word1=new StringBuilder();
             
       StringBuilder word2=new StringBuilder();        

        
    
        if(rowIndex[row]!=-1)
        word1.append(rowword[row].substring(rowIndex[row]+1));
        else
        word1.append(rowword[row]);                               //append character
        
        word1.append(w);
    
    
           
       
       

       if(colIndex[col]!=-1)
        word2.append(colword[col].substring(colIndex[col]+1));
        else
        word2.append(colword[col]);
           
        word2.append(w);

        

      
        
        int choice1=D.searchPrefix(word1);            //get the result of search 
        
        

        
        int choice2=D.searchPrefix(word2);
      

       
        
      
      if(row<line-1 && col<line-1)          //if the position is not the last row or col
        {
           
           
            if((choice1==3||choice1==1)&&(choice2==3||choice2==1))
            {
                return true;
            }

            
        }

        if(row<line-1&&col==line-1)     //if the position is on the last col
        {
            
            if((choice1==3||choice1==2)&&(choice2==3||choice2==1)) 
            {
                return true;

                
            }
            
            
        }
        if(row==line-1&&col<line-1)    // if the position is on the last row
        {
            
            
           
            if((choice1==3||choice1==1)&&(choice2==3||choice2==2))
            {
               return true;
                
            }
           

            
        }

        if(row==line-1&&col==line-1)    // if the position is on the last square
            {
                
              
                if((choice1==3||choice1==2)&&(choice2==3||choice2==2))
                {
                    
                rowword[row].append(w);
                
                colword[col].append(w);

                
                 Print();


              /*  double finish = System.nanoTime();
                double delta= finish-start;
                System.out.println("Time running the alogorithm : "+ (delta/1000000000)+"s");
                */
                    
                System.exit(0);
                 
            }
                

            }
            
            return false;

            
}
 
public static void Print()   // Print result
{
    for(int i=0;i<line;i++)
    {
        for(int j=0;j<line;j++)
    System.out.print(rowword[i].charAt(j)+" ");
    
    System.out.println();
    }

    int score=getPoints();

    System.out.println("Score: "+score);
    
}

public static int getPoints()    // get letter points 
{
    int score=0;
    for(int i=0;i<line;i++)
    {
        for(int j=0;j<line;j++)
        {
            if(rowword[i].charAt(j)!='-')
            score+=Score.get(Character.toUpperCase(rowword[i].charAt(j)));
            
        }
    }

    return score;
}


}
