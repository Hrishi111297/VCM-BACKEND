package com.bill.tech.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "key_value_store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyValue extends Auditable {

	private static final long serialVersionUID = -2371185926631648490L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`key`", unique = true, nullable = false)
    private String key;

    @Column(name = "`value`", nullable = false)
    private String value;
}

