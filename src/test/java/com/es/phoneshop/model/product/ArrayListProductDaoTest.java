package com.es.phoneshop.model.product;

import org.junit.Test;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ArrayListProductDao arrayListProductDao = ArrayListProductDao.getInstance();
    private Currency usd = Currency.getInstance("USD");

    @Test
    public void testFindProductsNoResults() {
       // assertFalse(ArrayListProductDao.getInstance().findProducts().isEmpty());
    }


    @Test
    public void testCorrectProductSave(){
        Product product = new Product("test-save", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        arrayListProductDao.save(product);
        assertTrue(product.getId()>0);

        Product result = arrayListProductDao.getProduct(product.getId()).get();
        assertNotNull(result);
        assertEquals("test-save",result.getCode());
    }

    @Test
    public void testCorrectProductDelete(){
        Product product = new Product("test-delete", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
        arrayListProductDao.save(product);
        assertTrue(product.getId()>0);
        arrayListProductDao.delete(product.getId());
        boolean isPresent = arrayListProductDao.getProduct(product.getId()).isPresent();
        assertFalse(isPresent);
    }

    @Test
    public void lol(){
        String description = "Samsung Galaxy S III";
        String falseQuery = "Samsung Kek";
        String query = "Samsung";

        String[] s1 = description.split(" ");
        String[] s2 = query.split(" ");

        boolean b = Arrays.stream(s2).allMatch(word -> Arrays.stream(s1).anyMatch(desc -> desc.contains(word)));
        System.out.println(b);


    }
}
