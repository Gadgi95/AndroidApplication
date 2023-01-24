package com.example.application.model;

/**
 Этот класс, AbstractNamedEntity, является сущностью JPA,
 которая действует как суперкласс для других сущностей,
 имеющих свойство имени. Он определяет поле имени как непустую строку с минимальной длиной 2 и максимальной длиной 128 символов.
 Он также имеет аннотацию @NoHtml, которая используется для проверки при использовании представления «Web».
 Кроме того, класс предоставляет геттеры и сеттеры для свойства name, а также метод toString,
 который возвращает строковое представление сущности.
 */

import com.example.application.View;
import com.example.application.util.validation.NoHtml;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class AbstractNamedEntity {

    @NotBlank
    @Size(min = 2, max = 128)
    @Column(name = "name", nullable = false)
    @NoHtml(groups = {View.Web.class})
    protected String name;

    protected AbstractNamedEntity() {
    }

    protected AbstractNamedEntity(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return super.toString() + '(' + name + ')';
    }
}