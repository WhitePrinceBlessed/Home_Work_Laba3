package org.example;

import lombok.Data;
import java.util.List;

@Data
public class Visitor {
    private String name;
    private String surname;
    private String phone;
    private Boolean subscribed;
    private List<Book> favoriteBooks;

}
