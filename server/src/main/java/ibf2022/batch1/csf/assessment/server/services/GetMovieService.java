package ibf2022.batch1.csf.assessment.server.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2022.batch1.csf.assessment.server.models.Review;
import ibf2022.batch1.csf.assessment.server.repositories.MovieRepository;
import ibf2022.batch1.csf.assessment.utils.Utils;;

@Service
public class GetMovieService {

	@Autowired
	private MovieRepository movieRepo;

	// TODO: Task 4
	// DO NOT CHANGE THE METHOD'S SIGNATURE

	@Value("${nyt.api.key}")
	private String apiKey;

	private static final String NYT_API = "https://api.nytimes.com/svc/movies/v2/reviews/search.json";

	public List<Review> searchReviews(String query) {

		String url = UriComponentsBuilder.fromUriString(NYT_API)
					.queryParam("query", query.replaceAll(" ", "+"))
					.queryParam("api-key", apiKey)
					.toUriString();
		
		RestTemplate restTemplate = new RestTemplate();

		RequestEntity<Void> requestEntity = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();

		ResponseEntity<String> response = null;

		try{
			response = restTemplate.exchange(requestEntity, String.class);

			System.out.println("Response: " + requestEntity.getBody());

			List<Review> reviewsResult = Utils.toReview(response.getBody());

			reviewsResult.stream().forEach(
				r -> {
					String title = r.getTitle();
					System.out.println(title);
					int count = movieRepo.countComments(title);
					r.setCommentCount(count);
				}
			);

			return reviewsResult;

		}catch(Exception e){
				System.out.println("Error: " + e.getMessage());
				return new ArrayList<>();
		}
	}



	

}
