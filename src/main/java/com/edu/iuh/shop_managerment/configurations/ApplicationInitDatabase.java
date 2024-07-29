package com.edu.iuh.shop_managerment.configurations;

import com.edu.iuh.shop_managerment.enums.product.Color;
import com.edu.iuh.shop_managerment.models.Category;
import com.edu.iuh.shop_managerment.models.Product;
import com.edu.iuh.shop_managerment.models.ProductDetail;
import com.edu.iuh.shop_managerment.repositories.CategoryRepository;
import com.edu.iuh.shop_managerment.repositories.ProductDetailRepository;
import com.edu.iuh.shop_managerment.repositories.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitDatabase {
    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    ProductDetailRepository productDetailRepository;

    @Bean
    ApplicationRunner applicationRunnerDatabase() {
        return args -> {
            if (!categoryRepository.existsCategoryByCategoryName("Túi tote")) {
                insertCategories();
            }
            if (!productRepository.existsProductByProductName("SOL BAG")) {
                insertProductsFromCategory();
            }
            if (!productDetailRepository.existsProductDetailByProductColor(Color.Đen)) {
                insertProductDetail();
            }
        };
    }

    private void insertCategories() {
        List<Category> categories = Arrays.asList(
                new Category("Túi tote"),
                new Category("Túi mini"),
                new Category("Túi xách tay"),
                new Category("Túi đeo vai"),
                new Category("Túi đeo chéo"),
                new Category("Cardholder"),
                new Category("Ví"),
                new Category("Phụ kiện"),
                new Category("Balo mini")
        );

        categoryRepository.saveAll(categories);
    }

    private void insertProductsFromCategory() {
        Category categoryTuiTote = categoryRepository.findCategoryByCategoryName("Túi tote");
        Category categoryTuiMini = categoryRepository.findCategoryByCategoryName("Túi mini");
        Category categoryTuiXachTay = categoryRepository.findCategoryByCategoryName("Túi xách tay");
        Category categoryTuiDeoVai = categoryRepository.findCategoryByCategoryName("Túi đeo vai");

        List<Product> products = Arrays.asList(
                new Product("ONIST BAG", 650000, null, 200, categoryTuiTote.getId()),
                new Product("SOL BAG", 550000, null, 250, categoryTuiTote.getId()),
                new Product("MOONEE BAG", 699000, null, 100, categoryTuiTote.getId()),
                new Product("LOU BAG", 650000, null, 50, categoryTuiTote.getId()),
                new Product("ERIN BAG", 670000, null, 40, categoryTuiTote.getId()),
                new Product("MIRRI BAG", 380000, null, 27, categoryTuiMini.getId()),
                new Product("JIM BAG", 500000, null, 50, categoryTuiMini.getId()),
                new Product("AMBER BAG", 450000, null, 30, categoryTuiXachTay.getId()),
                new Product("IVY BAG", 450000, null, 120, categoryTuiDeoVai.getId()),
                new Product("CHARIS BAG", 430000, null, 30, categoryTuiDeoVai.getId()),
                new Product("GIGI BAG", 450000, null, 50, categoryTuiDeoVai.getId()),
                new Product("WAVY BAG", 550000, null, 60, categoryTuiDeoVai.getId()),
                new Product("AELIA BAG", 510000, null, 50, categoryTuiDeoVai.getId()),
                new Product("ZIA BAG", 485000, null, 100, categoryTuiDeoVai.getId()),
                new Product("SKYE BAG", 485000, null, 75, categoryTuiDeoVai.getId()),
                new Product("CELINA BAG", 620000, null, 65, categoryTuiDeoVai.getId()),
                new Product("ROXY BAG", 550000, null, 110, categoryTuiDeoVai.getId()),
                new Product("CHLOE BAG", 510000, null, 55, categoryTuiDeoVai.getId())


        );
        productRepository.saveAll(products);
    }

    private void insertProductDetail() {
        Product productSOLBAG = productRepository.findProductByProductName("SOL BAG");
        List<ProductDetail> productDetailsSOLBAG = Arrays.asList(
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240116_tw3dlfT8L0.jpeg", true, productSOLBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20220825_XuC5fZhldUbfdRT9EPoYeYmK.jpg", false, productSOLBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20220825_2fNMF9hYOztgsXjPeptWw05i.jpg", false, productSOLBAG.getId())
        );
        productDetailRepository.saveAll(productDetailsSOLBAG);

        Product productMOONEEBAG = productRepository.findProductByProductName("MOONEE BAG");
        List<ProductDetail> productDetailsMOONEEBAG = Arrays.asList(
                new ProductDetail(Color.Nâu, "https://pos.nvncdn.com/b22375-44395/ps/20240716_bQ5XjzXKzf.jpeg",true, productMOONEEBAG.getId()),
                new ProductDetail(Color.Nâu, "https://pos.nvncdn.com/b22375-44395/ps/20240716_Sfuh0vtG6W.jpeg", false, productMOONEEBAG.getId()),
                new ProductDetail(Color.Nâu, "https://pos.nvncdn.com/b22375-44395/ps/20240716_CntUd9XTJc.jpeg", false, productMOONEEBAG.getId()),
                new ProductDetail(Color.Nâu, "https://pos.nvncdn.com/b22375-44395/ps/20240716_1qaAPcQojd.jpeg", false, productMOONEEBAG.getId()),
                new ProductDetail(Color.Nâu, "https://pos.nvncdn.com/b22375-44395/ps/20240716_2k9cYS23GY.jpeg", false, productMOONEEBAG.getId())
        );
        productDetailRepository.saveAll(productDetailsMOONEEBAG);

        Product productONISTBAG = productRepository.findProductByProductName("ONIST BAG");
        List<ProductDetail> productDetailsONISTBAG = Arrays.asList(
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20240701_au9dllq8fO.jpeg",true, productONISTBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20240610_0WLujCES2d.jpeg",false, productONISTBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20240610_c9B1BncbwC.jpeg",false, productONISTBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20240610_1qS95twQBB.jpeg",false, productONISTBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240610_yAV3hXITkh.jpeg",false, productONISTBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240610_TYLRRcQPJr.jpeg",false, productONISTBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240610_8dJq2i8Y6k.jpeg",false, productONISTBAG.getId())
        );
        productDetailRepository.saveAll(productDetailsONISTBAG);

        Product productLOUBAG = productRepository.findProductByProductName("LOU BAG");
        List<ProductDetail> productDetailsLOUBAG = Arrays.asList(
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240510_fIueniiqf3.jpeg",true, productLOUBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240510_X9IztFzQ8z.jpeg",false, productLOUBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240510_b9Tp6R7qWZ.jpeg",false, productLOUBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240510_fQ5oucJDmQ.jpeg",false, productLOUBAG.getId()),
                new ProductDetail(Color.Nâu, "https://pos.nvncdn.com/b22375-44395/ps/20240510_ZYIaPTqGa1.jpeg",false, productLOUBAG.getId()),
                new ProductDetail(Color.Nâu, "https://pos.nvncdn.com/b22375-44395/ps/20240510_UyvFxTpf2h.jpeg",false, productLOUBAG.getId())
        );
        productDetailRepository.saveAll(productDetailsLOUBAG);

        Product productERINBAG = productRepository.findProductByProductName("ERIN BAG");
        List<ProductDetail> productDetailsERINBAG = Arrays.asList(
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20230808_dUdck9Wuoy.jpeg",true, productERINBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20230808_8xn1VGYMKY.jpeg",false, productERINBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20230808_iuy81ZdqXE.jpeg",false, productERINBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20230808_WZG5rBmItE.jpeg",false, productERINBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20230808_808rKimgfJ.jpeg",false, productERINBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20230808_1bN4WGEofY.jpeg",false, productERINBAG.getId())
        );
        productDetailRepository.saveAll(productDetailsERINBAG);

        Product productMIRRIBAG = productRepository.findProductByProductName("MIRRI BAG");
        List<ProductDetail> productDetailsMIRRIBAG = Arrays.asList(
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240510_P6e6liBhXe.jpeg",true, productMIRRIBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240510_qqXihUktcx.jpeg",false, productMIRRIBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240510_0ygytehDj6.jpeg",false, productMIRRIBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20240510_ChwR6315mF.jpeg",false, productMIRRIBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20240510_582z4rh909.jpeg",false, productMIRRIBAG.getId()),
                new ProductDetail(Color.Hồng, "https://pos.nvncdn.com/b22375-44395/ps/20240510_D45Owtce1c.jpeg",false, productMIRRIBAG.getId()),
                new ProductDetail(Color.Hồng, "https://pos.nvncdn.com/b22375-44395/ps/20240510_sff2vTmFkK.jpeg",false, productMIRRIBAG.getId())
        );
        productDetailRepository.saveAll(productDetailsMIRRIBAG);

        Product productJIMBAG = productRepository.findProductByProductName("JIM BAG");
        List<ProductDetail> productDetailsJIMBAG = Arrays.asList(
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20230814_3SMOstRPwe.jpeg",true, productJIMBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20230808_OzF91ShSAK.jpeg",false, productJIMBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20230808_SswtKLlE0J.jpeg",false, productJIMBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20230808_3kfSi65DF1.jpeg",false, productJIMBAG.getId())
        );
        productDetailRepository.saveAll(productDetailsJIMBAG);

        Product productAMBERBAG = productRepository.findProductByProductName("AMBER BAG");
        List<ProductDetail> productDetailsAMBERBAG = Arrays.asList(
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20230905_G7w2uldzIB.jpeg",true, productAMBERBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20230905_dt0BbhbJnl.jpeg",false, productAMBERBAG.getId())
        );
        productDetailRepository.saveAll(productDetailsAMBERBAG);

        Product productIVYBAG = productRepository.findProductByProductName("IVY BAG");
        List<ProductDetail> productDetailsIVYBAG = Arrays.asList(
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240122_fEdSYoOWUz.jpeg",true, productIVYBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240122_3RG2vY3Ejz.jpeg",false, productIVYBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240122_duiCZUJVmq.jpeg",false, productIVYBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240122_lFR0ZDq0QG.jpeg",false, productIVYBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240122_K2C8FuWHSD.jpeg",false, productIVYBAG.getId())
        );
        productDetailRepository.saveAll(productDetailsIVYBAG);

        Product productCHARISBAG = productRepository.findProductByProductName("CHARIS BAG");
        List<ProductDetail> productDetailsCHARISBAG = Arrays.asList(
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20231207_tHSzvhxTW2.jpeg",true, productCHARISBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20211231_lrv3BnRG2fOaqYClx1AsAa14.jpg",false, productCHARISBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20231207_CJUOuFErOL.jpeg",false, productCHARISBAG.getId())
        );
        productDetailRepository.saveAll(productDetailsCHARISBAG);

        Product productGIGIBAG = productRepository.findProductByProductName("GIGI BAG");
        List<ProductDetail> productDetailsGIGIBAG = Arrays.asList(
                new ProductDetail(Color.Nâu, "https://pos.nvncdn.com/b22375-44395/ps/20240510_quMt2H386c.jpeg",true, productGIGIBAG.getId()),
                new ProductDetail(Color.Milktea, "https://pos.nvncdn.com/b22375-44395/ps/20240510_1zVyybGYNg.jpeg",false, productGIGIBAG.getId()),
                new ProductDetail(Color.Silver, "https://pos.nvncdn.com/b22375-44395/ps/20240510_VslsHSb0IL.jpeg",false, productGIGIBAG.getId()),
                new ProductDetail(Color.Silver, "https://pos.nvncdn.com/b22375-44395/ps/20240510_Ielz5FM1hk.jpeg",false, productGIGIBAG.getId()),
                new ProductDetail(Color.Silver, "https://pos.nvncdn.com/b22375-44395/ps/20240510_uGaTUo5fLN.jpeg",false, productGIGIBAG.getId()),
                new ProductDetail(Color.Nâu, "https://pos.nvncdn.com/b22375-44395/ps/20240510_8R675IvTL4.jpeg",false, productGIGIBAG.getId()),
                new ProductDetail(Color.Nâu, "https://pos.nvncdn.com/b22375-44395/ps/20240510_GDFlkIvXCL.jpeg",false, productGIGIBAG.getId())
        );
        productDetailRepository.saveAll(productDetailsGIGIBAG);

        Product productWAVYBAG = productRepository.findProductByProductName("WAVY BAG");
        List<ProductDetail> productDetailsWAVYBAG = Arrays.asList(
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240403_04jZotLvnA.jpeg",true, productWAVYBAG.getId()),
                new ProductDetail(Color.Nâu, "https://pos.nvncdn.com/b22375-44395/ps/20240403_UGW49RnpCV.jpeg",false, productWAVYBAG.getId()),
                new ProductDetail(Color.Vàng, "https://pos.nvncdn.com/b22375-44395/ps/20240403_kqtmT2T0C2.jpeg",false, productWAVYBAG.getId()),
                new ProductDetail(Color.Milktea, "https://pos.nvncdn.com/b22375-44395/ps/20240404_Gs3rUlDa16.jpeg",false, productWAVYBAG.getId()),
                new ProductDetail(Color.Nâu, "https://pos.nvncdn.com/b22375-44395/ps/20240404_ayIs6fHazv.jpeg",false, productWAVYBAG.getId()),
                new ProductDetail(Color.Milktea, "https://pos.nvncdn.com/b22375-44395/ps/20240404_m9PjizjMqd.jpeg",false, productWAVYBAG.getId())
        );
        productDetailRepository.saveAll(productDetailsWAVYBAG);

        Product productAELIABAG = productRepository.findProductByProductName("AELIA BAG");
        List<ProductDetail> productDetailsAELIABAG = Arrays.asList(
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240403_ZbXC9tjgJW.jpeg",true, productAELIABAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20240403_8JXyWFO04A.jpeg",false, productAELIABAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20240417_clSa36gk7I.jpeg",false, productAELIABAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240417_yGCKkuu4Lj.png",false, productAELIABAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20240404_XwmnsGauZq.jpeg",false, productAELIABAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240404_xjudtkGDWJ.jpeg",false, productAELIABAG.getId())
        );
        productDetailRepository.saveAll(productDetailsAELIABAG);

        Product productZIABAG = productRepository.findProductByProductName("ZIA BAG");
        List<ProductDetail> productDetailsZIABAG = Arrays.asList(
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240403_bx4RxLywza.jpeg",true, productZIABAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20240404_nrpzrc9E8A.jpeg",false, productZIABAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20240404_jihbBhJ8zu.jpeg",false, productZIABAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240404_KarhkpvI2V.jpeg",false, productZIABAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20240404_YPMKlzh2wQ.jpeg",false, productZIABAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240417_0BMye2hR3n.png",false, productZIABAG.getId())
        );
        productDetailRepository.saveAll(productDetailsZIABAG);

        Product productSKYEBAG = productRepository.findProductByProductName("SKYE BAG");
        List<ProductDetail> productDetailsSKYEBAG = Arrays.asList(
                new ProductDetail(Color.Denim, "https://pos.nvncdn.com/b22375-44395/ps/20240403_lbV6T03KVa.jpeg",true, productSKYEBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240403_lbV6T03KVa.jpeg",false, productSKYEBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20240403_Uh5YdMSjvQ.jpeg",false, productSKYEBAG.getId()),
                new ProductDetail(Color.Denim, "https://pos.nvncdn.com/b22375-44395/ps/20240404_1dnU45rDjI.jpeg",false, productSKYEBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20240417_24q5B4cWea.png",false, productSKYEBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20240404_oBsLbaC5w2.jpeg",false, productSKYEBAG.getId())
        );
        productDetailRepository.saveAll(productDetailsSKYEBAG);

        Product productCELINABAG = productRepository.findProductByProductName("CELINA BAG");
        List<ProductDetail> productDetailsCELINABAG = Arrays.asList(
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20231121_8ft7eiuWkY.jpeg",true, productCELINABAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20231121_EVl5xB2gKX.jpeg",false, productCELINABAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20231121_S2zpTIoL6q.jpeg",false, productCELINABAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20231121_7XEh5wEwit.jpeg",false, productCELINABAG.getId())
        );
        productDetailRepository.saveAll(productDetailsCELINABAG);

        Product productROXYBAG = productRepository.findProductByProductName("ROXY BAG");
        List<ProductDetail> productDetailsROXYBAG = Arrays.asList(
                new ProductDetail(Color.Denim, "https://pos.nvncdn.com/b22375-44395/ps/20240629_EWbc8RUtnp.jpeg",true, productROXYBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20231207_ogv03yTozU.jpeg",false, productROXYBAG.getId()),
                new ProductDetail(Color.Milktea, "https://pos.nvncdn.com/b22375-44395/ps/20231207_7HOznusrBY.jpeg",false, productROXYBAG.getId()),
                new ProductDetail(Color.Denim, "https://pos.nvncdn.com/b22375-44395/ps/20240629_6uvZ7u1KvF.jpeg",false, productROXYBAG.getId()),
                new ProductDetail(Color.Denim, "https://pos.nvncdn.com/b22375-44395/ps/20240629_LolRbi76zW.jpeg",false, productROXYBAG.getId()),
                new ProductDetail(Color.Milktea, "https://pos.nvncdn.com/b22375-44395/ps/20240510_7FxFSOUfke.jpeg",false, productROXYBAG.getId())
        );
        productDetailRepository.saveAll(productDetailsROXYBAG);

        Product productCHLOEBAG = productRepository.findProductByProductName("CHLOE BAG");
        List<ProductDetail> productDetailsCHLOEBAG = Arrays.asList(
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20230425_enuy7fLfCo.jpeg",true, productCHLOEBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20230425_jTJZUXaFYe.jpeg",false, productCHLOEBAG.getId()),
                new ProductDetail(Color.Trắng, "https://pos.nvncdn.com/b22375-44395/ps/20230425_yvj8vxChNu.jpeg",false, productCHLOEBAG.getId()),
                new ProductDetail(Color.Đen, "https://pos.nvncdn.com/b22375-44395/ps/20230425_SQG5jiJqKp.jpeg",false, productCHLOEBAG.getId())
        );
        productDetailRepository.saveAll(productDetailsCHLOEBAG);

    }
}
