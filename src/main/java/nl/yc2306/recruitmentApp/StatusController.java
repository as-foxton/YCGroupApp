package nl.yc2306.recruitmentApp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

	@RequestMapping("status")
	public String status() {
		return "OK";
	}
	
	
	
}
