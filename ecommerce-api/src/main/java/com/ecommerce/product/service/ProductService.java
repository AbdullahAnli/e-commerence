package com.ecommerce.product.service;

import com.ecommerce.product.dto.CategoryDto;
import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.repository.CategoryRepository;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.shared.exception.BadRequestException;
import com.ecommerce.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    // Category methods
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new BadRequestException("Category with this name already exists");
        }
        
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        
        Category savedCategory = categoryRepository.save(category);
        return convertCategoryToDto(savedCategory);
    }
    
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findByIsActiveTrue().stream()
            .map(this::convertCategoryToDto)
            .collect(Collectors.toList());
    }
    
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return convertCategoryToDto(category);
    }
    
    // Product methods
    public ProductDto createProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDto.getCategoryId()));
        
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setImageUrl(productDto.getImageUrl());
        product.setCategory(category);
        
        Product savedProduct = productRepository.save(product);
        return convertProductToDto(savedProduct);
    }
    
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDto.getCategoryId()));
            product.setCategory(category);
        }
        
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setImageUrl(productDto.getImageUrl());
        
        Product updatedProduct = productRepository.save(product);
        return convertProductToDto(updatedProduct);
    }
    
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return convertProductToDto(product);
    }
    
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productRepository.findByIsActiveTrue(pageable)
            .map(this::convertProductToDto);
    }
    
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryIdAndIsActiveTrue(categoryId).stream()
            .map(this::convertProductToDto)
            .collect(Collectors.toList());
    }
    
    public Page<ProductDto> searchProducts(String keyword, Pageable pageable) {
        return productRepository.findByKeyword(keyword, pageable)
            .map(this::convertProductToDto);
    }
    
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.setIsActive(false);
        productRepository.save(product);
    }
    
    // Conversion methods
    private CategoryDto convertCategoryToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setIsActive(category.getIsActive());
        return dto;
    }
    
    private ProductDto convertProductToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setImageUrl(product.getImageUrl());
        dto.setIsActive(product.getIsActive());
        dto.setCategoryId(product.getCategory().getId());
        dto.setCategoryName(product.getCategory().getName());
        return dto;
    }
}
