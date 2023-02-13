package com.example.application.views.list;

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
@Route(value = "", layout = MainLayout.class)
public class ListView extends VerticalLayout {
	private static final long serialVersionUID = 1L;

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
		closeEditor();
	}

	private void closeEditor() {
		form.setContact(null);
		form.setVisible(false);
		removeClassName("editing");
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

		form.addListener(ContactForm.SaveEvent.class, this::saveContact);
		form.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
		form.addListener(ContactForm.CloseEvent.class, e -> closeEditor());
	}

	private void saveContact(ContactForm.SaveEvent event) {
		service.saveContact(event.getContact());
		updateList();
		closeEditor();
	}

	private void deleteContact(ContactForm.DeleteEvent event) {
		service.deleteContact(event.getContact());
		updateList();
		closeEditor();
	}

	public void configuredGrid() {
		grid.addClassName("contact-grid");
		grid.setSizeFull();
		grid.setColumns("firstName", "lastName", "email");
		grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
		grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
		grid.getColumns().forEach(col -> col.setAutoWidth(true));

		grid.asSingleSelect().addValueChangeListener(event -> editContact(event.getValue()));
	}

	private void editContact(Contact contact) {
		if (contact == null) {
			closeEditor();
		} else {
			form.setContact(contact);
			form.setVisible(true);
			addClassNames("editing");
		}
	}

	private Component getToolbar() {
		textFilter.setClearButtonVisible(true);
		textFilter.setValueChangeMode(ValueChangeMode.LAZY);
		textFilter.setPlaceholder("filter name...");
		textFilter.addValueChangeListener(e -> updateList());

		Button button = new Button("Add Contact");
		button.addClickListener(e -> addContact());

		HorizontalLayout toolbar = new HorizontalLayout(textFilter, button);
		toolbar.setClassName("toolbar");

		return toolbar;
	}

	private void addContact() {
		grid.asSingleSelect().clear();
		editContact(new Contact());
	}

	private void updateList() {
		grid.setItems(service.findAllContacts(textFilter.getValue()));
	}

}
