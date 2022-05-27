package com.formacionbdi.microservicios.commons.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//hago un findall para pageable para la paginacion copiando el metodo iterable
//agregando <E> para que sea generico
public interface CommonService<E> {
public Iterable<E> findAll();
public Page<E> findAll(Pageable pageable);
public Optional<E> findById(Long id);
public E save(E entity);
public void deleteById(Long id);
}
