package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import persistence.daoManage.DAOFactory;
import persistence.daoManage.DatabaseManager;
import persistence.daoManage.jdbcDao.FeedbackDaoJDBC;
import persistence.daoManage.jdbcDao.PrenotazioneDaoJDBC;
import persistence.persistentModel.FeedBack;
import persistence.persistentModel.Prenotazione;

public class InviaFeedback extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DAOFactory df = DatabaseManager.getInstance().getDaoFactory();
		PrenotazioneDaoJDBC pdao = (PrenotazioneDaoJDBC) df.getPrenotazioneDAO();
		FeedbackDaoJDBC fdao = (FeedbackDaoJDBC) df.getFeedBackDAO();
		Prenotazione p = (Prenotazione) pdao.findByPrimaryKey(req.getParameter("preno"));
		String pren = req.getParameter("preno");
		String comment = req.getParameter("commento");
		FeedBack feed = new FeedBack(p, comment);
		FeedBack tmp = (FeedBack) fdao.findByPrimaryKey(p.getID() + "");
		if (tmp != null) {
			req.getSession().setAttribute("message-error", "Hai gi� mandato il feedback di questa prenotazione");
			RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/dynamicPages/feedback.jsp");
			rd.forward(req, resp);
		} else {
			fdao.save(feed);
			System.out.println(pren);
			req.getSession().setAttribute("message-error", null);
//			RequestDispatcher rd = req.getRequestDispatcher("/home");
//			rd.forward(req, resp);
			System.out.println(req.getServletContext().getContextPath());
			resp.sendRedirect("/ServizioNavetta/home");
		}
	}
}
