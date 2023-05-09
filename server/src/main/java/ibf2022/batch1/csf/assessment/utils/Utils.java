package ibf2022.batch1.csf.assessment.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import ibf2022.batch1.csf.assessment.server.models.Review;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

public class Utils {
    
    public static JsonObject toJson(List<Review> reviews) {
        JsonObjectBuilder jsonObj = Json.createObjectBuilder();
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();

        reviews.stream().forEach(
            v -> {
                JsonObjectBuilder j = Json.createObjectBuilder();
                j.add("title", v.getTitle())
                .add("rating", v.getRating())
                .add("byline", v.getByline())
                .add("headline", v.getHeadline())
                .add("summary", v.getSummary())
                .add("reviewUrl", v.getReviewURL())
                .add("image", v.getImage())
                .add("commentCount", v.getCommentCount());
                jsonArray.add(j);
            }
        );

        jsonObj.add("results", jsonArray);

        return jsonObj.build();
    }


    public static List<Review> toReview(String jsonResultFromAPI) {

		List<Review> reviewList = new ArrayList<>();

		JsonReader reader = Json.createReader(new StringReader(jsonResultFromAPI));
        JsonObject jsonObj = reader.readObject();
        // JsonArray jsonArray = jsonObj.getJsonArray("results").asJsonArray();
        // set "results" array as json array from json object
        JsonArray jsonArray = jsonObj.getJsonArray("results");

		jsonArray.stream().forEach(
			v -> {
                // make each element of the array as json object
				JsonObject obj = v.asJsonObject();
                // create new review object and set fields for each element of array
				Review review = new Review();
				review.setTitle(obj.getString("display_title"));
				review.setRating(obj.getString("mpaa_rating"));
				review.setByline(obj.getString("byline"));
				review.setHeadline(obj.getJsonString("headline").toString());
				review.setSummary(obj.getString("summary_short"));
				review.setReviewURL(obj.getJsonObject("link").getString("url"));

                try {
                    // set image if available
                    JsonObject multimedia = obj.getJsonObject("multimedia");
                    review.setImage(multimedia.getString("src"));

                } catch (Exception e) {
                    //if image is not available, leave it blank
                    review.setImage("");
                }
                // System.out.println(review);
				reviewList.add(review);
			}
		);

		// System.out.println(reviewList);
		return reviewList;
	}
}
