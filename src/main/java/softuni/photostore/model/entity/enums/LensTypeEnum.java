package softuni.photostore.model.entity.enums;

public enum LensTypeEnum {
    PRIME ("Фиксирани (твърди) обективи"), ZOOM ("Варио (зуум) обективи");

    private String title;

    LensTypeEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
