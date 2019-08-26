/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DBTools.FavoriteProductDao;
import DBTools.LabelDao;
import DBTools.OrderTemplateDao;
import DBTools.UserDao;
import DBTools.ProductDao;
import Models.FavoriteProduct;
import Models.Label;
import Models.Product;
import Models.TemplateItem;
import Models.User;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Michail Sitmalidis
 */
@Controller
public class ProductControler {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private FavoriteProductDao favoriteProductsDao;
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private OrderTemplateDao orderTemplateDao;

    @RequestMapping(value = "/FavoritProductsList.htm", method = RequestMethod.GET)
    public String favoritePorductsList(ModelMap model, @RequestParam(value = "user_id") int user_id) {

        User customer = userDao.getUserByID(user_id);
        List<Product> productsList = productDao.displayProducts();
        List<FavoriteProduct> favoriteProductsList = favoriteProductsDao.getFavoriteProductsByCustomerID(user_id);

        model.addAttribute("productsList", productsList);
        model.addAttribute("favoriteProductsList", favoriteProductsList);
        model.addAttribute("customer", customer);

        return "FavoriteProductsPage";
    }

    @RequestMapping(value = "/AddNewFavoriteProduct.htm", method = RequestMethod.GET)
    public String AddNewFavoriteProduct(ModelMap model, @RequestParam(value = "user_id") int user_id) {
        List<Product> productList = productDao.displayProducts();
        FavoriteProduct favoriteProduct = new FavoriteProduct();
        User user = userDao.getUserByID(user_id);
        favoriteProduct.setUser(user);
        List<Label> labelList = labelDao.displayLabels();
        model.addAttribute("labelList", labelList);
        model.addAttribute("favoriteProduct", favoriteProduct);
        model.addAttribute("productList", productList);
        return "AddNewFavoriteProductPage";
    }

    @RequestMapping(value = "/AddNewFavoriteProductHandling.htm", method = RequestMethod.POST)
    public String register(FavoriteProduct favoriteProduct) {

        favoriteProductsDao.InsertFavoriteProduct(favoriteProduct);
        List<TemplateItem> templateItems = orderTemplateDao.getOrderTemplate(favoriteProduct.getUser().getUser_id());
        if (templateItems.size() > 0) {
            orderTemplateDao.addFavoriteProductToOrderTemplate(favoriteProduct);
        }
        return "redirect:/FavoritProductsList.htm?user_id=" + favoriteProduct.getUser().getUser_id();

    }

    @RequestMapping(value = "/deleteFavoriteProduct.htm", method = RequestMethod.GET)
    public String deleteFavoriteProduct(@RequestParam(value = "user_id") int user_id, @RequestParam(value = "product_id") int product_id) {
        favoriteProductsDao.deleteFavoriteProduct(user_id, product_id);
        orderTemplateDao.deleteTemplateItem(user_id, product_id);

        return "redirect:/FavoritProductsList.htm?user_id=" + user_id;

    }

    @RequestMapping(value = "/Products.htm", method = RequestMethod.GET)
    public String products(ModelMap model, HttpSession session) {
        ArrayList<Product> producs = productDao.displayProducts();

        model.addAttribute("products", producs);
        return "Products";

    }

    @RequestMapping(value = "/deleteProduct.htm", method = RequestMethod.GET)
    public String deleteProduct(HttpSession session, @RequestParam(value = "product_id") int product_id) {
        User sessionUser = (User) session.getAttribute("user");
        if (!sessionUser.getRole().equals("admin")) {
            return "index";
        }
        productDao.deleteProductByProductID(product_id);

        return "redirect:/Products.htm";

    }

    @RequestMapping(value = "/createNewProduct.htm", method = RequestMethod.GET)
    public String createNewProduct(HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (!sessionUser.getRole().equals("admin")) {
            return "index";
        }
        return "CreateNewProduct";
    }

    @RequestMapping(value = "/createNewProductHandling.htm", method = RequestMethod.POST)

    public String createNewProductHandling(HttpSession session, ModelMap model, Product product) {
        User sessionUser = (User) session.getAttribute("user");
        if (!sessionUser.getRole().equals("admin")) {
            return "index";
        }
        //here need some validation

        product.setProduct_code(0);
        product.setStatus("active");
        productDao.insertNewProduct(product);

        return "redirect:/Products.htm";
    }
}
