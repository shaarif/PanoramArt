import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import edu.smu.tspell.wordnet.*;

public class creatingKeywords {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileWriter writer = new FileWriter("museum_updated.csv");
		writer.append("Museum Name");
		writer.append(",");
		writer.append("Title");
		writer.append(",");
		writer.append("Artist");
		writer.append(",");
		writer.append("Inscription");
		writer.append(",");
		writer.append("Technique");
		writer.append(",");
		writer.append("Dimension");
		writer.append(",");
		writer.append("Place");
		writer.append(",");
		writer.append("Date");
		writer.append(",");
		writer.append("Description");
		writer.append(",");
		writer.append("Image_URL");
		writer.append(",");
		writer.append("Museum_city");
		writer.append(",");
		writer.append("Keywords");
		writer.append('\n');
		String csvFile = "museum.csv";
		BufferedReader br = null;
		String line = "";
		String title;
		System.setProperty("wordnet.database.dir", "/Users/shaarif/Desktop/cs548 project/WordNet-3.0/dict/");
		//  Get the synsets containing the wrod form
		WordNetDatabase database = WordNetDatabase.getFileInstance();
		ArrayList<String> token_stopwords=new ArrayList<String>();
		ArrayList<String> token_NOstopwords=new ArrayList<String>();
		br = new BufferedReader(new FileReader(csvFile));
		line=br.readLine();
		int flag=0;
		 String[] stopWrds={"i", "a","and", "about","an","are","as","at","be","by","com","for","from","how","in", "is","it",
	                "of","on","or","that","the","this","to","was","what","when","where","who","will","with","untitled"};
		while ((line = br.readLine()) != null) {
			String[] fields=line.split(",");
            title=fields[1];
            StringTokenizer st = new StringTokenizer(title);
            while (st.hasMoreElements()) {
            	token_stopwords.add(st.nextElement().toString().replaceAll("[^a-zA-Z]", "").trim());
    		}
            for(String s:token_stopwords)
            {
            	for(String stop:stopWrds)
            	{
            		if(s.toLowerCase().equals(stop))
            			{
            			  flag=1;
            			  break;
            			}
            		flag=0;
            	}
            	if(flag==0)
            		token_NOstopwords.add(s.toLowerCase());	
            }
            writer.append(fields[0]);
    		writer.append(",");
    		writer.append(fields[1]);
    		writer.append(",");
    		writer.append(fields[2]);
    		writer.append(",");
    		writer.append(fields[3]);
    		writer.append(",");
    		writer.append(fields[4]);
    		writer.append(",");
    		writer.append(fields[5]);
    		writer.append(",");
    		writer.append(fields[6]);
    		writer.append(",");
    		writer.append(fields[7]);
    		writer.append(",");
    		writer.append(fields[8]);
    		writer.append(",");
    		writer.append(fields[9]);
    		writer.append(",");
    		if(fields[0].equals("SFMOMA"))
    			writer.append("151 3rd St; San Francisco; California 94103");
    		if(fields[0].equals("California African American"))
    			writer.append("600 State Dr; Los Angeles; CA 90037");
    		if(fields[0].equals("MOCA"))
    			writer.append("250 S Grand Ave; Los Angeles; CA 90012");
    		if(fields[0].equals("Getty"))
    			writer.append("1200 Getty Center Dr; Los Angeles; California 90049");
    		if(fields[0].equals("Japanese American"))
    			writer.append("100 N Central Ave; Los Angeles; CA 90012");
    		if(fields[0].equals("Oakland Museum of California"))
    			writer.append("1000 Oak St; Oakland; CA 94607");
    		writer.append(",");
    		String s="";
			for(String tok:token_NOstopwords)
            {
				s=s+tok;
				if(tok!=" ")
				{
					Synset[] synsets = database.getSynsets(tok);
					if (synsets.length > 0)
					{
						for (int i = 0; i < synsets.length; i++)
						{
							String[] wordForms = synsets[i].getWordForms();
							for (int j = 0; j < wordForms.length; j++)
							{
								//System.out.println(wordForms[j]);
								if(!s.contains(wordForms[j]))
								s=s+" "+wordForms[j];
							}
						}
					}
				}
				s=s+" ";
				
            }
			writer.append(s);
			writer.append('\n');
			token_NOstopwords.clear();
			token_stopwords.clear();
			
		}
	}
}
