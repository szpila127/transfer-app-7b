package com.transfer.app7b.view.admin;

import com.transfer.app7b.domain.dto.AppEventDto;
import com.transfer.app7b.service.AppEventService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "admin/app-events")
public class AdminAppEventsView extends VerticalLayout {

    private Button homeButton = new Button("HOME");
    private Button usersButton = new Button("USERS");
    private Button accountsButton = new Button("ACCOUNTS");
    private Button transactionsButton = new Button("TRANSACTIONS");
    private Button appEventsButton = new Button("APPLICATION EVENTS");
    private AppEventService appEventService = AppEventService.getInstance();
    private Grid<AppEventDto> gridAppEvent = new Grid<>(AppEventDto.class);
    private TextField filterEventsByType= new TextField();
    private TextField filterEventsByDate = new TextField();

    public AdminAppEventsView() {
        filterEventsByDate.setPlaceholder("Filter by date...");
        filterEventsByDate.setClearButtonVisible(true);
        filterEventsByDate.setValueChangeMode(ValueChangeMode.EAGER);
        filterEventsByDate.addValueChangeListener(e -> updateEventsByDate());

        filterEventsByType.setPlaceholder("Filter by type...");
        filterEventsByType.setClearButtonVisible(true);
        filterEventsByType.setValueChangeMode(ValueChangeMode.EAGER);
        filterEventsByType.addValueChangeListener(e -> updateEventsByType());

        gridAppEvent.setColumns("id", "date", "event", "information");
        gridAppEvent.getColumnByKey("id").setHeader("ID");
        homeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        homeButton.addClickListener(event -> {
            homeButton.getUI().ifPresent(ui -> ui.navigate("home"));
        });
        HorizontalLayout filterFields = new HorizontalLayout(filterEventsByDate, filterEventsByType);

        usersButton.addClickListener(event -> {
            homeButton.getUI().ifPresent(ui -> ui.navigate("admin/users"));
        });
        accountsButton.addClickListener(event -> {
            homeButton.getUI().ifPresent(ui -> ui.navigate("admin/accounts"));
        });
        transactionsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        appEventsButton.addClickListener(event -> {
            homeButton.getUI().ifPresent(ui -> ui.navigate("admin/app-events"));
        });
        HorizontalLayout menuButtons = new HorizontalLayout(usersButton, accountsButton, transactionsButton, appEventsButton);

        gridAppEvent.setSizeFull();
        add(homeButton, menuButtons, filterFields, gridAppEvent);
        setSizeFull();
        refreshAppEvents();
    }
    private void updateEventsByDate() {
        gridAppEvent.setItems(appEventService.filterByDate(filterEventsByDate.getValue()));
    }

    private void updateEventsByType() {
        gridAppEvent.setItems(appEventService.filterByEvent(filterEventsByType.getValue()));
    }

    public void refreshAppEvents() {
        appEventService.fetchAll();
        gridAppEvent.setItems(appEventService.getAppEventDtos());
    }

}
