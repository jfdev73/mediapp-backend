package com.mitocode.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Menu {
	
	@Id
	@EqualsAndHashCode.Include
	private Long idMenu;
	
	
	@Column(nullable = false, length = 20)
	private String icon;
	
	@Column(nullable = false, length = 20)
	private String name;
	
	@Column(nullable = false, length = 30)
	private String url;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "menu_role",
	joinColumns = @JoinColumn(name="id_menu", referencedColumnName = "idMenu"),
	inverseJoinColumns = @JoinColumn(name="id_role", referencedColumnName = "idRole")
	)
	private List<Role> roles;
	
	

}
