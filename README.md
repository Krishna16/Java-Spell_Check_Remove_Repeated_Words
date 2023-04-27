# Java - Spell Check & Remove Repeated Words
This program allows a user to spell-check and remove repeated words in a text file. The program follows these steps:

Prompts the user for a text file to check (the filename) [called input file below].
Prompts the user for a text file of known words with correct spellings (the filename).
Reads the contents of both files.
Reads the contents of a text file containing common mistakes (called common.txt).
Processes the input file line-by-line.
Prompts the user to correct each mistake found on each line.
Prompts the user for a new name to save the corrected document.
Saves the updated document in the file.
Updates commont.txt with any new common mistakes.
Prompts the user to repeat with new files or quit the program.

The program shows the following details:

1. For each "problem" encountered, which is either a misspelled word or repeated word, the program displays which line number the problem occurred and shows a snippet of the text showing the problem. The snippet of text should include the word that comes before the problem (unless it is the first word) and the word that comes after the problem (unless it is the last word in the line). The problem should be inside quotes.

2. For each problem, some options for the user must be displayed.
a. The option O is always to make no change.
b. For misspelled words, a short list (3) of suggested corrections will be listed. The first suggested correction will come from the common.txt file (if word is present) and the rest (either 2 or 3 words) will be computed (see below for details). The last option will allow the user to manually enter the correction (edit).
c. For repeated words, the second option (1) will be to remove one of the repeated words.

3. The common.txt file will have common spelling mistakes and the correct word. Each line consists of two words (with whitespace between them). The first word is the misspelled word and the second word is the correct version of the word.

4. When processing a file, if the same mistake and correction occurs two (2) or more times, AND the correction does not appear in the common.txt file, then this correction should be added to the common.txt file (after the input file is processed).

5. The rest of the suggested words will be determined by finding words (from the collection of known correct words) that are "similar" to it. For this, you will compute a simplified Levenshtein distance (edit distance) between the misspelled word and every known correct word and choose from the words with smallest distance.

# How to use the program
To use this program, follow the steps below:

1. Run the program using Python.
2. When prompted, enter the name of the file you wish to spell-check and remove repeated words from.
3. Enter the name of the file containing a list of known words with correct spellings.
4. The program will display the first problem it finds on each line, and prompt the user for the correct action to take.
5. For each problem, choose an option from the list displayed by the program.
6. After processing the entire file, the program will prompt the user for the name of the file to save the corrected version in.
7. The program will update the common.txt file with any new spelling mistakes discovered during processing.
8. The program will prompt the user to either continue with another file or quit.

# Note
This program uses a simplified Levenshtein distance (edit distance) to find similar words

