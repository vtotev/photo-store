package softuni.photostore.model.entity.enums;

public enum BagTypeEnum {
    BAG("Чанти и калъфи"), BACKPACK("Раници");

    private String title;

    BagTypeEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
