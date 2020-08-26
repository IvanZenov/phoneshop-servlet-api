package com.es.phoneshop.model.product;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.enums.SortOrder;
import com.es.phoneshop.service.impl.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @Mock
    private Product product4;

    private final ArrayListProductDao arrayListProductDao = ArrayListProductDao.getInstance();
    private final ProductService productService = ProductService.getInstance();

    private List<Product> products;

    @Before
    public void setup() {
        products = new ArrayList<>(Arrays.asList(product1, product3,product2,product4));
        arrayListProductDao.setProducts(products);

        when(product1.getId()).thenReturn(1L);
        when(product1.getPrice()).thenReturn(new BigDecimal(100));
        when(product1.getStock()).thenReturn(5);
        when(product1.getDescription()).thenReturn("Samsung Galaxy S");

        when(product2.getId()).thenReturn(2L);
        when(product2.getPrice()).thenReturn(new BigDecimal(110));
        when(product2.getStock()).thenReturn(4);
        when(product2.getDescription()).thenReturn("Apple iPhone 6");

        when(product3.getId()).thenReturn(3L);
        when(product3.getPrice()).thenReturn(new BigDecimal(300));
        when(product3.getStock()).thenReturn(3);
        when(product3.getDescription()).thenReturn("Samsung Galaxy S III");

        when(product4.getId()).thenReturn(2L);
        when(product4.getPrice()).thenReturn(new BigDecimal(110));
        when(product4.getStock()).thenReturn(4);
        when(product4.getDescription()).thenReturn("Apple iPhone");
    }


    @Test
    public void testFindProductsNoResults() {
        assertFalse(arrayListProductDao.findProducts().isEmpty());
    }

    @Test
    public void testGetProduct() {
        assertEquals(arrayListProductDao.getById(1L).getId(), product1.getId());
    }

    @Test
    public void testSaveProduct() {
        arrayListProductDao.save(product4);
        assertTrue(products.contains(product4));
    }

    @Test
    public void testDeleteProduct() {
        arrayListProductDao.delete(3L);
        assertFalse(arrayListProductDao.findProducts().contains(product3));
    }

    @Test
    public void testFindProductsWithQuery(){
        List<Product> findList = productService.findProducts("Samsung");
        assertEquals(findList,Arrays.asList(product1,product3));
    }

    @Test
    public void testFindProductsWithSort() {
        List<Product> findList = productService.sortProductsWithField(productService.findProducts(""), "PRICE", SortOrder.ASC);
        assertEquals(findList, Arrays.asList(product1,product2,product4,product3));
    }

    @Test
    public void testFindProductsWithQueryAndSort() {
        List<Product> listWithQuery = productService.findProducts("Apple");
        List<Product> findList = productService.sortProductsWithField(listWithQuery,"DESCRIPTION",SortOrder.DESC);
        assertEquals(findList, Arrays.asList(product2, product4));
    }


}
