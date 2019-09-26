package com.ipartek.formacion.rest.musiconcloud.controller;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.formacion.rest.musiconcloud.domain.Categoria;
import com.ipartek.formacion.rest.musiconcloud.domain.ReponseMensaje;
import com.ipartek.formacion.rest.musiconcloud.model.CategoriaRepository;


@RestController
public class CategoriasController {
	
		@Autowired
		CategoriaRepository categoriaRepository; 

	
	@RequestMapping(value = { "/categoria/", "/categoria" }, method = RequestMethod.GET)
	public ResponseEntity<Object> listar(){
		ResponseEntity<Object> response = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);	
		ArrayList<Categoria> lista =(ArrayList<Categoria>) categoriaRepository.findAll();
		
		try {
			
			if(!lista.isEmpty()) {
				response = new ResponseEntity<Object>(lista, HttpStatus.OK);
			}else {
				response = new ResponseEntity<Object>(lista, HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//response= new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@RequestMapping(value = "/categoria/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> detalle(@PathVariable int id) {
		ResponseEntity<Object> result = null;
		try {
			Optional<Categoria> categoria = categoriaRepository.findById(id);
			if (!categoria.isPresent()) {
				result = new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			} else {
				result = new ResponseEntity<Object>(categoria, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;

	}
	
	
	@RequestMapping(value = "/categoria/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> eliminar(@PathVariable int id) {

		ResponseEntity<Object> result = null;
		try {

			categoriaRepository.deleteById(id);
			result = new ResponseEntity<Object>(new ReponseMensaje("registro eliminado") , HttpStatus.OK);

		} catch (EmptyResultDataAccessException e) {
			result = new ResponseEntity<Object>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;

	}
	
	
	@RequestMapping(value = "/categoria/", method = RequestMethod.POST)
	public ResponseEntity<Object> insertar(@Valid @RequestBody Categoria categoria) {

		ResponseEntity<Object> result = null;
		try {
			categoriaRepository.save(categoria);
			result = new ResponseEntity<Object>(categoria, HttpStatus.CREATED);

		} catch (DataIntegrityViolationException e) {

			result = new ResponseEntity<Object>(new ReponseMensaje("Existe el nombre de la Categoria"),
					HttpStatus.CONFLICT);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;

	}

	@RequestMapping(value = "/categoria/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@Valid @RequestBody Categoria categoria, @PathVariable int id) {

		ResponseEntity<Object> result = null;
		try {
			categoria.setId(id);
			categoriaRepository.save(categoria);
			result = new ResponseEntity<Object>(categoria, HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;

	}
	
	
}
