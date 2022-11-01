package ru.glushenko.productlist.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "list")
public class ListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany
    @JoinTable(name = "list_products",
            joinColumns = @JoinColumn(name = "list_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    private List<ProductEntity> products = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListEntity listEntity = (ListEntity) o;
        return Objects.equals(id, listEntity.id) && Objects.equals(name, listEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}