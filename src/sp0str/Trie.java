package sp0str;
/*
   Write a Trie class for English words (converted to lower case), with functions:
   void add(String word) -- add a word to the dictionary,
   void remove(String word) -- remove a word from the dictionary,
   void import(String file) -- open a file and import all words in it,
   boolean contains(String word) -- search for a word in the dictionary,
   int prefix(String word) -- how many words in the dictionary start with this prefix?
   Add any additional functions that you think are needed.
*/

import java.util.ArrayList;

class Trie {
    TrieEntry root;
    ArrayList<TrieEntry> wordEntry;

    public Trie() {
        root = new TrieEntry();
    }

    public static void main(String args[]) {
        String word1 = "if";
        String word2 = "else";
        String word3 = "end";
        String word4 = "endLess";
        Trie trie = new Trie();
        trie.add(word1);
        trie.add(word2);
        trie.add(word3);
        trie.add(word4);

        System.out.println(trie);
        String prefix = "e";
        System.out.println("number of words with prefix " + prefix + " are " + trie.prefix(prefix));
        System.out.println("removing end ");
        trie.remove("word end");
        System.out.println("removed");
    }

    void add(String word) {
        TrieEntry head = root;
        char[] wordArray = word.toLowerCase().toCharArray();
        if (contains(word)) {
            return;
        }
        for (int i = 0; i < wordArray.length; i++) {
            TrieEntry temp = new TrieEntry(wordArray[i]);
            if (head.next.contains(temp)) {
                int ind = head.next.indexOf(temp);
                head = head.next.get(ind);
                head.count++;
            } else {
                temp.depth = head.depth + 1;
                temp.count = 1;
                if (i == wordArray.length - 1) {
                    temp.inDictionary = true;
                }
                head.next.add(temp);
                head = temp;
            }
        }
    }

    boolean contains(String word) {
        TrieEntry head = root;
        char[] wordArray = word.toLowerCase().toCharArray();
        wordEntry = new ArrayList<>();
        int i = 0;
        for (i = 0; i < wordArray.length; i++) {
            TrieEntry temp = new TrieEntry(wordArray[i]);
            if (head.next.contains(temp)) {
                head = head.next.get(head.next.indexOf(temp));
                wordEntry.add(0, head);
            } else {
                wordEntry.clear();
                return false;
            }
        }
        if (head.value != wordArray[i - 1]) {
            wordEntry.clear();
            return false;
        }
        if (!head.inDictionary) {
            return false;
        }
        return true;
    }

    void remove(String word) {
        if (!contains(word)) {
            System.out.println("Word not found");
            return;
        }
        for (int i = wordEntry.size() - 1; i >= 0; i--) {
            if (wordEntry.get(i).count > 1) {
                wordEntry.get(i).count--;
            } else {
                wordEntry.get(i).next.remove(wordEntry.get(i + 1));
            }
        }
    }

    /**
     * calculates total number of words in the dictionary start with this prefix (wordPrefix)
     *
     * @param wordPrefix
     * @return
     */
    int prefix(String wordPrefix) {
//		TrieEntry head=root;
        if (contains(wordPrefix) || wordEntry.size() > 0) {
            return wordEntry.get(0).count;
        } else {
            return -1;
        }

    }

    class TrieEntry {
        char value;
        boolean inDictionary;
        int depth;
        int count;
        ArrayList<TrieEntry> next;

        public TrieEntry() {
            this.value = ' ';
            this.depth = 0;
            this.count = 0;
            next = new ArrayList<>();
        }

        public TrieEntry(char value) {
            this.value = value;
            this.count = 0;
            next = new ArrayList<>();
        }

        public TrieEntry(char value, int depth) {
            this.value = value;
            this.depth = depth;
            this.count = 0;
            next = new ArrayList<>();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof TrieEntry) {
                TrieEntry temp = (TrieEntry) o;
                if (this.value == temp.value) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return String.valueOf(this.value);
        }
    }

}