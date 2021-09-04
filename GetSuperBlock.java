import java.util.*;
import java.io.*;
import java.nio.*;
import java.lang.*;

public class GetSuperBlock{
    private short magicNum;
    private int noInodes;
    private int noBlocks;
    private int blockSize;
    private int blocksPerGroup;
    private int numberInodes;
    private int sizeEachInode;
    private int blockCount;
    private byte[] volumeName = new byte[16];
    private int offset;

    /**
     * by calling this method the superblock will print its values 
     */
    public void printSuperBlock(){
        String mN = Integer.toHexString(magicNum);
        System.out.println("\n..................Super Block..................");
        System.out.printf("The magic number is: 0x%x\n", magicNum);
        System.out.println("Total number of Inodes in filesystem: "+noInodes);
        System.out.println("Total number of blocks in filesystem: "+noBlocks);

        blockSize = 1024*((int)Math.pow(2,blockSize));
        System.out.println("Filesystem block size: "+blockSize);
        System.out.println("Number of blocks per Group: "+blocksPerGroup);
        System.out.println("Number of Inodes per Group: "+numberInodes);
        System.out.println("Size of each inode in bytes "+sizeEachInode);

        if(noBlocks % blocksPerGroup !=0 ) blockCount++;
        System.out.println("Number of blocks: "+blockCount);
        System.out.println("Volume Label: "+ new String(volumeName));
    }

    /**
     * 
     * @return get the total number of Inodes in the filesystem 
     */
    public int getNoInodes(){
        return noInodes;
    }

    /**
     *  
     * @return get the total number of Inodes in a group
     */
    public int getNoInodesGroup(){
        return numberInodes;
    }

    /**
     * 
     * @return get the size of an Inode
     */
    public int getSizeInode(){
        return sizeEachInode;
    }

    /**
     * 
     * @return get the number of description blocks in the filesystem 
     */
    public int getNoDesBlocks(){
        return blockCount;
    }

    /**
     * 
     * @return get the number of blocks in a group
     */
    public int getNoBlocks(){
        return blocksPerGroup;
    }

    /**
     * 
     * @return get the block size 
     */
    public int getBlockSize(){
        return blockSize;
    }
    /**
     * 
     * @param f     is represented by the initial file that the volume was reading
     * @param of    offset form which the superblock will begin
     */
    public GetSuperBlock(Ext2 f,int of){
        byte[] bytes = new byte[1024];
        f.seek(of);
        bytes = f.read(of,1024);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(bytes);
        offset = of;
        magicNum = buffer.getShort(56);
        noInodes = buffer.getInt(0);
        noBlocks = buffer.getInt(4);
        blockSize = buffer.getInt(24);
        blocksPerGroup = buffer.getInt(32);
        numberInodes = buffer.getInt(40);
        sizeEachInode = buffer.getInt(88);
        blockCount = noBlocks / blocksPerGroup;
        buffer.position(120); 
        buffer.get(volumeName,0,16);   
    }
}