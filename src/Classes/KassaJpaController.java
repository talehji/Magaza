/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Entity.Satisnovu;
import Entity.Satis;
import Entity.Kassa;
import Entity.Member;
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
            Member idMember = kassa.getIdMember();
            if (idMember != null) {
                idMember = em.getReference(idMember.getClass(), idMember.getIdMember());
                kassa.setIdMember(idMember);
            }
            Satisnovu idSatisNovu = kassa.getIdSatisNovu();
            if (idSatisNovu != null) {
                idSatisNovu = em.getReference(idSatisNovu.getClass(), idSatisNovu.getIdSatisNovu());
                kassa.setIdSatisNovu(idSatisNovu);
            }
            Collection<Satis> attachedSatisCollection = new ArrayList<Satis>();
            for (Satis satisCollectionSatisToAttach : kassa.getSatisCollection()) {
                satisCollectionSatisToAttach = em.getReference(satisCollectionSatisToAttach.getClass(), satisCollectionSatisToAttach.getIdSatis());
                attachedSatisCollection.add(satisCollectionSatisToAttach);
            }
            kassa.setSatisCollection(attachedSatisCollection);
            em.persist(kassa);
            if (idMember != null) {
                idMember.getKassaCollection().add(kassa);
                idMember = em.merge(idMember);
            }
            if (idSatisNovu != null) {
                idSatisNovu.getKassaCollection().add(kassa);
                idSatisNovu = em.merge(idSatisNovu);
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

    public void edit(Kassa kassa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kassa persistentKassa = em.find(Kassa.class, kassa.getIdKassa());
            Member idMemberOld = persistentKassa.getIdMember();
            Member idMemberNew = kassa.getIdMember();
            Satisnovu idSatisNovuOld = persistentKassa.getIdSatisNovu();
            Satisnovu idSatisNovuNew = kassa.getIdSatisNovu();
            Collection<Satis> satisCollectionOld = persistentKassa.getSatisCollection();
            Collection<Satis> satisCollectionNew = kassa.getSatisCollection();
            if (idMemberNew != null) {
                idMemberNew = em.getReference(idMemberNew.getClass(), idMemberNew.getIdMember());
                kassa.setIdMember(idMemberNew);
            }
            if (idSatisNovuNew != null) {
                idSatisNovuNew = em.getReference(idSatisNovuNew.getClass(), idSatisNovuNew.getIdSatisNovu());
                kassa.setIdSatisNovu(idSatisNovuNew);
            }
            Collection<Satis> attachedSatisCollectionNew = new ArrayList<Satis>();
            for (Satis satisCollectionNewSatisToAttach : satisCollectionNew) {
                satisCollectionNewSatisToAttach = em.getReference(satisCollectionNewSatisToAttach.getClass(), satisCollectionNewSatisToAttach.getIdSatis());
                attachedSatisCollectionNew.add(satisCollectionNewSatisToAttach);
            }
            satisCollectionNew = attachedSatisCollectionNew;
            kassa.setSatisCollection(satisCollectionNew);
            kassa = em.merge(kassa);
            if (idMemberOld != null && !idMemberOld.equals(idMemberNew)) {
                idMemberOld.getKassaCollection().remove(kassa);
                idMemberOld = em.merge(idMemberOld);
            }
            if (idMemberNew != null && !idMemberNew.equals(idMemberOld)) {
                idMemberNew.getKassaCollection().add(kassa);
                idMemberNew = em.merge(idMemberNew);
            }
            if (idSatisNovuOld != null && !idSatisNovuOld.equals(idSatisNovuNew)) {
                idSatisNovuOld.getKassaCollection().remove(kassa);
                idSatisNovuOld = em.merge(idSatisNovuOld);
            }
            if (idSatisNovuNew != null && !idSatisNovuNew.equals(idSatisNovuOld)) {
                idSatisNovuNew.getKassaCollection().add(kassa);
                idSatisNovuNew = em.merge(idSatisNovuNew);
            }
            for (Satis satisCollectionOldSatis : satisCollectionOld) {
                if (!satisCollectionNew.contains(satisCollectionOldSatis)) {
                    satisCollectionOldSatis.setIdKassa(null);
                    satisCollectionOldSatis = em.merge(satisCollectionOldSatis);
                }
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            Member idMember = kassa.getIdMember();
            if (idMember != null) {
                idMember.getKassaCollection().remove(kassa);
                idMember = em.merge(idMember);
            }
            Satisnovu idSatisNovu = kassa.getIdSatisNovu();
            if (idSatisNovu != null) {
                idSatisNovu.getKassaCollection().remove(kassa);
                idSatisNovu = em.merge(idSatisNovu);
            }
            Collection<Satis> satisCollection = kassa.getSatisCollection();
            for (Satis satisCollectionSatis : satisCollection) {
                satisCollectionSatis.setIdKassa(null);
                satisCollectionSatis = em.merge(satisCollectionSatis);
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
