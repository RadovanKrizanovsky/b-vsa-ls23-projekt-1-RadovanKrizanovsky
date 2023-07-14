package sk.stuba.fei.uim.vsa.pr1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class FinalApplication
  extends AbstractThesisService<STUDENT, PEDAGOG, ZAVERECNAPRACA> {

  public FinalApplication() {
    super();
  }

  @Override
  public STUDENT createStudent(Long aisId, String name, String email) {
    EntityManager entityManager = emf.createEntityManager();

    entityManager.getTransaction().begin();
    try {
      STUDENT student = new STUDENT();
      student.setAisId(aisId);
      student.setMeno(name);
      student.setEmail(email);

      entityManager.persist(student);
      entityManager.getTransaction().commit();

      return student;
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      return null;
    } finally {
      entityManager.close();
    }
  }

  @Override
  public STUDENT getStudent(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("id je null");
    }

    EntityManager entityManager = emf.createEntityManager();

    try {
      STUDENT student = entityManager.find(STUDENT.class, id);
      return student;
    } catch (Exception e) {
      return null;
    } finally {
      entityManager.close();
    }
  }

  @Override
  public STUDENT updateStudent(STUDENT student) {
    if (student == null || student.getAisId() == null) {
      throw new IllegalArgumentException("id alebo student je null");
    }

    EntityManager entityManager = emf.createEntityManager();

    entityManager.getTransaction().begin();
    try {
      entityManager.merge(student);
      entityManager.getTransaction().commit();

      return student;
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      return null;
    } finally {
      entityManager.close();
    }
  }

  @Override
  public List<STUDENT> getStudents() {
    EntityManager entityManager = emf.createEntityManager();

    try {
      TypedQuery<STUDENT> query = entityManager.createQuery(
        "SELECT s FROM STUDENT s",
        STUDENT.class
      );
      List<STUDENT> students = query.getResultList();

      return students;
    } catch (Exception e) {
      List<STUDENT> prazdnyZoznam = new ArrayList<>();
      return prazdnyZoznam;
    } finally {
      entityManager.close();
    }
  }

  @Override
  public STUDENT deleteStudent(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("id je null");
    }

    EntityManager entityManager = emf.createEntityManager();

    entityManager.getTransaction().begin();
    try {
      STUDENT student = entityManager.find(STUDENT.class, id);
      entityManager.remove(student);
      entityManager.getTransaction().commit();
      return student;
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      return null;
    } finally {
      entityManager.close();
    }
  }

  //Whole student checked

  @Override
  public PEDAGOG createTeacher(
    Long aisId,
    String name,
    String email,
    String department
  ) {
    EntityManager entityManager = emf.createEntityManager();

    entityManager.getTransaction().begin();
    try {
      PEDAGOG pedagog = new PEDAGOG();
      pedagog.setAisId(aisId);
      pedagog.setMeno(name);
      pedagog.setEmail(email);
      pedagog.setInstitut(department);

      entityManager.persist(pedagog);
      entityManager.getTransaction().commit();

      return pedagog;
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      return null;
    } finally {
      entityManager.close();
    }
  }

  @Override
  public PEDAGOG getTeacher(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("id je null");
    }

    EntityManager entityManager = emf.createEntityManager();

    try {
      PEDAGOG teacher = entityManager.find(PEDAGOG.class, id);
      return teacher;
    } catch (Exception e) {
      return null;
    } finally {
      entityManager.close();
    }
  }

  @Override
  public PEDAGOG updateTeacher(PEDAGOG teacher) {
    if (teacher == null || teacher.getAisId() == null) {
      throw new IllegalArgumentException("id alebo student je null");
    }

    EntityManager entityManager = emf.createEntityManager();

    entityManager.getTransaction().begin();
    try {
      entityManager.merge(teacher);
      entityManager.getTransaction().commit();

      return teacher;
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      return null;
    } finally {
      entityManager.close();
    }
  }

  @Override
  public List<PEDAGOG> getTeachers() {
    EntityManager entityManager = emf.createEntityManager();

    try {
      TypedQuery<PEDAGOG> query = entityManager.createQuery(
        "SELECT p FROM PEDAGOG p",
        PEDAGOG.class
      );
      List<PEDAGOG> teachers = query.getResultList();

      return teachers;
    } catch (Exception e) {
      List<PEDAGOG> prazdnyZoznam = new ArrayList<>();
      return prazdnyZoznam;
    } finally {
      entityManager.close();
    }
  }

  @Override
  public PEDAGOG deleteTeacher(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("id je null");
    }

    EntityManager entityManager = emf.createEntityManager();

    entityManager.getTransaction().begin();
    try {
      PEDAGOG teacher = entityManager.find(PEDAGOG.class, id);
      entityManager.remove(teacher);
      entityManager.getTransaction().commit();
      return teacher;
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      return null;
    } finally {
      entityManager.close();
    }
  }

  //DONE
  @Override
  public ZAVERECNAPRACA makeThesisAssignment(
    Long supervisor,
    String title,
    String type,
    String description
  ) {
    if (supervisor == null) {
      throw new IllegalArgumentException("id je null");
    }

    EntityManager entityManager = emf.createEntityManager();

    entityManager.getTransaction().begin();
    try {
      ZAVERECNAPRACA zaverecnapraca = new ZAVERECNAPRACA();

      PEDAGOG pedagog = entityManager.find(PEDAGOG.class, supervisor);

      zaverecnapraca.setVeduciPrace(pedagog);
      zaverecnapraca.setNazov(title);
      zaverecnapraca.setTyp(Typ.valueOf(type));
      zaverecnapraca.setPopis(description);
      zaverecnapraca.setPracovisko(pedagog.getInstitut());
      zaverecnapraca.setDatumZverejnenia(LocalDate.now());
      zaverecnapraca.setDeadlineOdovzdania(LocalDate.now().plusMonths(3));
      zaverecnapraca.setStatus(Status.VOLNA);

      entityManager.persist(zaverecnapraca);
      entityManager.getTransaction().commit();

      return zaverecnapraca;
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      return null;
    } finally {
      entityManager.close();
    }
  }

  //DONE
  @Override
  public ZAVERECNAPRACA assignThesis(Long thesisId, Long studentId) {
    EntityManager entityManager = emf.createEntityManager();
    if (thesisId == null || studentId == null) {
      throw new IllegalArgumentException("niektore id je null");
    }
    ZAVERECNAPRACA zaverecnaPraca = entityManager.find(
      ZAVERECNAPRACA.class,
      thesisId
    );
    if (zaverecnaPraca == null) {
      return null;
    }

    if (
      zaverecnaPraca.getStatus() != Status.ZABRANA ||
      zaverecnaPraca.getDeadlineOdovzdania().isBefore(LocalDate.now())
    ) {
      throw new IllegalStateException("neda sa uz priradit");
    }
    STUDENT student = entityManager.find(STUDENT.class, studentId);
    if (student == null) {
      return null;
    }

    entityManager.getTransaction().begin();
    try {
      zaverecnaPraca.setVypracovatel(student);
      zaverecnaPraca.setStatus(Status.ZABRANA);
      entityManager.persist(zaverecnaPraca);
      entityManager.getTransaction().commit();
      return zaverecnaPraca;
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      return null;
    } finally {
      entityManager.close();
    }
  }

  //DONE
  @Override
  public ZAVERECNAPRACA submitThesis(Long thesisId) {
    EntityManager entityManager = emf.createEntityManager();
    if (thesisId == null) {
      throw new IllegalArgumentException("id je null");
    }
    ZAVERECNAPRACA zaverecnaPraca = entityManager.find(
      ZAVERECNAPRACA.class,
      thesisId
    );
    if (zaverecnaPraca == null) {
      return null;
    }

    if (
      zaverecnaPraca.getStatus() == Status.ZABRANA ||
      zaverecnaPraca.getStatus() == Status.ODOVZDANA ||
      zaverecnaPraca.getDeadlineOdovzdania().isBefore(LocalDate.now())
    ) {
      throw new IllegalStateException("neda sa odovzdat");
    }

    if (zaverecnaPraca.getVypracovatel() == null) {
      throw new IllegalStateException("nepriradena zav. praca");
    }
    entityManager.getTransaction().begin();
    try {
      zaverecnaPraca.setStatus(Status.ODOVZDANA);
      entityManager.persist(zaverecnaPraca);
      entityManager.getTransaction().commit();
      return zaverecnaPraca;
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      return null;
    } finally {
      entityManager.close();
    }
  }

  //DONE
  @Override
  public ZAVERECNAPRACA deleteThesis(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("id je null");
    }

    EntityManager entityManager = emf.createEntityManager();

    entityManager.getTransaction().begin();
    try {
      ZAVERECNAPRACA zp = entityManager.find(ZAVERECNAPRACA.class, id);
      entityManager.remove(zp);
      entityManager.getTransaction().commit();
      return zp;
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      return null;
    } finally {
      entityManager.close();
    }
  }

  //DONE
  @Override
  public List<ZAVERECNAPRACA> getTheses() {
    EntityManager entityManager = emf.createEntityManager();

    try {
      TypedQuery<ZAVERECNAPRACA> query = entityManager.createQuery(
        "SELECT z FROM ZAVERECNAPRACA z",
        ZAVERECNAPRACA.class
      );
      List<ZAVERECNAPRACA> zv = query.getResultList();

      return zv;
    } catch (Exception e) {
      List<ZAVERECNAPRACA> prazdnyZoznam = new ArrayList<>();
      return prazdnyZoznam;
    } finally {
      entityManager.close();
    }
  }

  //DONE
  @Override
  public List<ZAVERECNAPRACA> getThesesByTeacher(Long teacherId) {
    if (teacherId == null) {
      throw new IllegalArgumentException("Identifikátor nesmie byť null.");
    }
    EntityManager entityManager = emf.createEntityManager();

    try {
      TypedQuery<ZAVERECNAPRACA> query = entityManager.createQuery(
        "SELECT z FROM ZAVERECNAPRACA z WHERE z.veduciPrace.id = :teacherId",
        ZAVERECNAPRACA.class
      );
      query.setParameter("teacherId", teacherId);
      List<ZAVERECNAPRACA> theses = query.getResultList();
      if (theses.size() > 0) {
        return theses;
      } else {
        List<ZAVERECNAPRACA> prazdnyZoznam = new ArrayList<>();
        return prazdnyZoznam;
      }
    } finally {
      entityManager.close();
    }
  }

  //DONE
  @Override
  public ZAVERECNAPRACA getThesisByStudent(Long studentId) {
    if (studentId == null) {
      throw new IllegalArgumentException(
        "Identifikátor študenta nesmie byť null."
      );
    }
    EntityManager entityManager = emf.createEntityManager();

    try {
      TypedQuery<ZAVERECNAPRACA> query = entityManager.createQuery(
        "SELECT z FROM ZAVERECNAPRACA z WHERE z.vypracovatel.id = :studentId",
        ZAVERECNAPRACA.class
      );
      query.setParameter("studentId", studentId);
      List<ZAVERECNAPRACA> theses = query.getResultList();
      if (theses.size() > 0) {
        return theses.get(0);
      } else {
        return null;
      }
    } finally {
      entityManager.close();
    }
  }

  //DONE
  @Override
  public ZAVERECNAPRACA getThesis(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("id je null");
    }

    EntityManager entityManager = emf.createEntityManager();

    try {
      ZAVERECNAPRACA zp = entityManager.find(ZAVERECNAPRACA.class, id);
      return zp;
    } catch (Exception e) {
      return null;
    } finally {
      entityManager.close();
    }
  }

  //DONE
  @Override
  public ZAVERECNAPRACA updateThesis(ZAVERECNAPRACA thesis) {
    //------------------------------------------
    if (thesis == null) {
      throw new IllegalArgumentException(
        "Inštancia entity záverečnej práce nesmie byť null."
      );
    }

    Long thesisId = thesis.getId();
    if (thesisId == null) {
      throw new IllegalArgumentException(
        "Identifikátor entity záverečnej práce nesmie byť null."
      );
    }

    EntityManager entityManager = emf.createEntityManager();

    entityManager.getTransaction().begin();
    try {
      entityManager.merge(thesis);
      entityManager.getTransaction().commit();

      return thesis;
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      return null;
    } finally {
      entityManager.close();
    }
    //------------------------------------------
  }
}
