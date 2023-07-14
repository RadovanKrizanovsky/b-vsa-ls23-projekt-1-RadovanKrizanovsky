package sk.stuba.fei.uim.vsa.pr1;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import lombok.Data;

@Entity
@Data
public class ZAVERECNAPRACA implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Basic(optional = false)
  @Column(nullable = false, unique = true)
  private Long id;

  @Basic(optional = false)
  @Column(nullable = false, unique = true)
  private String registacneCislo;

  @PrePersist
  public void onPrePersist() {
    this.registacneCislo = "FEI-" + UUID.randomUUID().toString();
  }

  private String nazov;
  private String popis;
  private String pracovisko;

  @ManyToOne
  @JoinColumn(name = "veduci_id")
  @Basic(optional = false)
  @Column(nullable = false)
  private PEDAGOG veduciPrace;

  @OneToOne(optional = true)
  @JoinColumn(unique = true, nullable = true)
  private STUDENT vypracovatel;

  private LocalDate datumZverejnenia;

  private LocalDate deadlineOdovzdania;

  @Enumerated(EnumType.STRING)
  private Typ typ;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Basic(optional = false)
  private Status status;
  /*
    ADD
    
      public void addZaverecnaPraca(ZaverecnaPraca zaverecnaPraca) {
        this.vypisanePrace.add(zaverecnaPraca);
        zaverecnaPraca.setVeduciPrace(this);
    }
    
    public void removeZaverecnaPraca(ZaverecnaPraca zaverecnaPraca) {
        this.vypisanePrace.remove(zaverecnaPraca);
        zaverecnaPraca.setVeduciPrace(null);
    }
    
    */

}
