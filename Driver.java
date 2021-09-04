import java.util.*;
import java.io.*;
import java.nio.*;
import java.lang.*; 

public class Driver{
    public static void main(String[] args){
        Volume volume = new Volume("ext2fs");
        Ext2 file = new Ext2(volume);
        GetSuperBlock superBlock = new GetSuperBlock(file,1024);
        superBlock.printSuperBlock();
        GetBlockDescriptor blockDescriptor = new GetBlockDescriptor(file,superBlock);
        blockDescriptor.printBlockDescriptor();

        //the inode table from all 3 block descriptor
        int[] inodeTable = blockDescriptor.getInodetable();
        GetInodeInfo[] inodes = new GetInodeInfo[inodeTable.length];

        System.out.print("\n\n");

        //array containing all the inodes in the system
        GetInodeInfo[] allInodes = new GetInodeInfo[5136];
        int count=0;
        ///for loop to find the inodes in the system and populate the inode array  
        for(int i=0; i<5136; i++){
            if(count==1712){
                count=0;
            }
            if(i<1712){
                allInodes[i] = new GetInodeInfo(file,1024*84 + 128*count);
                count++;
            }
            else if(i<3424 && i>=1712){
                
            allInodes[i] = new GetInodeInfo(file, 1024*8276 + 128*count);
                count++;
            }
            else if(i<5136 && i>=3424){
                allInodes[i] = new GetInodeInfo(file, 1024*16387 + 128*count);
                count++;
            }
        }
        

        Scanner in = new Scanner(System.in);
        int navigator =2;

        //linked list of directories that begin at the given pointer 
        LinkedList<DirectoryEntry> directoryList = new LinkedList<DirectoryEntry>();
        PrintDirectories[] dummyDir = new PrintDirectories[12];

        while(true){
            directoryList.clear();
            for(int x=0; x<12; x++){
                //adding the data from 12 data blocks to the directory linked list 
                if(allInodes[navigator-1].getDataPointer(x)!=0){
                    dummyDir[x] = new PrintDirectories(file ,allInodes[navigator-1].getDataPointer(x),allInodes);
                    dummyDir[x].printDirectories(); 
                    directoryList.addAll(dummyDir[x].getDirectories());
                }
                
            }
            String s = in.nextLine();
            
            for(DirectoryEntry directory: directoryList){
                //access the directories in the filesystem
                if(s.equalsIgnoreCase(directory.getDirName()) == true ){
                    int temporary = directory.getInode();
                    if(directory.getTypeIndicator() == 1){
                        //print the tect files 
                        for(int x=0; x<12; x++){
                            if(allInodes[temporary-1].getDataPointer(x)!=0){
                                PrintText text = new PrintText(file,allInodes[temporary-1].getDataPointer(x));
                                text.printText();
                                System.out.println();
                            }
                        }
                    }
                    if(directory.getTypeIndicator() == 2){
                        navigator = directory.getInode();
                        break;
                    }
                }
            }     
        }
        
        //DumpHexBytes hex = new DumpHexBytes(file, 1024*298,1024);
        //hex.dumpHex();
    }
}