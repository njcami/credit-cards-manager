package com.manager.cards.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;

/**
 * Entity class for Card User Object Relational Mapping
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "USERS")
public class CardUser {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "user_id")
   private Long id;

   @Column(name = "user_name", unique = true)
   @Size(max = 10, min = 3, message = "{user.name.invalid}")
   @NotEmpty(message="Please enter your username")
   private String username;

   @Column(name = "user_password")
   @Size(max = 60, min = 6, message = "{user.password.invalid}")
   @NotEmpty(message="Please enter your password")
   private String password;

   @Column(name = "created_date")
   private Timestamp created;

   @Column(name = "is_admin")
   private Boolean isAdmin;
}
