package mobi.foo.training.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mobi.foo.training.FooResponse;
import mobi.foo.training.product.dto.ProductDto;
import mobi.foo.training.product.entity.Product;
import mobi.foo.training.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class ProductController {

    private final ProductService myproductService;

    @GetMapping("products")
    public ResponseEntity<FooResponse> getAllProducts(){
        List<ProductDto> products;
        products = myproductService.getAllProducts();
        FooResponse response = FooResponse.builder().data(products).message("Products Showing Successfully").status(true).build();
        return new ResponseEntity<FooResponse>(response, HttpStatus.OK);
    }

    @GetMapping("products/{id}")
    public ResponseEntity<FooResponse> getProductID(@PathVariable long id)
    {
        ProductDto product = myproductService.getProduct(id);
        FooResponse response = FooResponse.builder().data(product).message("Showing a Product").status(true).build();
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("product/Create")
    public ResponseEntity<FooResponse> AddProduct(@RequestBody @Valid Product product)
    {
        myproductService.saveProduct(product);
        FooResponse response = FooResponse.builder().data(product).message("Created Successfully").status(true).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("product/Delete/{id}")
    public ResponseEntity<FooResponse> deleteProduct(@PathVariable long id)
    {
        ProductDto product = myproductService.getProduct(id);
        myproductService.deleteProduct(id);
        FooResponse response = FooResponse.builder().data(product).message("Product id: " + id + " deleted Successfully").status(true).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }




}
