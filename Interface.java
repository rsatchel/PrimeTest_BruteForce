import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;


import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

class Interface extends JPanel 
{
	
	private static JFormattedTextField enterNum;
    private JButton checkNum;
    private JLabel primeCheck;
    private JButton button1;
    private static JTextArea textArea;
    
    
    private static ArrayList <BigInteger> arl = new ArrayList<>(); // holds all factors
    private static ArrayList <BigInteger> primeFactors = new ArrayList<>();    // holds prime factors
    private static Scanner userInput = new Scanner(System.in);
    private static BigInteger numBig;
    private static String input;
    private static boolean primeFlag = false;
    private static long endFactorTimeMillis = 0;
    private static long startFactorTimeMillis = 0;
    private static long endFactorTimeNano = 0;
    private static long startFactorTimeNano = 0;    
    private static BigInteger two = new BigInteger("2");
    public static Scanner scan = new Scanner(System.in);
    
    private static BigInteger i = new BigInteger("2");   // starting value for iterator 
    
    public Interface(){
    	 
    	setLayout(new GridLayout (1,2)); //overall layout for entire tab split left and right sides
    	 
        JPanel left = new JPanel();
        left.setLayout(new GridLayout(3,1));
    	 
        // code block adds the user input fields
        JLabel startMessage = new JLabel("This program uses brute force to find whether a number is prime.");
        left.add(startMessage);
    
        JPanel leftDivide = new JPanel();
        leftDivide.setLayout(new GridLayout(1,2));// creates layout for the left side of the table tab
        left.add(leftDivide);//adds second element to left side of gui overall layout

        JLabel userMessage = new JLabel("Enter Number to be tested.");
        leftDivide.add(userMessage);
        enterNum = new JFormattedTextField();//textfield to enter number to be checked
        leftDivide.add(enterNum);// adds textfield to left side

        // adds the button to create athlete objects
        JPanel button = new JPanel();
        button.setLayout(new BorderLayout());
        left.add(button);
    
        button1 = new JButton("Check Prime");
        button.add(button1, BorderLayout.SOUTH); 
        
        //BEGINS CODE FOR RIGHT SIDE
        JPanel right = new JPanel();
                
        // creates text area to display 
        textArea = new JTextArea("No Input", 27, 38);

        JPanel scrollPane = new JPanel();
        JScrollPane scrollPane1 = new JScrollPane(textArea); // adds text display to the scroll so the bar will display
        scrollPane.add(scrollPane1);
    
        textArea.setEditable(false);
        right.add(scrollPane);
    
        add(left);//adds to overall (1,2) layout
        add(right);//adds to overall (1,2) layout on right side

        button1.addActionListener( new ButtonListener( ) ); // listener for check prime button 
        }           // end Interface
    
    
    
    private static class ButtonListener implements ActionListener {      // ButtonListener Class
    
        public void actionPerformed(ActionEvent event){
          
     	String userNum = enterNum.getText();
        System.out.println("Number entered: " + userNum);
     	
     	numBig = new BigInteger(userNum);
        //System.out.println("Number entered as a BigInteger: " + numBig);      // used for testing
     	
     	while(isValidInput() == false){     // reject non numerical input
            textArea.setText("Error: Enter valid input!\n");
            System.out.println("Invalid input!");
         }
        
         BigInteger i;              // iterator for isPrime method
         long endPrimeTestMillis = 0;     // long for getting millis 
         long endPrimeTestNano = 0;         // long for nano
         long startPrimeTestNano = System.nanoTime();    // start timer   // depreciated 
         long startPrimeTestMillis = System.currentTimeMillis();
         //System.out.println("Timer started");             // used for testing
         
         primeFlag = isPrime(numBig); // check candidate for primality
         
         if(primeFlag == true){      // if prime, stop timer
             endPrimeTestMillis = System.currentTimeMillis();
             endPrimeTestNano = System.nanoTime(); 
             textArea.setText("Number entered is prime!\n\n");
         } 
         else{ 
             endPrimeTestMillis = System.currentTimeMillis();
             endPrimeTestNano = System.nanoTime(); 
             textArea.setText("Number entered is NOT prime!\n\n");
             textArea.append("Finding factors...");
             i = two;               // set BigInteger iterator to 2 
            
             startFactorTimeMillis = System.currentTimeMillis();       // start factor timer
             startFactorTimeNano = System.nanoTime();

                 // finds factors and separates them into prime factors and composite factors
             while(i.compareTo(numBig.divide(two)) == (-1) || i.compareTo(numBig.divide(two)) == (0) ){  
                 if (numBig.mod(i).equals(BigInteger.ZERO)){ 
                   // if i is a factor, add it to the arraylist      
                     arl.add(i); 
                    if(isPrime(i)){
                    // if i is a prime factor, add it to the prime factor arraylist
                        primeFactors.add(i);    
                    }       // endif
                 }          // endif
                 i = i.add(BigInteger.ONE);     // increment by one
                 //System.out.println("i = " + i);   // used for debugging and testing
             }        
             endFactorTimeMillis = System.currentTimeMillis();     // end of timing for factorization
             endFactorTimeNano = System.nanoTime();
             
             textArea.append("The factors of the candidate are: " + arl.toString()+ "\n\n");
             textArea.append("The PRIME factors of the candidate are:" + primeFactors.toString()+"\n");
             //System.out.println("The factors of the candidate are: " + arl.toString() + "\n");    //  used for CLI interface
             //System.out.println("The factors of the candidate are: " + arl.toString() + "\n");
         }
         if (endPrimeTestMillis-startPrimeTestMillis <= 5)
             textArea.append("Processor time spent determining primality: " + (endPrimeTestNano-startPrimeTestNano) + " nanoseconds.\n");
         else textArea.append("Processor time spent determining primality: " + (endPrimeTestMillis-startPrimeTestMillis) + " milliseconds.\n");
         
         if (endFactorTimeMillis - startFactorTimeMillis <= 5)
            textArea.append("Processor time spent finding factors: "+ (endFactorTimeNano-startFactorTimeNano)+" nanoseconds.\n");
         else textArea.append("Processor time spent finding factors: "+ (endFactorTimeMillis-startFactorTimeMillis)+" milliseconds.\n");

     }

     // Computes whether a number is prime
     private static boolean isPrime(BigInteger n){
         int comp = n.compareTo(BigInteger.ONE);
         if (comp == 0 || comp == -1)  //  if (n <= 1) it is NOT prime
             return false;
         else if (n.equals(two))       //if (n == 2) it is prime
             return true;
        else if (n.mod(two).equals(BigInteger.ZERO)){       // if its even, its not prime
            //System.out.println("Number is even, thus not prime!");
            return false;
        }
         else{                         // otherwise test for primality
         //System.out.println("Start testing...");
         
         // BigInteger version of  while ( i <= Math.sqrt(n))
         while(i.compareTo(bigIntSqRootFloor(n))==(-1) || i.compareTo(bigIntSqRootFloor(n))==(0) ){    //while ( i <= FLOOR[Math.sqrt(n))]{
             // if i is a factor, then n is NOT prime
            
             if(n.mod(i).compareTo(BigInteger.ZERO) == 0){          // if even, its not prime
                 return false;
             }
             else{ // otherwise increment to check next number
                 i = i.add(BigInteger.ONE);
                 //System.out.println("Adding one...: " + i );
             }   // end else
         
         }   // end while
         }   // end else
         return true;  
         
     }   // end isPrime method



     // Checks input to see if it is a valid integer
     private static boolean isValidInput(){   
         try{
            
             BigInteger otherNum;
             otherNum = new BigInteger(numBig.toString());
            
             textArea.append(otherNum.toString()+"\n");
             otherNum = new BigInteger(numBig.toString());
             
             textArea.append("num big is " + otherNum.toString()+"\n");
         }

         catch (NumberFormatException exception){
             //System.out.println("Bad input detected");  // used for debugging
             return false;
         }
         return true;
     }   // end isValidInput method   



     // calculates floor of square root of BigInteger passed to it
     public static BigInteger bigIntSqRootFloor(BigInteger x)
             throws IllegalArgumentException {
         if (x.compareTo(BigInteger.ZERO) < 0) {
             throw new IllegalArgumentException("Negative argument.");
         }
         // square roots of 0 and 1 are trivial and
         // y == 0 will cause a divide-by-zero exception    
         if (x.equals(BigInteger.ZERO) || x.equals(BigInteger.ONE)) {
             return x;
         } // end if
         BigInteger two = BigInteger.valueOf(2L);
         BigInteger y;
         // starting with y = x / 2 avoids magnitude issues with x squared
         for (y = x.divide(two);
                 y.compareTo(x.divide(y)) > 0;
                 y = ((x.divide(y)).add(y)).divide(two));
         
         return y;
     } // end bigIntSqRootFloor method
    
    } //end of actionPerformed method


    // returns current data and time. Used for brute forcing very large numbers as nano and millis will overflow
private static String getDateTime() {       
     DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
     Date date = new Date();
     return dateFormat.format(date);
 }


} //end of Interface class

