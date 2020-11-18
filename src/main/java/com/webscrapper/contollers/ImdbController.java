package com.webscrapper.contollers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

@CrossOrigin(origins = "*")
@RestController
public class ImdbController {
	
	String user = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36";

	@GetMapping("/top10Movies")
	public String top10Movies() throws IOException {
		Document doc = Jsoup.connect("https://www.imdb.com/search/title/?groups=top_250&sort=user_rating").userAgent(user).get();
		List<JsonObject> movies=new ArrayList<JsonObject>();
		for(Element e : doc.select("div.lister-item-content")){
			JsonObject movie=new JsonObject();
			movie.addProperty("name", e.select("h3.lister-item-header a").text());
			movie.addProperty("link",e.select("h3.lister-item-header a").attr("href"));
			movie.addProperty("year",e.select("h3.lister-item-header span.lister-item-year").text());
			movie.addProperty("runtime",e.select("span.runtime").text());
			movie.addProperty("genre",e.select("span.genre").text());
			movie.addProperty("rating",e.select(".ratings-imdb-rating").text());
			movie.addProperty("story",e.select("p.text-muted").text());
			movie.addProperty("votes",e.select("p.sort-num_votes-visible").text());
			movies.add(movie);
			if(movies.size()==10)
				break;
		}
		return movies.toString();
	}
	
	@GetMapping("/top10Movies/{genre}")
	public String top10MoviesByGenre(@PathVariable String genre) throws IOException {
		Document doc = Jsoup.connect("https://www.imdb.com/search/title/?genres="+genre+"&groups=top_250&sort=user_rating,desc").userAgent(user).get();
		List<JsonObject> movies=new ArrayList<JsonObject>();
		for(Element e : doc.select("div.lister-item-content")){
			JsonObject movie=new JsonObject();
			movie.addProperty("name", e.select("h3.lister-item-header a").text());
			movie.addProperty("link",e.select("h3.lister-item-header a").attr("href"));
			movie.addProperty("year",e.select("h3.lister-item-header span.lister-item-year").text());
			movie.addProperty("runtime",e.select("span.runtime").text());
			movie.addProperty("genre",e.select("span.genre").text());
			movie.addProperty("rating",e.select(".ratings-imdb-rating").text());
			movie.addProperty("story",e.select("p.text-muted").text());
			movie.addProperty("votes",e.select("p.sort-num_votes-visible").text());
			movie.addProperty("image", e.select("div.lister-item-image img").attr("loadlate"));
			movies.add(movie);
			if(movies.size()==10)
				break;
		}
		return movies.toString();
	}

}
