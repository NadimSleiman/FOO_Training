package mobi.foo.training.product.apiClient;

import lombok.RequiredArgsConstructor;
import mobi.foo.training.FooResponse;

import mobi.foo.training.product.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
public class ProductApiService {
    private final ProductApiClient productApiClient;


    public ResponseEntity<FooResponse> getAllProducts(String ApiVersion) {
        return productApiClient.getProducts(ApiVersion);
    }

    public ResponseEntity<FooResponse> getProductById(long id) {
        return productApiClient.getProductByID(id);

    }

    public ResponseEntity<FooResponse> createProduct(Product product) {
        return productApiClient.createProduct(product);

    }


    public ResponseEntity<FooResponse> deleteProduct(Long id) {
        return productApiClient.deleteProduct(id);

    }



}
