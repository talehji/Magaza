/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityController;

import Claslar.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Kateqoriya;
import Entity.Mallar;
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
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kateqoriya idKateqoriya = mallar.getIdKateqoriya();
            if (idKateqoriya != null) {
                idKateqoriya = em.getReference(idKateqoriya.getClass(), idKateqoriya.getIdKateqoriya());
                mallar.setIdKateqoriya(idKateqoriya);
            }
            em.persist(mallar);
            if (idKateqoriya != null) {
                idKateqoriya.getMallarCollection().add(mallar);
                idKateqoriya = em.merge(idKateqoriya);
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
            if (idKateqoriyaNew != null) {
                idKateqoriyaNew = em.getReference(idKateqoriyaNew.getClass(), idKateqoriyaNew.getIdKateqoriya());
                mallar.setIdKateqoriya(idKateqoriyaNew);
            }
            mallar = em.merge(mallar);
            if (idKateqoriyaOld != null && !idKateqoriyaOld.equals(idKateqoriyaNew)) {
                idKateqoriyaOld.getMallarCollection().remove(mallar);
                idKateqoriyaOld = em.merge(idKateqoriyaOld);
            }
            if (idKateqoriyaNew != null && !idKateqoriyaNew.equals(idKateqoriyaOld)) {
                idKateqoriyaNew.getMallarCollection().add(mallar);
                idKateqoriyaNew = em.merge(idKateqoriyaNew);
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
