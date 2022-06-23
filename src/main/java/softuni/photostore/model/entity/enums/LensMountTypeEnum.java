package softuni.photostore.model.entity.enums;

public enum LensMountTypeEnum {
    DSLR ("Обективи за DSLR фороапарати", "dslr"),
    MIRRORLESS ("Обективи за безогледални фотоапарати", "mirrorless");

    private String title;
    private String href;

    LensMountTypeEnum(String title, String href) {
        this.title = title;
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public String getHref() {
        return href;
    }
}
