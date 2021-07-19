package com.warys.app.household.application.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
abstract class AbstractControllerShould {

    protected static final String USER_ID = "60eecb143dafc2707237dc92";
    protected static final String SHOPPING_LIST_ID = "60eecb143dafc2707237dc92";
    protected static final String LIST_NAME = "list_name";
    @Autowired
    protected MockMvc mockMvc;

    protected static final ObjectMapper om = new ObjectMapper();

}