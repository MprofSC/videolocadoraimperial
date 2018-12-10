package com.sde.videolocadoraimperial.cucumber.stepdefs;

import com.sde.videolocadoraimperial.VideoLocadoraImperialApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = VideoLocadoraImperialApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
