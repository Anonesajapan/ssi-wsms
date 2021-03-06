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
import ph.com.smesoft.wsms.domain.Area;
import ph.com.smesoft.wsms.domain.Barangay;
import ph.com.smesoft.wsms.domain.City;
import ph.com.smesoft.wsms.domain.Contact;
import ph.com.smesoft.wsms.domain.Customer;
import ph.com.smesoft.wsms.domain.CustomerType;
import ph.com.smesoft.wsms.dto.SearchForm;
import ph.com.smesoft.wsms.service.AreaService;
import ph.com.smesoft.wsms.service.CustomerService;
import ph.com.smesoft.wsms.service.CustomerTypeService;
import ph.com.smesoft.wsms.service.IndustryTypeService;
import ph.com.smesoft.wsms.service.LocationTypeService;
import ph.com.smesoft.wsms.service.CityService;
import ph.com.smesoft.wsms.service.ContactService;
import ph.com.smesoft.wsms.service.BarangayService;
import ph.com.smesoft.wsms.service.StreetService;
import ph.com.smesoft.wsms.domain.IndustryType;
import ph.com.smesoft.wsms.domain.LocationType;
import ph.com.smesoft.wsms.domain.Street;



@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
    CustomerService customerService;
	@Autowired
	CustomerTypeService customerTypeService;
	@Autowired
	LocationTypeService locationTypeService;
	@Autowired
	IndustryTypeService industryTypeService;
	@Autowired
	CityService cityservice;
	@Autowired
	BarangayService barangayservice;
	@Autowired
	StreetService streetservice;
	@Autowired
	AreaService areaservice;

	@Autowired
	ContactService contactService;
	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Customer customer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, customer);       
            
            uiModel.addAttribute("CustomerType", CustomerType.findAllCustomerTypes());
            uiModel.addAttribute("IndustryType", IndustryType.findAllIndustrytypes());
            uiModel.addAttribute("LocationType", LocationType.findAllLocationTypes());
            uiModel.addAttribute("City", City.findAllCities());
            uiModel.addAttribute("Barangay", Barangay.findAllBarangays());
            uiModel.addAttribute("Street", Street.findAllStreets());
            uiModel.addAttribute("Area", Area.findAllAreas());
            
            return "customer/create";
        }      
        customerService.saveCustomer(customer);
        return "redirect:/customer/" + encodeUrlPathSegment(customer.getId().toString(), httpServletRequest);	
     }
	
	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Customer());
         return "customer/create";
    }
	
	
	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("customer", customerService.findCustomer(id));
        uiModel.addAttribute("itemId", id);
        uiModel.addAttribute("contactdetails", contactService.findContactDetailsByCustomerId(id));
        
        return "customer/show";
    }
		
	@RequestMapping(value = "/{id}",  params = "contact", produces = "text/html")
    public String showcontact(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("contactdetails", contactService.findContactDetailsByCustomerId(id));
        
        return "customer/showcontact";
    }
	
	@RequestMapping(params = "/{id}/add", produces = "text/html")
    public String addForm(Model uiModel) {
        populateEditForm(uiModel, new Customer());
        return "customer/add";
    }
	

	
	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("customer", Customer.findCustomerEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            uiModel.addAttribute("list", Contact.findContactEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) customerService.countAllCustomer() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("customer", Customer.findAllCustomer(sortFieldName, sortOrder));
            uiModel.addAttribute("list", Contact.findAllContact(sortFieldName, sortOrder));
        }
        return "customer/list";
    }
	
	
	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Customer customer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, customer);
            return "customer/update";
        }
        uiModel.asMap().clear();
        customerService.updateCustomer(customer);
        return "redirect:/customer/" + encodeUrlPathSegment(customer.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, customerService.findCustomer(id));
        return "customer/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Customer customer = customerService.findCustomer(id);
		customerService.deleteCustomer(customer);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/customer";
    }
	
	void populateEditForm(Model uiModel, Customer customer) {
        uiModel.addAttribute("customer", customer);
        uiModel.addAttribute("customertypes", customerTypeService.findAllCustomerTypes());
        uiModel.addAttribute("industrytypes", industryTypeService.findAllIndustrytypes());
        uiModel.addAttribute("locationtypes", locationTypeService.findAllLocationTypes());
        uiModel.addAttribute("cities", City.findAllCities());
        uiModel.addAttribute("barangays", Barangay.findAllBarangays());
        uiModel.addAttribute("streets", Street.findAllStreets());
        uiModel.addAttribute("area", areaservice.findAllAreas());
    
        
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
        	Customer customer = customerService.findCustomer(id);
            if (customer == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(customer.toJson(), headers, HttpStatus.OK);
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
            List<Customer> result = customerService.findAllCustomer();
            return new ResponseEntity<String>(Customer.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
        	Customer customer = ph.com.smesoft.wsms.domain.Customer.fromJsonToCustomer(json);
        	customerService.saveCustomer(customer);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+customer.getId().toString()).build().toUriString());
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
            for (Customer customer: Customer.fromJsonArrayToCustomer(json)) {
            	customerService.saveCustomer(customer);
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
        	Customer customer = ph.com.smesoft.wsms.domain.Customer.fromJsonToCustomer(json);
        	customer.setId(id);
            if (customerService.updateCustomer(customer) == null) {
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
        	Customer customer = customerService.findCustomer(id);
            if (customer == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            customerService.deleteCustomer(customer);
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@RequestMapping(value="/{cityId}", method=RequestMethod.GET, params="barangay")
	  public ResponseEntity<String> passBarangayList(@PathVariable Integer cityId, Model uiModel){
		HttpHeaders headers = new HttpHeaders();
		Long barangayIdtoLong = Long.valueOf(cityId.longValue());
		List<Barangay> barangayName = barangayservice.findAllBarangayByCityId(barangayIdtoLong);
		String json = new Gson().toJson(barangayName);
		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}
	
		@RequestMapping(value="/{barangayId}", method=RequestMethod.GET, params="street")
		public ResponseEntity<String> passStreetList(@PathVariable Integer barangayId, Model uiModel){
		HttpHeaders headers = new HttpHeaders();
		Long streetIdtoLong = Long.valueOf(barangayId.longValue());
		List<Street> streetName = streetservice.findAllStreetByBarangayId(streetIdtoLong);
		String json = new Gson().toJson(streetName);
		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}
		
		@RequestMapping(value="/{streetId}", method=RequestMethod.GET, params="area")
		public ResponseEntity<String> passAreaList(@PathVariable Integer streetId, Model uiModel){
		HttpHeaders headers = new HttpHeaders();
		Long areaIdtoLong = Long.valueOf(streetId.longValue());
		List<Area> AreaName = areaservice.findAllAreaByStreetId(areaIdtoLong);
		String json = new Gson().toJson(AreaName);
		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}
		
		

	@RequestMapping(value = "/search", method = { RequestMethod.GET })
	public String listofCustomer(@ModelAttribute("SearchCriteria") SearchForm searchForm, Model uiModel) {
		uiModel.addAttribute("customer", customerService.findCustomerbyid(searchForm.getSearchString()));
		return "customer/list";
	}
	
}
