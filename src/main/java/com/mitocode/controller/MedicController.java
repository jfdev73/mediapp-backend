package com.mitocode.controller;

import com.mitocode.dto.MedicDTO;
import com.mitocode.model.Medic;
import com.mitocode.service.IMedicService;
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
@RequestMapping("/medics")
@RequiredArgsConstructor
public class MedicController {
	
	private final IMedicService medicService;

	@Qualifier("medicMapper")
	private final ModelMapper mapper;
	
	@GetMapping
	public ResponseEntity<List<MedicDTO>>  findAll(){
		List<MedicDTO> lst = medicService.findAll().stream().map(this::toDto).toList();
		return new ResponseEntity<>(lst,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MedicDTO> findById(@PathVariable Long id) {
		Medic medic = medicService.findById(id);
		return new ResponseEntity<>(toDto(medic),HttpStatus.OK);
	}
	
/*	@PostMapping
	public ResponseEntity<Medic> save (@RequestBody Medic medic) {
		Medic obj = medicService.save(medic);
		return new ResponseEntity<>(obj,HttpStatus.CREATED);
	}
	*/
	
	@PostMapping
	public ResponseEntity<MedicDTO> save (@Valid @RequestBody MedicDTO medicDTO) {
		Medic obj = medicService.save(toEntity(medicDTO));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdMedic()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<MedicDTO> update ( @Valid @PathVariable Long id ,@RequestBody MedicDTO medicDTO) {
		Medic obj =medicService.update(toEntity(medicDTO), id); 
		return new ResponseEntity<>(toDto(obj), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Long id){
		medicService.delete(id);
		return ResponseEntity.noContent().build();
		
	}

	@GetMapping("/hateoas/{id}")
	public EntityModel<MedicDTO> findByIdHateoas(@PathVariable Long id){
		EntityModel<MedicDTO> resource = EntityModel.of(toDto( medicService.findById(id)));
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
		resource.add(link1.withRel("medic-info1"));
		return resource;
	}
	
	private MedicDTO toDto(Medic medic) {
		return mapper.map(medic, MedicDTO.class);
	}
	
	private Medic toEntity(MedicDTO medicDTO) {
		return mapper.map(medicDTO, Medic.class);
	}
	

}
