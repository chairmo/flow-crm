package com.example.application.views.list;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

	private static final long serialVersionUID = 1L;

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
		RouterLink listLink = new RouterLink("List", ListView.class);
		listLink.setHighlightCondition(HighlightConditions.sameLocation());
		
		RouterLink dashboardLink = new RouterLink("Dashboard", DashboardView.class);
		
		addToDrawer(new VerticalLayout(listLink, dashboardLink));
	}

}
