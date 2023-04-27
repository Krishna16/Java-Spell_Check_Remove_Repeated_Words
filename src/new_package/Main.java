package new_package;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class Main {
	//this variable is used in some places and is used to
	//point to the default location of the package
	//'new_package' where all the files are stored
	//you can even enter a new directory, make sure
	//the other files are also pointing to this directory
	//itself wherever the variable 'directory' is used
	static String directory = "<enter your default directory>";
	
	static File originalFile, spellingFile;
	static Scanner sc = new Scanner(System.in);
	

	public static int simplified_lev(String word1, String word2) {
		//System.out.println(word1 + " " + word2);
		
		String [] words = ab_star(word1, word2);
		
		//System.out.println(words[0] + " " + words[1]);
		
		return Math.max(words[0].length(), words[1].length());
	}
	
	
	public static String[] ab_star(String word1, String word2) {
		String[] finalWords = new String[2];
		
		String original1 = word1;
		String original2 = word2;
		
		//this loop will replace the same letter found between
		//the two words with an empty character and must iterate
		//till the length of the word which is lesser, or else
		//will go out of bounds
		for (int i = 0; i < Math.min(word1.length(), word2.length()); i++) {
			boolean replaced = false;
			
			if (word1.charAt(i) == word2.charAt(i)) {
				word1 = word1.replaceFirst("" + word1.charAt(i), "");
				word2 = word2.replaceFirst("" + word2.charAt(i), "");
				replaced = true;
			}
			
			if (replaced) {
				i = i - 1;
			}
		}
		
		if (original1.equals(word1) && original2.equals(word2)) {
			finalWords[0] = word1;
			finalWords[1] = word2;
			
			return finalWords;
		}
		
		finalWords[0] = word1;
		finalWords[1] = word2;
		
		return finalWords;
	}
	
	
	public static String findDuplicateWord(String line) {
		String [] words = line.split(" ");
		
		//HashMap<String, Integer> wordOccurences = new HashMap<String, Integer>();
		
		for (int i = 0; i < words.length; i++) {
			if (i != words.length - 1) {
				if (words[i].equals(words[i + 1])) {
					return words[i];
				}
			}
		}
		
		return null;
	}
	
	
	public static ArrayList<String> findIncorrectWords(String line) throws FileNotFoundException {
		String [] words = line.split(" ");
		ArrayList<String> incorrectWords = new ArrayList<String>();
		
		for (int i = 0; i < words.length; i++) {
			boolean wordFound = false;
			
			words[i] = words[i].replaceAll("\\p{Punct}","");
			
			Scanner sc = new Scanner(spellingFile);
			sc.useDelimiter("\n");
			
			//System.out.println("When i is " + i + " " + sc.next());
			
			while (sc.hasNextLine()) {
				String currentWord = sc.nextLine();
				
				//System.out.println(currentWord + " " + currentWord.contains("."));
				
				if (currentWord.contains(".") || currentWord.contains("?")){
					sc.close();
					break;
				}
				
				
				if (currentWord.toLowerCase().equals(words[i].toLowerCase())) {
					wordFound = true;
					sc.close();
					break;
				}
			}
			
			if (!wordFound) {
				incorrectWords.add(words[i]);
			}
		}
		
		return incorrectWords;
	}
	
	
	public static HashMap<String, ArrayList<String>> getReplacementWords(ArrayList<String> incorrectWords) throws FileNotFoundException {
		if (incorrectWords != null) {
			
			//this HashMap will store the incorrect word along with
			//the replacement words it found from the spell.txt file
			HashMap<String, ArrayList<String>> replacementWords = new HashMap<String, ArrayList<String>>();
			
			for (int i = 0; i < incorrectWords.size(); i++) {
				Scanner sc = new Scanner(spellingFile);
				sc.useDelimiter("\n");
				
				//this HashMap will store the corrected word which has
				//length either =, +1 or -1 of the length of the current
				//word along with the levenshtein distance of the incorrect
				//word it is being compared with
				HashMap<String, Integer> distances = new HashMap<String, Integer>();
				
				while(sc.hasNext()) {
					String currentWord = sc.next();
					
					if (currentWord.contains(".") || currentWord.contains("?")){
						break;
					}
					
					//comparing the current word in the spell.txt file
					//with the incorrect word
					if (currentWord.length() == incorrectWords.get(i).length()
						|| currentWord.length() + 1 == incorrectWords.get(i).length()
						|| currentWord.length() - 1 == incorrectWords.get(i).length()
						|| currentWord.length() - 2 == incorrectWords.get(i).length()
						|| currentWord.length() + 2 == incorrectWords.get(i).length()) {
						distances.put(currentWord.replaceAll("\\p{Punct}",""), simplified_lev(currentWord.replaceAll("\\p{Punct}","").toLowerCase(), incorrectWords.get(i).toLowerCase()));
					}
				}
				
				sc.close();
				
				ArrayList<String> temp = new ArrayList<String>();
				
				for (Entry<String, Integer> entry: distances.entrySet()) {
					if (entry.getValue() == 1) {
						//System.out.println(entry.getKey() + " " + entry.getValue());
						temp.add(entry.getKey());
					}
				}
				
				//System.out.println("Temp: (" + incorrectWords.get(i) + ") " + Arrays.toString(temp.toArray()));
				
				if (temp.size() < 3) {
					for (Entry<String, Integer> entry: distances.entrySet()) {
						if (entry.getValue() == 2 || entry.getValue() == 3) {
							if (temp.size() == 3) {
								break;
							}
							temp.add(entry.getKey());
						}
					}
				}
				
				//System.out.println("Temp: (" + incorrectWords.get(i) + ") " + Arrays.toString(temp.toArray()));
				
				replacementWords.put(incorrectWords.get(i), temp);
			}
			
			return replacementWords;
		}
		
		return null;
	}
	
	
	public static void printArray(ArrayList<String> array) {
		if (array != null) {
			for (int i = 0; i < array.size(); i++) {
				System.out.println(array.get(i));
			}
		}
	}
	
	
	public static String [] printPrompt(String line, String word, boolean duplicate) {
		String firstString = "";
		String secondString = "";
		
		if (line.indexOf(word) != 0) {	
			for (int i = line.indexOf(word) - 2; i >= 0; i--) {
				if (line.charAt(i) == ' ') {
					break;
				}
				
				else {
					firstString += line.charAt(i);
				}
			}
		}
		
		else {
			firstString += line.charAt(0);
		}
		
		if (duplicate) {
			for (int i = line.indexOf(word); i < line.length(); i++) {
				if (line.charAt(i) == ' ') {
					secondString += line.charAt(i);
					break;
				}
			
				else {
					secondString += line.charAt(i);
				}
			}
		}
		
		if (line.lastIndexOf(word) != line.length() - 1) {
			boolean spaceEncountered = false;
			
			for (int i = line.lastIndexOf(word); i < line.length(); i++) {
				if (line.charAt(i) == ' ') {
					if (spaceEncountered) {
						break;
					}
					
					else {
						spaceEncountered = true;
						secondString += line.charAt(i);
						continue;
					}
				}
				
				else {
					secondString += line.charAt(i);
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(firstString);
		sb.reverse();
		
		String [] strings = new String[2];
		strings[0] = sb.toString();
		strings[1] = secondString;
		
		return strings;
	}
	
	
	public static void main(String [] args) throws IOException {
		System.out.println("Fancy Spell-Checker");
		
		String continueLoop = "";
		
		while (true) {
			if (continueLoop.isEmpty() || continueLoop.equals("continue")) {
				int lineNumber = 0;
				
				while (true) {
					try {
						//fileName requires the entire path of the file
						//to be checked, you can edit and append the
						//directory variable available at the top
						System.out.println("Files to check: ");
						String fileName = sc.nextLine();

						originalFile = new File(fileName);

						if (!originalFile.exists()) {
							throw new FileNotFoundException();
						}

						else {
							break;
						}
					}

					catch(Exception e) {
						System.out.println("This file does not exist! Please provide another one!");
					}
				}

				while (true) {
					//spellFile requires the entire path of the file
					//to be checked, you can edit and append the
					//directory variable available at the top
					System.out.println("File with known words [enter for default]: ");
					String spellFile = sc.nextLine();

					if (spellFile.isEmpty()) {
						spellingFile = new File("<full_path_to_spell.txt>");
					}

					else {
						try {
							spellingFile = new File(spellFile);

							if (spellingFile.exists()) {
								break;
							}
						}

						catch(Exception e) {
							System.out.println("This file does not exist!");
						}
					}
				}

				System.out.println("----------------------");
				System.out.println("Processing " + originalFile.getPath().toString().substring(originalFile.getPath().toString().lastIndexOf("\\") + 1, originalFile.getPath().length()));

				Scanner fileReader = new Scanner(originalFile);

				//this HashMapwill store the incorrect words along with
				//the replacement words it found from the spell.txt file
				HashMap<String, ArrayList<String>> wordFixes = new HashMap<String, ArrayList<String>>();

				ArrayList<Record> records = new ArrayList<Record>();

				while(fileReader.hasNext()) {
					lineNumber += 1;

					String line = fileReader.nextLine();

					//System.out.println(line);

					String duplicateWord = findDuplicateWord(line);

					if (duplicateWord != null) {
						String [] strings = printPrompt(line, duplicateWord, true);
						System.out.println("Line " + lineNumber + ": " + strings[0] + " " + strings[1]);
						System.out.println("  (0) " + duplicateWord + " " + duplicateWord + " (1) " + duplicateWord);

						System.out.println("Option: ");
						int choice = Integer.parseInt(sc.nextLine());

						if (choice == 0) {
							records.add(new Record(line, lineNumber));
						}

						else if (choice == 1) {
							records.add(new Record(line.replaceFirst(duplicateWord, ""), lineNumber));
						}
					}

					else {
						ArrayList<String> incorrectWords = null;

						//incorrectWords arraylist is already keeping track
						//of all the incorrect words in that specific line
						incorrectWords = findIncorrectWords(line);

						if (incorrectWords.size() != 0) {
							wordFixes = getReplacementWords(incorrectWords);

							//this HashMap will record all the corrections the user
							//wants to make with the incorrect words and will later
							//be used to replace the incorrect words in the line
							//with the correct ones in order to be written to the
							//new file (wrongWord, correctedWord)
							HashMap<String, String> correctedWords = new HashMap<String, String>();

							for (Entry<String, ArrayList<String>> entry: wordFixes.entrySet()) {
								//System.out.println(entry.getKey());
								//System.out.println(Arrays.toString(entry.getValue().toArray()));

								String [] strings = printPrompt(line, entry.getKey(), false);
								System.out.println("Line " + lineNumber + ": " + strings[0] + " " + strings[1]);
								System.out.print("  (0) " + entry.getKey() + " ");
								for (int i = 0; i < 3; i++) {
									System.out.print("(" + (i + 1) + ") " + entry.getValue().get(i) + " ");
								}
								System.out.print("(4) edit");

								System.out.println();

								System.out.println("Option: ");
								int choice = Integer.parseInt(sc.nextLine());

								if (choice == 0) {
									//records.add(new Record(line, lineNumber));
									correctedWords.put(entry.getKey(), entry.getKey());
								}

								else if (choice == 1) {
									//records.add(new Record(line.replaceFirst(entry.getKey(), entry.getValue().get(0)), lineNumber));
									correctedWords.put(entry.getKey(), entry.getValue().get(0));
								}

								else if (choice == 2) {
									//records.add(new Record(line.replaceFirst(entry.getKey(), entry.getValue().get(1)), lineNumber));
									correctedWords.put(entry.getKey(), entry.getValue().get(1));
								}

								else if (choice == 3) {
									//records.add(new Record(line.replaceFirst(entry.getKey(), entry.getValue().get(2)), lineNumber));
									correctedWords.put(entry.getKey(), entry.getValue().get(0));
								}

								else if (choice == 4) {
									System.out.println("Enter your word: ");
									String word = sc.nextLine();
									//records.add(new Record(line.replaceFirst(entry.getKey(), word), lineNumber));
									correctedWords.put(entry.getKey(), word);
								}
							}

							//this loop will replace multiple errors in the line
							//using the corrected words selected by the user and
							//finally add the record to the records arraylist
							String temp = line;

							for (Entry<String, String> entry: correctedWords.entrySet()){
								temp = temp.replaceFirst(entry.getKey(), entry.getValue());
							}

							records.add(new Record(temp, lineNumber));
						}

						else {
							//if there are no errors in the file, add the
							//line directly to the records arraylist to
							//be written to the new file
							records.add(new Record(line, lineNumber));
						}
					}
				}

				System.out.println("----------------------");

				System.out.println("File to save updated document: ");
				String fileName = sc.nextLine();

				FileWriter updatedDocument = new FileWriter(directory + "\\" + fileName);

				for (int i = 0; i < records.size(); i++) {
					updatedDocument.write(records.get(i).getLine());
					updatedDocument.write("\n");
				}

				updatedDocument.flush();
				updatedDocument.close();
				
				System.out.println("Type 'continue' to continue with another file or quit to exit");
				continueLoop = sc.nextLine();
			}
			
			else {
				break;
			}
		}
		
		//updatedDocument.close();
		//fileReader.close();
		sc.close();
	}
}
