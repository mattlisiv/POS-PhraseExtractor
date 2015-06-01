package extractor;
/***
 * Author: Matt Lisivick
 * 5/30/14
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class PhraseExtractor {
	
	MaxentTagger tagger;
	String taggedFile;
	ArrayList<String> taggedTokens;
	ArrayList<String> phraseList2;
	ArrayList<ExtractedPhrase> phraseList;
	static boolean working = true;

	// Constructor that accepts file to be read as parameter
	public PhraseExtractor(String fileName) throws IOException{
		
		tagger = new MaxentTagger("taggerModel/english-left3words-distsim.tagger");
		String file =readFile(fileName);
		taggedFile =tagger.tagString(file);
		phraseList = new ArrayList<ExtractedPhrase>();
		
		
	}
	//Returns string representation of tagged file
	public String getTaggedFile(){
		
		return this.taggedFile;
	}
	
	//Returns index in the phrase list of a certain phrase
	public int getPhraseIndex(String phrase){
		
		for(int i=0;i<phraseList.size();i++){
			
			if(phrase.equals(phraseList.get(i).getPhrase())){
				
				return i;
			}
			
		}
		return -1;
	}
	
	
	//Reads in document
	public String readFile(String readFile){
		
		BufferedReader br = null; 
		try {
			String currentLine;
			String totalText="";
			br = new BufferedReader(new FileReader(readFile));
 
			while ((currentLine = br.readLine()) != null) {
					totalText = totalText+" "+currentLine;
			}
			return totalText;
			} catch (IOException e) {
					System.out.println(("The file specified can not be found or does not exist"));
					working = false;
						} finally {
							try {	
									if (br != null)br.close();
										} catch (IOException ex) {
					System.out.println(("The file specified can not be found or does not exist"));
					working = false;

										}
								}
			return "Problem with document reader";
			}
	//Returns an array list of tagged words from string
	public ArrayList<String> getTaggedTokens(String taggedFile){
		taggedTokens = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(taggedFile);
		while(tokenizer.hasMoreTokens()){
			
			taggedTokens.add(tokenizer.nextToken());
			
		}
		
		
		return taggedTokens;
	}
	
	public String untagString(String taggedString){
		
		String untaggedString="";
		int index =0;
		boolean found = true;
		while(found==true && index<=taggedString.length()){
			
				if(taggedString.charAt(index)=='_'){
					found = false;
				}else{
					index++;
				}
				
			
		}
		untaggedString = taggedString.substring(0, index);
		
		return untaggedString;
	}
	
	public void phraseExtraction(ArrayList<String> tokens){
		//Phrase extracted are based on research of Turney (2002)
		for(int index=0;index<tokens.size();index++){
			String token = tokens.get(index);
			if(token.endsWith("JJ")){
				
				
							//Adjective + Noun
							if((index+1)<tokens.size()){
									if(tokens.get(index+1).endsWith("NN") || tokens.get(index+1).endsWith("NNS")){
											//String adjectiveNoun = token+" "+tokens.get(index+1);
											  String adjectiveNoun = this.untagString(token)+" "+this.untagString(tokens.get(index+1));
											if(phraseList.contains(new ExtractedPhrase(adjectiveNoun.toLowerCase()))){
												
												phraseList.get(this.getPhraseIndex(adjectiveNoun.toLowerCase())).incrementCount();

											}
											else{ 
											phraseList.add(new ExtractedPhrase(adjectiveNoun.toLowerCase()));
												
											
											}
									
									// Adjective + Adjective	
									}}
							if((index+2)<tokens.size()){
									if(tokens.get(index+1).endsWith("JJ") && !(tokens.get(index+2).endsWith("NN") || tokens.get(index+2).endsWith("NNS"))){
										//	String adjectiveAdjective = token+" "+tokens.get(index+1);
											String adjectiveAdjective = this.untagString(token)+" "+this.untagString(tokens.get(index+1));
											if(phraseList.contains(new ExtractedPhrase(adjectiveAdjective.toLowerCase()))){
												phraseList.get(this.getPhraseIndex(adjectiveAdjective.toLowerCase())).incrementCount();
											}
											else{ 
												  phraseList.add(new ExtractedPhrase(adjectiveAdjective.toLowerCase()));
											}
									//Adjective + Adjective
									}}
							if((index+3)<tokens.size()){
									if(tokens.get(index+1).equals(",_,") && tokens.get(index+2).endsWith("JJ") && !(tokens.get(index+3).endsWith("NN") || tokens.get(index+3).endsWith("NNS"))){
									//		String adjectiveAdjective = token+" "+tokens.get(index+2);
											String adjectiveAdjective = this.untagString(token)+" "+this.untagString(tokens.get(index+2));
											if(phraseList.contains(new ExtractedPhrase(adjectiveAdjective.toLowerCase()))){
												phraseList.get(this.getPhraseIndex(adjectiveAdjective.toLowerCase())).incrementCount();
											}
											else {
												  phraseList.add(new ExtractedPhrase(adjectiveAdjective.toLowerCase()));
									}
											
									}}
											//Single Adjective
											token = this.untagString(token);
											if(phraseList.contains(new ExtractedPhrase(token.toLowerCase()))){
												phraseList.get(this.getPhraseIndex(token.toLowerCase())).incrementCount();

											}
											else{
												 phraseList.add(new ExtractedPhrase(token.toLowerCase()));
											}
			}else if((token.endsWith("RB") || token.endsWith("RBR") || token.endsWith("RBS")) && !token.endsWith("WRB")){
									//Adverb + Adjective
								if((index+2)<tokens.size()){
									if(tokens.get(index+1).endsWith("JJ") && !(tokens.get(index+2).endsWith("NN") || tokens.get(index+2).endsWith("NNS"))){
										//	String adverbAdjective = token+" "+tokens.get(index+1);
											String adverbAdjective = this.untagString(token)+" "+this.untagString(tokens.get(index+1));
											if(phraseList.contains(new ExtractedPhrase(adverbAdjective.toLowerCase()))){

												phraseList.get(this.getPhraseIndex(adverbAdjective.toLowerCase())).incrementCount();
												
											}
											else{
												
												phraseList.add(new ExtractedPhrase(adverbAdjective.toLowerCase()));
											
											}
									//Adverb+ Verb		
									}}
								if((index+1)<tokens.size()){
									if(tokens.get(index+1).endsWith("VB") || tokens.get(index+1).endsWith("VBD") || tokens.get(index+1).endsWith("VBN") || tokens.get(index+1).endsWith("VBG") ){
										//	String adverbVerb = token+" "+tokens.get(index+1);
											String adverbVerb = this.untagString(token)+" "+ this.untagString(tokens.get(index+1));
											if(phraseList.contains(new ExtractedPhrase(adverbVerb.toLowerCase()))){
													
												phraseList.get(this.getPhraseIndex(adverbVerb.toLowerCase())).incrementCount();
												
											}
											else{
												  phraseList.add(new ExtractedPhrase(adverbVerb.toLowerCase()));
											
											}
											
									}}
											//Single Adverb
											token = this.untagString(token);
											if(phraseList.contains(new ExtractedPhrase(token.toLowerCase()))){
													
												phraseList.get(this.getPhraseIndex(token.toLowerCase())).incrementCount();
												
											}
											else{
												 phraseList.add(new ExtractedPhrase(token.toLowerCase()));
											
											}

										
			}else if(token.endsWith("NNS") || token.endsWith("NN")){
									//Noun + Adjective
								if((index+2)<tokens.size()){
									if(tokens.get(index+1).endsWith("JJ") && !(tokens.get(index+2).endsWith("NN") || tokens.get(index+2).endsWith("NNS"))){
									//		String nounAdjective = token+" "+tokens.get(index+1);
											String nounAdjective = this.untagString(token)+" "+this.untagString(tokens.get(index+1));
											if(phraseList.contains(new ExtractedPhrase(nounAdjective.toLowerCase()))){
												
												phraseList.get(this.getPhraseIndex(nounAdjective.toLowerCase())).incrementCount();
												
											}
											else {
												  phraseList.add(new ExtractedPhrase(nounAdjective.toLowerCase()));
											}
									}}
			}
		}
		
	}
	
	//Compares extracted phrases based on frequency
	public class PhraseListComparator implements Comparator<ExtractedPhrase>{

		@Override
		public int compare(ExtractedPhrase arg0, ExtractedPhrase arg1) {
			return -1*Integer.compare(arg0.getCount(), arg1.getCount());
		}
	}
	
		
	
	
	
	public static void main(String args[]) throws IOException{
		
		
		if(args.length>0){
		PhraseExtractor extractor = new PhraseExtractor(args[0]);
		
		if(working==true){
		ArrayList<String> tokens = extractor.getTaggedTokens(extractor.getTaggedFile());
		extractor.phraseExtraction(tokens);
		//Sorts phrase list into descending order
		Collections.sort(extractor.phraseList, extractor.new PhraseListComparator());
		
		File f = new File(args[0]+"-extractedPhrases.csv");
		FileWriter writer = new FileWriter(f);
		//Writes phrase list to CSV
		for(int i=0;i<extractor.phraseList.size();i++){
			writer.write(extractor.phraseList.get(i).getPhrase()+","+ extractor.phraseList.get(i).getCount()+"\n");
		}
		if(working==true){
		System.out.println("Program complete. Phrases have been extracted to file.");
		writer.close();}else{
			System.out.println("Sorry, but an error occured in reading document. Please ensure that the correct document was selected");
		}}
	}else{
		System.out.println("Please specify a file to be read.");
	}
		
	}
	

	
	

}

