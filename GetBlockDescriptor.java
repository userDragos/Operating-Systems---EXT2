import java.util.*;
import java.io.*;
import java.nio.*;
import java.lang.*;


public class GetBlockDescriptor{
    private Ext2 file;
    private int sBlocks;
    private int[] inodeTable = new int[3];
    private short[] noDirectories = new short[3];
    private GetInodeInfo[] inodesInfo;

    /**
     * print the block descriptor
     */
    public void printBlockDescriptor(){
        for(int x=0; x<3; x++){
            System.out.println("\n.................Block Descriptor " + x + "..................");
            System.out.println("Starting block address of inode table: " + inodeTable[x]);
            System.out.println("Number of directories in group: "+noDirectories[x]);
        }
    }

    /**
     * 
     * @return return where the inode begin on the system  
     */
    public int[] getInodetable(){
        return inodeTable;
    }

    /**
     * 
     * @param f         is represented by the initial file that the volume was reading
     * @param block     A super block  
     */
    public GetBlockDescriptor(Ext2 f,GetSuperBlock block){

        sBlocks = block.getNoDesBlocks();
        int offset;
        byte[] bytes = new byte[(int)f.size()];
        ByteBuffer buffer = ByteBuffer.allocate((int)f.size());
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        bytes = f.read(0,(int)f.size());
        buffer.put(bytes);
        //a for loop to find where the inode begin on each block and store the data in the array 
        for(int i=0; i<3; i++){    
            offset = 2048+32*i;
            inodeTable[i] = buffer.getInt(8+offset);
            noDirectories[i] = buffer.getShort(16+offset);  
        }
    }
}