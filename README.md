README

The PhraseExtractor Program 
Coded by Matt Lisivick
2014

This program was written as a collegiate project in my junior year. I hope to update it in the near future. As of now, the processing can be slow due to the lazy parse algorithm.

This Java based program extracts phrases from given text based on a part of speech pattern as defined by Turney (2002).
The POS tagger used in this program is a model based on the Stanford POS Tagger v3.1, Maxent Tagger.
The program takes an argument that selects the input text file and outputs a file named '[Input TXT File]-extractedPhrases'.
The output file is a CSV file that contains a list of extracted phrases and the phrase's frequency within the document in descending order.

The patterns extracted are as follows:


Key:
JJ-adjectives
NN-nouns
RB-adverbs
VB-verbs



1. JJ + (NN or NNS) followed by anything
2. (RB, RBR, or RBS) + JJ when not followed by NN nor NNS
3. JJ + JJ when not followed by NN nor NNS
4. (NN or NNS) + JJ when not followed by NN nor NNS
5. (RB, RBR or RBS) + (VB,VBD,VBN or VBG) followed by anything

To run exectutable:

java -jar PhraseExtractor.jar [Input TXT File]










