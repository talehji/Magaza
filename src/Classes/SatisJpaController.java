/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Entity.Musteri;
import Entity.Kassa;
import Entity.Satis;
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
public class SatisJpaController implements Serializable {

    public SatisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Satis satis) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kassa idKassa = satis.getIdKassa();
            if (idKassa != null) {
                idKassa = em.getReference(idKassa.getClass(), idKassa.getIdKassa());
                satis.setIdKassa(idKassa);
            }
            Mallar idMallar = satis.getIdMallar();
            if (idMallar != null) {
                idMallar = em.getReference(idMallar.getClass(), idMallar.getIdMallar());
                satis.setIdMallar(idMallar);
            }
            Musteri idMusteri = satis.getIdMusteri();
            if (idMusteri != null) {
                idMusteri = em.getReference(idMusteri.getClass(), idMusteri.getIdMusteri());
                satis.setIdMusteri(idMusteri);
            }
            em.persist(satis);
            if (idKassa != null) {
                idKassa.getSatisCollection().add(satis);
                idKassa = em.merge(idKassa);
            }
            if (idMallar != null) {
                idMallar.getSatisCollection().add(satis);
                idMallar = em.merge(idMallar);
            }
            if (idMusteri != null) {
                idMusteri.getSatisCollection().add(satis);
                idMusteri = em.merge(idMusteri);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Satis satis) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Satis persistentSatis = em.find(Satis.class, satis.getIdSatis());
            Kassa idKassaOld = persistentSatis.getIdKassa();
            Kassa idKassaNew = satis.getIdKassa();
            Mallar idMallarOld = persistentSatis.getIdMallar();
            Mallar idMallarNew = satis.getIdMallar();
            Musteri idMusteriOld = persistentSatis.getIdMusteri();
            Musteri idMusteriNew = satis.getIdMusteri();
            if (idKassaNew != null) {
                idKassaNew = em.getReference(idKassaNew.getClass(), idKassaNew.getIdKassa());
                satis.setIdKassa(idKassaNew);
            }
            if (idMallarNew != null) {
                idMallarNew = em.getReference(idMallarNew.getClass(), idMallarNew.getIdMallar());
                satis.setIdMallar(idMallarNew);
            }
            if (idMusteriNew != null) {
                idMusteriNew = em.getReference(idMusteriNew.getClass(), idMusteriNew.getIdMusteri());
                satis.setIdMusteri(idMusteriNew);
            }
            satis = em.merge(satis);
            if (idKassaOld != null && !idKassaOld.equals(idKassaNew)) {
                idKassaOld.getSatisCollection().remove(satis);
                idKassaOld = em.merge(idKassaOld);
            }
            if (idKassaNew != null && !idKassaNew.equals(idKassaOld)) {
                idKassaNew.getSatisCollection().add(satis);
                idKassaNew = em.merge(idKassaNew);
            }
            if (idMallarOld != null && !idMallarOld.equals(idMallarNew)) {
                idMallarOld.getSatisCollection().remove(satis);
                idMallarOld = em.merge(idMallarOld);
            }
            if (idMallarNew != null && !idMallarNew.equals(idMallarOld)) {
                idMallarNew.getSatisCollection().add(satis);
                idMallarNew = em.merge(idMallarNew);
            }
            if (idMusteriOld != null && !idMusteriOld.equals(idMusteriNew)) {
                idMusteriOld.getSatisCollection().remove(satis);
                idMusteriOld = em.merge(idMusteriOld);
            }
            if (idMusteriNew != null && !idMusteriNew.equals(idMusteriOld)) {
                idMusteriNew.getSatisCollection().add(satis);
                idMusteriNew = em.merge(idMusteriNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = satis.getIdSatis();
                if (findSatis(id) == null) {
                    throw new NonexistentEntityException("The satis with id " + id + " no longer exists.");
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
            Satis satis;
            try {
                satis = em.getReference(Satis.class, id);
                satis.getIdSatis();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The satis with id " + id + " no longer exists.", enfe);
            }
            Kassa idKassa = satis.getIdKassa();
            if (idKassa != null) {
                idKassa.getSatisCollection().remove(satis);
                idKassa = em.merge(idKassa);
            }
            Mallar idMallar = satis.getIdMallar();
            if (idMallar != null) {
                idMallar.getSatisCollection().remove(satis);
                idMallar = em.merge(idMallar);
            }
            Musteri idMusteri = satis.getIdMusteri();
            if (idMusteri != null) {
                idMusteri.getSatisCollection().remove(satis);
                idMusteri = em.merge(idMusteri);
            }
            em.remove(satis);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Satis> findSatisEntities() {
        return findSatisEntities(true, -1, -1);
    }

    public List<Satis> findSatisEntities(int maxResults, int firstResult) {
        return findSatisEntities(false, maxResults, firstResult);
    }

    private List<Satis> findSatisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Satis.class));
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

    public Satis findSatis(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Satis.class, id);
        } finally {
            em.close();
        }
    }

    public int getSatisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Satis> rt = cq.from(Satis.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
