
package turing.machine;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TuringMachine {
    Scanner scan = new Scanner(System.in);
    int numberOfStates;
    int numberOfAlphabets;
    int numberOfTransitions;
    char [] Alphabet;
    char []inputArray;
    String [] Transition;
    String [] ResultTransition;
    String [] string;
    String headState;
    String acceptState;
    String temp;
    String input;
    String Resultinput;
    static boolean status=false;
    
    public static void main(String[] args) {

        TuringMachine TM = new TuringMachine();
        TM.InputPhase();
        TM.Process();
        TM.Result(status);
        
  
    }
    
    void InputPhase(){
        // Collection of Data size
        System.out.print("Enter the no of states: ");
          numberOfStates =  scan.nextInt();
          System.out.print("Enter the no of alphabets: ");
          numberOfAlphabets =  scan.nextInt();
          numberOfTransitions = numberOfStates *numberOfAlphabets;
          //~
          
          // Collection of Data
          int i=1;
          Alphabet = new char[numberOfAlphabets+1];
          Alphabet[0] = '-';
          
                // Storing Alphabets
          while(i<=numberOfAlphabets)
          {
          System.out.print("Enter alphabet no "+i+": ");
          Alphabet[i] =  scan.next().charAt(0);
              i++;
          }
                //~
                
                // Storing Transitions
          i=0;
          Transition = new String[numberOfTransitions];
          ResultTransition = new String[numberOfTransitions];
          
          System.out.print("Enter no of transitions: ");
          numberOfTransitions = scan.nextInt();
          if(numberOfTransitions> numberOfAlphabets*numberOfStates)
          {
              System.out.println("You tried to cheat me , i won't help you anymore");
              System.out.println("\t\t Bye :( ");
              System.exit(0);
          }
          
          Pattern p = Pattern.compile("[\\S]*[,][\\S]*[=][\\S]*[,][\\S][,][rRlL]");
          Matcher m;
          while(i<numberOfTransitions)
          {
          System.out.print("Enter transition no "+(i+1)+": ");
          temp = scan.next();
          m=p.matcher(temp);
          while(!m.find())
          {
              System.out.println("Syntax Error please use this format: ");
                       System.out.println("\t State,Alphabet=State,change,move");
                       System.out.print("Enter transition no "+(i+1)+": ");
                       temp = scan.next();
                       m=p.matcher(temp);
          }   
           string = temp.split("=");
           Transition[i] = string[0];
           ResultTransition[i] = string[1];
           //System.out.println(Transition[i]+"|"+ ResultTransition[i]);
              i++;
          }
                //~
                
                // Tape input string
                System.out.print("Enter tape string: ");
                input = scan.next();
                
                    //Loop to check if alphabet exist
                char []inputArray = input.toCharArray();
                i=0;
                while(i<input.length())
                {
                    if ( !checkAlphabet(inputArray[i]) )
                {
                    System.out.println("Your string is different from alphabets !! ");
                    System.out.print("Please ,Re-Enter tape string: ");
                    input = scan.next();
                    input += "-";
                    inputArray=input.toCharArray();
                    i=0;
                }
                    else
                        i++;
                }
                    //~
                System.out.print("Enter head state symbol: ");
                headState = scan.next();
                
                while(!checkState(headState))
                {
                    System.out.print("Focus please, Enter head state symbol: ");
                headState = scan.next();
                }
                
                
                
                System.out.print("Enter accept state symbol: ");
                acceptState = scan.next();
                
                while(!checkState(acceptState))
                {
                    System.out.print("pfffff, Enter accept state symbol correctly: ");
                acceptState = scan.next();
                }
                //~
                
                
          
          //~
     
    } 
    void Process(){
        
        String TransitionState="";
        String ResultTransitionState="";
        String currentState=headState;
        String change;
        String direction;
        inputArray = input.toCharArray();
        int i=0;
        
        while(i<inputArray.length){
            TransitionState= currentState+","+inputArray[i];
            ResultTransitionState = getResultTransitionState(TransitionState);
            
            if(ResultTransitionState.equals("not determined"))
            {
                if(currentState.equals(acceptState))
                {
                    status=currentState.equals(acceptState);
                    break;
                }
            }
            
            string = ResultTransitionState.split(",");
            currentState = string[0];
            System.out.println(string[1]);
            change = string[1];
            direction = string[2];
            
            //Modify tape
            inputArray[i] = change.charAt(0);
            //~
            
            //Move right or left
            if( (i+1<inputArray.length)&& (direction.charAt(0) == 'R' || direction.charAt(0) == 'r') )
            {
                i++;
            }
            else if( (i-1>=0)&& (direction.charAt(0) == 'L' || direction.charAt(0) == 'l') )
            {
                i--;
            }
            //~
            else{
                break;
            }
            //System.out.println(getResultTransitionState(TransitionState));
            
        }
        
        status = currentState.equals(acceptState);
        
        
    
        

        
        
        
        
    }
    void Result(boolean Decision){
        if(Decision)
        {
            String ResultTape = new String(inputArray);
            System.out.println("The input       : "+input+"  Accepted");
            System.out.println("The Result tape : "+ResultTape);
            
        }
        else
        {
            System.out.println("The input : "+input+"  Rejected");
        }
        
    }
    
    boolean checkState(String state)
    {
        int i=0;     
        while(i<Transition.length)
        {
            string = Transition[i].split(",");
            if(string[0].equals(state))
                return true;
            
            i++;
        }
        return false;
    }
    boolean checkAlphabet(char Alpha){
        int i=0;
        
        while(i<Alphabet.length)
        {
            if(Alpha == Alphabet[i])
            {
                return true;
            }
            i++;
        }
        
        return false;
    }
    String getResultTransitionState(String TransitionState)
    {
        int i=0;
        while(i<Transition.length)
        {
            //System.out.println(TransitionState+" | "+Transition[i]+" :"+TransitionState.equals(Transition[i]));
            if(TransitionState.equals(Transition[i]))
            {
                return ResultTransition[i];
            }
            i++;
        }
        return "not determined";
    }
}


   

