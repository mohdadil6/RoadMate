package com.project.ridealong.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ridealong.dao.PostDao;
import com.project.ridealong.dao.RidersDao;
import com.project.ridealong.dao.TripDao;
import com.project.ridealong.entities.Post;
import com.project.ridealong.entities.Riders;
import com.project.ridealong.entities.Trip;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MyController {

	@Autowired
		private RidersDao ridersDao;
	
	 @Autowired
	    private TripDao tripDao;
	 
	 @Autowired
	    private PostDao postDao;
	 
	 @PostMapping("/api/posts")
	    public ResponseEntity<Post> addPost(@RequestBody Post post) {
	        try {
	            Post newPost = new Post(post.getName(), post.getExperience());
	            Post savedPost = postDao.save(newPost);
	            return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

	  @GetMapping("/api/posts")
	    public ResponseEntity<List<Post>> getAllPosts() {
	        try {
	            List<Post> posts = new ArrayList<Post>();
	            postDao.findAll().forEach(posts::add);
	            if (posts.isEmpty()) {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            }
	            return new ResponseEntity<>(posts, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } 
	 
	 
	  @GetMapping("/trips/search")
	  @ResponseBody
	  public String searchTrips(@RequestParam String source, @RequestParam String destination) throws JsonProcessingException {
	      List<Trip> trips = tripDao.findByFromAndTo(source, destination);
	      ObjectMapper objectMapper = new ObjectMapper();
	      String json = objectMapper.writeValueAsString(trips);
	      return json;
	  }




	 
	 
	  @PostMapping("/api/trips")
	    public ResponseEntity<Trip> addTrip(@RequestBody Trip trip, @RequestParam("riderId") Long riderId) {
	        Optional<Riders> riderData = ridersDao.findById(riderId);
	        if (riderData.isPresent()) {
	            trip.setRider(riderData.get());
	            Trip savedTrip = tripDao.save(trip);
	            return ResponseEntity.status(HttpStatus.CREATED).body(savedTrip);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	    }
	 
	  @GetMapping("/api/trips")
	  public ResponseEntity<List<Trip>> getAllTrips() {
	      try {
	          List<Trip> trips = new ArrayList<Trip>();
	          tripDao.findAll().forEach(trips::add);
	          if (trips.isEmpty()) {
	              return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	          }
	          return new ResponseEntity<>(trips, HttpStatus.OK);
	      } catch (Exception e) {
	          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	  }

	  
	 
	
	 @GetMapping("/")
	    public void index(HttpServletResponse response) throws IOException {
	        ClassPathResource resource = new ClassPathResource("static/index.html");
	        response.getOutputStream().write(resource.getInputStream().readAllBytes());
	    }
	
	
	
	
	@GetMapping("/api/riders")
	public ResponseEntity<List<Riders>> getAllRiders(@RequestParam(required = false) String email) {
		 try {

			List<Riders> riders = new ArrayList<Riders>();

			if (email == null)
				ridersDao.findAll().forEach(riders::add);
			else
				ridersDao.findByEmailContaining(email).forEach(riders::add);

			if (riders.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(riders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/api/riders/{id}")
	public ResponseEntity<Riders> getRiderById(@PathVariable("id") long id) {
		Optional<Riders> riderData = ridersDao.findById(id);

		if (riderData.isPresent()) {
			return new ResponseEntity<>(riderData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/api/riders")
	public ResponseEntity<Riders> addRider(@RequestBody Riders rider) {
		try {
			Optional<Riders> existingRider = ridersDao.findByEmail(rider.getEmail());
			if (existingRider.isPresent()) {
				// Return an error response indicating that the email already exists
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}

			Riders newRider = new Riders(rider.getId(), rider.getEmail(), rider.getPassword());
			Riders savedRider = ridersDao.save(newRider);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedRider);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/api/riders/{id}/trips")
	public ResponseEntity<List<Trip>> getTripsByRiderId(@PathVariable(value = "id") Long riderId) {
		try {
			List<Trip> trips = tripDao.findByRiderId(riderId);

			if (trips.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(trips, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/api/trips/{id}")
	public ResponseEntity<HttpStatus> deleteTrip(@PathVariable("id") long id) {
		try {
			tripDao.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/api/riders/{id}")
	public ResponseEntity<HttpStatus> deleteRider(@PathVariable("id") long id) {
		try {
			ridersDao.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/api/posts/{id}")
	public ResponseEntity<HttpStatus> deletePost(@PathVariable("id") long id) {
		try {
			postDao.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/api/riders/login")
	public ResponseEntity<Riders> login(@RequestBody Riders rider) {
		try {
			Optional<Riders> existingRider = ridersDao.findByEmail(rider.getEmail());
			if (existingRider.isPresent() && existingRider.get().getPassword().equals(rider.getPassword())) {
				// Return a success response indicating that the credentials are correct
				return ResponseEntity.status(HttpStatus.OK).body(existingRider.get());
			} else {
				// Return an error response indicating that the credentials are incorrect
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}