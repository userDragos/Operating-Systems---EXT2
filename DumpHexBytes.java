import java.util.*;
import java.io.*;
import java.nio.*;
import java.lang.*;

public class DumpHexBytes{
    private int startPosition;
    private int finishPosition;
    private byte[] byteArray;

    public void dumpHex(){

        /**
        * print the sequence in string format from start up to the start + length 
        */
        System.out.println("................................");
        int k =0;
        for(byte b : byteArray){
            int copy = b;
            String he = Integer.toHexString(copy); 
            System.out.print(he);
            if(k%128 ==0)System.out.println("");
            if(k%1024 ==0) System.out.println("\n.......1024........\n");
            k++;
        }
        System.out.println("................................");
    }
    /**
     * print the sequence in string format from start up to the start + length 
     */
    public void dumbString(){
        int k=0;
        for(byte c : byteArray){
            int copy = c;   
            System.out.print((char)byteArray[k]);
            k++;
        }
    }

    /**
     * 
     * @param f         is represented by the initial file that the volume was reading
     * @param s         start from which the program will begin to read and print 
     * @param no        length of the bytes to be read and print
     */
    public DumpHexBytes(Ext2 f, int s, int no){
        byteArray = new byte[no];
        byteArray = f.read(s,no);
        ByteBuffer buffer = ByteBuffer.allocate(no);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(byteArray);
        startPosition = s;
        finishPosition = s+no;
    }
}