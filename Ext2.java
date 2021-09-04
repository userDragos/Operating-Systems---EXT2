import java.util.*;
import java.io.*;
import java.nio.*;
import java.lang.*;

public class Ext2{

    private Volume volume;
    private long position;

    /**
     * 
     * @param start     the position where the bytes are going to be taken from 
     * @param length    how many bytes would be taken from the start 
     * @return          return a byte array taken from start + the length
     */
    public byte[] read(long start, long length){
        byte[] bytes = new byte[(int)length];
        try{
            volume.getFile().seek(start);
            volume.getFile().read(bytes,0,(int)length);
            return bytes;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    /**
     * 
     * @param length    return the amount of bytes from the position of the cursor
     * @return          return the bytes take in a byte array 
     */
    public byte[] read(long length){
        byte[] bytes = new byte[(int)length];
        try{
            volume.getFile().seek(position);
            volume.getFile().read(bytes,0,(int)length);
            return bytes;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    /**
     * 
     * @param pos change the position of the cursor to this 
     */
    public void seek(long pos){
        position = pos;
    }
    
    /**
     * 
     * @return the positon of the cursor in the file 
     */
    public long position(){
        return position;
    }

    /**
     * 
     * @return return the file size 
     */
    public long size(){
        try{
            long fSize = volume.getFile().length();
            return fSize;
        }catch(Exception e){
            System.out.println(e);
            return 0;
        }  
    }
    /**
     * 
     * @param v in order to use this class there must be a volume that holds the file 
     */
    public Ext2(Volume v){
        volume = v;
        position = 0;
    }
}