package com.example.application.views.list;

import java.util.List;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Status;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class ContactForm extends FormLayout {
	
	private static final long serialVersionUID = 6659584376541598701L;
	
	TextField firstName = new TextField("First name");
	TextField lastName = new TextField("Last name");
	EmailField email = new EmailField("Email");
	ComboBox<Company> company = new ComboBox<>("Company");
	ComboBox<Status> status = new ComboBox<>("Status");
	
	Button save = new Button("Save");
	Button delete = new Button("Delete");
	Button close = new Button("Cancel");
	
	HorizontalLayout hl = new HorizontalLayout(save, delete, close);
	
	private Contact contact;
	
	Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);

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
		
		binder.bindInstanceFields(this);
	}
	
	private Component createButton() {
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		
		save.addClickShortcut(Key.ENTER);
		delete.addClickShortcut(Key.DELETE);
		close.addClickShortcut(Key.ESCAPE);
		
		save.addClickListener(e-> validateAndSave());
		delete.addClickListener(e-> fireEvent(new DeleteEvent(this, contact)));
		close.addClickListener(e-> fireEvent(new CloseEvent(this)));
		
		binder.addStatusChangeListener(e-> save.setEnabled(binder.isValid()));
		return new HorizontalLayout(save, delete, close);
	}
	
	private void validateAndSave() {
		try {
			binder.writeBean(contact);
			fireEvent(new SaveEvent(this, contact));
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}
	public void setContact(Contact contact) {
		this.contact = contact;
		binder.readBean(contact);
	}
	// Events
	public static abstract class ContactFormEvent extends ComponentEvent<ContactForm> {
		private static final long serialVersionUID = 1L;
		
	private Contact contact;

	  protected ContactFormEvent(ContactForm source, Contact contact) { 
	    super(source, false);
	    this.contact = contact;
	  }

	  public Contact getContact() {
	    return contact;
	  }
	}

	public static class SaveEvent extends ContactFormEvent {
		private static final long serialVersionUID = 1L;

	SaveEvent(ContactForm source, Contact contact) {
	    super(source, contact);
	  }
	}

	public static class DeleteEvent extends ContactFormEvent {
		private static final long serialVersionUID = 1L;

	DeleteEvent(ContactForm source, Contact contact) {
	    super(source, contact);
	  }

	}

	public static class CloseEvent extends ContactFormEvent {
		private static final long serialVersionUID = 1L;

	CloseEvent(ContactForm source) {
	    super(source, null);
	  }
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
	    ComponentEventListener<T> listener) { 
	  return getEventBus().addListener(eventType, listener);
	}
}

