package combinations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Combinations {
	
	public void run(String fileName) {
		List<String> wordList = null;
		try {
			wordList = getWords(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		findAnagrams(wordList);
	}
	
	private List<String> getWords(String fileName) throws IOException {
		/* 
		 * Retrieve the words from the text file.
		 * Reads each line with a BufferedReader and adds it to the ArrayList.
		 */
		List<String> wordList = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String word;
		while ((word = reader.readLine()) != null) {
			wordList.add(word);
		}
		reader.close();
		return wordList;
	}
	
	private void findAnagrams(List<String> wordList) {
		/* 
		 * Each key-value pair represents a set of anagrams.
		 * Finding anagrams works by sorting the characters of a word into alphabetical order.
		 * The sorted word can be used as a key to retrieve an anagram set. This works because anagrams
		 * look exactly the same when sorted alphabetically. The key is used to retrieve the appropriate
		 * anagram set to put the original word in. If it doesn't exist, a new entry is made with the sorted
		 * word as a key and the original word is added to the new set.
		 */
		
		/* To track the anagram set with the most words */
		String biggestSetKey = "";
		int biggestSetCount = 0;
		/* To track the anagram set with the longest words */
		String longestSet = "";
		
		Map<String, Set<String>> anagramSets = new HashMap<>();
		for(String word : wordList) {
			String sortedWord = sortWord(word);
			if (!anagramSets.containsKey(sortedWord)) {
				anagramSets.put(sortedWord, new HashSet<>());
			}
			anagramSets.get(sortedWord).add(word);
			/* 
			 * Check if the set returned by the current key has the most words.
			 * Set the current key as biggestSet if it does. Used to track the biggest anagram set.
			 */
			if(anagramSets.get(sortedWord).size() > biggestSetCount) {
				biggestSetKey = sortedWord;
				biggestSetCount = anagramSets.get(sortedWord).size();
			}
			/* 
			 * Check if the current key is the longest.
			 * The longest key will have the anagram set with the longest words.
			 */
			if(sortedWord.length() > longestSet.length() && anagramSets.get(sortedWord).size() > 1) {
				longestSet = sortedWord;
			}
		}
		int count = printAnagrams(anagramSets);
		System.out.printf("\nThere are %d anagrams.\n", count);
		System.out.printf("The biggest anagram set is %s, with a size of %d.\n",
				anagramSets.get(biggestSetKey), biggestSetCount);
		System.out.printf("The longest anagrams are %s, with a length of %s characters.",
				anagramSets.get(longestSet), longestSet.length());
	}
	
	private String sortWord(String word) {
		/* 
		 * Sorts the word characters in alphabetical order.
		 * The word String is split into a char array so that the sort method can be used.
		 * StringBuilder is used to append the sorted characters back together.
		 */
		char[] characters = word.toCharArray();
		Arrays.sort(characters);
		StringBuilder sortedString = new StringBuilder();
		for (char c : characters) {
			sortedString.append(c);
		}
		return sortedString.toString();
	}
	
	private int printAnagrams(Map<String, Set<String>> anagramSets) {
		/* Count the number of anagrams */
		int count = 0;
		/* Disregard sets with 1 element as they aren't anagrams. */
		for(Map.Entry<String, Set<String>> entry : anagramSets.entrySet()) {
			if(entry.getValue().size() != 1) {
				for(String word : entry.getValue()) {
					System.out.printf("%s ", word);
				}
				System.out.println();
				count++;
			}
		}
		return count;
	}
}
