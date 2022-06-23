package softuni.photostore.model.entity.enums;

public enum CameraTypeEnum {
    DSLR ("DSLR фороапарати", "dslr"),
    MIRRORLESS ("Безогледални фотоапарати", "mirrorless"),
    COMPACT ("Компактни фотоапарати", "compact");

    private String title;
    private String href;

    CameraTypeEnum(String title, String href) {
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
