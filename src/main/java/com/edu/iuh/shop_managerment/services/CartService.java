package com.edu.iuh.shop_managerment.services;

import com.edu.iuh.shop_managerment.dto.request.CartRequest;
import com.edu.iuh.shop_managerment.dto.respone.CartRespone;
import com.edu.iuh.shop_managerment.exception.AppException;
import com.edu.iuh.shop_managerment.exception.ErrorCode;
import com.edu.iuh.shop_managerment.mappers.CartMapper;
import com.edu.iuh.shop_managerment.models.Cart;
import com.edu.iuh.shop_managerment.models.Product;
import com.edu.iuh.shop_managerment.models.ProductDetail;
import com.edu.iuh.shop_managerment.models.User;
import com.edu.iuh.shop_managerment.repositories.CartRespository;
import com.edu.iuh.shop_managerment.repositories.ProductDetailRepository;
import com.edu.iuh.shop_managerment.repositories.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CartService {
    CartRespository cartRespository;
    UserService userService;
    CartMapper cartMapper;
    ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;

    public Cart getCartById(String id) {
        return cartRespository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUNDED));
    }

    public CartRespone addProductToCart(CartRequest cartRequest) {
        User user = userService.getCurrentUser();

        Cart cart = cartRespository.findTopByProductId(cartRequest.getProductId());
        if (cart != null) {
            cart.setProductColor(cartRequest.getProductColor());
            cart.setQuantity(cart.getQuantity() + cartRequest.getQuantity());
            cart.setTotalPrice(cart.getTotalPrice() + cartRequest.getTotalPrice());
            cart.setDate(new Date(System.currentTimeMillis()));
            cartRespository.save(cart);
            return cartMapper.cartToCartRespone(cart);
        }
        Cart cartCreate = Cart.builder()
                .productId(cartRequest.getProductId())
                .productDetail(cartRequest.getProductDetail())
                .quantity(cartRequest.getQuantity())
                .totalPrice(cartRequest.getTotalPrice())
                .productColor(cartRequest.getProductColor())
                .date(new Date(System.currentTimeMillis()))
                .userId(user.getId())
                .build();
        cartRespository.save(cartCreate);
        return cartMapper.cartToCartRespone(cartCreate);
    }

    public CartRespone updateCart(CartRequest cartRequest) {
        Cart cart = getCartById(cartRequest.getId());
        cart.setQuantity(cartRequest.getQuantity());
        cart.setDate(new Date(System.currentTimeMillis()));
        cart.setProductColor(cartRequest.getProductColor());
        cart.setTotalPrice(cartRequest.getTotalPrice());
        cartRespository.save(cart);
        return cartMapper.cartToCartRespone(cart);
    }

    public List<CartRespone> getCarts() {
        List<Cart> carts = cartRespository.findAll();
        return carts.stream().map(cart -> {
            Product product = productRepository.findById(cart.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUNDED));

            List<ProductDetail> productDetails = new ArrayList<>();

            ProductDetail productDetail = productDetailRepository.findById(cart.getProductDetail())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_DETAIL_NOT_FOUNDED));

            productDetails.add(productDetail);
            product.setProductDetail(productDetails);

            return CartRespone.builder()
                    .id(cart.getId())
                    .productId(cart.getProductId())
                    .product(product)
                    .quantity(cart.getQuantity())
                    .productColor(cart.getProductColor())
                    .userId(cart.getUserId())
                    .date(cart.getDate())
                    .build();
        }).collect(Collectors.toList());
    }

    public void deleteCart(String id) {
        Cart cart = getCartById(id);
        cartRespository.delete(cart);
    }

}
