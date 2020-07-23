package com.es.phoneshop.web;

import com.es.phoneshop.model.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.web.servlet.ProductDetailsPageServlet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ProductDetailsPageServletTest.class)
public class ProductDetailsPageServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private Product product;
    @Mock
    private ArrayListProductDao arrayListProductDao;

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();


    @Before
    public void setup() {
        Whitebox.setInternalState(ArrayListProductDao.class, "INSTANCE", arrayListProductDao);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn("/1");
        when(arrayListProductDao.getProduct(1L)).thenReturn(product);
        when(request.getRequestDispatcher("/WEB-INF/pages/product.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute("product", product);
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoGetProductFound() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/products/2");
        servlet.doGet(request, response);

        verify(request).setAttribute("product", product);
        verify(request).getRequestDispatcher("/WEB-INF/pages/product.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}
