package com.formacionbdi.microservicios.commons.service;

import java.util.Optional;


//agregando <E> para que sea generico
public interface CommonService<E> {
public Iterable<E> findAll();
public Optional<E> findById(Long id);
public E save(E entity);
public void deleteById(Long id);
}
