package my.flick.rd.hw3.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import my.flick.rd.hw3.controller.model.ProductModel;
import my.flick.rd.hw3.dto.ProductDto;
import my.flick.rd.hw3.dto.validationgroups.OnCreate;
import my.flick.rd.hw3.dto.validationgroups.OnUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Product management api")
@RequestMapping("/api/v1/products")
public interface ProductApi {
    @ApiOperation("get all")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductModel> getAllProducts();

    @ApiOperation("get by id")
    @ApiImplicitParam(name = "id", type = "path", required = true, paramType = "long")
    @GetMapping("/{id}")
    public ProductModel getProduct(@PathVariable("id") long id);

    @ApiOperation("create new product")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductModel addProduct(@Validated(OnCreate.class) @RequestBody ProductDto product);

    @ApiImplicitParam(name = "id", type = "path", required = true, paramType = "long")
    @ApiOperation("update existing product")
    @PutMapping("/{id}")
    public ProductModel updateProduct(@PathVariable("id") long id, @Validated(OnUpdate.class) @RequestBody ProductDto productDto);

    @ApiImplicitParam(name = "id", type = "path", required = true, paramType = "long")
    @ApiOperation("delete existing product")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") long id);
}
