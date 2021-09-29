package net.proselyte.jwtappdemo.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "subcategories")
public class Subcategory extends BaseEntityFinance {
    @Column(name = "name")
    private String name;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subcategory")
    private List<Operation> operations;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Subcategory{" +
                "id: " + super.getId() + ", " +
                "name: " + name + ", " +
                "category_id: " + category.getId() + ", " +
                "type: " + super.getType() + "}"
                ;
    }
}
