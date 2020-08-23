package com.es.phoneshop.web.listener;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.PriceHistory;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class DemoDataListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        boolean insertDemoData = Boolean.parseBoolean(servletContextEvent.getServletContext().getInitParameter("insertDemoData"));
        if (insertDemoData) {
            for (Product product : createProducts()) {
                ArrayListProductDao.getInstance().save(product);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    public static List<Product> createProducts() {
        List<Product> result = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        result.add(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg",
                addPriceHistory(new BigDecimal(100))));
        result.add(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg",
                addPriceHistory(new BigDecimal(200))));
        result.add(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg",
                addPriceHistory(new BigDecimal(300))));
        result.add(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg",
                addPriceHistory(new BigDecimal(200))));
        result.add(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg",
                addPriceHistory(new BigDecimal(1000))));
        result.add(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg",
                addPriceHistory(new BigDecimal(320))));
        result.add(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg",
                addPriceHistory(new BigDecimal(420))));
        result.add(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg",
                addPriceHistory(new BigDecimal(120))));
        result.add(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg",
                addPriceHistory(new BigDecimal(70))));
        result.add(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg",
                addPriceHistory(new BigDecimal(170))));
        result.add(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg",
                addPriceHistory(new BigDecimal(70))));
        result.add(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg",
                addPriceHistory(new BigDecimal(80))));
        result.add(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg",
                addPriceHistory(new BigDecimal(150))));
        return result;
    }

    private static List<PriceHistory> addPriceHistory(BigDecimal price) {
        List<PriceHistory> priceHistory = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        priceHistory.add(new PriceHistory(LocalDate.of(2020, 7, 2), price.add(new BigDecimal(30)), usd));
        priceHistory.add(new PriceHistory(LocalDate.of(2020, 7, 28), price.add(new BigDecimal(20)), usd));
        priceHistory.add(new PriceHistory(LocalDate.of(2020, 8, 20), price.add(new BigDecimal(10)), usd));
        return priceHistory;
    }
}
