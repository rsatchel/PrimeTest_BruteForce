/*  Uses Brute Force to determine whether a candidate
*   number is prime or not using BigInteger class
*
*   It then computes the prime and composite
*   factors of the candidate number and lists them separately
*/


import java.util.*;
import java.lang.Math;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.*;


public class BruteForce extends JApplet
{
	
	private int APPLET_WIDTH = 500, APPLET_HEIGHT = 400;
 


public static void main(String args[]){
	
	JFrame frame = new JFrame("Prime Checker");
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    //setTitle( "Prime Checker" );

    Interface panel = new Interface();
    frame.getContentPane().add(panel);
    frame.setSize( 1000, 500 );// set size of GUI
    frame.setVisible(true );
    
   
	}

}   // end class


    