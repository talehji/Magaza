/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityController;

import Claslar.exceptions.IllegalOrphanException;
import Claslar.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Kassa;
import Entity.Menber;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pallas
 */
public class MenberJpaController implements Serializable {

    public MenberJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Menber menber) {
        if (menber.getKassaCollection() == null) {
            menber.setKassaCollection(new ArrayList<Kassa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Kassa> attachedKassaCollection = new ArrayList<Kassa>();
            for (Kassa kassaCollectionKassaToAttach : menber.getKassaCollection()) {
                kassaCollectionKassaToAttach = em.getReference(kassaCollectionKassaToAttach.getClass(), kassaCollectionKassaToAttach.getIdKassa());
                attachedKassaCollection.add(kassaCollectionKassaToAttach);
            }
            menber.setKassaCollection(attachedKassaCollection);
            em.persist(menber);
            for (Kassa kassaCollectionKassa : menber.getKassaCollection()) {
                Menber oldIdMenberOfKassaCollectionKassa = kassaCollectionKassa.getIdMenber();
                kassaCollectionKassa.setIdMenber(menber);
                kassaCollectionKassa = em.merge(kassaCollectionKassa);
                if (oldIdMenberOfKassaCollectionKassa != null) {
                    oldIdMenberOfKassaCollectionKassa.getKassaCollection().remove(kassaCollectionKassa);
                    oldIdMenberOfKassaCollectionKassa = em.merge(oldIdMenberOfKassaCollectionKassa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Menber menber) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Menber persistentMenber = em.find(Menber.class, menber.getIdMenber());
            Collection<Kassa> kassaCollectionOld = persistentMenber.getKassaCollection();
            Collection<Kassa> kassaCollectionNew = menber.getKassaCollection();
            List<String> illegalOrphanMessages = null;
            for (Kassa kassaCollectionOldKassa : kassaCollectionOld) {
                if (!kassaCollectionNew.contains(kassaCollectionOldKassa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Kassa " + kassaCollectionOldKassa + " since its idMenber field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Kassa> attachedKassaCollectionNew = new ArrayList<Kassa>();
            for (Kassa kassaCollectionNewKassaToAttach : kassaCollectionNew) {
                kassaCollectionNewKassaToAttach = em.getReference(kassaCollectionNewKassaToAttach.getClass(), kassaCollectionNewKassaToAttach.getIdKassa());
                attachedKassaCollectionNew.add(kassaCollectionNewKassaToAttach);
            }
            kassaCollectionNew = attachedKassaCollectionNew;
            menber.setKassaCollection(kassaCollectionNew);
            menber = em.merge(menber);
            for (Kassa kassaCollectionNewKassa : kassaCollectionNew) {
                if (!kassaCollectionOld.contains(kassaCollectionNewKassa)) {
                    Menber oldIdMenberOfKassaCollectionNewKassa = kassaCollectionNewKassa.getIdMenber();
                    kassaCollectionNewKassa.setIdMenber(menber);
                    kassaCollectionNewKassa = em.merge(kassaCollectionNewKassa);
                    if (oldIdMenberOfKassaCollectionNewKassa != null && !oldIdMenberOfKassaCollectionNewKassa.equals(menber)) {
                        oldIdMenberOfKassaCollectionNewKassa.getKassaCollection().remove(kassaCollectionNewKassa);
                        oldIdMenberOfKassaCollectionNewKassa = em.merge(oldIdMenberOfKassaCollectionNewKassa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = menber.getIdMenber();
                if (findMenber(id) == null) {
                    throw new NonexistentEntityException("The menber with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Menber menber;
            try {
                menber = em.getReference(Menber.class, id);
                menber.getIdMenber();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The menber with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Kassa> kassaCollectionOrphanCheck = menber.getKassaCollection();
            for (Kassa kassaCollectionOrphanCheckKassa : kassaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Menber (" + menber + ") cannot be destroyed since the Kassa " + kassaCollectionOrphanCheckKassa + " in its kassaCollection field has a non-nullable idMenber field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(menber);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Menber> findMenberEntities() {
        return findMenberEntities(true, -1, -1);
    }

    public List<Menber> findMenberEntities(int maxResults, int firstResult) {
        return findMenberEntities(false, maxResults, firstResult);
    }

    private List<Menber> findMenberEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Menber.class));
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

    public Menber findMenber(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Menber.class, id);
        } finally {
            em.close();
        }
    }

    public int getMenberCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Menber> rt = cq.from(Menber.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
