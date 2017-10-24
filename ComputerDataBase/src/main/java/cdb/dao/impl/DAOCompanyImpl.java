package cdb.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cdb.dao.DAOCompany;
import cdb.model.Company;

/**
 * Class maping the request made to the database and the {@link Company}.
 * @author aserre
 */
@Repository
public class DAOCompanyImpl implements DAOCompany {

    private SessionFactory sessionFactory;

    /**
     * Constructor.
     * @param sessionFactory {@link SessionFactory} used for the DAO
     */
    @Autowired
    private DAOCompanyImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Company company) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(company);
    }

    @Override
    public void update(Company company) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(company);
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Company personToDelete = getFromId(id);
        session.delete(personToDelete);
    }

    @Override
    public List<Company> getFromName(String name) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Company> criteria = builder.createQuery(Company.class);
        Root<Company> root = criteria.from(Company.class);
        criteria.select(root).where(builder.equal(root.get("name"), name));
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public List<Company> getAll()  {
        Session session = sessionFactory.getCurrentSession();
        CriteriaQuery<Company> criteria = session.getCriteriaBuilder().createQuery(Company.class);
        criteria.from(Company.class);
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public Company getFromId(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Company retrievedPerson = (Company) session.get(Company.class, id);
        return retrievedPerson;
    }
}