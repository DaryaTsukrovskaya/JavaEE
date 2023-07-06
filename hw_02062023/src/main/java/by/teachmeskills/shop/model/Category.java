package by.teachmeskills.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Data
@ToString
@AllArgsConstructor
public class Category {
    private String id;
    private String name;
    private List<Product> productList;

    public Category(String name) {
        this.name = name;
    }
}
