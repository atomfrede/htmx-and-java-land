package io.github.atomfrede.htmx_and_javaland.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ItemFormData {

    @NotBlank
    public String name;

    @Size(min = 3, max = 12)
    public String field;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
