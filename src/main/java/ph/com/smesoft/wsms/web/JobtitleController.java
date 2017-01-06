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

import ph.com.smesoft.wsms.domain.Jobtitle;
import ph.com.smesoft.wsms.dto.SearchForm;
import ph.com.smesoft.wsms.service.JobtitleService;

@Controller
@RequestMapping("/Jobtitle")
public class JobtitleController {

	@Autowired
    JobtitleService JobtitleService;

	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Jobtitle Jobtitle, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		
		if(JobtitleService.checkIfJobtitleExist(Jobtitle.getJobtitleName().trim()) > 0){
			 bindingResult.reject("Jobtitle", "Customer Type already exists.");
			 
			 populateEditForm(uiModel, Jobtitle);
	        	 //uiModel.asMap().clear();
	             
             return "Jobtitle/create";
        }
	     if(!JobtitleService.checkRegex(Jobtitle.getJobtitleName().trim(), "^([^0-9]*)$")){
	    	 bindingResult.reject("Jobtitle", "Invalid entry of Characters");
	    	 populateEditForm(uiModel, Jobtitle);
	        	 //uiModel.asMap().clear();
	         return "Jobtitle/create";
	    } 
		if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, Jobtitle);
            return "Jobtitle/create";
        }
        
        /*if((int) JobtitleService.checkIfJobtitleExist(Jobtitle.getJobtitleName()) > 0){
            bindingResult.reject("Jobtitle", "Floor Number already exists.");
            return "Jobtitle/create";
        } */
        
        if(!JobtitleService.checkRegex(Jobtitle.getJobtitleName().trim(), "^([^0-9]*)$")){
        	 populateEditForm(uiModel, Jobtitle);
        	 //uiModel.asMap().clear();
             
        	  return "Jobtitle/create";
        }
        
        uiModel.asMap().clear();
        JobtitleService.saveJobtitle(Jobtitle);
        return "redirect:/Jobtitle/" + encodeUrlPathSegment(Jobtitle.getId().toString(), httpServletRequest);
    }
	/*@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Jobtitle Jobtitle, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, Jobtitle);
            return "Jobtitles/create";
        }
        uiModel.asMap().clear();
        JobtitleService.saveJobtitle(Jobtitle);
        return "redirect:/Jobtitles/" + encodeUrlPathSegment(Jobtitle.getId().toString(), httpServletRequest);
    }*/

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Jobtitle());
        return "Jobtitle/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("jobtitle", JobtitleService.findJobtitle(id));
       // uiModel.addAttribute("itemId", id);
        return "Jobtitle/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("jobtitles", Jobtitle.findJobtitleEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) JobtitleService.countAllJobtitles() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("jobtitles", Jobtitle.findAllJobtitles(sortFieldName, sortOrder));
        }
        return "Jobtitle/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Jobtitle Jobtitle, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, Jobtitle);
            return "Jobtitle/update";
        }
        uiModel.asMap().clear();
        JobtitleService.updateJobtitle(Jobtitle);
        return "redirect:/Jobtitle/" + encodeUrlPathSegment(Jobtitle.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, JobtitleService.findJobtitle(id));
        return "Jobtitle/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Jobtitle Jobtitle = JobtitleService.findJobtitle(id);
        JobtitleService.deleteJobtitle(Jobtitle);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/Jobtitle";
    }

	void populateEditForm(Model uiModel, Jobtitle Jobtitle) {
        uiModel.addAttribute("jobtitle", Jobtitle);
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
            Jobtitle Jobtitle = JobtitleService.findJobtitle(id);
            if (Jobtitle == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<String>(Jobtitle.toJson(), headers, HttpStatus.OK);
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
            List<Jobtitle> result = JobtitleService.findAllJobtitles();
            return new ResponseEntity<String>(Jobtitle.toJsonArray(result), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
            Jobtitle jobtitle = Jobtitle.fromJsonToJobtitle(json);
            JobtitleService.saveJobtitle(jobtitle);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+jobtitle.getId().toString()).build().toUriString());
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
            for (Jobtitle Jobtitle: Jobtitle.fromJsonArrayToJobtitles(json)) {
                JobtitleService.saveJobtitle(Jobtitle);
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
            Jobtitle jobtitle = Jobtitle.fromJsonToJobtitle(json);
            jobtitle.setId(id);
            if (JobtitleService.updateJobtitle(jobtitle) == null) {
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
            Jobtitle Jobtitle = JobtitleService.findJobtitle(id);
            if (Jobtitle == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
            JobtitleService.deleteJobtitle(Jobtitle);
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
		uiModel.addAttribute("jobtitles", JobtitleService.findJobtitlebyJobtitleNumber(searchForm.getSearchString()));
		return "Jobtitle/list";
	}
}
