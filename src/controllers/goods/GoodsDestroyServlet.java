package controllers.goods;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Good;
import utils.DBUtil;

/**
 * Servlet implementation class GoodsDestroyServlet
 */
@WebServlet("/goods/destroy")
public class GoodsDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodsDestroyServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        Good g = em.find(Good.class, Integer.parseInt(request.getParameter("gid")));
        em.getTransaction().begin();
        em.remove(g);
        em.getTransaction().commit();
        em.close();

        response.sendRedirect(request.getContextPath() + "/reports/index");

    }

}
