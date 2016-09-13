/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityController;

import Claslar.exceptions.NonexistentEntityException;
import Entity.Satisnovu;
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
public class SatisnovuJpaController implements Serializable {

    public SatisnovuJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Satisnovu satisnovu) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(satisnovu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Satisnovu satisnovu) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            satisnovu = em.merge(satisnovu);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = satisnovu.getIdSatisNovu();
                if (findSatisnovu(id) == null) {
                    throw new NonexistentEntityException("The satisnovu with id " + id + " no longer exists.");
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
            Satisnovu satisnovu;
            try {
                satisnovu = em.getReference(Satisnovu.class, id);
                satisnovu.getIdSatisNovu();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The satisnovu with id " + id + " no longer exists.", enfe);
            }
            em.remove(satisnovu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Satisnovu> findSatisnovuEntities() {
        return findSatisnovuEntities(true, -1, -1);
    }

    public List<Satisnovu> findSatisnovuEntities(int maxResults, int firstResult) {
        return findSatisnovuEntities(false, maxResults, firstResult);
    }

    private List<Satisnovu> findSatisnovuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Satisnovu.class));
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

    public Satisnovu findSatisnovu(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Satisnovu.class, id);
        } finally {
            em.close();
        }
    }

    public int getSatisnovuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Satisnovu> rt = cq.from(Satisnovu.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
