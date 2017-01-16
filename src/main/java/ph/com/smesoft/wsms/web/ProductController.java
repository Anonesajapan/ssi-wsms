package ph.com.smesoft.wsms.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.google.gson.Gson;

import ph.com.smesoft.wsms.domain.Product;
import ph.com.smesoft.wsms.domain.ProductType;
import ph.com.smesoft.wsms.domain.Subcategory;
import ph.com.smesoft.wsms.domain.Unit;
import ph.com.smesoft.wsms.domain.Brand;
import ph.com.smesoft.wsms.domain.Category;
import ph.com.smesoft.wsms.dto.SearchForm;
import ph.com.smesoft.wsms.service.ProductService;
import ph.com.smesoft.wsms.service.CategoryService;
import ph.com.smesoft.wsms.service.ProductTypeService;
import ph.com.smesoft.wsms.service.BrandService;
import ph.com.smesoft.wsms.service.UnitService;
@Controller
@RequestMapping("/Product")
public class ProductController {

	@Autowired
    ProductService ProductService;
	@Autowired
    ProductTypeService ProductTypeService;
	@Autowired
    CategoryService CategoryService;
	@Autowired
    BrandService BrandService;
	@Autowired
    UnitService UnitService;
	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Product Product, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		
		
	     
		if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, Product);
            return "Product/create";
        }
        
        /*if((int) ProductService.checkIfProductExist(Product.getProductName()) > 0){
            bindingResult.reject("Product", "Floor Number already exists.");
            return "Product/create";
        } */
        
       
        
        uiModel.asMap().clear();
        ProductService.saveProduct(Product);
        return "redirect:/Product/" + encodeUrlPathSegment(Product.getId().toString(), httpServletRequest);
    }
	/*@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Product Product, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, Product);
            return "Products/create";
        }
        uiModel.asMap().clear();
        ProductService.saveProduct(Product);
        return "redirect:/Products/" + encodeUrlPathSegment(Product.getId().toString(), httpServletRequest);
    }*/

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Product());
        return "Product/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("product", ProductService.findProduct(id));
       // uiModel.addAttribute("itemId", id);
        return "Product/show";
    }

	
	
	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("products", Product.findProductEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) ProductService.countAllProducts() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("products", Product.findAllProducts(sortFieldName, sortOrder));
        }
        return "Product/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Product Product, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, Product);
            return "Product/update";
        }
        uiModel.asMap().clear();
        ProductService.updateProduct(Product);
        return "redirect:/Product/" + encodeUrlPathSegment(Product.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, ProductService.findProduct(id));
        return "Product/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Product Product = ProductService.findProduct(id);
        ProductService.deleteProduct(Product);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/Product";
    }

	void populateEditForm(Model uiModel, Product Product) {
		uiModel.addAttribute("product", Product);
		uiModel.addAttribute("producttype", ProductType.findAllProductTypes());
		uiModel.addAttribute("category", Category.findAllCategorys());
		uiModel.addAttribute("subcategory", Subcategory.findAllSubcategorys());
		uiModel.addAttribute("brand", Brand.findAllBrands());
		uiModel.addAttribute("unit", Unit.findAllUnits());
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
            Product Product = ProductService.findProduct(id);
            if (Product == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(Product.toJson(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
            List<Product> result = ProductService.findAllProducts();
            return new ResponseEntity<String>(Product.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Product product = Product.fromJsonToProduct(json);
            ProductService.saveProduct(product);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+product.getId().toString()).build().toUriString());
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            for (Product Product: Product.fromJsonArrayToProducts(json)) {
                ProductService.saveProduct(Product);
            }
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Product product = Product.fromJsonToProduct(json);
            product.setId(id);
            if (ProductService.updateProduct(product) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Product Product = ProductService.findProduct(id);
            if (Product == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            ProductService.deleteProduct(Product);
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
/*
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<Floor> listofFloor(@RequestParam("floorNumber") String searchKeyword) {
		System.out.println("First");
		List<Floor> searchResult = floorService.findFloorbyFloorNumber(searchKeyword);
		System.out.println("2nd");
		System.out.println("THird" + searchResult);
		return searchResult;
	}*/
	
	/*@RequestMapping(value = "/search", method = { RequestMethod.GET })
	public ResponseEntity<String> listofFloor(@ModelAttribute("SearchCriteria") SearchForm searchForm) {
		HttpHeaders headers = new HttpHeaders();
		System.out.println("First");
		String searchResult = floorService.findFloorbyFloorNumber(searchForm.getSearchString());
		System.out.println("2nd");
		System.out.println("THird" + searchResult);
		//return searchResult;
		return new ResponseEntity<String>(searchResult,headers, HttpStatus.OK);
	}*/
	
	@RequestMapping(value = "/search", method = { RequestMethod.GET })
	public String listofCity(@ModelAttribute("SearchCriteria") SearchForm searchForm, Model uiModel) {
		uiModel.addAttribute("products", ProductService.findProductbyProductNumber(searchForm.getSearchString()));
		return "Product/list";
	}
	@RequestMapping(value="/{streetId}", method = RequestMethod.GET, params="form")
	  public ResponseEntity<String> passAreaList(@PathVariable String streetId, Model uiModel){
	  HttpHeaders headers = new HttpHeaders();
	  Long id = Long.valueOf(streetId);
	  List<ProductType> result = ProductTypeService.getProductTypeName(id);
	  String json = new Gson().toJson(result);
	  return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	 }
}


