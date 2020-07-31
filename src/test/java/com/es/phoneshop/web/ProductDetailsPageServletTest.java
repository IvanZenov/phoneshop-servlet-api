package com.es.phoneshop.web;

import com.es.phoneshop.model.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.service.impl.RecentlyViewProductServiceImpl;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ProductDetailsPageServletTest.class, RecentlyViewProductServiceImpl.class,ArrayListProductDao.class})
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
    @Mock
    private RecentlyViewProductServiceImpl viewProductService;
    @Mock
    private HttpSession session;

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();
    private List<Product> recentlyView = Arrays.asList(product);


    @Before
    public void setup() {
        Whitebox.setInternalState(ArrayListProductDao.class, "INSTANCE", arrayListProductDao);
        Whitebox.setInternalState(RecentlyViewProductServiceImpl.class, "INSTANCE", viewProductService);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn("/1");
        when(arrayListProductDao.getProduct(1L)).thenReturn(product);
        when(request.getRequestDispatcher("/WEB-INF/pages/product.jsp")).thenReturn(requestDispatcher);
        //Returns null
        when(viewProductService.getRecentlyViewProduct(request)).thenReturn(recentlyView);
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
