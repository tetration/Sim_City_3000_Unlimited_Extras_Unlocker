import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

/* Imports for the Unzip Class*/
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.net.MalformedURLException;
import java.net.URL;

/* Packages for File Replacer*/
import java.nio.file.*;


public class Simcity3000_Extras{
	public static Scanner user_input = new Scanner(System.in);
	public static void main(String[] args) {
		welcome();
		menu();
	}
	public static void welcome(){
		System.out.println("Simcity3000_Extras\n"+
			"Awakening the true power of your Simcity 3000 \n"+
			"Developer: Tetration \n"+
			"Contact: Tetration@outlook.com\n"+
			"Version 0.01");
	}
	public static void menu(){
		int user;
		System.out.println("Simcity 3000_Extra_Unlcoker(No GUI Edition) Mainmenu\n"+
		"Type 1:Unlock hidden Soundtrack files in game Directory\n"+
		"Type 2:Increases Audio Quality of Simcity 3000 Unlimited\n"+
		"Type 3:Download Extra terrains and scenarios\n"+
		"Type 4:Not implemented yet\n"+
		"Type 5:About/Version\n");
		user=user_input.nextInt();
		switch (user) {
			case 1:System.out.println("Soundtrack Unlocker");
				unlocker();
			case 2:System.out.println("Simcity 3000 Higher Audio Quality ");
				highquality_soundtrack_unlocker();

			case 3:System.out.println("Coming soon! Not implemented yet!");
				menu();

			case 4:System.out.println("Coming soon! Not implemented yet!");
				menu();

			case 5: welcome();
				Prompt_Messages.ys_question();

			default:
			System.out.println("Error... please try again...");
			menu();
			
		}
	}
	public static void unlocker(){
		System.out.println("Unlocking Files....");
		try{
			String original=("/INI_Unlocker/AUDIO.INI");
			String target=("/res/sound/audio.ini");
		  FileReplacer.replacer(original,target);
		}catch(IOException e){
		  e.printStackTrace();
		}
		Prompt_Messages.ys_question();

	}
	public static void highquality_soundtrack_unlocker(){
		/*File which will act as the replacement */
		String new_files []= {"/Vanilla_Music/3KG2.XA","/Vanilla_Music/3KJ1M.XA","/Vanilla_Music/3KJ2M.XA","/Vanilla_Music/3KJ3M.XA","/Vanilla_Music/3KJ4M.XA","/Vanilla_Music/3KP3M.XA","/Vanilla_Music/3KP5M.XA"};
		/*Target: Files which will be replaced*/
		String old_files_location []= {"/res/sound/music/3kg2.xa","/res/sound/music/3kj1m.xa","/res/sound/music/3kj2m.xa","/res/sound/music/3kj3m.xa","/res/sound/music/3kj4m.xa","/res/sound/music/3kp3m.xa","/res/sound/music/3kp5m.xa"};
		try{
			System.out.println("Unzip Completed!");
			for (int i=0; i<=6 ;i++) {
				FileReplacer.replacer(new_files[i],old_files_location[i]);
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		System.out.println("Tasks Finished!");
		Prompt_Messages.ys_question();
	}
}
class Prompt_Messages{
	public static Scanner p_messages= new Scanner(System.in);
	public static void ys_question(){
		int ys=9;
		System.out.println("Press 0 to return to main menu");
		System.out.println("Press 1 to Quit the program");
		ys=p_messages.nextInt();
		if (ys==1) {
			System.out.println("Exiting the program...");
			System.exit(0);
		}
		if (ys==0) {
			System.out.println("Returning to the Mainmenu");
			Simcity3000_Extras.menu();
			
		}
		else{
			System.out.println("Error... please try again type 1 or 2");
			ys_question();
		}

	}

}




class Download_Stuff{
	public static void downloader(){
		String dirName = "/terrains";
		try{
		// Using NIO
		  Download_Stuff.downloadFileFromURLUsingNIO(
		    dirName + "/sampleFiles.zip",
		    "https://github.com/Someone/Test/archive/master.zip");

		  System.out.println("Downloaded file from github using NIO");
		  System.out.println("---------------------------");

		} catch (MalformedURLException e) {
		  e.printStackTrace();
		} catch (IOException e) {
		  e.printStackTrace();
		}
	}
 // Using NIO
 private static void downloadFileFromURLUsingNIO(String fileName,String fileUrl) throws IOException {
  URL url = new URL(fileUrl);
  ReadableByteChannel rbc = Channels.newChannel(url.openStream());
  FileOutputStream fOutStream = new FileOutputStream(fileName);
  fOutStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
  fOutStream.close();
  rbc.close();
 }

}


/**
 * This utility extracts files and directories of a standard zip file to
 * a destination directory.
 * 
 *
 */
class UnzipUtility {
    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;
    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
} 

class Unzip_New_Maps {
    public static void unzipper(String zip_path,String un_zip_path) {
        String zipFilePath = "terrains.zip";
        String destDirectory = "/scenarios";
        UnzipUtility unzipper = new UnzipUtility();
        try {
            unzipper.unzip(zipFilePath, destDirectory);
        } catch (Exception ex) {
            // some errors occurred
            ex.printStackTrace();
        }
    }
}


class FileReplacer {
    public static void replacer(String origin,String replaced) throws IOException {
        // Get paths for input and target files.
        final String dir = System.getProperty("user.dir");
        FileSystem system = FileSystems.getDefault();
        Path original=system.getPath(dir+origin);/* File which will be used*/
        System.out.println("original:"+original);
        File parent = new File("").getCanonicalFile();// Created for the purpose of going one directory above
        Path target =system.getPath(parent.getParent()+replaced);/* File which will be replaced*/
        System.out.println("target:"+target);
        // Replace an existing file with REPLACE_EXISTING.
        // ... No FileAlreadyExistsException will be thrown.
        //parent.GetParent() makes possible to go one directory above the src folder
        try{
        	Files.copy(original, target, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
        	e.printStackTrace();
        }
        // Helpful message.
        System.out.println("File:" +replaced+ "changed succesfully!");
    }

}
