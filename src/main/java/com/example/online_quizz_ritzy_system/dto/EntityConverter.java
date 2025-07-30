package com.example.online_quizz_ritzy_system.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
public class EntityConverter<T,D> {

	private final ModelMapper mapper;

	@Autowired
    public EntityConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public D entityToDto(T entity , Class<D> dtoClass) {
		return mapper.map(entity, dtoClass);
	}
	
	public T dtoToEntity(D dto , Class<T> entityClass) {
		return mapper.map(dto, entityClass);
	}

}
