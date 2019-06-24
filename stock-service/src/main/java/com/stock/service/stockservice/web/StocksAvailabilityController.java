package com.stock.service.stockservice.web;

import com.stock.service.stockservice.data.exception.DuplicateEntityException;
import com.stock.service.stockservice.model.OrderDTO;
import com.stock.service.stockservice.model.ProductDTO;
import com.stock.service.stockservice.model.StocksDTO;
import com.stock.service.stockservice.service.StocksAvailabilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

@RestController
@RequestMapping(value = "/stock")
public class StocksAvailabilityController {

    private Logger log = LoggerFactory.getLogger(StocksAvailabilityController.class);
    private final StocksAvailabilityService stocksAvailabilityService;

    @Autowired
    public StocksAvailabilityController(StocksAvailabilityService stocksAvailabilityService) {
        this.stocksAvailabilityService=stocksAvailabilityService;
    }


    /****** ADD STOCKS ***************/
    @PostMapping(value = "/add/")
    public ResponseEntity<StocksDTO> create(@Valid @RequestBody StocksDTO dto) {
        log.info("Create Stocks details with {}", dto);
        try {
            return new ResponseEntity<>(stocksAvailabilityService.create(dto), HttpStatus.CREATED);
        } catch (DuplicateEntityException ex) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /****** GET STOCKS ***************/
    @GetMapping("/stockId/{stocksId}")
    public ResponseEntity<StocksDTO> getStocksInfoById(@PathVariable String stocksId){
        log.info("Get Stocks details for stocksId code {}", stocksId);
        StocksDTO stocksDTO = stocksAvailabilityService.getStocksById(stocksId);
        if (stocksDTO != null)
            return new ResponseEntity<>(stocksDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /****** LIST ALL STOCKS ***************/
    @GetMapping("/listAllStocks")
    public ResponseEntity<List<StocksDTO>> list() {
        log.info("List all the Stocks details");
        List<StocksDTO> stocksDTOList=stocksAvailabilityService.listAll();
        return ResponseEntity.ok(stocksDTOList);
    }

    /****** LIST ALL Products ***************/
    @GetMapping("/listAllProducts")
    public ResponseEntity<List<ProductDTO>> listProducts() {
        log.info("List all the Stocks details");
        List<ProductDTO> productDTOList=stocksAvailabilityService.listAllProducts();
        return ResponseEntity.ok(productDTOList);
    }


    /********* GET CURRENT STOCK AVAILABILITY OF PRODUCT**********/
    @GetMapping
    public ResponseEntity<ProductDTO> getStockForProduct(@RequestParam(value = "productId") final String productId) {
        ProductDTO productDTO = stocksAvailabilityService.getStockForProduct(productId);
        if (productDTO != null){
            //return new ResponseEntity<>(productDTO, HttpStatus.OK);
            return  ResponseEntity.ok().eTag("\"" + productDTO.getStock().getVersion() + "\"").body(productDTO);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /******** Order the Item ******************/
    @PatchMapping("/orderstocks")
    public ResponseEntity<OrderDTO> patch(WebRequest request,@Valid @RequestBody OrderDTO orderDTO) {
        log.info("Patching stocks with id {}  with data {}", orderDTO);
        String ifMatchValue = request.getHeader("If-Match");
        if (isEmpty(ifMatchValue)) {
            return ResponseEntity.badRequest().build();
        }
        else {
            try {
               return stocksAvailabilityService.patch(orderDTO, ifMatchValue)
                        .map(dto -> new ResponseEntity<>(orderDTO, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
            }catch(DuplicateEntityException e){
                log.error(e.getMessage());
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
            }catch (OptimisticLockingFailureException e){
                log.error(e.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
    }


    //****** UPDATE STOCKS ***************//
    @PostMapping("/update")
    public ResponseEntity<String> update(WebRequest request,@Valid @RequestBody StocksDTO stocksDto) {
        log.info("Updating stocks  with data {}",  stocksDto);
        String ifMatchValue = request.getHeader("If-Match");
        if (isEmpty(ifMatchValue)) {
            return ResponseEntity.badRequest().build();
        }
        else {
            try{
                return stocksAvailabilityService
                        .update(stocksDto,ifMatchValue)
                        .map(stocksDTO -> new ResponseEntity<>("Everything went well and the  entry for stock was updated", HttpStatus.CREATED))
                        .orElse(new ResponseEntity<>("Outdated stock, because a newer stock was processed first", HttpStatus.NO_CONTENT));
            }catch(DuplicateEntityException e){
                log.error(e.getMessage());
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
            }catch (OptimisticLockingFailureException e){
                log.error(e.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
    }
}

