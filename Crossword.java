import java.io.*;
import java.util.*;


public class Crossword
{
    private  static char[][] Board;
    private  static char[] alphabet;
    private  static DictInterface D;
    private  static StringBuilder[] rowword;
    private  static StringBuilder[] colword;
    private static boolean endpoint=true;

   
    
    
     private static int line;
     private static int depth=0;
    



    public static void main(String[] args) throws Exception
    {
        String word;
       
        String file1= args[0];
        String file2 = args[1];
        Scanner dictionary=null;
        Scanner testfile=null;

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
        
       // read the dictionary 

       alphabet=new char[26];

       for(int i=0;i<26;i++)
       alphabet[i]=(char)(97+i);
       //get 26 character;
       
       
        D = new MyDictionary();

        while(dictionary.hasNext())
        {
            word=dictionary.nextLine();
            
            D.add(word);
        }//  input the dictionary, which makes D is the dictionary 

        dictionary.close();
        

        
        
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
       // print(Board);
        //read board

        rowword=new StringBuilder[line];
        
        colword=new StringBuilder[line];

        
        for(int i=0;i<line;i++)
        {
            
            rowword[i]=new StringBuilder();
            colword[i]=new StringBuilder();
           

            
            
        }
        
        
        for(int i=0;i<line;i++)
        for(int j=0;j<line;j++)
        if((!Character.isUpperCase(Board[i][j])))
            solve(i,j);

                       

                   
        
        print(Board);
       

    }



    public static void solve(int row,int col)
    {
    
    
  
      
    switch(Case(row,col))
    {
    case 0:
    for(int i=0;i<26;i++)
    {
          
        if(isValid(row,col,alphabet[i])&&endpoint)
            {
                rowword[row].append(alphabet[i]);
                
                colword[col].append(alphabet[i]);

                Board[row][col]=Character.toUpperCase(alphabet[i]);

                
        
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
    

    
    
    if(isValid(row,col,Board[row][col])&&endpoint)
       { 
       
        
        
        rowword[row].append(Board[row][col]);
                
        colword[col].append(Board[row][col]);

        
        
       Board[row][col]=Character.toUpperCase(Board[row][col]);
             
        
             if(col<line-1)
               solve(row,col+1);
            else if(row<line)
            solve(row+1,0);
                
                
            
               
           Board[row][col]=Character.toLowerCase(Board[row][col]);
            
            if(rowword[row].length()!=0)
             rowword[row].deleteCharAt(rowword[row].length()-1); 

                           

             if(colword[col].length()!=0)
               colword[col].deleteCharAt(colword[col].length()-1);

               return;
              
           
          
            
        }

        

           
            break;
       
      

                
        

    case 2:
    
    if(isOk(row,col))
       { 
        
        StringBuilder word1=new StringBuilder();
        StringBuilder word2=new StringBuilder();
        word1.append(rowword[row]);
        word2.append(colword[col]);

        if(rowword[row].length()!=0)
        rowword[row].delete(0,rowword[row].length());

       
               
               
            
            
        if(colword[col].length()!=0)
       colword[col].delete(0,colword[col].length());
        
    
        
       
       if(col<line-1)
       {
           solve(row,col+1);
       }
       else if(row<line)
       {
           solve(row+1,0);
       }

       rowword[row].append(word1);
       colword[col].append(word2);

     
      
          

      }
      break;

        
    }

}

public static boolean valid(int row,int col)
{
    if(row<line&&col<line)
    return true;
    return false;
    
    
    
}

    

    


        
    






public static boolean isOk(int row,int col)
{
    int choice1=D.searchPrefix(rowword[row]);
    
    int choice2=D.searchPrefix(colword[col]);

   if(row==0&&col<line)
    {
        if(choice1==3||choice1==2)
        return true;
    }
    else if(row<line&&col==0)
    {
        if(choice2==3||choice2==2)
        return true;
    
    }
    else if(row<line&&col<line)
    {
        if ((choice1==3||choice1==2)&&(choice2==3||choice2==2))
        return true;
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

        word1.append(rowword[row]);
        word1.append(w);
           
       
       //System.out.println("word1 is "+ word1);

        word2.append(colword[col]);
           
        word2.append(w);
       // System.out.println("word2 is "+ word2);
        
        int choice1=D.searchPrefix(word1);
        //System.out.println("choice1 is "+ choice1);
        

        
        int choice2=D.searchPrefix(word2);
       // System.out.println("choice2 is "+ choice2);

        //System.out.println("row is "+row+" col is "+col);

      //System.out.println("");

        
      
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
                
                if(Board[row][col]=='-')
                  {
                      endpoint=false;
                      return true;
                  }
                else if((choice1==3||choice1==2)&&(choice2==3||choice2==2))
                {
                    endpoint=false;
                    Board[row][col]=Character.toUpperCase(w);
                    return true;
                }
                else 
                return false;

            }
            
            return false;

            
        
       
         
        
        
    
    }
 
public static void print(char array[][])
    {
        for(int i=0;i<array.length;i++)
        {
            for(int j=0;j<array.length;j++)
            {
                System.out.print(array[i][j]+" ");
                
                
                
            }

            System.out.println();
        }

    }

   
      

}