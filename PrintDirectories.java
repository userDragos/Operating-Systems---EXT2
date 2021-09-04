import java.util.*;
import java.io.*;
import java.nio.*;
import java.lang.*;

public class PrintDirectories{
    LinkedList<DirectoryEntry> directories = new LinkedList<DirectoryEntry>();
    Ext2 file;
    int[] dumb;
    GetInodeInfo[] info = new GetInodeInfo[5136];

    /**
     * print the directories from the offset given in the constructor
     */
    public void printDirectories(){
        //directories.clear();
        for(DirectoryEntry d: directories){
            
            if(d.getInode() != 0){
                //white
                System.out.print("\u001B[37m");

                info[d.getInode()-1].printInodes();
                //blue
                if(d.getTypeIndicator()==2){
                    System.out.print("\u001B[34m");
                }
                //green
                if(d.getTypeIndicator()==1){
                    System.out.print("\u001B[32m");
                }
                d.printDirectory();
            } 
        }
    }
    /**
     * 
     * @return a linked list of directories begining at the offset given 
     */
    public LinkedList<DirectoryEntry> getDirectories(){
        return directories;
    }
    /**
     * 
     * @param f     is represented by the initial file that the volume was reading
     * @param of    offset form which the directories will begin
     * @param in    The complete array of inodes in the filesystem 
     */
    public PrintDirectories(Ext2 f, int of, GetInodeInfo[] in){
        int count=0;
        file = f;
        info = in;
        while(count<1024){
            DirectoryEntry entry = new DirectoryEntry(f,1024*of+count);
            directories.add(entry);
            count += entry.getTotalSize();
        }
    }
}