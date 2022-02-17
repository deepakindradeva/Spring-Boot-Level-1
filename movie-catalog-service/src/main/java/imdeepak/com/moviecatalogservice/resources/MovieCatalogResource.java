package imdeepak.com.moviecatalogservice.resources;

import imdeepak.com.moviecatalogservice.models.CatalogItem;
import imdeepak.com.moviecatalogservice.models.Movie;
import imdeepak.com.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        // Get all Rated movie Ids
        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 4),
                new Rating("4321", 3)
        );

        // For each movie id call movie info service and get details
        return ratings.stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId() , Movie.class);
            return new CatalogItem(movie.getName(), "Test", rating.getRating());
        }).collect(Collectors.toList());

        // Put them all together
    }
}
