package com.mitocode.controller;

import com.mitocode.dto.SpecialtyDTO;
import com.mitocode.model.Specialty;
import com.mitocode.service.ISpecialtyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/specialties")
@RequiredArgsConstructor
public class SpecialtyController {
	
	private final ISpecialtyService specialtyService;

	@Qualifier("defaultMapper")
	private final ModelMapper mapper;
	
	@GetMapping
	public ResponseEntity<List<SpecialtyDTO>>  findAll(){
		List<SpecialtyDTO> lst = specialtyService.findAll().stream().map(this::toDto).toList();
		return new ResponseEntity<>(lst,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SpecialtyDTO> findById(@PathVariable Long id) {
		Specialty specialty = specialtyService.findById(id);
		return new ResponseEntity<>(toDto(specialty),HttpStatus.OK);

	}
	
/*	@PostMapping
	public ResponseEntity<Specialty> save (@RequestBody Specialty specialty) {
		Specialty obj = specialtyService.save(specialty);
		return new ResponseEntity<>(obj,HttpStatus.CREATED);
	}
	*/
	
	@PostMapping
	public ResponseEntity<SpecialtyDTO> save (@Valid @RequestBody SpecialtyDTO specialtyDTO) {
		Specialty obj = specialtyService.save(toEntity(specialtyDTO));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdSpecialty()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<SpecialtyDTO> update ( @Valid @PathVariable Long id ,@RequestBody SpecialtyDTO specialtyDTO) {
		Specialty obj =specialtyService.update(toEntity(specialtyDTO), id); 
		return new ResponseEntity<>(toDto(obj), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Long id){
		specialtyService.delete(id);
		return ResponseEntity.noContent().build();
		
	}

	@GetMapping("/hateoas/{id}")
	public EntityModel<SpecialtyDTO> findByIdHateoas(@PathVariable Long id){
		EntityModel<SpecialtyDTO> resource = EntityModel.of(toDto( specialtyService.findById(id)));
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
		resource.add(link1.withRel("specialty-info1"));
		return resource;
	}
	
	private SpecialtyDTO toDto(Specialty specialty) {
		return mapper.map(specialty, SpecialtyDTO.class);
	}
	
	private Specialty toEntity(SpecialtyDTO specialtyDTO) {
		return mapper.map(specialtyDTO, Specialty.class);
	}
	

}
