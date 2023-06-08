package mobi.foo.training.product.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "API endpoints for products")
public class ProductController {

    private final ProductService myproductService;

    @GetMapping("products")
    @Operation(summary = "Get all Products")
    public ResponseEntity<FooResponse> getAllProducts(@RequestHeader("ApiVersion") String apiVersion){
        List<ProductDto> products;
        if(apiVersion.equals("V1"))
        {
            products = myproductService.getAllProducts();
        }
        else
        {
            products = myproductService.getAllProductsV2();
        }

        FooResponse response = FooResponse.builder().data(products).message("Products Showing Successfully").status(true).build();
        return new ResponseEntity<FooResponse>(response, HttpStatus.OK);
    }


    @GetMapping("products/{id}")
    @Operation(summary ="Get Product by Id")
    public ResponseEntity<FooResponse> getProductID(@PathVariable long id)
    {
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

    @PostMapping("product/Create")
    @Operation(summary ="Creating a Product")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Product Created Successfully"),
            @ApiResponse(responseCode = "404", description = "Product Invalid")

    })
    public ResponseEntity<FooResponse> AddProduct(@RequestBody @Valid Product product)
    {
        myproductService.saveProduct(product);
        FooResponse response = FooResponse.builder().data(product).message("Created Successfully").status(true).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("product/Delete/{id}")
    @Operation(summary ="Deleting Product By Id")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Product Deleted Successfully"),
            @ApiResponse(responseCode = "404", description = "Product Not Found")

    })
    public ResponseEntity<FooResponse> deleteProduct(@PathVariable long id)
    {
        ProductDto product = myproductService.getProduct(id);
        myproductService.deleteProduct(id);
        FooResponse response = FooResponse.builder().data(product).message("Product id: " + id + " deleted Successfully").status(true).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
