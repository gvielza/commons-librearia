package com.formacionbdi.microservicios.commons.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import com.formacionbdi.microservicios.commons.service.CommonService;

public class CommonController<E, S extends CommonService<E>> {
	@Autowired
	protected S service;

	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok().body(service.findAll());
	}
	//copio el listar para hacer el metodo paginable y cambiamos ruta, y todo automatico
	//localhost:8090/api/alumnos/paginas?page=0&size=3 sería la ruta para acceder
	@GetMapping("/paginas")
	public ResponseEntity<?> listar(Pageable pageable) {
		return ResponseEntity.ok().body(service.findAll(pageable));
	}


	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id) {
		Optional<E> o = service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(o.get());
	}
//el  BindingResult result siempre debe ir siguiendo a la entidad
	@PostMapping
	public ResponseEntity<?> crear(@Valid @RequestBody E entity, BindingResult result) {
		if (result.hasErrors()) {
			return this.validar(result);
		}
		
		E entityDb = service.save(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	//metodo para generar los mensajes de error JSON , validaciones
	protected ResponseEntity<?> validar(BindingResult result) {
		Map<String, Object> errores =new HashMap<String, Object>();
		result.getFieldErrors().forEach(err->{
			errores.put(err.getField(), "el campo "+err.getField()+" "+err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
	
	
	
	
}
