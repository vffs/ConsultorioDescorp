package com.mycompany.consultoriodescorp;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author valeria
 */
@Entity
@Table(name = "TB_PACIENTE")
@DiscriminatorValue(value = "P")
@PrimaryKeyJoinColumn(name = "ID_PACIENTE",referencedColumnName ="ID_USUARIO")
public class Paciente extends Usuario implements Serializable {
    
    @Enumerated(EnumType.STRING)
    @Column(name = "CL_PLANO")
    private TipoPlanoSaude plano;

    public TipoPlanoSaude getPlano() {
        return plano;
    }

    public void setPlano(TipoPlanoSaude plano) {
        this.plano = plano;
    }

}
