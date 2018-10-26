package com.softplan.restapi.api.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pela integração genérica com o Front-end, ou seja, todos os métodos dos controladores deve retornar esse objeto 
 * afim de ficar padronizado a comunicação de retorno do Restfull.
 * @author anderson.marques
 *
 * @param <T>
 */
public class Response<T> {

	private T data;
	
	private List<String> errors;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getErrors() {
		if(this.errors == null) {
			this.errors = new ArrayList<>();
		}
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
