package com.panroets.aroundtheworld.cucumber.stepdefs;

import com.panroets.aroundtheworld.AroundTheWorldApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = AroundTheWorldApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
