package ibf2022.batch1.csf.assessment.server.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.batch1.csf.assessment.server.models.Comment;
import ibf2022.batch1.csf.assessment.server.models.Review;
import ibf2022.batch1.csf.assessment.server.services.CommentsService;
import ibf2022.batch1.csf.assessment.server.services.GetMovieService;
import ibf2022.batch1.csf.assessment.utils.Utils;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class MovieController {

	@Autowired
	private GetMovieService movieSvc;

	@Autowired
	private CommentsService commentsSvc;

	// TODO: Task 4, Task 8

	@GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	// @GetMapping(path = "/search")

    public ResponseEntity<?> getGames(@RequestParam String query) {

		//api results is returned as list
		List<Review> reviews = movieSvc.searchReviews(query);
		//change list to json
        JsonObject json = Utils.toJson(reviews);

        return ResponseEntity.ok(json.toString());
    }

	@PostMapping(path = "/comment", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> postComment(@RequestParam Map<String, String> comment) {

        Comment commentObj = new Comment();
        commentObj.setName(comment.get("name"));
        commentObj.setTitle(comment.get("title"));
        commentObj.setRating(Integer.parseInt(comment.get("rating")));
        commentObj.setComment(comment.get("comment"));

        System.out.println(commentObj);

        commentsSvc.saveComment(commentObj);
        JsonObject response = Json.createObjectBuilder().add("response", "comment saved").build();
        return ResponseEntity.ok(response.toString());

    }

	
}


		
