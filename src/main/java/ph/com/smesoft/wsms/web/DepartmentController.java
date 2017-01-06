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

import ph.com.smesoft.wsms.domain.Department;
import ph.com.smesoft.wsms.dto.SearchForm;
import ph.com.smesoft.wsms.service.DepartmentService;

@Controller
@RequestMapping("/Department")
public class DepartmentController {

	@Autowired
    DepartmentService DepartmentService;

	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Department Department, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		
		if(DepartmentService.checkIfDepartmentExist(Department.getDepartmentName().trim()) > 0){
			 bindingResult.reject("Department", "Customer Type already exists.");
			 
			 populateEditForm(uiModel, Department);
	        	 //uiModel.asMap().clear();
	             
             return "Department/create";
        }
	     if(!DepartmentService.checkRegex(Department.getDepartmentName().trim(), "^([^0-9]*)$")){
	    	 bindingResult.reject("Department", "Invalid entry of Characters");
	    	 populateEditForm(uiModel, Department);
	        	 //uiModel.asMap().clear();
	         return "Department/create";
	    } 
		if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, Department);
            return "Department/create";
        }
        
        /*if((int) DepartmentService.checkIfDepartmentExist(Department.getDepartmentName()) > 0){
            bindingResult.reject("Department", "Floor Number already exists.");
            return "Department/create";
        } */
        
        if(!DepartmentService.checkRegex(Department.getDepartmentName().trim(), "^([^0-9]*)$")){
        	 populateEditForm(uiModel, Department);
        	 //uiModel.asMap().clear();
             
        	  return "Department/create";
        }
        
        uiModel.asMap().clear();
        DepartmentService.saveDepartment(Department);
        return "redirect:/Department/" + encodeUrlPathSegment(Department.getId().toString(), httpServletRequest);
    }
	/*@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Department Department, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, Department);
            return "Departments/create";
        }
        uiModel.asMap().clear();
        DepartmentService.saveDepartment(Department);
        return "redirect:/Departments/" + encodeUrlPathSegment(Department.getId().toString(), httpServletRequest);
    }*/

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Department());
        return "Department/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("department", DepartmentService.findDepartment(id));
       // uiModel.addAttribute("itemId", id);
        return "Department/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("departments", Department.findDepartmentEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) DepartmentService.countAllDepartments() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("departments", Department.findAllDepartments(sortFieldName, sortOrder));
        }
        return "Department/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Department Department, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, Department);
            return "Department/update";
        }
        uiModel.asMap().clear();
        DepartmentService.updateDepartment(Department);
        return "redirect:/Department/" + encodeUrlPathSegment(Department.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, DepartmentService.findDepartment(id));
        return "Department/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Department Department = DepartmentService.findDepartment(id);
        DepartmentService.deleteDepartment(Department);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/Department";
    }

	void populateEditForm(Model uiModel, Department Department) {
        uiModel.addAttribute("department", Department);
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
            Department Department = DepartmentService.findDepartment(id);
            if (Department == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(Department.toJson(), headers, HttpStatus.OK);
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
            List<Department> result = DepartmentService.findAllDepartments();
            return new ResponseEntity<String>(Department.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Department department = Department.fromJsonToDepartment(json);
            DepartmentService.saveDepartment(department);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+department.getId().toString()).build().toUriString());
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
            for (Department Department: Department.fromJsonArrayToDepartments(json)) {
                DepartmentService.saveDepartment(Department);
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
            Department department = Department.fromJsonToDepartment(json);
            department.setId(id);
            if (DepartmentService.updateDepartment(department) == null) {
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
            Department Department = DepartmentService.findDepartment(id);
            if (Department == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            DepartmentService.deleteDepartment(Department);
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
		uiModel.addAttribute("departments", DepartmentService.findDepartmentbyDepartmentNumber(searchForm.getSearchString()));
		return "Department/list";
	}
}
