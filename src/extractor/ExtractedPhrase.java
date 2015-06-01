package extractor;
/**
 * 
 * @author Matt Lisivick
 * 5/30/14
 *
 */
public class ExtractedPhrase {
	
	String phrase;
	int count;
	
	public ExtractedPhrase(String phrase){
		
		this.phrase=phrase;
		count=1;
	}
	
	public void incrementCount(){
		
		count++;
	}
	
	public String getPhrase(){
		
		return this.phrase;
	}
	
	public int getCount(){
		
		return this.count;
	}
	
	
	@Override
	public boolean equals(Object object){
		
		if(object instanceof ExtractedPhrase){
				
			ExtractedPhrase otherPhrase = (ExtractedPhrase) object;
			if(this.phrase.equals(otherPhrase.getPhrase())){
				
				return true;
			}else return false;
		
		}
		else return false;
		
	}
	
	@Override
	public int hashCode(){
		
		return this.phrase.hashCode();
	}
	
	
	
}
