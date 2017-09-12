package controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import dao.DAOFactory;

@WebListener
public class DAOWebListener implements ServletContextListener {
	private static final String DAO_FACTORY = "daofactory";
	private DAOFactory factory;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		/* Récupération du ServletContext lors du chargement de l'application */
		ServletContext servletContext = event.getServletContext();
		/* Instanciation de notre DAOFactory */
		this.factory = DAOFactory.getInstance();
		/* Enregistrement dans un attribut ayant pour portée toute l'application */
		servletContext.setAttribute(DAO_FACTORY, this.factory);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		/* Rien à réaliser lors de la fermeture de l'application... */
	}
}
