package com.example.demo.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class CompteComptable {

		@Id
		private String  id;
		private String  uuid;
		private String  uuidUtilisateur;
		@Indexed
		private String  uuidEntreprise; // null dans le cas d'un compte pcg

		private String  numero; //8 digits obligatoire
		private String  description;
		private boolean comptePcg = true; // true dans le cas d'un compte pcg : plan comptable générale
		private String  dateCreation = LocalDateTime.now().toString();
}
