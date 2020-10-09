package com.example.demo.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class CompteComptable {

	private String id;
	private String uuid;
	private String uuidUtilisateur;
	private String uuidEntreprise; // Null dans le cas d'un compte privé

	private String classe;
	private String numero;
	private String description;
	private boolean comptePrive; // True dans le cas d'un compte privé
	private String dateCreation = LocalDateTime.now().toString();
}
