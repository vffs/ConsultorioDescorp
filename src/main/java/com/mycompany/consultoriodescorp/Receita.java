package com.mycompany.consultoriodescorp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author valeria
 */
@Entity
@Table(name = "TB_RECEITA")
public class Receita implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TXT_DATA_RECEITA")
    @Temporal(TemporalType.DATE)
    private Date dataReceita;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_RECEITA_REMEDIO", joinColumns = {
        @JoinColumn(name = "ID_RECEITA")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_REMEDIO")})
    private List<Remedio> remedios;
    
    @JoinColumn(name = "ID_USUARIO")
    @OneToOne(fetch = FetchType.LAZY)
    private Funcionario medico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Remedio> getRemedios() {
        return remedios;
    }

    public void adicionarRemedio(Remedio remedio) {
        if(this.remedios==null){
            this.remedios=new ArrayList<>();
        }
        this.remedios.add(remedio);
    }

    public Funcionario getMedico() {
        return medico;
    }

    public void setMedico(Funcionario medico) {
        this.medico = medico;
    }

    public Date getDataReceita() {
        return dataReceita;
    }

    public void setDataReceita(Date dataReceita) {
        this.dataReceita = dataReceita;
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
        final Receita other = (Receita) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
