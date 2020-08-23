package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.RecentlyViewProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class RecentlyViewProductServiceImpl implements RecentlyViewProductService {
    private static volatile RecentlyViewProductServiceImpl INSTANCE;
    private static final String RECENTLY_VIEW_SESSION_ATTRIBUTE = RecentlyViewProductService.class.getName() + ".recentlyView";
    private static final int LIMIT_OF_VIEW_PRODUCT = 3;
    private ProductDao productDao = ArrayListProductDao.getInstance();

    private RecentlyViewProductServiceImpl() {
    }

    public static RecentlyViewProductServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (RecentlyViewProductServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RecentlyViewProductServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public List<Product> getRecentlyViewProduct(HttpServletRequest request) {
        List<Product> recentlyView = (List<Product>) request.getSession().getAttribute(RECENTLY_VIEW_SESSION_ATTRIBUTE);
        if (recentlyView == null) {
            request.getSession().setAttribute(RECENTLY_VIEW_SESSION_ATTRIBUTE, recentlyView = new ArrayList<>());
        }
        return recentlyView;
    }

    @Override
    public void add(List<Product> recentlyViewedProducts, Long productId) {
        Product product = productDao.getById(productId);

        if (recentlyViewedProducts.contains(product)) {
            recentlyViewedProducts.remove(product);
        }
        if (recentlyViewedProducts.size() == LIMIT_OF_VIEW_PRODUCT) {
            recentlyViewedProducts.remove(LIMIT_OF_VIEW_PRODUCT - 1);
        }
        recentlyViewedProducts.add(0, product);
    }
}
