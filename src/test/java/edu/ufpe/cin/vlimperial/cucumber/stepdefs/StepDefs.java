package edu.ufpe.cin.vlimperial.cucumber.stepdefs;

import edu.ufpe.cin.vlimperial.VlimperialApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = VlimperialApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
