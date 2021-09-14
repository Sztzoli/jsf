package com.example.empapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateEmployeeControllerTest {

    @Mock
    EmployeeService service;

    @Mock
    MessageContext messageContext;

    @Spy
    ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    UpdateEmployeeController controller;

    @Test
    void testFillForm() {
        when(service.findById(any()))
                .thenReturn(Optional.of(new EmployeeDto("abcd1234","John Doe")));
        controller.setId("abcd1234");
        controller.findEmployee();
        assertEquals("John Doe", controller.getUpdateEmployeeCommand().getName());
    }

    @Test
    void testUpdate() {
        when(service.findById(any()))
                .thenReturn(Optional.of(new EmployeeDto("abcd1234","John Doe")));
        controller.setId("abcd1234");
        controller.findEmployee();

        controller.getUpdateEmployeeCommand().setName("Joe Doe");
        controller.update();

        verify(service).updateEmployee(argThat(c -> c.getName().equals("Joe Doe")));
        verify(messageContext).setFlashMessage("Employee has updated");
    }
}