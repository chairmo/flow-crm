package com.example.application.views.list;

import javax.annotation.security.PermitAll;

import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@PermitAll
@Route(value = "dashboard", layout = MainLayout.class)
public class DashboardView  extends VerticalLayout{
	private static final long serialVersionUID = 1L;
	private final CrmService service;

	
	public DashboardView(CrmService service) {
		super();
		this.service = service;
		addClassName("dashboard-view");
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		
		add(getContactStats(), getCompaniesChart());
	}


	private Component getContactStats() {
		Span stat = new Span(service.countContacts() + " contacts");
		addClassNames("text-xl", "mt-m");
		
		return stat;
	}


	private Chart getCompaniesChart() {
		Chart chart = new Chart(ChartType.PIE);
		
		DataSeries data = new DataSeries();
		service.findAllCompanies().forEach(contact -> data.add(new DataSeriesItem(contact.getName(), 
				contact.getEmployeeCount())));
		
		chart.getConfiguration().setSeries(data);
		
		return chart;
		
	}
	
	
}
