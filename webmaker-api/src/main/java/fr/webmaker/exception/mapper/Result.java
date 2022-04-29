package fr.webmaker.exception.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.StringUtils;

public class Result {

	private String message;
	private boolean success;

	Result(String message) {
		this.success = true;
		this.message = message;
	}

	Result(Set<? extends ConstraintViolation<?>> violations) {
		this.success = false;
		this.message = violations.stream().map(cv -> {
			List<String> list = List.of(StringUtils.split(cv.getPropertyPath().toString(), '.'));
			return list.get(list.size() - 1) + " " + cv.getMessage();
		}).collect(Collectors.joining(", "));
	}

	public String getMessage() {
		return message;
	}

	public boolean isSuccess() {
		return success;
	}

}