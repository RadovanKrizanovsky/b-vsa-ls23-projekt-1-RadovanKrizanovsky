package sk.stuba.fei.uim.vsa.pr1;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import lombok.Data;

/**
 *
 * @author edu
 */
@Entity
@Data
@NamedQueries({
    @NamedQuery(name = "STUDENT.findAll", query = "SELECT s FROM STUDENT s"),
    @NamedQuery(name = "STUDENT.findByAisId", query = "SELECT s FROM STUDENT s WHERE s.aisId = :aisId")
})
public class STUDENT implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private Long aisId;

    //@Column(nullable = false)
    private String meno;

    @Column(unique = true)
    private String email;

    //@Column(nullable = false)
    private int rocnik;

    //@Column(nullable = false)
    private int semester;

    //@Column(nullable = false)
    private String program;

    //@OneToOne(mappedBy = "vypracovatel")

    @OneToOne(optional = true)
    @JoinColumn(unique = true, nullable = true)
    private ZAVERECNAPRACA zaverecnaPraca;

}
