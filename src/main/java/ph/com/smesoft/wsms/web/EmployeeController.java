package ph.com.smesoft.wsms.web;


import java.io.UnsupportedEncodingException;
import java.util.Arrays;
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
import ph.com.smesoft.wsms.domain.Department;
import ph.com.smesoft.wsms.domain.Employee;
import ph.com.smesoft.wsms.domain.Jobtitle;
import ph.com.smesoft.wsms.dto.SearchForm;
import ph.com.smesoft.wsms.reference.DayOff;
import ph.com.smesoft.wsms.reference.EmploymentStatus;
import ph.com.smesoft.wsms.reference.Gender;
import ph.com.smesoft.wsms.service.AreaService;
import ph.com.smesoft.wsms.service.EmployeeService;
import ph.com.smesoft.wsms.service.JobtitleService;
import ph.com.smesoft.wsms.service.DepartmentService;


@Controller
@RequestMapping("/Employee")
public class EmployeeController {
	
	@Autowired
    EmployeeService employeeService;
	@Autowired
	JobtitleService jobtitleService;
	@Autowired
	DepartmentService departmentService;
		
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Employee employee, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, employee);       
            
          
            return "Employee/create";
        }      
        employeeService.saveEmployee(employee);
        return "redirect:/Employee/" + encodeUrlPathSegment(employee.getId().toString(), httpServletRequest);	
     }
	
	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Employee());
         return "Employee/create";
    }
	
	
	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("employee", employeeService.findEmployee(id));
        uiModel.addAttribute("itemId", id);
 
        return "Employee/show";
    }
		

	
	@RequestMapping(params = "/{id}/add", produces = "text/html")
    public String addForm(Model uiModel) {
        populateEditForm(uiModel, new Employee());
        return "Employee/add";
    }
	

	
	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("employee", Employee.findEmployeeEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            uiModel.addAttribute("list", Contact.findContactEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) employeeService.countAllEmployee() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("employee", Employee.findAllEmployee(sortFieldName, sortOrder));
           
        }
        return "Employee/list";
    }
	
	
	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Employee employee, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, employee);
            return "Employee/update";
        }
        uiModel.asMap().clear();
        employeeService.updateEmployee(employee);
        return "redirect:/Employee/" + encodeUrlPathSegment(employee.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, employeeService.findEmployee(id));
        return "Employee/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Employee employee = employeeService.findEmployee(id);
		employeeService.deleteEmployee(employee);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/Employee";
    }
	
	void populateEditForm(Model uiModel, Employee employee) {
        uiModel.addAttribute("employee", employee);
        uiModel.addAttribute("jobtitle", Jobtitle.findAllJobtitles());
        uiModel.addAttribute("department", Department.findAllDepartments());
        uiModel.addAttribute("gender", Arrays.asList(Gender.values()));
        uiModel.addAttribute("dayoff1", Arrays.asList(DayOff.values()));
        uiModel.addAttribute("dayoff2", Arrays.asList(DayOff.values()));
        uiModel.addAttribute("employmentstatus", Arrays.asList(EmploymentStatus.values()));
      
    
        
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
        	Employee employee = employeeService.findEmployee(id);
            if (employee == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(employee.toJson(), headers, HttpStatus.OK);
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
            List<Employee> result = employeeService.findAllEmployee();
            return new ResponseEntity<String>(Employee.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
        	Employee employee = ph.com.smesoft.wsms.domain.Employee.fromJsonToEmployee(json);
        	employeeService.saveEmployee(employee);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+employee.getId().toString()).build().toUriString());
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
            for (Employee employee: Employee.fromJsonArrayToEmployee(json)) {
            	employeeService.saveEmployee(employee);
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
        	Employee employee = ph.com.smesoft.wsms.domain.Employee.fromJsonToEmployee(json);
        	employee.setId(id);
            if (employeeService.updateEmployee(employee) == null) {
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
        	Employee employee = employeeService.findEmployee(id);
            if (employee == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            employeeService.deleteEmployee(employee);
            return new ResponseEntity<String>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@RequestMapping(value="/{departmentId}", method=RequestMethod.GET, params="jobtitle")
	  public ResponseEntity<String> passJobtitleList(@PathVariable Integer departmentId, Model uiModel){
		HttpHeaders headers = new HttpHeaders();
		Long jobtitleIdtoLong = Long.valueOf(departmentId.longValue());
		List<Jobtitle> jobtitleName = jobtitleService.findAllJobtitleByDepartmentId(jobtitleIdtoLong);
		String json = new Gson().toJson(jobtitleName);
		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}
		

	@RequestMapping(value = "/search", method = { RequestMethod.GET })
	public String listofCustomer(@ModelAttribute("SearchCriteria") SearchForm searchForm, Model uiModel) {
		uiModel.addAttribute("customer", employeeService.findEmployeebyid(searchForm.getSearchString()));
		return "Employee/list";
	}
	
}
