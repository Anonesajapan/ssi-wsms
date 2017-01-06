package ph.com.smesoft.wsms.service;

import java.util.List;

import ph.com.smesoft.wsms.domain.Jobtitle;

public interface JobtitleService {

		public abstract long countAllJobtitles();


		public abstract void deleteJobtitle(Jobtitle jobtitle);


		public abstract Jobtitle findJobtitle(Long id);


		public abstract List<Jobtitle> findAllJobtitles();


		public abstract List<Jobtitle> findJobtitleEntries(int firstResult, int maxResults);


		public abstract void saveJobtitle(Jobtitle jobtitle);


		public abstract Jobtitle updateJobtitle(Jobtitle jobtitle);

	    //public abstract List<Floor> findFloorbyFloorNumber(String searchKeyword);
	    //public abstract String findFloorbyFloorNumber(String searchString);
		
		public abstract List<Jobtitle> findJobtitlebyJobtitleNumber(String searchString);


		public abstract long checkIfJobtitleExist(String Jobtitle);
		public boolean checkRegex(String input, String user_pattern);

}
