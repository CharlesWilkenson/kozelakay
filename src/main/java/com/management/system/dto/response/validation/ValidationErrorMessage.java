package com.management.system.dto.response.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidationErrorMessage
{
  private String fieldName;

  private String message;
}
