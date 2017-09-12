package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import dao.IDAOCompany;
import dao.IDAOComputer;
import controller.CLICommand;
import model.Company;
import model.Computer;

/**
 * Servlet implementation class ConsoleView
 */
@WebServlet("/ConsoleView")
public class ConsoleView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/console.jsp";
	private static final String DAO_FACTORY = "daofactory";
	private static final String FIELD_COMMAND = "command";

	private IDAOCompany daoCompany;
	private IDAOComputer daoComputer;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO */
		this.daoCompany = ((DAOFactory) getServletContext().getAttribute(DAO_FACTORY)).getCompanyDao();
		this.daoComputer = ((DAOFactory) getServletContext().getAttribute(DAO_FACTORY)).getComputerDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Computer> cptList = new ArrayList<Computer>();
		List<Company> cpnList = new ArrayList<Company>();
		cptList.add(daoComputer.getFromId(5));
		cpnList.add(daoCompany.getFromId(5));
		
		request.setAttribute("computers", cptList);
		request.setAttribute("companies", cpnList);
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String a = request.getParameter(FIELD_COMMAND);
		boolean parseStatus = CLICommand.parse(a);
		request.setAttribute("parse_status", parseStatus);

		doGet(request, response);
	}

}
