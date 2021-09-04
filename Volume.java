import java.util.*;
import java.io.*;
import java.nio.*;
import java.lang.*;

public class Volume{

    private RandomAccessFile file;

    /**
     * 
     * @return return a the file read by the volume using random access file 
     */
    public RandomAccessFile getFile(){
        return file;
    }

    /**
     * 
     * @param s the location of the ext2 file to be read by the program. 
     */
    public Volume(String s){
        try{
            file = new RandomAccessFile(s, "r");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}