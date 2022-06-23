package softuni.photostore.model.entity.accounts;

import java.time.LocalDate;
import java.util.List;

public class UserCart {
    private String username;
    private LocalDate dateAdded;
    private List<String> itemsIDs;

    public UserCart() {
    }

    public String getUsername() {
        return username;
    }

    public UserCart setUsername(String username) {
        this.username = username;
        return this;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public UserCart setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public List<String> getItemsIDs() {
        return itemsIDs;
    }

    public UserCart setItemsIDs(List<String> itemsIDs) {
        this.itemsIDs = itemsIDs;
        return this;
    }
}
