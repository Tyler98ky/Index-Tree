import java.io.*;
import java.util.Scanner;

public class ReadingFromFileExample {
	
	public static void main(String [] args) {

		String fileName = "pg100.txt";
		
		try {
			Scanner scanner = new Scanner(new File(fileName));
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				String[] words = line.split("\\s+");
				for(String word : words){
					word = word.replaceAll("\\W", "").toLowerCase();
					System.out.println(word);
				}
			}
			scanner.close();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}


