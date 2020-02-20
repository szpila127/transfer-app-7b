package com.transfer.app7b.form;

import com.transfer.app7b.domain.dto.UserDto;
import com.transfer.app7b.service.UserService;
import com.transfer.app7b.view.admin.AdminUsersView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class UserAdminForm extends FormLayout {

    private TextField email = new TextField("Email");
    private TextField password = new TextField("Password");
    private TextField pesel = new TextField("Pesel");
    public Button saveUserButton = new Button("Save");
    public Button updateUserButton = new Button("Update");
    public Button deleteUserButton = new Button("Delete");
    public Button cancelUserButton = new Button("Cancel");

    private Binder<UserDto> binder = new Binder<>(UserDto.class);
    private UserService userService = UserService.getInstance();

    private AdminUsersView adminUsersView;

    public UserAdminForm(AdminUsersView adminUsersView2) {
        HorizontalLayout buttons = new HorizontalLayout(saveUserButton, updateUserButton, deleteUserButton, cancelUserButton);
        saveUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(email, password, pesel, buttons);
        binder.bindInstanceFields(this);
        adminUsersView = adminUsersView2;

    }

    private void updateUser() {
        UserDto userDto = binder.getBean();
        userService.update(userDto);
        adminUsersView.refreshAccounts();
        setUser(null);
    }

    private void saveUser() {
        UserDto userDto = binder.getBean();
        userService.save(userDto);
        adminUsersView.refreshAccounts();
        setUser(null);
    }

    private void deleteUser() {
        UserDto userDto = binder.getBean();
        userService.delete(userDto.getId());
        adminUsersView.refreshAccounts();
        setUser(null);
    }

    public void setUser(UserDto userDto) {
        binder.setBean(userDto);
        if (userDto == null) {
            setVisible(false);
        } else {
            setVisible(true);
            email.focus();
        }
    }
}
