package ru.geekbrains.library.dictionary;

public enum RoleEnum {
    ROLE_ADMIN("Администратор"),
    ROLE_USER("Пользователь"),
    ROLE_MANAGER("Менеджер");

    private final String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}