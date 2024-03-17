package com.mitocode.controller;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.mitocode.dto.PatientDTO;
import com.mitocode.model.Patient;
import com.mitocode.service.IPatientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class PatienController {
	
	private final IPatientService patientService;

	@Qualifier("defaultMapper")
	private final ModelMapper mapper;
	
	@GetMapping
	public ResponseEntity<List<PatientDTO>>  findAll(){
		List<PatientDTO> lst = patientService.findAll().stream().map(this::toDto).toList();
		return new ResponseEntity<>(lst,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PatientDTO> findById(@PathVariable Long id) {
		Patient patient = patientService.findById(id);
		return new ResponseEntity<>(toDto(patient),HttpStatus.OK);
	}
	
/*	@PostMapping
	public ResponseEntity<Patient> save (@RequestBody Patient patient) {
		Patient obj = patientService.save(patient);
		return new ResponseEntity<>(obj,HttpStatus.CREATED);
	}
	*/
	
	@PostMapping
	public ResponseEntity<PatientDTO> save (@Valid @RequestBody PatientDTO patientDTO) {
		Patient obj = patientService.save(toEntity(patientDTO));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPatient()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PatientDTO> update ( @Valid @PathVariable Long id ,@RequestBody PatientDTO patientDTO) {
		patientDTO.setIdPatient(id);
		Patient obj =patientService.update(toEntity(patientDTO), id);
		return new ResponseEntity<>(toDto(obj), HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Long id){
		patientService.delete(id);
		return ResponseEntity.noContent().build();
		
	}

	@GetMapping("/hateoas/{id}")
	public EntityModel<PatientDTO> findByIdHateoas(@PathVariable Long id){
		EntityModel<PatientDTO> resource = EntityModel.of(toDto( patientService.findById(id)));
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
		resource.add(link1.withRel("patient-info1"));
		return resource;
	}
	
	private PatientDTO toDto(Patient patient) {
		return mapper.map(patient, PatientDTO.class);
	}
	
	private Patient toEntity(PatientDTO patientDTO) {
		return mapper.map(patientDTO, Patient.class);
	}
	

}
