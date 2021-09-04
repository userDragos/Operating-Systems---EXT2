import java.util.*;
import java.io.*;
import java.nio.*;
import java.lang.*;

public class PrintText{
    DirectoryEntry directories;
    Ext2 file;
    int offset;


    /**
     * it will print the text from the location given as an offset 
     */
    public void printText(){
        //white
        System.out.print("\u001B[37m");
        //info[directories.getInode()-1].printInodes();
        //directories.printDirectory();
        //7681*1024
        DumpHexBytes dump = new DumpHexBytes(file,offset,1024);
        dump.dumbString();
    }
    
    /**
     * 
     * @param f     is represented by the initial file that the volume was reading
     * @param of    offset form which the directories will begin
     */
    public PrintText(Ext2 f, int of){
        file = f;
        offset = of*1024;
        directories = new DirectoryEntry(f,offset);
    }
}