package com.sgdJamIIIBackend;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

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
	private volatile ApartmentRepository repository;
	
	private Random random;
	
	private HashMap<String, Long> liveBathrooms;
	private HashMap<String, Long> liveBedrooms;
	private HashMap<String, Long> liveKitchens;
	private HashMap<String, Long> liveLivingRooms;
	
	private long maxLiveMillis = 60*1000;
	private long checkerPeriodSeconds = 15;
	
	private final ScheduledExecutorService scheduler =
			Executors.newScheduledThreadPool(1);
	
	@PostConstruct
	public void init() {
		random = new Random();
		liveBathrooms = new HashMap<String, Long>();
		liveBedrooms = new HashMap<String, Long>();
		liveKitchens = new HashMap<String, Long>();
		liveLivingRooms = new HashMap<String, Long>();
		
		List<Apartment> apartments = repository.findAll();
		apartments.forEach((apartment)->{
			if(apartment.getBathroomState() == 1)
				releaseBathroom(apartment.getId());
			if(apartment.getBedroomState() == 1)
				releaseBedroom(apartment.getId());
			if(apartment.getKitchenState() == 1)
				releaseKitchen(apartment.getId());
			if(apartment.getLivingRoomState() == 1)
				releaseLivingRoom(apartment.getId());
		});
		
		final Runnable liveChecker = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				checkAllLiveRooms();
			}
		};
		
		scheduler.scheduleAtFixedRate(liveChecker, 0, checkerPeriodSeconds, TimeUnit.SECONDS);
	}
	
	private void checkAllLiveRooms() {
		System.out.println("Checking live rooms");
		long currentTime = System.currentTimeMillis();
		List<String> ids = new ArrayList<>();
		
		liveBathrooms.forEach((id, time) ->{
			if(currentTime - time > maxLiveMillis) {
				ids.add(id);
				releaseBathroom(id);
			}
		});
		ids.forEach((id)->{
			liveBathrooms.remove(id);
		});
		ids.clear();
		
		liveBedrooms.forEach((id, time) ->{
			if(currentTime - time > maxLiveMillis) {
				ids.add(id);
				releaseBedroom(id);
			}
		});
		ids.forEach((id)->{
			liveBedrooms.remove(id);
		});
		ids.clear();
		
		liveKitchens.forEach((id, time) ->{
			if(currentTime - time > maxLiveMillis) {
				ids.add(id);
				releaseKitchen(id);
			}
		});
		ids.forEach((id)->{
			liveKitchens.remove(id);
		});
		ids.clear();
		
		liveLivingRooms.forEach((id, time) ->{
			if(currentTime - time > maxLiveMillis) {
				ids.add(id);
				releaseLivingRoom(id);
			}
		});
		ids.forEach((id)->{
			liveLivingRooms.remove(id);
		});
		ids.clear();
	}
	
	private void releaseBathroom(String id) {
		Optional<Apartment> opApartment = repository.findById(id);
		if(opApartment.isPresent()) {
			Apartment apartment = opApartment.get();
			apartment.setBathroomAuthor("");
			apartment.setBathroomCode("");
			apartment.setBathroomMsg("");
			apartment.setBathroomState(0);
			repository.save(apartment);
		}
		
	}
	
	private void releaseBedroom(String id) {
		Optional<Apartment> opApartment = repository.findById(id);
		if(opApartment.isPresent()) {
			Apartment apartment = opApartment.get();
			apartment.setBedroomAuthor("");
			apartment.setBedroomCode("");
			apartment.setBedroomMsg("");
			apartment.setBedroomState(0);
			repository.save(apartment);
		}
		
	}
	
	private void releaseKitchen(String id) {
		Optional<Apartment> opApartment = repository.findById(id);
		if(opApartment.isPresent()) {
			Apartment apartment = opApartment.get();
			apartment.setKitchenAuthor("");
			apartment.setKitchenCode("");
			apartment.setKitchenMsg("");
			apartment.setKitchenState(0);
			repository.save(apartment);
		}
		
	}
	
	private void releaseLivingRoom(String id) {
		Optional<Apartment> opApartment = repository.findById(id);
		if(opApartment.isPresent()) {
			Apartment apartment = opApartment.get();
			apartment.setLivingRoomAuthor("");
			apartment.setLivingRoomCode("");
			apartment.setLivingRoomMsg("");
			apartment.setLivingRoomState(0);
			repository.save(apartment);
		}
		
	}
	
	@GetMapping("/wakeUp")
	public String WakeUp(){
		return "AWAKE";
	}
	
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
		if(getApartment(apartment.getId()) != null) {
			return "UPDATED APARTMENT: " + apartment.getId();
		} else {
			return "APARTMENT DOES NOT EXIST SO IT WAS CREATED";
		}
	}
	
	@PutMapping("/putBathroom")
	public String putBathroom(@RequestBody Apartment apartment) {
		
		Optional<Apartment> updatedAp = repository.findById(apartment.getId());
		
		System.out.println("putting bathroom " + apartment.getBathroomState());
		
		if(updatedAp.isPresent()) {
			Apartment ap = updatedAp.get();
			ap.setBathroomState(apartment.getBathroomState());
			ap.setBathroomAuthor(apartment.getBathroomAuthor());
			ap.setBathroomCode(apartment.getBathroomCode());
			ap.setBathroomMsg(apartment.getBathroomMsg());
			repository.save(ap);
			
			if(ap.getBathroomState() == 1) {
				liveBathrooms.put(ap.getId(), System.currentTimeMillis());
			}
			
			return "SUCCESS";
		}
		else
			return "FAILURE";
	}
	
	@PutMapping("/putLivingRoom")
	public String putLivingRoom(@RequestBody Apartment apartment) {
		
		Optional<Apartment> updatedAp = repository.findById(apartment.getId());
		
		if(updatedAp.isPresent()) {
			Apartment ap = updatedAp.get();
			ap.setLivingRoomState(apartment.getLivingRoomState());
			ap.setLivingRoomAuthor(apartment.getLivingRoomAuthor());
			ap.setLivingRoomCode(apartment.getLivingRoomCode());
			ap.setLivingRoomMsg(apartment.getLivingRoomMsg());
			repository.save(ap);
			
			if(ap.getLivingRoomState() == 1) {
				liveLivingRooms.put(ap.getId(), System.currentTimeMillis());
			}
			
			return "SUCCESS";
		}
		else
			return "FAILURE";
	}
	
	@PutMapping("/putBedroom")
	public String putBedroom(@RequestBody Apartment apartment) {
		
		Optional<Apartment> updatedAp = repository.findById(apartment.getId());
		
		if(updatedAp.isPresent()) {
			Apartment ap = updatedAp.get();
			ap.setBedroomState(apartment.getBedroomState());
			ap.setBedroomAuthor(apartment.getBedroomAuthor());
			ap.setBedroomCode(apartment.getBedroomCode());
			ap.setBedroomMsg(apartment.getBedroomMsg());
			repository.save(ap);
			
			if(ap.getBedroomState() == 1) {
				liveBedrooms.put(ap.getId(), System.currentTimeMillis());
			}
			
			return "SUCCESS";
		}
		else
			return "FAILURE";
	}
	
	@PutMapping("/putKitchen")
	public String putKitchen(@RequestBody Apartment apartment) {
		
		Optional<Apartment> updatedAp = repository.findById(apartment.getId());
		
		if(updatedAp.isPresent()) {
			Apartment ap = updatedAp.get();
			ap.setKitchenState(apartment.getKitchenState());
			ap.setKitchenAuthor(apartment.getKitchenAuthor());
			ap.setKitchenCode(apartment.getKitchenCode());
			ap.setKitchenMsg(apartment.getKitchenMsg());
			repository.save(ap);
			
			if(ap.getKitchenState() == 1) {
				liveKitchens.put(ap.getId(), System.currentTimeMillis());
			}
			
			return "SUCCESS";
		}
		else
			return "FAILURE";
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
	public Optional<Apartment> getApartment(@PathVariable String id){
		System.out.println(repository.findById(id).get().getId());
		return repository.findById(id);
	}
	
	@GetMapping("/randomUnfinished/{id}")
	public Optional<Apartment> getRandomUnfinishedApartment(@PathVariable String id){
		System.out.println("here");
		List<Apartment> apartments = repository.findUnfinishedApartments();
		int size;
		Optional<Apartment> currentApartment = repository.findById(id);
		if(currentApartment.isPresent() && isApartmentEmpty(currentApartment.get())) {
			size = apartments.size();
		} else {
			size = removeIdFromApartmentsAndGetSize(apartments, id);
		}
	
		Apartment result;
		
		if(size > 0) {
			int index = random.nextInt(size);
			result = apartments.get(index);
		}
		else {
			result = new Apartment();
			result = repository.save(result);
			System.out.println("Created apartment with id: " + result.getId());
		}
		
		return Optional.of(result);
	}
	
	@GetMapping("/randomFinished/{id}")
	public Optional<Apartment> getRandomFinishedApartment(@PathVariable String id){
		
		List<Apartment> apartments = repository.findFinishedApartments();
		int size = removeIdFromApartmentsAndGetSize(apartments, id);
	
		Apartment result;
		
		if(size > 0) {
			int index = random.nextInt(size);
			result = apartments.get(index);
		}
		else {
			result = new Apartment();
			result.setBathroomState(2);
			result.setBedroomState(2);
			result.setLivingRoomState(2);
			result.setKitchenState(2);
			//result = repository.save(result);
		}
		
		return Optional.of(result);
	}
	
	@GetMapping("/getRanking/{count}")
	public List<Apartment> getRanking(@PathVariable String count){
		System.out.println("Ranking requested");
		
		List<Apartment> apartments = repository.findRanking();
		
		ArrayList<Apartment> ranking = new ArrayList<>();
		
		int maxSize = apartments.size();
		int countInt = Integer.parseInt(count);
		for(int i = 0; i < maxSize && i < countInt; i++) {
			Apartment apartment = apartments.get(i);
			ranking.add(apartment);
		}
		
		return ranking;
	}
	
	/*@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable String id) {
		repository.deleteById(id);
		return "user deleted: " + id;
	}*/
	
	private int removeIdFromApartmentsAndGetSize(List<Apartment> apartments, String id) {
		int currentIndex = -1;
		int size = apartments.size();
		for(int i = 0; i < size; i++) {
			Apartment apartment = apartments.get(i);
			if(apartment.getId().equals(id)) {
				currentIndex = i;
			}
		}
		
		if(currentIndex >= 0) {
			apartments.remove(currentIndex);
			size--;
		}
		
		return size;
	}
	
	private boolean isApartmentEmpty(Apartment apartment) {
		return apartment.getBathroomState() == 0 && apartment.getBedroomState() == 0 && apartment.getKitchenState() == 0 && apartment.getLivingRoomState() == 0;
	}
}
