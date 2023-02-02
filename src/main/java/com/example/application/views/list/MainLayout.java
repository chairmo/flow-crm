package com.example.application.views.list;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MainLayout extends AppLayout {

	public MainLayout() {
		createHeader();
		createDrawer();
	}

	private void createHeader() {
		H1 logo = new H1("Vaadim CRM");

		logo.addClassNames("text-l", "m-m");
		HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
		
		header.setDefaultVerticalComponentAlignment(Alignment.CENTER);
		header.setWidth("100%");
		header.addClassNames("py-0","px-m");
		
		addToNavbar(header);
	}

	private void createDrawer() {

	}

}
