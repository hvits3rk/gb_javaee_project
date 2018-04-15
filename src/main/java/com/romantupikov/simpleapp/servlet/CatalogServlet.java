package com.romantupikov.simpleapp.servlet;

import com.romantupikov.simpleapp.repository.ProductRepository;
import com.romantupikov.simpleapp.repository.ProductRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CatalogServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        productRepository = new ProductRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", productRepository.getAllProducts());
        req.getRequestDispatcher("WEB-INF/catalog.jsp").forward(req, resp);
    }
}
