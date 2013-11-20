package sesem2013.services;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import sesem2013.entities.Kunde;
import static sesem2013.util.EntityManagerFactoryHelper.getFactory;

public class KundenServiceImpl implements KundenService {
	
	
	/* (non-Javadoc)
	 * @see sesem2013.services.KundenService#create(sesem2013.entities.Kunde)
	 */
	@Override
	public void create(Kunde kunde){		
		EntityManager em = getFactory().createEntityManager();
        em.getTransaction().begin();      
        
        em.persist(kunde);
        
        em.getTransaction().commit();
	}
	
	/* (non-Javadoc)
	 * @see sesem2013.services.KundenService#update(sesem2013.entities.Kunde)
	 */
	@Override
	public void update (Kunde kunde){		
		EntityManager em = getFactory().createEntityManager();
        em.getTransaction().begin();      
        
        em.merge(kunde);
        
        em.getTransaction().commit();
	}
	
	/* (non-Javadoc)
	 * @see sesem2013.services.KundenService#delete(sesem2013.entities.Kunde)
	 */
	@Override
	public void delete (Kunde kunde){		
		EntityManager em = getFactory().createEntityManager();
        em.getTransaction().begin();      
        
        
        em.remove(em.merge(kunde));
        
        em.getTransaction().commit();
	}
	
	/* (non-Javadoc)
	 * @see sesem2013.services.KundenService#find(sesem2013.entities.Kunde)
	 */
	@Override
	public List<Kunde> find(Kunde kunde){
		EntityManager em = getFactory().createEntityManager();
        
		Session session = (Session) em.getDelegate();
		Criteria criteria = session.createCriteria(Kunde.class);
		
		if(kunde.getName()!=null && !kunde.getName().equals("")){
			criteria.add(Restrictions.ilike("name", kunde.getName(), MatchMode.ANYWHERE));
		}
		
		@SuppressWarnings("unchecked")
		List<Kunde> list = (List<Kunde>) criteria.list();
		return 	list;
	}
	
	

}
