package com.bill.tech.entity;

import java.util.Date;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("deprecation")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "studyMaterial")
@Where(clause = "deleted_by is null")

public class StudyMaterial extends Auditable{

	private static final long serialVersionUID = 102706204299942902L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    private String filePath;
    
    @Column(columnDefinition = "date")
    private Date uploadedDate;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}

