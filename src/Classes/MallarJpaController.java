/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Entity.Kateqoriya;
import Entity.Depo;
import Entity.Satis;
import Entity.Mallar;
import Classes.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pallas
 */
public class MallarJpaController implements Serializable {

    public MallarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mallar mallar) {
        if (mallar.getSatisCollection() == null) {
            mallar.setSatisCollection(new ArrayList<Satis>());
        }
        if (mallar.getDepoCollection() == null) {
            mallar.setDepoCollection(new ArrayList<Depo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kateqoriya idKateqoriya = mallar.getIdKateqoriya();
            if (idKateqoriya != null) {
                idKateqoriya = em.getReference(idKateqoriya.getClass(), idKateqoriya.getIdKateqoriya());
                mallar.setIdKateqoriya(idKateqoriya);
            }
            Collection<Satis> attachedSatisCollection = new ArrayList<Satis>();
            for (Satis satisCollectionSatisToAttach : mallar.getSatisCollection()) {
                satisCollectionSatisToAttach = em.getReference(satisCollectionSatisToAttach.getClass(), satisCollectionSatisToAttach.getIdSatis());
                attachedSatisCollection.add(satisCollectionSatisToAttach);
            }
            mallar.setSatisCollection(attachedSatisCollection);
            Collection<Depo> attachedDepoCollection = new ArrayList<Depo>();
            for (Depo depoCollectionDepoToAttach : mallar.getDepoCollection()) {
                depoCollectionDepoToAttach = em.getReference(depoCollectionDepoToAttach.getClass(), depoCollectionDepoToAttach.getIdDepo());
                attachedDepoCollection.add(depoCollectionDepoToAttach);
            }
            mallar.setDepoCollection(attachedDepoCollection);
            em.persist(mallar);
            if (idKateqoriya != null) {
                idKateqoriya.getMallarCollection().add(mallar);
                idKateqoriya = em.merge(idKateqoriya);
            }
            for (Satis satisCollectionSatis : mallar.getSatisCollection()) {
                Mallar oldIdMallarOfSatisCollectionSatis = satisCollectionSatis.getIdMallar();
                satisCollectionSatis.setIdMallar(mallar);
                satisCollectionSatis = em.merge(satisCollectionSatis);
                if (oldIdMallarOfSatisCollectionSatis != null) {
                    oldIdMallarOfSatisCollectionSatis.getSatisCollection().remove(satisCollectionSatis);
                    oldIdMallarOfSatisCollectionSatis = em.merge(oldIdMallarOfSatisCollectionSatis);
                }
            }
            for (Depo depoCollectionDepo : mallar.getDepoCollection()) {
                Mallar oldIdMallarOfDepoCollectionDepo = depoCollectionDepo.getIdMallar();
                depoCollectionDepo.setIdMallar(mallar);
                depoCollectionDepo = em.merge(depoCollectionDepo);
                if (oldIdMallarOfDepoCollectionDepo != null) {
                    oldIdMallarOfDepoCollectionDepo.getDepoCollection().remove(depoCollectionDepo);
                    oldIdMallarOfDepoCollectionDepo = em.merge(oldIdMallarOfDepoCollectionDepo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mallar mallar) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mallar persistentMallar = em.find(Mallar.class, mallar.getIdMallar());
            Kateqoriya idKateqoriyaOld = persistentMallar.getIdKateqoriya();
            Kateqoriya idKateqoriyaNew = mallar.getIdKateqoriya();
            Collection<Satis> satisCollectionOld = persistentMallar.getSatisCollection();
            Collection<Satis> satisCollectionNew = mallar.getSatisCollection();
            Collection<Depo> depoCollectionOld = persistentMallar.getDepoCollection();
            Collection<Depo> depoCollectionNew = mallar.getDepoCollection();
            if (idKateqoriyaNew != null) {
                idKateqoriyaNew = em.getReference(idKateqoriyaNew.getClass(), idKateqoriyaNew.getIdKateqoriya());
                mallar.setIdKateqoriya(idKateqoriyaNew);
            }
            Collection<Satis> attachedSatisCollectionNew = new ArrayList<Satis>();
            for (Satis satisCollectionNewSatisToAttach : satisCollectionNew) {
                satisCollectionNewSatisToAttach = em.getReference(satisCollectionNewSatisToAttach.getClass(), satisCollectionNewSatisToAttach.getIdSatis());
                attachedSatisCollectionNew.add(satisCollectionNewSatisToAttach);
            }
            satisCollectionNew = attachedSatisCollectionNew;
            mallar.setSatisCollection(satisCollectionNew);
            Collection<Depo> attachedDepoCollectionNew = new ArrayList<Depo>();
            for (Depo depoCollectionNewDepoToAttach : depoCollectionNew) {
                depoCollectionNewDepoToAttach = em.getReference(depoCollectionNewDepoToAttach.getClass(), depoCollectionNewDepoToAttach.getIdDepo());
                attachedDepoCollectionNew.add(depoCollectionNewDepoToAttach);
            }
            depoCollectionNew = attachedDepoCollectionNew;
            mallar.setDepoCollection(depoCollectionNew);
            mallar = em.merge(mallar);
            if (idKateqoriyaOld != null && !idKateqoriyaOld.equals(idKateqoriyaNew)) {
                idKateqoriyaOld.getMallarCollection().remove(mallar);
                idKateqoriyaOld = em.merge(idKateqoriyaOld);
            }
            if (idKateqoriyaNew != null && !idKateqoriyaNew.equals(idKateqoriyaOld)) {
                idKateqoriyaNew.getMallarCollection().add(mallar);
                idKateqoriyaNew = em.merge(idKateqoriyaNew);
            }
            for (Satis satisCollectionOldSatis : satisCollectionOld) {
                if (!satisCollectionNew.contains(satisCollectionOldSatis)) {
                    satisCollectionOldSatis.setIdMallar(null);
                    satisCollectionOldSatis = em.merge(satisCollectionOldSatis);
                }
            }
            for (Satis satisCollectionNewSatis : satisCollectionNew) {
                if (!satisCollectionOld.contains(satisCollectionNewSatis)) {
                    Mallar oldIdMallarOfSatisCollectionNewSatis = satisCollectionNewSatis.getIdMallar();
                    satisCollectionNewSatis.setIdMallar(mallar);
                    satisCollectionNewSatis = em.merge(satisCollectionNewSatis);
                    if (oldIdMallarOfSatisCollectionNewSatis != null && !oldIdMallarOfSatisCollectionNewSatis.equals(mallar)) {
                        oldIdMallarOfSatisCollectionNewSatis.getSatisCollection().remove(satisCollectionNewSatis);
                        oldIdMallarOfSatisCollectionNewSatis = em.merge(oldIdMallarOfSatisCollectionNewSatis);
                    }
                }
            }
            for (Depo depoCollectionOldDepo : depoCollectionOld) {
                if (!depoCollectionNew.contains(depoCollectionOldDepo)) {
                    depoCollectionOldDepo.setIdMallar(null);
                    depoCollectionOldDepo = em.merge(depoCollectionOldDepo);
                }
            }
            for (Depo depoCollectionNewDepo : depoCollectionNew) {
                if (!depoCollectionOld.contains(depoCollectionNewDepo)) {
                    Mallar oldIdMallarOfDepoCollectionNewDepo = depoCollectionNewDepo.getIdMallar();
                    depoCollectionNewDepo.setIdMallar(mallar);
                    depoCollectionNewDepo = em.merge(depoCollectionNewDepo);
                    if (oldIdMallarOfDepoCollectionNewDepo != null && !oldIdMallarOfDepoCollectionNewDepo.equals(mallar)) {
                        oldIdMallarOfDepoCollectionNewDepo.getDepoCollection().remove(depoCollectionNewDepo);
                        oldIdMallarOfDepoCollectionNewDepo = em.merge(oldIdMallarOfDepoCollectionNewDepo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mallar.getIdMallar();
                if (findMallar(id) == null) {
                    throw new NonexistentEntityException("The mallar with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mallar mallar;
            try {
                mallar = em.getReference(Mallar.class, id);
                mallar.getIdMallar();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mallar with id " + id + " no longer exists.", enfe);
            }
            Kateqoriya idKateqoriya = mallar.getIdKateqoriya();
            if (idKateqoriya != null) {
                idKateqoriya.getMallarCollection().remove(mallar);
                idKateqoriya = em.merge(idKateqoriya);
            }
            Collection<Satis> satisCollection = mallar.getSatisCollection();
            for (Satis satisCollectionSatis : satisCollection) {
                satisCollectionSatis.setIdMallar(null);
                satisCollectionSatis = em.merge(satisCollectionSatis);
            }
            Collection<Depo> depoCollection = mallar.getDepoCollection();
            for (Depo depoCollectionDepo : depoCollection) {
                depoCollectionDepo.setIdMallar(null);
                depoCollectionDepo = em.merge(depoCollectionDepo);
            }
            em.remove(mallar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mallar> findMallarEntities() {
        return findMallarEntities(true, -1, -1);
    }

    public List<Mallar> findMallarEntities(int maxResults, int firstResult) {
        return findMallarEntities(false, maxResults, firstResult);
    }

    private List<Mallar> findMallarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mallar.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Mallar findMallar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mallar.class, id);
        } finally {
            em.close();
        }
    }

    public int getMallarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mallar> rt = cq.from(Mallar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
