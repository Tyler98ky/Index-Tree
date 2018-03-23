import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Your class. Notice how it has no generics.
// This is because we use generics when we have no idea what kind of data we are getting
// Here we know we are getting two pieces of data:  a string and a line number
public class IndexTree {

	// This is your root 
	// again, your root does not use generics because you know your nodes
	// hold strings, an int, and a list of integers
	private IndexNode root;
	
	// Make your constructor
	// It doesn't need to do anything
	private IndexTree(){
	}
	
	// complete the methods below
	
	// this is your wrapper method
	// it takes in two pieces of data rather than one
	// call your recursive add method
	private void add(String word, int lineNumber){
		this.root = add(this.root, word, lineNumber);
	}
	
	
	
	// your recursive method for add
	// Think about how this is slightly different the the regular add method
	// When you add the word to the index, if it already exists, 
	// you want to  add it to the IndexNode that already exists
	// otherwise make a new indexNode
	private IndexNode add(IndexNode root, String word, int lineNumber){
		if(root == null){
			return new IndexNode(word, lineNumber);
		}
		if(root.word.equals(word)){
			root.occurences++;
			root.list.add(lineNumber);
			return root;

		} else if(root.word.compareTo(word) > 0){
				root.left = add(root.left, word, lineNumber);
				return root;
		}
		else {
			root.right = add(root.right, word, lineNumber);
			return root;
		}
	}

	
	// returns true if the word is in the index
	private boolean contains(String word){
		IndexNode temp = this.root;

		while(temp != null){
			if(temp.word.equals(word)){
				return true;
			} else{
				if(temp.word.compareTo(word) < 0){
					temp = temp.right;
				}else{
					temp = temp.left;
				}
			}
		}

		return false;
	}
	
	// call your recursive method
	// use book as guide
	private void delete(String word){
		this.root = delete(this.root, word);
	}
	
	// your recursive case
	// remove the word and all the entries for the word
	// This should be no different than the regular technique.
	private IndexNode delete(IndexNode root, String word){

		if (root == null) {
			return null;
		}
		int comparisonResult = word.compareTo(root.word);
		if (comparisonResult < 0) {
			root.left = delete(root.left, word);
			return root;
		} else if (comparisonResult > 0) {
			root.right = delete(root.right, word);
			return root;
		} else {  // root is the item we want to delete

			// case 1, root is leaf
			if (root.left == null && root.right == null) {
				return null;
			} // case 2, root has only left child
			else if (root.left != null && root.right == null) {
				return root.left;
			} else if (root.left == null) {
				return root.right;
			} else {
				IndexNode rootOfLeftSubtree = root.left;
				IndexNode parentOfPredecessor;
				IndexNode predecessor;

				if (rootOfLeftSubtree.right == null) {
					root.word = rootOfLeftSubtree.word;
					root.left = rootOfLeftSubtree.left;
					return root;
				} else {
					parentOfPredecessor = rootOfLeftSubtree;
					IndexNode current = rootOfLeftSubtree.right;
					while (current.right != null) {
						parentOfPredecessor = current;
						current = current.right;
					}

					predecessor = current;
					root.word = predecessor.word;
					parentOfPredecessor.right = predecessor.left;
					return root;

				}
			}

		}

	}
	
	
	// prints all the words in the index in inorder order
	// To successfully print it out
	// this should print out each word followed by the number of occurrences and the list of all occurrences
	// each word and its data gets its own line

	private void printIndex(){
		printIndex(this.root);
	}

	private void printIndex(IndexNode root){
		if(root == null) return;

		printIndex(root.left);
		System.out.println(root.toString());
		printIndex(root.right);
	}

	private void addWords(){
		String fileName = "pg100.txt";

		try {
			Scanner scanner = new Scanner(new File(fileName));
			int lineNum = 1;
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				String[] words = line.split("\\s+");
				for(String word : words){
					word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
					if(word.length() > 0)	this.add(word, lineNum);
				}
				lineNum++;
			}
			scanner.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		IndexTree index = new IndexTree();
		
		// add all the words to the tree
		index.addWords();
		// print out the index
		System.out.println("FINISHED");
		index.printIndex();
		// test removing a word from the index

		System.out.println("Does index contain the word combat: \n\n");
		System.out.printf("Before calling delete(combat): %b\n", index.contains("combat"));
		index.delete("combat");
		System.out.printf("After calling delete(combat): %b\n\n\n", index.contains("combat"));

		index.printIndex();
		
	}
}
