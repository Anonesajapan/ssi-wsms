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

import ph.com.smesoft.wsms.domain.Area;
import ph.com.smesoft.wsms.dto.SearchForm;
import ph.com.smesoft.wsms.service.AreaService;

@Controller
@RequestMapping("/Area")
public class AreaController {

	@Autowired
    AreaService AreaService;

	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Area Area, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		
		if(AreaService.checkIfAreaExist(Area.getAreaName().trim()) > 0){
			 bindingResult.reject("area", "Area already exists.");
			 
			 populateEditForm(uiModel, Area);
	        	 //uiModel.asMap().clear();
	             
             return "Area/create";
        }
	     if(!AreaService.checkRegex(Area.getAreaName().trim(), "^([^0-9]*)$")){
	    	 bindingResult.reject("Area", "Invalid entry of Characters");
	    	 populateEditForm(uiModel, Area);
	        	 //uiModel.asMap().clear();
	         return "Area/create";
	    } 
		if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, Area);
            return "Area/create";
        }
        
        /*if((int) AreaService.checkIfAreaExist(Area.getAreaName()) > 0){
            bindingResult.reject("Area", "Floor Number already exists.");
            return "Area/create";
        } */
        
        if(!AreaService.checkRegex(Area.getAreaName().trim(), "^([^0-9]*)$")){
        	 populateEditForm(uiModel, Area);
        	 //uiModel.asMap().clear();
             
        	  return "Area/create";
        }
        
        uiModel.asMap().clear();
        AreaService.saveArea(Area);
        return "redirect:/Area/" + encodeUrlPathSegment(Area.getId().toString(), httpServletRequest);
    }
	/*@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Area Area, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, Area);
            return "Areas/create";
        }
        uiModel.asMap().clear();
        AreaService.saveArea(Area);
        return "redirect:/Areas/" + encodeUrlPathSegment(Area.getId().toString(), httpServletRequest);
    }*/

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Area());
        return "Area/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("area", AreaService.findArea(id));
       // uiModel.addAttribute("itemId", id);
        return "Area/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("areas", Area.findAreaEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) AreaService.countAllAreas() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("areas", Area.findAllAreas(sortFieldName, sortOrder));
        }
        return "Area/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Area Area, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, Area);
            return "Area/update";
        }
        uiModel.asMap().clear();
        AreaService.updateArea(Area);
        return "redirect:/Area/" + encodeUrlPathSegment(Area.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, AreaService.findArea(id));
        return "Area/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Area Area = AreaService.findArea(id);
        AreaService.deleteArea(Area);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/Area";
    }

	void populateEditForm(Model uiModel, Area Area) {
        uiModel.addAttribute("Area", Area);
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
            Area Area = AreaService.findArea(id);
            if (Area == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(Area.toJson(), headers, HttpStatus.OK);
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
            List<Area> result = AreaService.findAllAreas();
            return new ResponseEntity<String>(Area.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Area area = Area.fromJsonToArea(json);
            AreaService.saveArea(area);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+area.getId().toString()).build().toUriString());
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
            for (Area Area: Area.fromJsonArrayToAreas(json)) {
                AreaService.saveArea(Area);
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
            Area area = Area.fromJsonToArea(json);
            area.setId(id);
            if (AreaService.updateArea(area) == null) {
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
            Area Area = AreaService.findArea(id);
            if (Area == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            AreaService.deleteArea(Area);
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
		uiModel.addAttribute("areas", AreaService.findAreabyAreaNumber(searchForm.getSearchString()));
		return "Area/list";
	}
}
