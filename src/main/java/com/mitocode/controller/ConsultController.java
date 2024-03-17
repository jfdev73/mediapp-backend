package com.mitocode.controller;

import com.mitocode.dto.*;
import com.mitocode.model.Consult;
import com.mitocode.model.Exam;
import com.mitocode.service.IConsultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/consults")
@RequiredArgsConstructor
public class ConsultController {
	
	private final IConsultService consultService;

	@Qualifier("consultMapper")
	private final ModelMapper mapper;
	
	@GetMapping
	public ResponseEntity<List<ConsultDTO>>  findAll(){
		List<ConsultDTO> lst = consultService.findAll().stream().map(this::toDto).toList();
		return new ResponseEntity<>(lst,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ConsultDTO> findById(@PathVariable Long id) {
		Consult consult = consultService.findById(id);
		return new ResponseEntity<>(toDto(consult),HttpStatus.OK);
	}
	
/*	@PostMapping
	public ResponseEntity<Consult> save (@RequestBody Consult consult) {
		Consult obj = consultService.save(consult);
		return new ResponseEntity<>(obj,HttpStatus.CREATED);
	}
	*/
	
	@PostMapping
	public ResponseEntity<ConsultDTO> save (@Valid @RequestBody ConsultListExamDTO dto) {

		Consult cons = toEntity(dto.getConsult());
		//List<Exam> exams =  dto.getLstExam().stream().map(e-> mapper.map(e, Exam.class)).toList();
		List<Exam> exams = mapper.map(dto.getLstExam(), new TypeToken<List<Exam>>(){}.getType());
		Consult obj = consultService.saveTransactional(cons, exams);
		//Consult obj = consultService.save(toEntity(dto));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsult()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ConsultDTO> update ( @Valid @PathVariable Long id ,@RequestBody ConsultDTO consultDTO) {
		Consult obj =consultService.update(toEntity(consultDTO), id);
		return new ResponseEntity<>(toDto(obj), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Long id){
		consultService.delete(id);
		return ResponseEntity.noContent().build();
		
	}

	@GetMapping("/hateoas/{id}")
	public EntityModel<ConsultDTO> findByIdHateoas(@PathVariable Long id){
		EntityModel<ConsultDTO> resource = EntityModel.of(toDto( consultService.findById(id)));
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
		resource.add(link1.withRel("consult-info1"));
		return resource;
	}

	@PostMapping("/search/others")
	public ResponseEntity<List<ConsultDTO>> searchByOthers(@RequestBody FilterConsultDTO filterDTO){
		List<Consult> consults = consultService.search(filterDTO.getDni(), filterDTO.getFullname());
		List<ConsultDTO> consultDTOS = mapper.map(consults, new TypeToken<List<ConsultDTO>>(){}.getType());

		return new ResponseEntity<>(consultDTOS, HttpStatus.OK);
	}

	@GetMapping("/search/date")
	public ResponseEntity<List<ConsultDTO>> searchByDates(
			@RequestParam (value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate){
		List<Consult> consults = consultService.searchByDates(LocalDateTime.parse(startDate), LocalDateTime.parse(endDate));
		List<ConsultDTO> consultDTOS = mapper.map(consults, new TypeToken<List<ConsultDTO>>(){}.getType());
		return new ResponseEntity<>(consultDTOS, HttpStatus.OK);
	}

	@GetMapping("/callProcedureNative")
	public ResponseEntity<List<ConsultProcDTO>> callProcedureOrFunctionNative(){
		List<ConsultProcDTO> consults = consultService.callProcedureOrFunctionNative();
		return new ResponseEntity<>(consults, HttpStatus.OK);
	}

	@GetMapping("/callProcedureProjection")
	public ResponseEntity<List<IConsultProcDTO>> callProcedureOrFunctionProjection(){
		List<IConsultProcDTO> consults = consultService.callProcedureOrFunctionProjection();
		return new ResponseEntity<>(consults, HttpStatus.OK);
	}
	
	private ConsultDTO toDto(Consult consult) {
		return mapper.map(consult, ConsultDTO.class);
	}
	
	private Consult toEntity(ConsultDTO consultDTO) {
		return mapper.map(consultDTO, Consult.class);
	}
	

}
