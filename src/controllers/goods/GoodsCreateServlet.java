package controllers.goods;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Good;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class GoodsCreateServlet
 */
@WebServlet("/goods/create")
public class GoodsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodsCreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        em.getTransaction().begin();

        Good g = new Good();

        g.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("rid")));
        g.setReport((Report)r);

        em.persist(g);
        em.getTransaction().commit();
        em.close();

        response.sendRedirect(request.getContextPath() + "/reports/index");

    }

}
