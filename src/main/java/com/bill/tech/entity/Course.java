package com.bill.tech.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
@Where(clause = "deleted_by is null")
public class Course extends Auditable {

    private static final long serialVersionUID = 1454952666985168047L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "name cannot be blank")
    @Column(name = "name", nullable = false, unique = true,length = 100)
    private String name;
    
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "fee", nullable = false)
    private Double fee;
    
    @Column(name = "syllabus", nullable = true, columnDefinition = "TEXT")
    private String syllabus;
    
    @Column(name = "duration", nullable = false)
    private Integer duration; 
    @Lob
    @Column(name = "image", nullable = true,columnDefinition = "MEDIUMBLOB")
    private byte[] image;  
    
    @Column(name = "status", nullable = false, length = 20)
    private String status;  
    
    @Column(name = "level", nullable = false, length = 50)
    private String level;  
    
    @Column(name = "language", nullable = false, length = 50)
    private String language;  

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;  
    
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;  

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Batch> batches;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyMaterial> studyMaterials;
    
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true) 
    private Category category;
    
    @Column(name = "imageUrl", unique = true,length = 100)
    private String imageUrl;
    @PrePersist
    public void validateDates() {
               if (this.endDate.isBefore(this.startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
    }
}
