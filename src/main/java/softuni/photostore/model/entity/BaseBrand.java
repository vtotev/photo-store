package softuni.photostore.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseBrand extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "brand_name", nullable = false, unique = true)
    private String brandName;

    public BaseBrand() {
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public BaseBrand setId(String id) {
        this.id = id;
        return this;
    }

    public String getBrandName() {
        return brandName;
    }

    public BaseBrand setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

}
