
package com.mycompany.consultoriodescorp;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @author valeria
 */
@Entity
@Table(name="TB_USUARIO")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="DISC_USUARIO",discriminatorType=DiscriminatorType.STRING,length=1)
@Access(AccessType.FIELD)
public abstract class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="TXT_NOME")
    private String nome;
    @Column(name="TXT_LOGIN")
    private String login;
    @Column(name="TXT_SENHA")
    private String senha;
    @Column(name="TXT_EMAIL")
    private String email;
    @Column(name="TXT_SEXO")
    private String sexo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
   
    
}
