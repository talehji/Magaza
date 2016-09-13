/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityController;

import Claslar.exceptions.IllegalOrphanException;
import Claslar.exceptions.NonexistentEntityException;
import Entity.Kateqoriya;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Mallar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pallas
 */
public class KateqoriyaJpaController implements Serializable {

    public KateqoriyaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Kateqoriya kateqoriya) {
        if (kateqoriya.getMallarCollection() == null) {
            kateqoriya.setMallarCollection(new ArrayList<Mallar>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Mallar> attachedMallarCollection = new ArrayList<Mallar>();
            for (Mallar mallarCollectionMallarToAttach : kateqoriya.getMallarCollection()) {
                mallarCollectionMallarToAttach = em.getReference(mallarCollectionMallarToAttach.getClass(), mallarCollectionMallarToAttach.getIdMallar());
                attachedMallarCollection.add(mallarCollectionMallarToAttach);
            }
            kateqoriya.setMallarCollection(attachedMallarCollection);
            em.persist(kateqoriya);
            for (Mallar mallarCollectionMallar : kateqoriya.getMallarCollection()) {
                Kateqoriya oldIdKateqoriyaOfMallarCollectionMallar = mallarCollectionMallar.getIdKateqoriya();
                mallarCollectionMallar.setIdKateqoriya(kateqoriya);
                mallarCollectionMallar = em.merge(mallarCollectionMallar);
                if (oldIdKateqoriyaOfMallarCollectionMallar != null) {
                    oldIdKateqoriyaOfMallarCollectionMallar.getMallarCollection().remove(mallarCollectionMallar);
                    oldIdKateqoriyaOfMallarCollectionMallar = em.merge(oldIdKateqoriyaOfMallarCollectionMallar);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Kateqoriya kateqoriya) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kateqoriya persistentKateqoriya = em.find(Kateqoriya.class, kateqoriya.getIdKateqoriya());
            Collection<Mallar> mallarCollectionOld = persistentKateqoriya.getMallarCollection();
            Collection<Mallar> mallarCollectionNew = kateqoriya.getMallarCollection();
            List<String> illegalOrphanMessages = null;
            for (Mallar mallarCollectionOldMallar : mallarCollectionOld) {
                if (!mallarCollectionNew.contains(mallarCollectionOldMallar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mallar " + mallarCollectionOldMallar + " since its idKateqoriya field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Mallar> attachedMallarCollectionNew = new ArrayList<Mallar>();
            for (Mallar mallarCollectionNewMallarToAttach : mallarCollectionNew) {
                mallarCollectionNewMallarToAttach = em.getReference(mallarCollectionNewMallarToAttach.getClass(), mallarCollectionNewMallarToAttach.getIdMallar());
                attachedMallarCollectionNew.add(mallarCollectionNewMallarToAttach);
            }
            mallarCollectionNew = attachedMallarCollectionNew;
            kateqoriya.setMallarCollection(mallarCollectionNew);
            kateqoriya = em.merge(kateqoriya);
            for (Mallar mallarCollectionNewMallar : mallarCollectionNew) {
                if (!mallarCollectionOld.contains(mallarCollectionNewMallar)) {
                    Kateqoriya oldIdKateqoriyaOfMallarCollectionNewMallar = mallarCollectionNewMallar.getIdKateqoriya();
                    mallarCollectionNewMallar.setIdKateqoriya(kateqoriya);
                    mallarCollectionNewMallar = em.merge(mallarCollectionNewMallar);
                    if (oldIdKateqoriyaOfMallarCollectionNewMallar != null && !oldIdKateqoriyaOfMallarCollectionNewMallar.equals(kateqoriya)) {
                        oldIdKateqoriyaOfMallarCollectionNewMallar.getMallarCollection().remove(mallarCollectionNewMallar);
                        oldIdKateqoriyaOfMallarCollectionNewMallar = em.merge(oldIdKateqoriyaOfMallarCollectionNewMallar);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = kateqoriya.getIdKateqoriya();
                if (findKateqoriya(id) == null) {
                    throw new NonexistentEntityException("The kateqoriya with id " + id + " no longer exists.");
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
            Kateqoriya kateqoriya;
            try {
                kateqoriya = em.getReference(Kateqoriya.class, id);
                kateqoriya.getIdKateqoriya();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kateqoriya with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Mallar> mallarCollectionOrphanCheck = kateqoriya.getMallarCollection();
            for (Mallar mallarCollectionOrphanCheckMallar : mallarCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Kateqoriya (" + kateqoriya + ") cannot be destroyed since the Mallar " + mallarCollectionOrphanCheckMallar + " in its mallarCollection field has a non-nullable idKateqoriya field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(kateqoriya);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Kateqoriya> findKateqoriyaEntities() {
        return findKateqoriyaEntities(true, -1, -1);
    }

    public List<Kateqoriya> findKateqoriyaEntities(int maxResults, int firstResult) {
        return findKateqoriyaEntities(false, maxResults, firstResult);
    }

    private List<Kateqoriya> findKateqoriyaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kateqoriya.class));
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

    public Kateqoriya findKateqoriya(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kateqoriya.class, id);
        } finally {
            em.close();
        }
    }

    public int getKateqoriyaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kateqoriya> rt = cq.from(Kateqoriya.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
