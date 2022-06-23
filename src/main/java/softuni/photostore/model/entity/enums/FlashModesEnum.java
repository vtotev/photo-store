package softuni.photostore.model.entity.enums;

public enum FlashModesEnum {
    TTL ("Автоматична (TTL)"), MANUAL ("Ръчна (non-TTL)");

    private String title;

    FlashModesEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
