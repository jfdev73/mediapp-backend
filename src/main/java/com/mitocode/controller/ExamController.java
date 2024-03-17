package com.mitocode.controller;

import com.mitocode.dto.ExamDTO;
import com.mitocode.model.Exam;
import com.mitocode.service.IExamService;
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
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamController {
	
	private final IExamService examService;

	@Qualifier("defaultMapper")
	private final ModelMapper mapper;
	
	@GetMapping
	public ResponseEntity<List<ExamDTO>>  findAll(){
		List<ExamDTO> lst = examService.findAll().stream().map(this::toDto).toList();
		return new ResponseEntity<>(lst,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ExamDTO> findById(@PathVariable Long id) {
		Exam exam = examService.findById(id);
		return new ResponseEntity<>(toDto(exam),HttpStatus.OK);

	}
	
/*	@PostMapping
	public ResponseEntity<Exam> save (@RequestBody Exam exam) {
		Exam obj = examService.save(exam);
		return new ResponseEntity<>(obj,HttpStatus.CREATED);
	}
	*/
	
	@PostMapping
	public ResponseEntity<ExamDTO> save (@Valid @RequestBody ExamDTO examDTO) {
		Exam obj = examService.save(toEntity(examDTO));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdExam()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ExamDTO> update ( @Valid @PathVariable Long id ,@RequestBody ExamDTO examDTO) {
		Exam obj =examService.update(toEntity(examDTO), id); 
		return new ResponseEntity<>(toDto(obj), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Long id){
		examService.delete(id);
		return ResponseEntity.noContent().build();
		
	}

	@GetMapping("/hateoas/{id}")
	public EntityModel<ExamDTO> findByIdHateoas(@PathVariable Long id){
		EntityModel<ExamDTO> resource = EntityModel.of(toDto( examService.findById(id)));
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
		resource.add(link1.withRel("exam-info1"));
		return resource;
	}
	
	private ExamDTO toDto(Exam exam) {
		return mapper.map(exam, ExamDTO.class);
	}
	
	private Exam toEntity(ExamDTO examDTO) {
		return mapper.map(examDTO, Exam.class);
	}
	

}
