/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

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
public class MemberJpaController implements Serializable {

    public MemberJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Member member1) {
        if (member1.getKassaCollection() == null) {
            member1.setKassaCollection(new ArrayList<Kassa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Kassa> attachedKassaCollection = new ArrayList<Kassa>();
            for (Kassa kassaCollectionKassaToAttach : member1.getKassaCollection()) {
                kassaCollectionKassaToAttach = em.getReference(kassaCollectionKassaToAttach.getClass(), kassaCollectionKassaToAttach.getIdKassa());
                attachedKassaCollection.add(kassaCollectionKassaToAttach);
            }
            member1.setKassaCollection(attachedKassaCollection);
            em.persist(member1);
            for (Kassa kassaCollectionKassa : member1.getKassaCollection()) {
                Member oldIdMemberOfKassaCollectionKassa = kassaCollectionKassa.getIdMember();
                kassaCollectionKassa.setIdMember(member1);
                kassaCollectionKassa = em.merge(kassaCollectionKassa);
                if (oldIdMemberOfKassaCollectionKassa != null) {
                    oldIdMemberOfKassaCollectionKassa.getKassaCollection().remove(kassaCollectionKassa);
                    oldIdMemberOfKassaCollectionKassa = em.merge(oldIdMemberOfKassaCollectionKassa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Member member1) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Member persistentMember1 = em.find(Member.class, member1.getIdMember());
            Collection<Kassa> kassaCollectionOld = persistentMember1.getKassaCollection();
            Collection<Kassa> kassaCollectionNew = member1.getKassaCollection();
            Collection<Kassa> attachedKassaCollectionNew = new ArrayList<Kassa>();
            for (Kassa kassaCollectionNewKassaToAttach : kassaCollectionNew) {
                kassaCollectionNewKassaToAttach = em.getReference(kassaCollectionNewKassaToAttach.getClass(), kassaCollectionNewKassaToAttach.getIdKassa());
                attachedKassaCollectionNew.add(kassaCollectionNewKassaToAttach);
            }
            kassaCollectionNew = attachedKassaCollectionNew;
            member1.setKassaCollection(kassaCollectionNew);
            member1 = em.merge(member1);
            for (Kassa kassaCollectionOldKassa : kassaCollectionOld) {
                if (!kassaCollectionNew.contains(kassaCollectionOldKassa)) {
                    kassaCollectionOldKassa.setIdMember(null);
                    kassaCollectionOldKassa = em.merge(kassaCollectionOldKassa);
                }
            }
            for (Kassa kassaCollectionNewKassa : kassaCollectionNew) {
                if (!kassaCollectionOld.contains(kassaCollectionNewKassa)) {
                    Member oldIdMemberOfKassaCollectionNewKassa = kassaCollectionNewKassa.getIdMember();
                    kassaCollectionNewKassa.setIdMember(member1);
                    kassaCollectionNewKassa = em.merge(kassaCollectionNewKassa);
                    if (oldIdMemberOfKassaCollectionNewKassa != null && !oldIdMemberOfKassaCollectionNewKassa.equals(member1)) {
                        oldIdMemberOfKassaCollectionNewKassa.getKassaCollection().remove(kassaCollectionNewKassa);
                        oldIdMemberOfKassaCollectionNewKassa = em.merge(oldIdMemberOfKassaCollectionNewKassa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = member1.getIdMember();
                if (findMember1(id) == null) {
                    throw new NonexistentEntityException("The member1 with id " + id + " no longer exists.");
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
            Member member1;
            try {
                member1 = em.getReference(Member.class, id);
                member1.getIdMember();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The member1 with id " + id + " no longer exists.", enfe);
            }
            Collection<Kassa> kassaCollection = member1.getKassaCollection();
            for (Kassa kassaCollectionKassa : kassaCollection) {
                kassaCollectionKassa.setIdMember(null);
                kassaCollectionKassa = em.merge(kassaCollectionKassa);
            }
            em.remove(member1);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Member> findMember1Entities() {
        return findMember1Entities(true, -1, -1);
    }

    public List<Member> findMember1Entities(int maxResults, int firstResult) {
        return findMember1Entities(false, maxResults, firstResult);
    }

    private List<Member> findMember1Entities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Member.class));
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

    public Member findMember1(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Member.class, id);
        } finally {
            em.close();
        }
    }

    public int getMember1Count() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Member> rt = cq.from(Member.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
