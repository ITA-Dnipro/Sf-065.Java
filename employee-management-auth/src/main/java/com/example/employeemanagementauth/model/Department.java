package com.example.employeemanagementauth.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "departments")
@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Departmentname is required")
    private String departmentName;

    private boolean enabled;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leadId", referencedColumnName = "id")
    private User departmentLead;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "department",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    List<User> employees = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", referencedColumnName = "id" )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Department parentDepartment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Department department = (Department) o;
        return id != null && Objects.equals(id, department.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
