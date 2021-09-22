package com.sgdJamIIIBackend;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.MongoClient;



@CrossOrigin(origins = "https://pedro876.itch.io/coliving")
@RestController
@RequestMapping("/apartments")
public class ApartmentController {
	
	@Autowired
	private ApartmentRepository repository;
	
	@PostMapping("/addApartment")
	public String SaveApartment(@RequestBody Apartment apartment) {
		System.out.println("HEREE");
		System.out.println("repository = null? " + (this.repository == null));
		
		
		Optional<Apartment> auxApartment = repository.findById(apartment.getId());
		if(auxApartment.isPresent()) {
			return "FAILURE";
		} else {
			//Apartment apartment = new Apartment(apartmentName);
			repository.save(apartment);
			return "SUCCESS";
		}
	}
	
	@PutMapping("/putApartment")
	public String UpdateApartment(@RequestBody Apartment apartment) {
		repository.save(apartment);
		if(getUser(apartment.getId()) != null) {
			return "UPDATED APARTMENT: " + apartment.getId();
		} else {
			return "APARTMENT DOES NOT EXIST SO IT WAS CREATED";
		}
	}
	
	/*@GetMapping("/findAllApartments")
	public List<Apartment> getUsers(){
		return repository.findAll();
	}*/

	/*@GetMapping("/ranking")
	public List<Apartment> ranking(){
		List<Apartment> users = repository.findAll();
		users.sort((a,b)->{
			return Integer.compare(-a.getPoints(), -b.getPoints());
		});
		return users;
	}*/
	
	@GetMapping("/findApartment/{id}")
	public Optional<Apartment> getUser(@PathVariable String id){
		System.out.println(repository.findById(id).get().getId());
		return repository.findById(id);
	}
	
	/*@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable String id) {
		repository.deleteById(id);
		return "user deleted: " + id;
	}*/
}
