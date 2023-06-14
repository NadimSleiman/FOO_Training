package mobi.foo.training.product.service;

import mobi.foo.training.product.dto.ProductDto;
import mobi.foo.training.product.entity.Product;
import mobi.foo.training.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    private  ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }
    @Test
    void getAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1L, "Book1"));
        productList.add(new Product(2L, "Book2"));
        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDto> res = productService.getAllProducts();

        assertEquals(2,res.size());
        verify(productRepository,times(1)).findAll();
    }

    @Test
    void getAllProductsV2() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1L, "Book1"));
        productList.add(new Product(2L, "Book2"));
        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDto> res = productService.getAllProductsV2();

        assertEquals(2,res.size());

        verify(productRepository,times(1)).findAll();
    }

    @Test
    void getProduct() {
        Long productId = 1L;
        Product product = new Product(productId, "Book1");
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductDto pDto = new ProductDto(product.getId(),product.getProductName());

        assertEquals(pDto, productService.getProduct(productId));

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void saveProduct() {
        Product product = new Product(1L, "Book1");

        productService.saveProduct(product);


        verify(productRepository, times(1)).save(product);
    }

    @Test
    void deleteProduct() {
        Long productId = 1L;

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }
}