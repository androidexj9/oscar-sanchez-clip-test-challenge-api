package com.java.clip.challenge.api.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GlobalExceptionBody {

	private LocalDateTime timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;
	private List<ErrorDetails> details;

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<ErrorDetails> getDetails() {
		return details;
	}

	public void setDetails(List<ErrorDetails> details) {
		this.details = details;
	}

	@Data
	public static class ErrorDetails {
		private String fieldName;
		private String errorMessage;
		public String getFieldName() {
			return fieldName;
		}
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		public String getErrorMessage() {
			return errorMessage;
		}
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
	}
}
