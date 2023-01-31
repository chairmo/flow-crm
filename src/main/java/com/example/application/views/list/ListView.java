package com.example.application.views.list;

import java.util.Collections;
import java.util.List;

import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Contacts | Vaadin CRM")
@Route(value = "")
public class ListView extends VerticalLayout {
	Grid<Contact> grid = new Grid<>(Contact.class);
	TextField textFilter = new TextField();
	ContactForm form;
	CrmService service;

    public ListView(CrmService service) {
    	this.service = service;
    	addClassName("list-view");
    	setSizeFull();
       
       configuredGrid();
       configureForm();
       
       add(getToolbar(), getContent());
       updateList();
    }
    
    public Component getContent() {
    	HorizontalLayout content = new HorizontalLayout(grid, form);
    	content.setFlexGrow(2, grid);
    	content.setFlexGrow(1, form);
    	content.addClassNames("content");
    	content.setSizeFull();
    	
    	return content;
    }
    public void configureForm() {
    	form = new ContactForm(service.findAllCompanies(), service.findAllStatuses());
    	form.setWidth("25em");
    }
    
    public void configuredGrid() {
    	grid.addClassName("contact-grid");
    	grid.setSizeFull();
    	grid.setColumns("firstName", "lastName", "email");
    	grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
    	grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
    	grid.getColumns().forEach(col -> col.setAutoWidth(true));
    	
    }
    
    private Component getToolbar() {
    	textFilter.setClearButtonVisible(true);
    	textFilter.setValueChangeMode(ValueChangeMode.LAZY);
    	textFilter.setPlaceholder("filter name...");
    	textFilter.addValueChangeListener(e -> updateList());
    	
    	Button button = new Button("add contact");
    	
    	HorizontalLayout toolbar = new HorizontalLayout(textFilter, button);
    	toolbar.setClassName("toolbar");
    	
    	return toolbar;
    }
    private void updateList(){
    	grid.setItems(service.findAllContacts(textFilter.getValue()));
    }

}
