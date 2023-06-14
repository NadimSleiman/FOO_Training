package mobi.foo.training.product.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mobi.foo.training.FooResponse;
import mobi.foo.training.product.apiClient.ProductApiService;
import mobi.foo.training.product.dto.ProductDto;
import mobi.foo.training.product.entity.Product;
import mobi.foo.training.product.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RestController
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "API endpoints for products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService myproductService;

    private final ProductApiService clientProductService;

    @GetMapping("/products")
    @Operation(summary = "Get all Products")
    public ResponseEntity<FooResponse> getAllProducts(@RequestHeader("ApiVersion") String apiVersion){
        if (apiVersion.isEmpty()) {
            logger.error("Invalid API version provided");
            FooResponse response = FooResponse.builder().message("Invalid API Version").status(false).build();
            return new ResponseEntity<FooResponse>(response, HttpStatus.OK);
        }

        List<ProductDto> products;
        if(apiVersion.equals("V1"))
        {
            products = myproductService.getAllProducts();
        }
        else
        {
            products = myproductService.getAllProductsV2();
        }
        logger.info("Getting Products");

        FooResponse response = FooResponse.builder().data(products).message("Products Showing Successfully").status(true).build();
        System.out.println(response.getData());
        return new ResponseEntity<FooResponse>(response, HttpStatus.OK);
    }


    @GetMapping("products/{id}")
    @Operation(summary ="Get Product by Id")
    public ResponseEntity<FooResponse> getProductID(@PathVariable long id)
    {
        logger.info("Getting Product of id:" + id);
        ProductDto product = myproductService.getProduct(id);
        FooResponse response = FooResponse.builder().data(product).message("Showing a Product").status(true).build();
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("test-async")
    public ResponseEntity<FooResponse> testingAsync()
    {
        CompletableFuture<String> msg = myproductService.performAsyncTask();
        System.out.println("Hello");
        String s;
        try {
            s = msg.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        FooResponse response = FooResponse.builder().status(true).message(""+s).build();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("products/Create")
    @Operation(summary ="Creating a Product")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Product Created Successfully"),
            @ApiResponse(responseCode = "404", description = "Product Invalid")

    })
    public ResponseEntity<FooResponse> AddProduct(@RequestBody @Valid Product product)
    {
        logger.info("You Created a Product");
        myproductService.saveProduct(product);
        FooResponse response = FooResponse.builder().data(product).message("Product Created Successfully").status(true).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("products/Delete/{id}")
    @Operation(summary ="Deleting Product By Id")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Product Deleted Successfully"),
            @ApiResponse(responseCode = "404", description = "Product Not Found")

    })
    public ResponseEntity<FooResponse> deleteProduct(@PathVariable long id)
    {
        logger.warn("Warning, you Deleted Product of id:" + id);
        ProductDto product = myproductService.getProduct(id);
        myproductService.deleteProduct(id);
        FooResponse response = FooResponse.builder().data(product).message("Product id: " + id + " deleted Successfully").status(true).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/products-client")
    public ResponseEntity<FooResponse> getProducts(@RequestHeader("ApiVersion") String apiVersion)
    {
        if (apiVersion.isEmpty()) {
            FooResponse response = FooResponse.builder().message("Invalid API Version").status(false).build();
            return new ResponseEntity<FooResponse>(response, HttpStatus.OK);
        }
        System.out.println("api client");
        return clientProductService.getAllProducts(apiVersion);
    }

    @GetMapping("products-client/{id}")
    public ResponseEntity<FooResponse> getProductByID(@PathVariable long id)
    {
        return clientProductService.getProductById(id);
    }

    @PostMapping("products-client/Create")
    public ResponseEntity<FooResponse> AddProductClient(@RequestBody @Valid Product product)
    {
        return clientProductService.createProduct(product);
    }

    @DeleteMapping("products-client/Delete/{id}")
    public ResponseEntity<FooResponse> deleteProductClient(@PathVariable long id)
    {
        return clientProductService.deleteProduct(id);
    }

}
