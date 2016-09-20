/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Entity.Depo;
import Entity.Mallar;
import Classes.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Pallas
 */
public class DepoJpaController implements Serializable {

    public DepoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Depo depo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mallar idMallar = depo.getIdMallar();
            if (idMallar != null) {
                idMallar = em.getReference(idMallar.getClass(), idMallar.getIdMallar());
                depo.setIdMallar(idMallar);
            }
            em.persist(depo);
            if (idMallar != null) {
                idMallar.getDepoCollection().add(depo);
                idMallar = em.merge(idMallar);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Depo depo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Depo persistentDepo = em.find(Depo.class, depo.getIdDepo());
            Mallar idMallarOld = persistentDepo.getIdMallar();
            Mallar idMallarNew = depo.getIdMallar();
            if (idMallarNew != null) {
                idMallarNew = em.getReference(idMallarNew.getClass(), idMallarNew.getIdMallar());
                depo.setIdMallar(idMallarNew);
            }
            depo = em.merge(depo);
            if (idMallarOld != null && !idMallarOld.equals(idMallarNew)) {
                idMallarOld.getDepoCollection().remove(depo);
                idMallarOld = em.merge(idMallarOld);
            }
            if (idMallarNew != null && !idMallarNew.equals(idMallarOld)) {
                idMallarNew.getDepoCollection().add(depo);
                idMallarNew = em.merge(idMallarNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = depo.getIdDepo();
                if (findDepo(id) == null) {
                    throw new NonexistentEntityException("The depo with id " + id + " no longer exists.");
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
            Depo depo;
            try {
                depo = em.getReference(Depo.class, id);
                depo.getIdDepo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The depo with id " + id + " no longer exists.", enfe);
            }
            Mallar idMallar = depo.getIdMallar();
            if (idMallar != null) {
                idMallar.getDepoCollection().remove(depo);
                idMallar = em.merge(idMallar);
            }
            em.remove(depo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Depo> findDepoEntities() {
        return findDepoEntities(true, -1, -1);
    }

    public List<Depo> findDepoEntities(int maxResults, int firstResult) {
        return findDepoEntities(false, maxResults, firstResult);
    }

    private List<Depo> findDepoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Depo.class));
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

    public Depo findDepo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Depo.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Depo> rt = cq.from(Depo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
