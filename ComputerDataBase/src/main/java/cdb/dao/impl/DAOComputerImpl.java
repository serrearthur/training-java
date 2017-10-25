package cdb.dao.impl;

//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.query.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;

//import cdb.dao.DAOComputer;
//import cdb.dao.exceptions.DAOException;
import cdb.model.Computer;

/**
 * Class maping the request made to the database and the {@link Computer}.
 * @author aserre
 */
//@Repository
public class DAOComputerImpl  {
//    private SessionFactory sessionFactory;
//
//    /**
//     * Constructor.
//     * @param sessionFactory {@link SessionFactory} used for the DAO
//     */
//    @Autowired
//    private DAOComputerImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    public void create(Computer computer) {
//        try {
//            Session session = this.sessionFactory.getCurrentSession();
//            session.save(computer);
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            throw new DAOException(e);
//        }
//    }
//
//    @Override
//    public void update(Computer computer) {
//        try {
//            Session session = this.sessionFactory.getCurrentSession();
//            session.update(computer);
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            throw new DAOException(e);
//        }
//    }
//
//    private static final String REQUEST_DELETE = "DELETE FROM Computer c WHERE c.id IN (:idList)";
//    @Override
//    public int delete(List<String> idList) {
//        try {
//            Session session = this.sessionFactory.getCurrentSession();
//            Query<Computer> query = session.createQuery(REQUEST_DELETE, Computer.class);
//            query.setParameter(":idList", idList);
//            return query.executeUpdate();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            throw new DAOException(e);
//        }
//    }
//
//    private static final String REQUEST_DELETE_COMPANYID = "DELETE FROM Computer c WHERE c.companyId = :companyId";
//    @Override
//    public int deleteCompanyId(Integer companyId) {
//        try {
//            Session session = this.sessionFactory.getCurrentSession();
//            Query<Computer> query = session.createQuery(REQUEST_DELETE_COMPANYID, Computer.class);
//            query.setParameter(":companyId", companyId);
//            return query.executeUpdate();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            throw new DAOException(e);
//        }
//    }
//
//    @Override
//    public Computer getFromId(Integer id) {
//        try {
//            Session session = sessionFactory.getCurrentSession();
//            Computer retrievedPerson = (Computer) session.get(Computer.class, id);
//            return retrievedPerson;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            throw new DAOException(e);
//        }
//    }
//
//    private static final String REQUEST_SELECT_COMPANYID = "FROM Computer WHERE companyId = :companyId";
//    @Override
//    public List<Computer> getFromCompanyId(Integer companyId) {
//        try {
//            Session session = sessionFactory.getCurrentSession();
//            Query<Computer> query = session.createQuery(REQUEST_SELECT_COMPANYID, Computer.class);
//            query.setParameter(":companyId", companyId);
//            return query.getResultList();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            throw new DAOException(e);
//        }
//    }
//
//    private static final String REQUEST_SELECT_JOIN = "FROM Computer cpt LEFT JOIN Company cpn ON cpt.company_id = cpn.id WHERE cpt.name LIKE %:name% OR cpn.name LIKE %:name%";
//    private static final String REQUEST_SELECT_JOIN_ORDER = REQUEST_SELECT_JOIN + "ORDER BY :col :order";
//    private static final String REQUEST_SELECT_COUNT = "SELECT COUNT(*)" + REQUEST_SELECT_JOIN;
//    @Override
//    public List<Computer> getFromName(Integer start, Integer limit, AtomicInteger count, String name, String col, String order) {
//        try {
//            Session session = this.sessionFactory.getCurrentSession();
//            Query<Integer> queryCount = session.createQuery(REQUEST_SELECT_COUNT, Integer.class);
//            queryCount.setParameter(":name", name);
//            count.set(queryCount.uniqueResult());
//
//            Query<Computer> queryComputer = session.createQuery(REQUEST_SELECT_JOIN_ORDER, Computer.class);
//            queryComputer.setParameter(":name", name).setParameter(":col", col).setParameter(":order", order);
//            queryComputer.setFirstResult(start);
//            queryComputer.setMaxResults(limit);
//            return queryComputer.getResultList();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            throw new DAOException(e);
//        }
//    }
}