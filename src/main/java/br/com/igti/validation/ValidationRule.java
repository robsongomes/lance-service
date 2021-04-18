package br.com.igti.validation;

import java.util.List;

public interface ValidationRule<T, U> {

	List<String> validate(T t, U u);
}
