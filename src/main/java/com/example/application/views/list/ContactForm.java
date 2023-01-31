package com.example.application.views.list;

import java.util.List;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Status;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

public class ContactForm extends FormLayout {
	
	TextField firstName = new TextField("First name");
	TextField lastName = new TextField("Last name");
	EmailField email = new EmailField("Email");
	ComboBox<Company> company = new ComboBox<>("Company");
	ComboBox<Status> status = new ComboBox<>("Status");
	
	Button save = new Button("Save");
	Button delete = new Button("Delete");
	Button cancel = new Button("Cancel");
	
	HorizontalLayout hl = new HorizontalLayout(save, delete, cancel);

	public ContactForm(List<Company> companies, List<Status> statuses) {
		addClassName("Contact-From");
		
		company.setItems(companies);
		company.setItemLabelGenerator(Company::getName);
		status.setItems(statuses);
		status.setItemLabelGenerator(Status::getName);
		
		add(firstName,
				lastName,
				email,
				company,
				status,
				createButton()
				);
		
	}
	
	private Component createButton() {
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		
		save.addClickShortcut(Key.ENTER);
		delete.addClickShortcut(Key.DELETE);
		cancel.addClickShortcut(Key.ESCAPE);
		
		return new HorizontalLayout(save, delete, cancel);
	}
	
	
}
