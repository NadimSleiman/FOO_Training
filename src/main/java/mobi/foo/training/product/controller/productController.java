package mobi.foo.training.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import mobi.foo.training.FooResponse;

@RestController
public class productController {
    @PostMapping("/Api/v1/print")
    public ResponseEntity<FooResponse> printHello()
    {
        FooResponse response = (FooResponse.builder().data("success")).message("Hello World").status(true).build();
       return new ResponseEntity<>(response,HttpStatus.OK);
//        return ResponseEntity.ok(response);
    }
}
