import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class JSONtoCSV {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//sfmoma();
			//californiaafrican();
			//moca();
			getty();
			//japaneseamerican();
			//oakland();
			/*
			hammer(writer);
			*/
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static void sfmoma() throws FileNotFoundException, IOException, ParseException
	{
		FileWriter writer = new FileWriter("sfmoma.csv");
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
		writer.append('\n');
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("sfmoma.json"));
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray aworks = (JSONArray) jsonObject.get("art-works");
		Iterator<JSONObject> iterator = aworks.iterator();
		while (iterator.hasNext()) {
			JSONObject j=(JSONObject) iterator.next().get("art-work");
			String title = j.get("title").toString().replaceAll(",",";");
			String inscription = j.get("credit").toString().replaceAll(",",";");
			String img_url= (String) j.get("image-URL");
			String technique=j.get("type").toString().replaceAll(",",";");
			String description=null;
			String dimension=(String)j.get("dimensions"); 
			String place=(String)j.get("birthPlaceState");
			String date=(String)j.get("when-acquired");
			String artist=j.get("artistName").toString().replaceAll(",",";");
			writer.append("SFMOMA");
			writer.append(",");
			writer.append(title);
			writer.append(",");
			writer.append(artist);
			writer.append(",");
			writer.append(inscription);
			writer.append(",");
			writer.append(technique);
			writer.append(",");
			writer.append(dimension);
			writer.append(",");
			writer.append(place);
			writer.append(",");
			writer.append(date);
			writer.append(",");
			writer.append(description);
			writer.append(",");
			writer.append(img_url);
			//keyword
			writer.append('\n');
		}
		writer.close();
	}
	static void californiaafrican() throws FileNotFoundException, IOException, ParseException
	{
		FileWriter writer = new FileWriter("AfricanAmerican.csv");
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
		writer.append('\n');
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("caliAfricamAmerican.json"));
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray aworks = (JSONArray) jsonObject.get("artworks");
		Iterator<JSONObject> iterator = aworks.iterator();
		while (iterator.hasNext()) {
			JSONObject j=(JSONObject) iterator.next();
			String title=(String)j.get("title");
            if(title!=null){
			if(title.contains(","))
			{
			title=title.toString().replaceAll(",",";");
			}
            }
			String inscription=j.get("provenance").toString().replaceAll(",",";");
			String imageUrl=(String) j.get("imageUrl");
			String technique=j.get("technique").toString().replaceAll(",",";");
			String description=(String) j.get("additionalInfo").toString().replaceAll(",",";");
			String dimension=(String) j.get("dimensions");
			if(dimension!=null){
				if(dimension.contains(","))
				{
					dimension=dimension.toString().replaceAll(",",";");
				}
	            }
			String place=null;
			String date=null;
			String artist=(String) j.get("artist").toString().replaceAll(",",";");
			//museum name and keyword
			writer.append("California African American");
			writer.append(",");
			writer.append(title);
			writer.append(",");
			writer.append(artist);
			writer.append(",");
			writer.append(inscription);
			writer.append(",");
			writer.append(technique);
			writer.append(",");
			writer.append(dimension);
			writer.append(",");
			writer.append(place);
			writer.append(",");
			writer.append(date);
			writer.append(",");
			writer.append(description);
			writer.append(",");
			writer.append(imageUrl);
			//keyword
			writer.append('\n');
		}
		writer.close();
	}
    static void moca() throws FileNotFoundException, IOException, ParseException
    {
    	FileWriter writer = new FileWriter("moca.csv");
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
		writer.append('\n');
    	JSONParser parser = new JSONParser();
    	Object obj = parser.parse(new FileReader("moca.json"));
		JSONObject jsonObject = (JSONObject) obj;
		JSONObject aworks = (JSONObject) jsonObject.get("artworks");
		JSONArray arts = (JSONArray) aworks.get("Artist_Name");
		Iterator<JSONObject> iterator = arts.iterator();
		while (iterator.hasNext()) {
			JSONObject j=(JSONObject) iterator.next();
			String Artwork_Name_and_Year= (String) j.get("Artwork_Name_and_Year");
			String title="";
			String date="";
			if(Artwork_Name_and_Year.contains(","))
			{
			if(Artwork_Name_and_Year.lastIndexOf(",")==0)
			{
				title=" ";
			}
			else
			{
				title=Artwork_Name_and_Year.substring(0, Artwork_Name_and_Year.lastIndexOf(",")-1);
			}
			if(Artwork_Name_and_Year.lastIndexOf(",")==Artwork_Name_and_Year.length()-1)
			{
				date=" ";
			}
			else
			{
			date=Artwork_Name_and_Year.substring(Artwork_Name_and_Year.lastIndexOf(",")+1,Artwork_Name_and_Year.length());
			}
			}
			else
			{
			title=	Artwork_Name_and_Year;
			date=" ";
			}
			if(title!=null)
			{
			if(title.contains("\n"))
			{
				title=title.replaceAll("(\\r|\\n)"," ");
			}
			title=title.replaceAll(",",";");
			}
			String inscription=((String) j.get("Provenance"));
			if(inscription!=null)
			{
			if(inscription.contains("\n"))
			{
				inscription=inscription.replaceAll("(\\r|\\n)"," ");
				inscription=inscription.replaceAll(",",";");
				//inscription=inscription.substring(inscription.indexOf("es")+2, inscription.length()).trim();
			}
			inscription=inscription.replaceAll(",",";");
			}
			String imageUrl=(String) j.get("image");
			String technique=((String) j.get("Process"));
			if(technique!=null)
			{
			if(technique.contains("\n"))
			{
				technique=technique.replaceAll("(\\r|\\n)"," ");
			}
			technique=technique.replaceAll(",",";");
			}
			String description=null;
			String dimension=((String) j.get("Dimensions"));
			if(dimension!=null)
			{
			if(dimension.contains("\n"))
			{
				//System.out.println(dimension);
				dimension=dimension.replaceAll("(\\r|\\n)"," ");
				//System.out.println(dimension);
			}
			dimension=dimension.replaceAll(",",";");
			}
			String artist=((String) j.get("Artist_Name")).replaceAll(",",";");
			String place=null;
			//museum name and keyword
			writer.append("MOCA");
			writer.append(",");
			writer.append(title);
			writer.append(",");
			writer.append(artist);
			writer.append(",");
			writer.append(inscription);
			writer.append(",");
			writer.append(technique);
			writer.append(",");
			writer.append(dimension);
			writer.append(",");
			writer.append(place);
			writer.append(",");
			writer.append(date);
			writer.append(",");
			writer.append(description);
			writer.append(",");
			writer.append(imageUrl);
			//keyword
			writer.append('\n');
			
		}
		writer.close();
    }
    static void hammer() throws FileNotFoundException, IOException, ParseException
    {
    	FileWriter writer = new FileWriter("hammer.csv");
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
		writer.append('\n');
    	JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("hammer.json"));
		JSONObject jsonObject = (JSONObject) obj;
		JSONObject collections = (JSONObject) jsonObject.get("collections");
		JSONArray coll=(JSONArray) collections.get("collection");
		Iterator<JSONObject> iterator = coll.iterator();
		while (iterator.hasNext()) {
			JSONObject j=(JSONObject) iterator.next();
			JSONArray item=(JSONArray) j.get("item");
			Iterator<JSONObject> itemIterator = item.iterator();
			while(itemIterator.hasNext())
			{
			JSONObject i=(JSONObject) itemIterator.next();
			String title= (String) i.get("title");
			String inscription=null;
			String imageUrl=(String) i.get("image");
			String technique=null;
			String description=(String) i.get("description"); //contains the technique,inscription,dimension
			String dimension=null;
			String date=(String) i.get("year");
			String place=null;
			String artist=(String) i.get("artist");
			//museum name and keyword
			writer.append("Hammer");
			writer.append(",");
			writer.append(title);
			writer.append(",");
			writer.append(artist);
			writer.append(",");
			writer.append(inscription);
			writer.append(",");
			writer.append(technique);
			writer.append(",");
			writer.append(dimension);
			writer.append(",");
			writer.append(place);
			writer.append(",");
			writer.append(date);
			writer.append(",");
			writer.append(description);
			writer.append(",");
			writer.append(imageUrl);
			//keyword
			writer.append('\n');
			}
		}
		writer.close();
    }
    static void oakland() throws FileNotFoundException, IOException, ParseException
    {
    	FileWriter writer = new FileWriter("oakland.csv");
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
		writer.append('\n');
    	JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("oakland.json"));
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray aworks = (JSONArray) jsonObject.get("artwork");
		Iterator<JSONObject> iterator = aworks.iterator();
		while (iterator.hasNext()) {
			JSONObject j=(JSONObject) iterator.next();
			String date=null;
			String title= (String) j.get("Title");
			if(title.contains(","))
				title=title.replaceAll(",", ";");
			String inscription=(String) j.get("Credit");
			if(inscription.contains(","))
				inscription=inscription.replaceAll(",", ";");
			String imageUrl=(String) j.get("Pic_URL");
			String technique=(String) j.get("Type_of_Art")+"-"+(String) j.get("Subtype_of_Art");
			if(technique.contains(","))
				technique=technique.replaceAll(",", ";");
			String description=(String) j.get("Inscription"); //contains the date
			if(description!=null)
			{
			if(description.contains(","))
			{
			if(description.lastIndexOf(",")<description.length())
			{date=description.substring(description.lastIndexOf(",")+1, description.length());
			description=description.substring(0, description.lastIndexOf(",")-1);
			date = date.replaceAll("\\D+","");
			if(date.length()>4)
				date="";
			}
			}
			if(description.contains(","))
				description=description.replaceAll(",", ";");
			}
			String dimension=(String) j.get("Dimensions");
			if(dimension!=null)
			{
			if(dimension.contains(","))
				dimension=dimension.replaceAll(",", ";");
			}
			String place=null;
			String artist=(String) j.get("ArtistName");
			if(artist!=null)
			{
			if(artist.contains(","))
				artist=artist.replaceAll(",", ";");
			}
			//museum name and keyword
			writer.append("Oakland Museum of California");
			writer.append(",");
			writer.append(title);
			writer.append(",");
			writer.append(artist);
			writer.append(",");
			writer.append(inscription);
			writer.append(",");
			writer.append(technique);
			writer.append(",");
			writer.append(dimension);
			writer.append(",");
			writer.append(place);
			writer.append(",");
			writer.append(date);
			writer.append(",");
			writer.append(description);
			writer.append(",");
			writer.append(imageUrl);
			//keyword
			writer.append('\n');
		}
		writer.close();
    }
    static void japaneseamerican() throws FileNotFoundException, IOException, ParseException
    {
    	FileWriter writer = new FileWriter("japaneseAmerican.csv");
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
		writer.append('\n');
    	JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("AmericanJapanese.json"));
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray aworks = (JSONArray) jsonObject.get("artworks");
		Iterator<JSONObject> iterator = aworks.iterator();
		while (iterator.hasNext()) {
			JSONObject j=(JSONObject) iterator.next();
			JSONArray titlearr= (JSONArray) j.get("title");
			Iterator<String> ite1 = titlearr.iterator();
			String title="";
			while (ite1.hasNext())
			{
				title=title+ite1.next();
			}
			if(title!=null)
				title=title.replaceAll(",", ";");
			JSONArray inscriptionarr= (JSONArray) j.get("credit");
			Iterator<String> ite2 = inscriptionarr.iterator();
			String inscription="";
			while (ite2.hasNext())
			{
				inscription=inscription+ite2.next();
			}
			if(inscription!=null)
				inscription=inscription.replaceAll(",", ";");
			JSONArray image_URLarr= (JSONArray) j.get("image_URL");
			Iterator<String> ite3 = image_URLarr.iterator();
			String image_URL="";
			while (ite3.hasNext())
			{
				image_URL=image_URL+ite3.next();
			}
			JSONArray technique_typearr= (JSONArray) j.get("technique_type");
			Iterator<String> ite4 = technique_typearr.iterator();
			String technique_type="";
			while (ite4.hasNext())
			{
				technique_type=technique_type+ite4.next();
			}
			if(technique_type!=null)
				technique_type=technique_type.replaceAll(",", ";");
			JSONArray descriptionarr= (JSONArray) j.get("description");
			Iterator<String> ite5 = descriptionarr.iterator();
			String description="";
			while (ite5.hasNext())
			{
				description=description+ite5.next();
			}
			if(description!=null)
				description=description.replaceAll(",", ";");
			JSONArray dimensionsarr= (JSONArray) j.get("dimensions");
			Iterator<String> ite6 = dimensionsarr.iterator();
			String dimensions="";
			while (ite6.hasNext())
			{
				dimensions=dimensions+ite6.next();
			}
			if(dimensions!=null)
				dimensions=dimensions.replaceAll(",", ";");
			JSONArray place_and_data_madearr= (JSONArray) j.get("place_and_data_made");
			Iterator<String> ite7 = place_and_data_madearr.iterator();
			String place_and_data_made="";
			while (ite7.hasNext())
			{
				place_and_data_made=place_and_data_made+ite7.next();
			}
			String place="";
			if(place_and_data_made.lastIndexOf(",")==0)
			{
				place=" ";
			}
			else
			{
			place=place_and_data_made.substring(0, place_and_data_made.lastIndexOf(",")-1);
			}
			String date="";
			if(place_and_data_made.lastIndexOf(",")==place_and_data_made.length()-1)
			{
				date=" ";
			}
			else
			{
			date=place_and_data_made.substring(place_and_data_made.lastIndexOf(",")+1,place_and_data_made.length());
			}
			if(place!=null)
				place=place.replaceAll(",", ";");
			if(date!=null)
				date=date.replaceAll(",", ";");
			JSONArray artistsarr= (JSONArray) j.get("artist");
			Iterator<String> ite8 = artistsarr.iterator();
			String artist="";
			while (ite8.hasNext())
			{
				artist=artist+ite8.next();
			}
			if(artist!=null)
				artist=artist.replaceAll(",", ";");
			//this museum has tags, so we will add those as well
			JSONArray subject_tagsarr= (JSONArray) j.get("subject_tags");
			Iterator<String> ite9 = subject_tagsarr.iterator();
			ArrayList<String> keywords=new ArrayList<String>();
			while (ite9.hasNext())
			{
				keywords.add(ite9.next().toString());
			}
			writer.append("Japanese American");
			writer.append(",");
			writer.append(title);
			writer.append(",");
			writer.append(artist);
			writer.append(",");
			writer.append(inscription);
			writer.append(",");
			writer.append(technique_type);
			writer.append(",");
			writer.append(dimensions);
			writer.append(",");
			writer.append(place);
			writer.append(",");
			writer.append(date);
			writer.append(",");
			writer.append(description);
			writer.append(",");
			writer.append(image_URL);
			//keyword
			writer.append('\n');
		}
		writer.close();
    }
    static void getty() throws FileNotFoundException, IOException, ParseException
    {
    	FileWriter writer = new FileWriter("getty.csv");
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
		writer.append('\n');
    	JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("getty.json"));
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray aworks = (JSONArray) jsonObject.get("artwork");
		Iterator<JSONObject> iterator = aworks.iterator();
		while (iterator.hasNext()) {
			JSONObject j=(JSONObject) iterator.next();
			String title= (String) j.get("title");
			if(title!=null)
			title=title.replaceAll(",", ";");
			String inscription=null;
			String imageUrl=(String) j.get("img_url");
			String technique=(String) j.get("technique");
			technique=technique.replaceAll(",", ";");
			String description=(String) j.get("description");
			description=description.replaceAll(",", ";");
			String dimension=(String) j.get("dimensions");
			if(dimension!=null)
			dimension=dimension.replaceAll(",", ";");
			String place=null;
			String date=(String) j.get("date_made");
			if(date!=null)
			date=date.replaceAll(",", ";");
			String artist=(String) j.get("artist_name");
			if(artist!=null)
			artist=artist.replaceAll(",", ";");
			writer.append("Getty");
			writer.append(",");
			writer.append(title);
			writer.append(",");
			writer.append(artist);
			writer.append(",");
			writer.append(inscription);
			writer.append(",");
			writer.append(technique);
			writer.append(",");
			writer.append(dimension);
			writer.append(",");
			writer.append(place);
			writer.append(",");
			writer.append(date);
			writer.append(",");
			writer.append(description);
			writer.append(",");
			writer.append(imageUrl);
			//keyword
			writer.append('\n');
		}
		writer.close();
    }
}
