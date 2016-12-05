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

import ph.com.smesoft.wsms.domain.LocationType;
import ph.com.smesoft.wsms.dto.SearchForm;
import ph.com.smesoft.wsms.service.LocationTypeService;

@Controller
@RequestMapping("/LocationType")
public class LocationTypeController {

	@Autowired
    LocationTypeService LocationTypeService;

	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid LocationType LocationType, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		
		if(LocationTypeService.checkIfLocationTypeExist(LocationType.getLocationTypeName().trim()) > 0){
			 bindingResult.reject("locationtype", "Location Type already exists.");
			 
			 populateEditForm(uiModel, LocationType);
	        	 //uiModel.asMap().clear();
	             
             return "LocationType/create";
        }
	     if(!LocationTypeService.checkRegex(LocationType.getLocationTypeName().trim(), "^([^0-9]*)$")){
	    	 bindingResult.reject("LocationType", "Invalid entry of Characters");
	    	 populateEditForm(uiModel, LocationType);
	        	 //uiModel.asMap().clear();
	         return "LocationType/create";
	    } 
		if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, LocationType);
            return "LocationType/create";
        }
        
        /*if((int) LocationTypeService.checkIfLocationTypeExist(LocationType.getLocationTypeName()) > 0){
            bindingResult.reject("LocationType", "Floor Number already exists.");
            return "LocationType/create";
        } */
        
        if(!LocationTypeService.checkRegex(LocationType.getLocationTypeName().trim(), "^([^0-9]*)$")){
        	 populateEditForm(uiModel, LocationType);
        	 //uiModel.asMap().clear();
             
        	  return "LocationType/create";
        }
        
        uiModel.asMap().clear();
        LocationTypeService.saveLocationType(LocationType);
        return "redirect:/LocationType/" + encodeUrlPathSegment(LocationType.getId().toString(), httpServletRequest);
    }
	/*@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid LocationType LocationType, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, LocationType);
            return "LocationTypes/create";
        }
        uiModel.asMap().clear();
        LocationTypeService.saveLocationType(LocationType);
        return "redirect:/LocationTypes/" + encodeUrlPathSegment(LocationType.getId().toString(), httpServletRequest);
    }*/

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new LocationType());
        return "LocationType/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("locationtype", LocationTypeService.findLocationType(id));
       // uiModel.addAttribute("itemId", id);
        return "LocationType/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("locationtypes", LocationType.findLocationTypeEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) LocationTypeService.countAllLocationTypes() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("locationtypes", LocationType.findAllLocationTypes(sortFieldName, sortOrder));
        }
        return "LocationType/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid LocationType LocationType, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, LocationType);
            return "LocationType/update";
        }
        uiModel.asMap().clear();
        LocationTypeService.updateLocationType(LocationType);
        return "redirect:/LocationType/" + encodeUrlPathSegment(LocationType.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, LocationTypeService.findLocationType(id));
        return "LocationType/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        LocationType LocationType = LocationTypeService.findLocationType(id);
        LocationTypeService.deleteLocationType(LocationType);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/LocationType";
    }

	void populateEditForm(Model uiModel, LocationType LocationType) {
        uiModel.addAttribute("LocationType", LocationType);
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
            LocationType LocationType = LocationTypeService.findLocationType(id);
            if (LocationType == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(LocationType.toJson(), headers, HttpStatus.OK);
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
            List<LocationType> result = LocationTypeService.findAllLocationTypes();
            return new ResponseEntity<String>(LocationType.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            LocationType locationtype = LocationType.fromJsonToLocationType(json);
            LocationTypeService.saveLocationType(locationtype);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+locationtype.getId().toString()).build().toUriString());
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
            for (LocationType LocationType: LocationType.fromJsonArrayToLocationTypes(json)) {
                LocationTypeService.saveLocationType(LocationType);
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
            LocationType locationtype = LocationType.fromJsonToLocationType(json);
            locationtype.setId(id);
            if (LocationTypeService.updateLocationType(locationtype) == null) {
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
            LocationType LocationType = LocationTypeService.findLocationType(id);
            if (LocationType == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            LocationTypeService.deleteLocationType(LocationType);
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
		uiModel.addAttribute("locationtypes", LocationTypeService.findLocationTypebyLocationTypeNumber(searchForm.getSearchString()));
		return "LocationType/list";
	}
}
