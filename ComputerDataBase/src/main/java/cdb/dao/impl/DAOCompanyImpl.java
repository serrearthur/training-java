package cdb.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cdb.dao.DAOCompany;
import cdb.dao.exceptions.DAOException;
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
        try {
            Session session = this.sessionFactory.getCurrentSession();
            session.save(company);
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Company company) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            session.update(company);
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Company companyToDelete = getFromId(id);
            session.delete(companyToDelete);
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    @Override
    public Company getFromId(Integer id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Company retrievedPerson = (Company) session.get(Company.class, id);
            return retrievedPerson;
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    private static final String REQUEST_SELECT_NAME = "FROM Company WHERE name = :name";
    @Override
    public List<Company> getFromName(String name) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            Query<Company> query = session.createQuery(REQUEST_SELECT_NAME, Company.class);
            query.setParameter(":name", name);
            return query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    private static final String REQUEST_SELECT_ALL = "FROM Company";
    @Override
    public List<Company> getAll()  {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            Query<Company> query = session.createQuery(REQUEST_SELECT_ALL, Company.class);
            return query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }
}