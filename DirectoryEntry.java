import java.util.*;
import java.io.*;
import java.nio.*;
import java.lang.*;

public class DirectoryEntry{
    private int inode;
    private short totalSize;
    private byte nameLength;
    private byte typeIndicator;
    private byte[] name;
    private int startInode;
    private String sName;

    /**
     * print the directory info
     */
    public void printDirectory(){
        /*
        System.out.println("\n..........Print Directory..........\n");
        System.out.println("Inode Directory entry: " + inode);
        System.out.println("Total size of this entry: " +totalSize);
        System.out.println("Name Length of the directory: "+ nameLength);
        System.out.println("Type Indicator: "+ typeIndicator);
        System.out.print("Name of the directory:");
        */
        sName = new String(name);
        System.out.print(sName+ "\n");
    }

    /**
     * 
     * @return return the total size of this directory 
     */
    public int getTotalSize(){
        return totalSize;
    }
    /**
     * 
     * @return return the inode from which this directory is pointing to 
     */
    public int getInode(){
        return inode;
    }
    /**
     * 
     * @return return the directory name 
     */
    public String getDirName(){
        return sName;
    }
    /**
     * 
     * @return return the type of this directory
     */
    public byte getTypeIndicator(){
        return typeIndicator;
    }

    /**
     * 
     * @param f     is represented by the initial file that the volume was reading
     * @param of    offset form which the superblock will begin
     */
    public DirectoryEntry(Ext2 f, int of){
        byte[] bytes = new byte[1024];
        bytes = f.read(of,1024);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.put(bytes);
        inode = buffer.getInt(0);
        totalSize = buffer.getShort(4);
        nameLength = buffer.get(6);
        typeIndicator = buffer.get(7);
        buffer.position(8);
        byte[] tempName = new byte[nameLength];
        buffer.get(tempName,0,nameLength);
        name = tempName;
    }
}