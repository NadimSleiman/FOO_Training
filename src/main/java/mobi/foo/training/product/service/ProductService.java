package mobi.foo.training.product.service;


import lombok.RequiredArgsConstructor;
import mobi.foo.training.FooResponse;
import mobi.foo.training.product.dto.ProductDto;
import mobi.foo.training.product.entity.Product;
import mobi.foo.training.product.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
@EnableAsync
public class ProductService {
    private final ProductRepository productRepo;


    @Cacheable("CachedProducts")
    public List<ProductDto> getAllProducts()
    {
        List<ProductDto> myproducts = new ArrayList<>();
        List<Product> products = productRepo.findAll();
        for (Product p  : products) {
            ProductDto pDto = new ProductDto(p.getId(), p.getProductName());
            myproducts.add(pDto);

        }
        System.out.println("Getting Data from Database Version 1");
        return myproducts;

    }

    @Cacheable("CachedProducts")
    public List<ProductDto> getAllProductsV2()
    {
        List<ProductDto> myproducts = new ArrayList<>();
        List<Product> products = productRepo.findAll();
        for (Product p  : products) {
            ProductDto pDto = new ProductDto(p.getId(), p.getProductName());
            myproducts.add(pDto);

        }
        System.out.println("Getting Data from Database Version 2");
        return myproducts;

    }

    @Async
    public CompletableFuture<String> performAsyncTask()
    {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture("Performing Async Task");
    }


    @Cacheable("CachedProducts")
    public ProductDto getProduct(Long id)
    {
        Optional<Product> p =  productRepo.findById(id);
        ProductDto product = new ProductDto(p.get().getId(),p.get().getProductName());
        System.out.println("Getting product of id:" + id + "from Database");
        return  product;
    }

    @CacheEvict(value = "CachedProducts" , allEntries = true)
    public void saveProduct(Product P)
    {
        productRepo.save(P);
    }

    @CacheEvict(value = "CachedProducts" , allEntries = true)
    public void deleteProduct(Long id)
    {

        productRepo.deleteById(id);
    }



}
