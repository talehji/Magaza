/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Entity.Musteri;
import Entity.Satis;
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
public class MusteriJpaController implements Serializable {

    public MusteriJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Musteri musteri) {
        if (musteri.getSatisCollection() == null) {
            musteri.setSatisCollection(new ArrayList<Satis>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Satis> attachedSatisCollection = new ArrayList<Satis>();
            for (Satis satisCollectionSatisToAttach : musteri.getSatisCollection()) {
                satisCollectionSatisToAttach = em.getReference(satisCollectionSatisToAttach.getClass(), satisCollectionSatisToAttach.getIdSatis());
                attachedSatisCollection.add(satisCollectionSatisToAttach);
            }
            musteri.setSatisCollection(attachedSatisCollection);
            em.persist(musteri);
            for (Satis satisCollectionSatis : musteri.getSatisCollection()) {
                Musteri oldIdMusteriOfSatisCollectionSatis = satisCollectionSatis.getIdMusteri();
                satisCollectionSatis.setIdMusteri(musteri);
                satisCollectionSatis = em.merge(satisCollectionSatis);
                if (oldIdMusteriOfSatisCollectionSatis != null) {
                    oldIdMusteriOfSatisCollectionSatis.getSatisCollection().remove(satisCollectionSatis);
                    oldIdMusteriOfSatisCollectionSatis = em.merge(oldIdMusteriOfSatisCollectionSatis);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Musteri musteri) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Musteri persistentMusteri = em.find(Musteri.class, musteri.getIdMusteri());
            Collection<Satis> satisCollectionOld = persistentMusteri.getSatisCollection();
            Collection<Satis> satisCollectionNew = musteri.getSatisCollection();
            Collection<Satis> attachedSatisCollectionNew = new ArrayList<Satis>();
            for (Satis satisCollectionNewSatisToAttach : satisCollectionNew) {
                satisCollectionNewSatisToAttach = em.getReference(satisCollectionNewSatisToAttach.getClass(), satisCollectionNewSatisToAttach.getIdSatis());
                attachedSatisCollectionNew.add(satisCollectionNewSatisToAttach);
            }
            satisCollectionNew = attachedSatisCollectionNew;
            musteri.setSatisCollection(satisCollectionNew);
            musteri = em.merge(musteri);
            for (Satis satisCollectionOldSatis : satisCollectionOld) {
                if (!satisCollectionNew.contains(satisCollectionOldSatis)) {
                    satisCollectionOldSatis.setIdMusteri(null);
                    satisCollectionOldSatis = em.merge(satisCollectionOldSatis);
                }
            }
            for (Satis satisCollectionNewSatis : satisCollectionNew) {
                if (!satisCollectionOld.contains(satisCollectionNewSatis)) {
                    Musteri oldIdMusteriOfSatisCollectionNewSatis = satisCollectionNewSatis.getIdMusteri();
                    satisCollectionNewSatis.setIdMusteri(musteri);
                    satisCollectionNewSatis = em.merge(satisCollectionNewSatis);
                    if (oldIdMusteriOfSatisCollectionNewSatis != null && !oldIdMusteriOfSatisCollectionNewSatis.equals(musteri)) {
                        oldIdMusteriOfSatisCollectionNewSatis.getSatisCollection().remove(satisCollectionNewSatis);
                        oldIdMusteriOfSatisCollectionNewSatis = em.merge(oldIdMusteriOfSatisCollectionNewSatis);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = musteri.getIdMusteri();
                if (findMusteri(id) == null) {
                    throw new NonexistentEntityException("The musteri with id " + id + " no longer exists.");
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
            Musteri musteri;
            try {
                musteri = em.getReference(Musteri.class, id);
                musteri.getIdMusteri();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The musteri with id " + id + " no longer exists.", enfe);
            }
            Collection<Satis> satisCollection = musteri.getSatisCollection();
            for (Satis satisCollectionSatis : satisCollection) {
                satisCollectionSatis.setIdMusteri(null);
                satisCollectionSatis = em.merge(satisCollectionSatis);
            }
            em.remove(musteri);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Musteri> findMusteriEntities() {
        return findMusteriEntities(true, -1, -1);
    }

    public List<Musteri> findMusteriEntities(int maxResults, int firstResult) {
        return findMusteriEntities(false, maxResults, firstResult);
    }

    private List<Musteri> findMusteriEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Musteri.class));
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

    public Musteri findMusteri(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Musteri.class, id);
        } finally {
            em.close();
        }
    }

    public int getMusteriCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Musteri> rt = cq.from(Musteri.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
