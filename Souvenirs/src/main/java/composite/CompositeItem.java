package composite;

import java.util.ArrayList;
import java.util.List;

public class CompositeItem implements Item {
    private String name;
    private List<Item> items = new ArrayList<>();

    public CompositeItem(String name) {
        this.name = name;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    @Override
    public void display() {
        System.out.println("Composite Item: " + name);
        for (Item item : items) {
            item.display();
        }
    }
}
