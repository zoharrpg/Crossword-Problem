import java.io.*;
import java.util.*;


public class Crossword
{
    private  static char[][] Board;
    private  static char[] alphabet;
    private  static DictInterface D;
    private  static StringBuilder[] rowword;
    private  static StringBuilder[] colword;
    private static int [] rowIndex;
    private static int [] colIndex;
    private static HashMap<Character,Integer> Score;
    
    
    private static boolean type=false;
    

   
    
    
     private static int line;
     private static int depth=0;
    



    public static void main(String[] args) throws Exception
    {
        String word;
       
        String file1= args[0];
        String file2 = args[1];
        Scanner dictionary=null;
        Scanner testfile=null;
        Scanner grade=null;

      Score=new HashMap<>();

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

    while(grade.hasNext())
    {
        char character=grade.next().charAt(0);
        int score=grade.nextInt();

        Score.put(character,score);
    }

    grade.close();
        
        line = testfile.nextInt();
        
        Board =new char [line][line];

        for(int i=0; i<line;i++)
        {
             String row = testfile.next();
             for(int j=0; j<line; j++)
            {
                
              
               Board[i][j]=row.charAt(j);

            }

        }
        testfile.close();
        
       // read the dictionary 

       alphabet=new char[26];

       for(int i=0;i<26;i++)
       alphabet[i]=(char)(97+i);
       //get 26 character;

       

       for(int i=0; i<line;i++)
        {
            for(int j=0; j<line; j++)
            {
                if(Board[i][j]=='-')
                {
                type=true;
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
                if(word.length()<=line)
                D.add(word);
            }
            else
            {
                if(word.length()==line)
                D.add(word);

            }
        }//  input the dictionary, which makes D is the dictionary 

        dictionary.close();

        
        

        
        
          

       
       

        rowword=new StringBuilder[line];
        
        colword=new StringBuilder[line];
        
        
      

        
        for(int i=0;i<line;i++)
        {
            
            rowword[i]=new StringBuilder();
            colword[i]=new StringBuilder();
            

           
           

            
            
        }
        
       
        
            rowIndex = new int [line];
            colIndex = new int[line];
            for(int i=0;i<line;i++)
            {
                rowIndex[i]=-1;
                colIndex[i]=-1;
            }
            
        
            solve(0,0);

                       

                   
        
       System.out.println("No solution!");
       

    }



    public static void solve(int row,int col)
    {
    
    
  
      
    switch(Case(row,col))
    {
    case 0:
    for(int i=0;i<26;i++)
    {
          
        if(isValid(row,col,alphabet[i]))
            {
                rowword[row].append(alphabet[i]);
                
                colword[col].append(alphabet[i]);
              
                if(col<line-1)
               solve(row,col+1);
               else if (row<line)
                solve(row+1,0);

                depth++;
                if(rowword[row].length()!=0)
                rowword[row].deleteCharAt(rowword[row].length()-1);

            
                 if(colword[col].length()!=0)
                colword[col].deleteCharAt(colword[col].length()-1);

               
               }

               
        }

        break;
             


    case 1:
    

    
    
    if(isValid(row,col,Board[row][col]))
       { 
       
        
        
        rowword[row].append(Board[row][col]);
                
        colword[col].append(Board[row][col]);

        
        
     
             
        
             if(col<line-1)
               solve(row,col+1);
            else if(row<line)
            solve(row+1,0);
                
                
            
               
          
            
          if(rowword[row].length()!=0)
          rowword[row].deleteCharAt(rowword[row].length()-1);

      
           if(colword[col].length()!=0)
          colword[col].deleteCharAt(colword[col].length()-1);
        }

        

           
            break;
       
      

                
        

    case 2:
    
    if(isOk(row,col))
       { 
       
        
       
        rowword[row].append('-');
        colword[col].append('-');

        int prerow=rowIndex[row];
        int precol=colIndex[col];
        
        rowIndex[row]=col;
        
        colIndex[col]=row;

      

       if(col<line-1)
      {
           solve(row,col+1);
      }
       else if(row<line)
       {
           solve(row+1,0);
       }

       
       if(rowword[row].length()!=0)
          rowword[row].deleteCharAt(rowword[row].length()-1);

      
        if(colword[col].length()!=0)
          colword[col].deleteCharAt(colword[col].length()-1);

        rowIndex[row]=prerow;
        
        colIndex[col]=precol;

       

       


     

     
      
          

      }
      break;

        
    }

}



    public static boolean isOk(int row,int col)
{    
    
    StringBuilder word1=new StringBuilder();
             
    StringBuilder word2=new StringBuilder();

    

  
    if(rowIndex[row]!=-1)
    word1.append(rowword[row].substring(rowIndex[row]+1));
   else
    word1.append(rowword[row]);
    
   if(colIndex[col]!=-1)
    word2.append(colword[col].substring(colIndex[col]+1));
    else
    word2.append(colword[col]);

    
    
    
    int choice1=D.searchPrefix(word1);
    
    int choice2=D.searchPrefix(word2);

    if(colIndex[col]!=-1&&rowIndex[row]!=-1)
    return true;

    
    
     
   /* if(row>0&&col>0)
    {
    if(Board[row-1][col]=='-'&&Board[row][col-1]!='-')
    {
        if(choice1==3||choice1==2)
        return true;    
    }
    else if(Board[row][col-1]=='-'&&Board[row+1][col]!='-')
    {
        if(choice2==3||choice2==2)
        return true;

    }
    else if(Board[row][col-1]=='-'&&Board[row+1][col]=='-')
    {
        return true;
    }
        */
    


   if(row==0&&col<line-1)
    {
        if(choice1==3||choice1==2)
        return true;
    }
    else if(row<line-1&&col==0)
    {
        if(choice2==3||choice2==2)
        return true;
    
    }
    else if(row<line-1&&col<line-1)
    {
        if ((choice1==3||choice1==2)&&(choice2==3||choice2==2))
        return true;
    }
    else if(row==line-1&&col<line-1)
    {
        if(choice2==3||choice2==2)
        return true;
    }
    else if(row<line-1&&col==line-1)
    {
        if(choice1==3||choice1==2)
        return true;
    }
   
    else if(row==line-1&&col==line-1)
    {
        Print();
        System.exit(0);
    }
            
         
            return false;

    

}


    
public static int Case(int row,int col)
{
    
    if(Board[row][col]=='-')
       return 2;
    else if(Character.isLowerCase(Board[row][col]))
    return 1;
    else
    return 0;
}

   
public static boolean isValid(int row, int col,char w)
    {
       StringBuilder word1=new StringBuilder();
             
        StringBuilder word2=new StringBuilder();

        
    
        if(rowIndex[row]!=-1)
        word1.append(rowword[row].substring(rowIndex[row]+1));
        else
        word1.append(rowword[row]);
        
        word1.append(w);
    
    
           
       
       

       if(colIndex[col]!=-1)
        word2.append(colword[col].substring(colIndex[col]+1));
        else
        word2.append(colword[col]);
           
        word2.append(w);

      
        
        int choice1=D.searchPrefix(word1);
        
        

        
        int choice2=D.searchPrefix(word2);
      

        
      
      if(row<line-1 && col<line-1)
        {
           
           
            if((choice1==3||choice1==1)&&(choice2==3||choice2==1))
            {
                return true;
            }

            
        }

        if(row<line-1&&col==line-1)
        {
            
            if((choice1==3||choice1==2)&&(choice2==3||choice2==1))
            {
                return true;

                
            }
            
            
        }

         
        if(row==line-1&&col<line-1)
        {
            
            
           
            if((choice1==3||choice1==1)&&(choice2==3||choice2==2))
            {
               return true;
                
            }
           

            
        }

        if(row==line-1&&col==line-1)
            {
                
              
                if((choice1==3||choice1==2)&&(choice2==3||choice2==2))
                {
                    
                rowword[row].append(w);
                
                colword[col].append(w);
              
                    
                    Print();
                    System.exit(0);
                 }
                

            }
            
            return false;

            
}
 
public static void Print()
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

public static int getPoints()
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


 /*public static boolean test(StringBuilder word)
{
    /*for(int i =0;i<word.length();i++)
    {
        if(word.charAt(i)=='-')
        return true;
    }
    return false;
}
    */




}