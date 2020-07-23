package com.es.phoneshop.model.service.impl;

import com.es.phoneshop.model.dao.ArrayListProductDao;
import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.service.RecentlyViewProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class RecentlyViewProductServiceImpl implements RecentlyViewProductService {
    private static volatile RecentlyViewProductServiceImpl INSTANCE;
    private static final String RECENTLY_VIEW_SESSION_ATTRIBUTE = RecentlyViewProductService.class.getName() + ".recentlyView";
    private ProductDao productDao =  ArrayListProductDao.getInstance();


    private RecentlyViewProductServiceImpl(){
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

    //TODO: Do not add the same product, Limit of 3
    @Override
    public void add(List<Product> recentlyViewedProducts, Long productId) {
        Product product = productDao.getProduct(productId);
        recentlyViewedProducts.add(product);
    }


}
