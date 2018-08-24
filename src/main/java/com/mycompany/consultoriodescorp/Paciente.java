
package com.mycompany.consultoriodescorp;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author valeria
 */
@Entity
@Table(name="TB_PACIENTE")
@DiscriminatorValue(value="P")
@PrimaryKeyJoinColumn(name="ID_USUARIO",referencedColumnName="ID")
public class Paciente extends Usuario implements Serializable{
    @Column(name="TXT_PLANO")
    private String plano;

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }
    
    
}
