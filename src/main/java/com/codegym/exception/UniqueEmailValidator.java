package com.codegym.exception;

import com.codegym.repository.IUserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private IUserRepository userRepository;

    public UniqueEmailValidator(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && userRepository.findByEmail(email) == null;
    }
}
