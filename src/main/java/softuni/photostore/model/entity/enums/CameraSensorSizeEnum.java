package softuni.photostore.model.entity.enums;

public enum CameraSensorSizeEnum {
    APS_C("APS-C"), FULL_FRAME("Full Frame"), MEDIUM_FORMAT ("Medium Format"),
    SMALLER_SENSOR ("Smaller sensor");

    private String displayName;

    CameraSensorSizeEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
