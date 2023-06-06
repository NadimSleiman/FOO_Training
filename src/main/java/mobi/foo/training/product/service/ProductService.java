package mobi.foo.training.product.service;


import lombok.RequiredArgsConstructor;
import mobi.foo.training.product.dto.ProductDto;
import mobi.foo.training.product.entity.Product;
import mobi.foo.training.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepo;

    public List<ProductDto> getAllProducts()
    {
        List<ProductDto> myproducts = new ArrayList<>();
        List<Product> products = productRepo.findAll();
        for (Product p  : products)
        {
            ProductDto pDto = new ProductDto(p.getId(),p.getProductName());
            myproducts.add(pDto);

        }
        return myproducts;

    }



    public ProductDto getProduct(Long id)
    {
        Optional<Product> p =  productRepo.findById(id);
        ProductDto product = new ProductDto(p.get().getId(),p.get().getProductName());
        return  product;
    }

    public void saveProduct(Product P)
    {
        productRepo.save(P);
    }

    public void deleteProduct(Long id)
    {

        productRepo.deleteById(id);
    }



}
