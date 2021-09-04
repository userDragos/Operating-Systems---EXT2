import java.util.*;
import java.io.*;
import java.nio.*;
import java.lang.*;

public class GetInodeInfo{

    private short fileMode;
    private short userIdOfOwner;
    private int fileSizeLower;
    private int lastAccesstime;
    private int creationTime;
    private int lastModifiedTime;
    private int deletedTime;
    private short groupIdOwner;
    private short numberHardLinks;
    private int checkDirect;

    private int[] pointersToData = new int[12];
    private int indirectpointer;
    private int doubleIndirectPointers;
    private int tripleIndirectPointers;
    private int[] dummy = new int[100];

    private int offset;


    /**
     * print the Inode info that is necessary to be used in order to only read a file/directory 
     */
    public void printInodes(){
        /*
        System.out.println("\n..................Inode Info..................");
        System.out.println("File mode: "+ fileMode);
        System.out.println("User id: "+ userIdOfOwner);
        System.out.println("File Size Lower in bytes: "+ fileSizeLower);
        System.out.println("Last access time: " + new Date((long)lastAccesstime * 1000));
        System.out.println("Creation Time: " + new Date((long)creationTime *1000));
        System.out.println("Last modified Time: " + new Date((long)lastModifiedTime * 1000));
        System.out.println("Deleted time: "+deletedTime);
        System.out.println("Group ID of Owner: "+groupIdOwner);
        System.out.println("Number of hard links: "+numberHardLinks+"\n");
        for(int x=0; x<12; x++){
            System.out.println("Pointer "+ x + " to Direct data: "+pointersToData[x]);
        }

        System.out.println("\nIndirect pointer: "+indirectpointer);
        System.out.println("Double indirect pointer: "+doubleIndirectPointers);
        System.out.println("Triple indirect pointer: "+tripleIndirectPointers);
        */
        if((fileMode & 0x4000) == 0x4000){
            System.out.print("d");
        }
        if((fileMode & 0x6000) == 0x6000){
            System.out.print("b");
        }
        if((fileMode & 0x2000)==0x2000){
            System.out.print("c");
        }
        if((fileMode & 0x8000) == 0x8000){
            System.out.print("-");
        }
        if((fileMode &0xA000)==0xA000){
            System.out.print("l");
        }
        if((fileMode&0x1000) ==0x1000){
            System.out.print("p");
        }
        if((fileMode & 0xC000) == 0xC000){
            System.out.print("s");
        }
        //Process user read, write, execute
        if((fileMode &0x0100)==0x0100){
            System.out.print("r");
        }
        else{
            System.out.print("-");
        }
        if((fileMode &0x0080)==0x0080){
            System.out.print("w");
        }
        else{
            System.out.print("-");
        }
        if((fileMode &0x0040)==0x0040){
            System.out.print("x");
        }
        else{
            System.out.print("-");
        }
        //Process group read,write execute
        if((fileMode &0x0020)==0x0020){
            System.out.print("r");
        }
        else{
            System.out.print("-");
        }
        if((fileMode &0x0010)==0x0010){
            System.out.print("w");
        }
        else{
            System.out.print("-");
        }
        if((fileMode &0x0008)==0x0008){
            System.out.print("x");
        }
        else{
            System.out.print("-");
        }
        //Process other read,write execute
        if((fileMode &0x0004)==0x0004){
            System.out.print("r");
        }
        else{
            System.out.print("-");
        }
        if((fileMode &0x0002)==0x0002){
            System.out.print("w");
        }
        else{
            System.out.print("-");
        }
        if((fileMode &0x0001)==0x0001){
            System.out.print("x");
        }
        else{
            System.out.print("-");
        }

        //Hard Links, User id, group Id , date
        System.out.print(" "+numberHardLinks+" " + userIdOfOwner+" "+groupIdOwner+" "+ new Date((long)lastModifiedTime * 1000 )+ " ");
 
    }

    /**
     * 
     * @return the location of the indirect pointer from this Inode 
     */
    public int getIndirect(){
        return indirectpointer;
    }
    /**
     * 
     * @return return the location of the double indirect pointer from this Inode 
     */
    public int getDoubleIndirect(){
        return doubleIndirectPointers;
    }
    /**
     * 
     * @return return the location of the tripple indirect pointer from this Inode 
     */
    public int getTrippleIndirect(){
        return tripleIndirectPointers;
    }

    /**
     * 
     * @param x     the position of the Dta block pointer in the array 
     * @return      return the Data pointer at the X position in the array 
     */
    public int getDataPointer(int x){
        return pointersToData[x];
    }

    /**
     * 
     * @return return the number of hard links to this Inode
     */
    public short getNoHardLinks(){
        return numberHardLinks;
    }

    /**
     * 
     * @return  return the file size to which the Inode is pointing 
     */
    public int getFileSize(){
        return fileSizeLower;
    }

    /**
     * 
     * @param f     is represented by the initial file that the volume was reading
     * @param of    offset form which the superblock will begin
     */
    public GetInodeInfo(Ext2 f, int of){
        byte[] bytes = new byte[116];
        f.seek(0);
        bytes = f.read(of,116);
        ByteBuffer buffer = ByteBuffer.allocate(116);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(bytes);

        fileMode = buffer.getShort(0);
        userIdOfOwner = buffer.getShort(2);
        fileSizeLower = buffer.getInt(4);
        lastAccesstime = buffer.getInt(8);
        creationTime = buffer.getInt(12);
        lastModifiedTime = buffer.getInt(16);
        deletedTime = buffer.getInt(20);
        groupIdOwner = buffer.getShort(24);
        numberHardLinks = buffer.getShort(26);
        
        //loop 12 times to populate the array with data pointers
        for(int i=0; i<12; i++){
            pointersToData[i]=buffer.getInt(40+ (i*4));
        }
        indirectpointer = buffer.getInt(88);
        doubleIndirectPointers = buffer.getInt(92);
        tripleIndirectPointers = buffer.getInt(96);
    }
}