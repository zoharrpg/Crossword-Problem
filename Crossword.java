import java.io.*;
import java.util.*;


public class Crossword
{
    private  static char[][] Board;
    private  static char[] alphabet;
    private  static DictInterface D;
    private  static StringBuilder[] rowword;
    private  static StringBuilder[] colword;
    
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
        

        
        
          

       
       // print(Board);
        //read board

        rowword=new StringBuilder[line];
        
        colword=new StringBuilder[line];
        
        
      

        
        for(int i=0;i<line;i++)
        {
            
            rowword[i]=new StringBuilder();
            colword[i]=new StringBuilder();

           
           

            
            
        }
        
        
        //for(int i=0;i<line;i++)
       // for(int j=0;j<line;j++)
        //if((!Character.isUpperCase(Board[i][j])))
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
                //rowword1[row].append(alphabet[i]);
               //colword2[col].append(alphabet[i]);
               


                //Board[row][col]=Character.toUpperCase(alphabet[i]);

                
        
                if(col<line-1)
               solve(row,col+1);
               else if (row<line)
                solve(row+1,0);

                depth++;
                if(rowword[row].length()!=0)
                rowword[row].deleteCharAt(rowword[row].length()-1);

            
                 if(colword[col].length()!=0)
                colword[col].deleteCharAt(colword[col].length()-1);

                //if(rowword1[row].length()!=0)
                //rowword1[row].deleteCharAt(rowword1[row].length()-1);
                
            
                // if(colword2[col].length()!=0)
                //colword2[col].deleteCharAt(row);

               }

               
        }

        break;
             


    case 1:
    

    
    
    if(isValid(row,col,Board[row][col]))//&&endpoint)
       { 
       
        
        
        rowword[row].append(Board[row][col]);
                
        colword[col].append(Board[row][col]);

        
        
      // Board[row][col]=Character.toUpperCase(Board[row][col]);
             
        
             if(col<line-1)
               solve(row,col+1);
            else if(row<line)
            solve(row+1,0);
                
                
            
               
          // Board[row][col]=Character.toLowerCase(Board[row][col]);
            
          if(rowword[row].length()!=0)
          rowword[row].deleteCharAt(rowword[row].length()-1);

      
           if(colword[col].length()!=0)
          colword[col].deleteCharAt(colword[col].length()-1);



              

                //if(rowword1[row].length()!=0)
               // rowword1[row].deleteCharAt(col);
                
            
                // if(colword2[col].length()!=0)
               // colword2[col].deleteCharAt(row);
              
           
          
            
        }

        

           
            break;
       
      

                
        

    case 2:
    
    if(isOk(row,col))
       { 
        
       StringBuilder word1=new StringBuilder();
        StringBuilder word2 =new StringBuilder();
        word1.append(rowword[row]);
        word2.append(colword[col]);

       // for(int i=0;i<word1.length();i++)
       // {
           // Board[row][i]=word1.charAt(i)
        //}

        
        
        
        if(rowword[row].length()!=0)
        rowword[row].delete(0,rowword[row].length());

       // rowword1[row].append('-');
        //colword2[col].append('-');

       
               
               
            
            
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
       
       //if(rowword1[row].length()!=0)
       //rowword1[row].deleteCharAt(col);
       
       //if(colword2[col].length()!=0)
       //colword2[col].deleteCharAt(row);

     
      
          

      }
      break;

        
    }

}



    public static boolean isOk(int row,int col)
{    
    
    
    
    int choice1=D.searchPrefix(rowword[row]);
    
    int choice2=D.searchPrefix(colword[col]);
    
     
    if(row>0&&col>0)
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
        
    
}

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
           
       
       System.out.println("word1 is "+ word1);

        word2.append(colword[col]);
           
        word2.append(w);
        System.out.println("word2 is "+ word2);
        
        int choice1=D.searchPrefix(word1);
        System.out.println("choice1 is "+ choice1);
        

        
        int choice2=D.searchPrefix(word2);
       System.out.println("choice2 is "+ choice2);

        System.out.println("row is "+row+" col is "+col);

      System.out.println("");

        
      
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
                
               // if(Board[row][col]=='-')
                  //{
                     
                    //rowword[row].append(w);
                
                    //colword[col].append(w);
                   // Print();
                  //  System.exit(0);
                    
                    //endpoint=false;
                     // return true;
                  //}
                if((choice1==3||choice1==2)&&(choice2==3||choice2==2))
                {
                    
                rowword[row].append(w);
                
                colword[col].append(w);
               // rowword1[row].append(w);
                //colword2[col].append(w);
                    
                    Print();
                    System.exit(0);

                    
                    
                    
                    //endpoint=false;
                    //Board[row][col]=Character.toUpperCase(w);
                    //return true;
                }
                

            }
            
            return false;

            
        
       
         
        
        
    
    }
 
public static void Print()
    {
       
        
        
       if(!type)
       {
           for(int i=0;i<line;i++)
           {
               for(int j=0;j<line;j++)
               {
                   System.out.print(rowword[i].charAt(j)+" ");
               }

               System.out.println();
           }

       }

   
      

}
}