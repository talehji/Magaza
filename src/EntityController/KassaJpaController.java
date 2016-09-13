/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityController;

import Claslar.exceptions.IllegalOrphanException;
import Claslar.exceptions.NonexistentEntityException;
import Entity.Kassa;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Satisnovu;
import Entity.Menber;
import Entity.Satis;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pallas
 */
public class KassaJpaController implements Serializable {

    public KassaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Kassa kassa) {
        if (kassa.getSatisCollection() == null) {
            kassa.setSatisCollection(new ArrayList<Satis>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Satisnovu idSatisNovu = kassa.getIdSatisNovu();
            if (idSatisNovu != null) {
                idSatisNovu = em.getReference(idSatisNovu.getClass(), idSatisNovu.getIdSatisNovu());
                kassa.setIdSatisNovu(idSatisNovu);
            }
            Menber idMenber = kassa.getIdMenber();
            if (idMenber != null) {
                idMenber = em.getReference(idMenber.getClass(), idMenber.getIdMenber());
                kassa.setIdMenber(idMenber);
            }
            Collection<Satis> attachedSatisCollection = new ArrayList<Satis>();
            for (Satis satisCollectionSatisToAttach : kassa.getSatisCollection()) {
                satisCollectionSatisToAttach = em.getReference(satisCollectionSatisToAttach.getClass(), satisCollectionSatisToAttach.getIdSatis());
                attachedSatisCollection.add(satisCollectionSatisToAttach);
            }
            kassa.setSatisCollection(attachedSatisCollection);
            em.persist(kassa);
            if (idSatisNovu != null) {
                idSatisNovu.getKassaCollection().add(kassa);
                idSatisNovu = em.merge(idSatisNovu);
            }
            if (idMenber != null) {
                idMenber.getKassaCollection().add(kassa);
                idMenber = em.merge(idMenber);
            }
            for (Satis satisCollectionSatis : kassa.getSatisCollection()) {
                Kassa oldIdKassaOfSatisCollectionSatis = satisCollectionSatis.getIdKassa();
                satisCollectionSatis.setIdKassa(kassa);
                satisCollectionSatis = em.merge(satisCollectionSatis);
                if (oldIdKassaOfSatisCollectionSatis != null) {
                    oldIdKassaOfSatisCollectionSatis.getSatisCollection().remove(satisCollectionSatis);
                    oldIdKassaOfSatisCollectionSatis = em.merge(oldIdKassaOfSatisCollectionSatis);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Kassa kassa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kassa persistentKassa = em.find(Kassa.class, kassa.getIdKassa());
            Satisnovu idSatisNovuOld = persistentKassa.getIdSatisNovu();
            Satisnovu idSatisNovuNew = kassa.getIdSatisNovu();
            Menber idMenberOld = persistentKassa.getIdMenber();
            Menber idMenberNew = kassa.getIdMenber();
            Collection<Satis> satisCollectionOld = persistentKassa.getSatisCollection();
            Collection<Satis> satisCollectionNew = kassa.getSatisCollection();
            List<String> illegalOrphanMessages = null;
            for (Satis satisCollectionOldSatis : satisCollectionOld) {
                if (!satisCollectionNew.contains(satisCollectionOldSatis)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Satis " + satisCollectionOldSatis + " since its idKassa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idSatisNovuNew != null) {
                idSatisNovuNew = em.getReference(idSatisNovuNew.getClass(), idSatisNovuNew.getIdSatisNovu());
                kassa.setIdSatisNovu(idSatisNovuNew);
            }
            if (idMenberNew != null) {
                idMenberNew = em.getReference(idMenberNew.getClass(), idMenberNew.getIdMenber());
                kassa.setIdMenber(idMenberNew);
            }
            Collection<Satis> attachedSatisCollectionNew = new ArrayList<Satis>();
            for (Satis satisCollectionNewSatisToAttach : satisCollectionNew) {
                satisCollectionNewSatisToAttach = em.getReference(satisCollectionNewSatisToAttach.getClass(), satisCollectionNewSatisToAttach.getIdSatis());
                attachedSatisCollectionNew.add(satisCollectionNewSatisToAttach);
            }
            satisCollectionNew = attachedSatisCollectionNew;
            kassa.setSatisCollection(satisCollectionNew);
            kassa = em.merge(kassa);
            if (idSatisNovuOld != null && !idSatisNovuOld.equals(idSatisNovuNew)) {
                idSatisNovuOld.getKassaCollection().remove(kassa);
                idSatisNovuOld = em.merge(idSatisNovuOld);
            }
            if (idSatisNovuNew != null && !idSatisNovuNew.equals(idSatisNovuOld)) {
                idSatisNovuNew.getKassaCollection().add(kassa);
                idSatisNovuNew = em.merge(idSatisNovuNew);
            }
            if (idMenberOld != null && !idMenberOld.equals(idMenberNew)) {
                idMenberOld.getKassaCollection().remove(kassa);
                idMenberOld = em.merge(idMenberOld);
            }
            if (idMenberNew != null && !idMenberNew.equals(idMenberOld)) {
                idMenberNew.getKassaCollection().add(kassa);
                idMenberNew = em.merge(idMenberNew);
            }
            for (Satis satisCollectionNewSatis : satisCollectionNew) {
                if (!satisCollectionOld.contains(satisCollectionNewSatis)) {
                    Kassa oldIdKassaOfSatisCollectionNewSatis = satisCollectionNewSatis.getIdKassa();
                    satisCollectionNewSatis.setIdKassa(kassa);
                    satisCollectionNewSatis = em.merge(satisCollectionNewSatis);
                    if (oldIdKassaOfSatisCollectionNewSatis != null && !oldIdKassaOfSatisCollectionNewSatis.equals(kassa)) {
                        oldIdKassaOfSatisCollectionNewSatis.getSatisCollection().remove(satisCollectionNewSatis);
                        oldIdKassaOfSatisCollectionNewSatis = em.merge(oldIdKassaOfSatisCollectionNewSatis);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = kassa.getIdKassa();
                if (findKassa(id) == null) {
                    throw new NonexistentEntityException("The kassa with id " + id + " no longer exists.");
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
            Kassa kassa;
            try {
                kassa = em.getReference(Kassa.class, id);
                kassa.getIdKassa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kassa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Satis> satisCollectionOrphanCheck = kassa.getSatisCollection();
            for (Satis satisCollectionOrphanCheckSatis : satisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Kassa (" + kassa + ") cannot be destroyed since the Satis " + satisCollectionOrphanCheckSatis + " in its satisCollection field has a non-nullable idKassa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Satisnovu idSatisNovu = kassa.getIdSatisNovu();
            if (idSatisNovu != null) {
                idSatisNovu.getKassaCollection().remove(kassa);
                idSatisNovu = em.merge(idSatisNovu);
            }
            Menber idMenber = kassa.getIdMenber();
            if (idMenber != null) {
                idMenber.getKassaCollection().remove(kassa);
                idMenber = em.merge(idMenber);
            }
            em.remove(kassa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Kassa> findKassaEntities() {
        return findKassaEntities(true, -1, -1);
    }

    public List<Kassa> findKassaEntities(int maxResults, int firstResult) {
        return findKassaEntities(false, maxResults, firstResult);
    }

    private List<Kassa> findKassaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kassa.class));
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

    public Kassa findKassa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kassa.class, id);
        } finally {
            em.close();
        }
    }

    public int getKassaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kassa> rt = cq.from(Kassa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
