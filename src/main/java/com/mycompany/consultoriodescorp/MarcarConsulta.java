package com.mycompany.consultoriodescorp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author valeria
 */
@Entity
@Table(name = "TB_MARCAR_CONSULTA")
public class MarcarConsulta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TXT_DATA")
    @Temporal(TemporalType.DATE)
    private Date dataConsuta;
    @Column(name = "TXT_HORA")
    private String hora;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_CONSULTA_MEDICOS", joinColumns = {
        @JoinColumn(name = "ID_MARCAR_CONSULTA")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_USUARIO")})
    private List<Funcionario> medicos;
 
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_CONSULTA_PACIENTES", joinColumns = {
        @JoinColumn(name = "ID_MARCAR_CONSULTA")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_USUARIO")})
    private List<Paciente> pacientes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataConsuta() {
        return dataConsuta;
    }

    public void setDataConsuta(Date dataConsuta) {
        this.dataConsuta = dataConsuta;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public List<Funcionario> getMedicos() {
        return medicos;
    }

    public void setMedicos(Funcionario medico) {
        
        if (this.medicos == null) {
            this.medicos = new ArrayList<>();
        }
        this.medicos.add(medico);
    }
    

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(Paciente paciente) {
        if (this.pacientes == null) {
            this.pacientes = new ArrayList<>();
        }
        this.pacientes.add(paciente);
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MarcarConsulta other = (MarcarConsulta) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
