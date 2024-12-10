package xyz.cupscoffee.imagecoffeeutils.ui.shared.application.port.in.controller;

import java.io.Serializable;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class ContentBean implements Serializable {
    private String currentPage = "/components/organisms/colors.xhtml";

    public void changePage(String page) {
        this.currentPage = page;
    }
}