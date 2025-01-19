package com.bill.tech.util;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.lang.Objects;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ListToStringConverter implements AttributeConverter<List<String>, String> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
		if (java.util.Objects.nonNull(attribute)) {
			try {
				return objectMapper.writeValueAsString(attribute);
			} catch (Exception e) {
				throw new IllegalArgumentException("Error converting list to JSON string", e);
			}
		}
		return null;

	}

	@Override
	public List<String> convertToEntityAttribute(String dbData) {
		if (java.util.Objects.nonNull(dbData)) {
			try {
				return objectMapper.readValue(dbData, new TypeReference<List<String>>() {
				});
			} catch (Exception e) {
				throw new IllegalArgumentException("Error converting JSON string to list", e);
			}
		}
		return null;
	}
}
