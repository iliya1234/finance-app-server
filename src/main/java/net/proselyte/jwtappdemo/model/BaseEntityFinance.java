package net.proselyte.jwtappdemo.model;

import lombok.Data;
import javax.persistence.*;


@MappedSuperclass
@Data
public class BaseEntityFinance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;
}
