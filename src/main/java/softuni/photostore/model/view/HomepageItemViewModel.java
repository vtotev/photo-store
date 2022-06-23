package softuni.photostore.model.view;

import softuni.photostore.model.entity.PictureEntity;

public class HomepageItemViewModel {
    private String modelName;
    private PictureEntity pictures;
    private String itemUrl;


    public HomepageItemViewModel() {
    }

    public HomepageItemViewModel(String modelName, PictureEntity pictures, String itemType) {
        this.modelName = modelName;
        this.pictures = pictures;
        this.itemUrl = itemType;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public PictureEntity getPictures() {
        return pictures;
    }

    public void setPictures(PictureEntity pictures) {
        this.pictures = pictures;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }
}
