package sk.stuba.fei.uim.vsa.pr1;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
@NamedQueries(
  {
    @NamedQuery(name = "PEDAGOG.findAll", query = "SELECT p FROM PEDAGOG p"),
    @NamedQuery(
      name = "PEDAGOG.findByAisId",
      query = "SELECT p FROM PEDAGOG p WHERE p.aisId = :aisId"
    ),
  }
)
public class PEDAGOG implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false, unique = true)
  private Long aisId;

  //@Column(nullable = false)
  private String meno;

  @Column(unique = true)
  private String email;

  //@ManyToOne(optional = false)
  //@JoinColumn(name = "institut_id")
  private String institut;

  //@ManyToOne(optional = false)
  //@JoinColumn(name = "oddelenie_id")
  private String oddelenie;

  @OneToMany(mappedBy = "veduciPrace", orphanRemoval = true)
  @Column(nullable = true)
  @Basic(optional = true)
  private List<ZAVERECNAPRACA> vypisanePrace;
}
