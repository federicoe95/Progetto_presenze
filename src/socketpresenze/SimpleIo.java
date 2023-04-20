package socketpresenze;

import java.io.*;
import java.text.*;

public class SimpleIo
{
  public SimpleIo() {
  }
    
  public static int getInt(){
    String testo = new String();
    int val = 0;
    try{
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      testo = in.readLine();
      val = NumberFormat.getInstance().parse(testo).intValue();  
    }
    catch(ParseException pe) {
      System.out.println("Attenzione errore di conversione numerica");
      val = 0;
    }
    catch(IOException e) {
      System.out.println("Attenzione errore nella lettura da tastiera");
      val = 0;
    }
    return val;
  }
    
  public static String Read()
  {
    InputStreamReader isr;
    BufferedReader tastiera;
    isr = new InputStreamReader(System.in);
    tastiera = new BufferedReader(isr);
    String r=null;
	
    try{
      r = tastiera.readLine();
    }
    catch(java.io.IOException e)
    {
      System.out.println("Attenzione errore nella lettura da tastiera");
    }
    return r;
  }

}
