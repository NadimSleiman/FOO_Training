package mobi.foo.training.product.apiClient;


import lombok.RequiredArgsConstructor;
import mobi.foo.training.FooResponse;
import mobi.foo.training.product.entity.Product;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class ProductApiClient {

    private static final String BASE_URL = "http://localhost:8080/products";

    private final  RestTemplate restTemplate;


    public ResponseEntity<FooResponse> getProducts(String apiVersion) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("ApiVersion", apiVersion);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<FooResponse> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, entity, FooResponse.class);

        FooResponse fooResponseBody = response.getBody();
        return new ResponseEntity<>(fooResponseBody,HttpStatus.OK);
    }


    public ResponseEntity<FooResponse> getProductByID(long id)
    {
        String url = BASE_URL + "/" + id;
        FooResponse fooresponse = restTemplate.getForObject(url,FooResponse.class);
        return new ResponseEntity<>(fooresponse,HttpStatus.OK);
    }


    public ResponseEntity<FooResponse> createProduct(Product product) {
        String url = BASE_URL + "/Create";
        FooResponse fooresponse = restTemplate.postForObject(url,product,FooResponse.class);
        return new ResponseEntity<>(fooresponse,HttpStatus.OK);
    }


    public ResponseEntity<FooResponse> deleteProduct(long id) {

        String url = BASE_URL + "/Delete/" + id;
        ResponseEntity<FooResponse> entity = getProductByID(id);
        restTemplate.delete(url);
        FooResponse fooResponse = FooResponse.builder().data(entity.getBody().getData()).status(true).message("Product Deleted Successfully").build();
        return new ResponseEntity<>(fooResponse,HttpStatus.OK);
    }

}
