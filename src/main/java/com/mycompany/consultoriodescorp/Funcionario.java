
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
@Table(name="TB_FUNCIONARIO")
@DiscriminatorValue(value="F")
@PrimaryKeyJoinColumn(name="ID_USUARIO",referencedColumnName="ID")
public class Funcionario extends Usuario implements Serializable{
    
    @Enumerated(EnumType.STRING)
    @Column(name="TXT_TIPO_FUNCIONARIO")
    private TipoFuncionario tipo;

    public TipoFuncionario getTipo() {
        return tipo;
    }

    public void setTipo(TipoFuncionario tipo) {
        this.tipo = tipo;
    }

    

   
    
}
