package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee e = (Employee)request.getSession().getAttribute("login_employee");
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        Good g = null;

        try{
        g = em.createNamedQuery("checkGoodDone",Good.class)
                    .setParameter("e",e)
                    .setParameter("r",r)
                    .getSingleResult();
        } catch(NoResultException ex) {}


        long goods_count = (long)em.createNamedQuery("getGoodsCount",Long.class)
                                      .setParameter("r",r)
                                      .getSingleResult();

        em.close();

        request.setAttribute("report", r);
        request.setAttribute("good", g);
        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("goods_count", goods_count);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}