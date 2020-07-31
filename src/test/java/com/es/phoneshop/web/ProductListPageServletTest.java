package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.service.impl.ProductService;
import com.es.phoneshop.model.service.impl.RecentlyViewProductServiceImpl;
import com.es.phoneshop.web.servlet.ProductListPageServlet;
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
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ProductListPageServletTest.class)
public class ProductListPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private RecentlyViewProductServiceImpl viewProductService;
    @Mock
    private Product product;


    private ProductListPageServlet servlet = new ProductListPageServlet();

    @Before
    public void setup(){
        Whitebox.setInternalState(RecentlyViewProductServiceImpl.class, "INSTANCE", viewProductService);
        when(viewProductService.getRecentlyViewProduct(request)).thenReturn(Arrays.asList(product));
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute(eq("products"),any());
    }

}