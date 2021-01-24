package com.product.target.service.validator;

import java.util.Objects;

public enum ProductParameterRequestValidator {
  PARAMETER_REQUEST_VALIDATOR;

  private static final String EMPTY = "";

  public void assertPriceRange(Double lowerLimit, Double higherLimit) {
    StringBuilder errorMessageBuilder = new StringBuilder();
    errorMessageBuilder.append(isGivenParameterNull(lowerLimit, higherLimit));
    errorMessageBuilder.append(isGivenHigherLimitValueIsZero(higherLimit));
    errorMessageBuilder.append(isGivenLowerLimitValueIsNegative(lowerLimit));
    errorMessageBuilder.append(isGivenHigherLimitValueIsNegative(higherLimit));
    errorMessageBuilder.append(isLowerLimitGreaterThanHigherLimit(lowerLimit, higherLimit));

    if (!errorMessageBuilder.toString().isEmpty())
      throw new IllegalArgumentException(errorMessageBuilder.toString());
  }

  private String isGivenParameterNull(Double lowerLimit, Double higherLimit) {
    return Objects.isNull(lowerLimit) || Objects.isNull(higherLimit)
        ? "lowerLimit/higherLimit value cannot be null "
        : EMPTY;
  }

  private String isGivenHigherLimitValueIsZero(Double higherLimit) {
    return higherLimit == 0.0 ? "Higher limit value cannot be zero " : EMPTY;
  }

  private String isGivenHigherLimitValueIsNegative(Double higherLimit) {
    return higherLimit < 0.0 ? "higherLimit value cannot be negative " : EMPTY;
  }

  private String isGivenLowerLimitValueIsNegative(Double lowerLimit) {
    return lowerLimit < 0.0 ? "lowerLimit value cannot be negative " : EMPTY;
  }

  private String isLowerLimitGreaterThanHigherLimit(Double lowerLimit, Double higherLimit) {
    return lowerLimit >= higherLimit
        ? "Lower limit cannot be greater/equal to higher limit "
        : EMPTY;
  }

  public void assertParameterStringValue(String parameter) {
    StringBuilder errorMessageBuilder = new StringBuilder();
    errorMessageBuilder.append(isGivenParameterNullOrEmpty(parameter));

    if (!errorMessageBuilder.toString().isEmpty())
      throw new IllegalArgumentException(errorMessageBuilder.toString());
  }

  private String isGivenParameterNullOrEmpty(String parameter) {
    return Objects.isNull(parameter) | parameter.isEmpty()? "The requesting parameter value cannot be null or Empty " : EMPTY;
  }
}
