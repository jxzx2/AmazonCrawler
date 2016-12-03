import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Amazon {

	LinkedList<String> links = new LinkedList<>();
	int i = 1;

	public void getLinks(){
		String sitemap = "https://www.amazon.com/gp/search/other/ref=sr_in_A_Z?rh=i:garden,n:1055398&bbn=1055398&pickerToList=enc-merchantbin&indexField=A&ie=UTF8&qid=1480715114";
		links.add(sitemap);
		Document doc;
		try {
			doc = Jsoup.connect(sitemap)
					.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.timeout(7000).get();
			Elements e = doc.getElementsByClass("a-row a-spacing-none pagn");
			for (Element element : e) {
				Elements nextLinks = element.getElementsByAttribute("href");
				for( Element next : nextLinks ){ 
					links.add(next.absUrl("href"));
					System.out.println(next.absUrl("href"));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void visitURL(){

		for (String string : links) {
			System.out.println("==================================================================================================================================================================");
			Document doc;
			try {
				doc = Jsoup.connect(string)
						.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
						.timeout(10000).get();
				Elements e = doc.select("#searchTemplate");
				LinkedList<String > data = new LinkedList<>();
				for (Element element : e) {
					Elements nextLinks = element.getElementsByClass("a-list-item");
					int counter = 0;
					for( Element next : nextLinks ){ 
						counter++;
						
							System.out.println(next.text());
							//data+=next.text()+System.lineSeparator();
							//data.add(next.text()+System.lineSeparator());
						
					}
				}


				//				FileWriter writ = new FileWriter("file "+i+".txt");
				//				writ.write(data.toString());
				//				i++;
				//				writ.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public static void main(String[] args){
		Amazon a = new Amazon();
		a.getLinks();
		a.visitURL();
	}


}
