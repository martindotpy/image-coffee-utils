package xyz.cupscoffee.imagecoffeeutils.ui.shared.domain.model;

import java.util.List;

import lombok.Data;

@Data
public class CommonColorResponse {
    private String message;
    private List<CommonColor> content;
}
