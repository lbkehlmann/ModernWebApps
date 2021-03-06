import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmailController {

	private EditorService englishEditorService;
	private EditorService spanishEditorService;

	public EmailController() {
		
	}
	
	public EmailController(EditorService editorService) {
		this.englishEditorService = editorService;
	}
	
	@ResponseBody
    @RequestMapping(value = "/")
    public String helloWorld()
    {
        return "Hello world!";
    }
	
	@ResponseBody
	@RequestMapping(value = "/compose")
	public String getComposedEmail() {
		return "Composed email: " + englishEditorService.composeEmail();
	}
	
	@ResponseBody
	@RequestMapping(value = "/email") 
	public String getEmail(@RequestParam("language") String language)
	{
		if (language.equalsIgnoreCase("English")) {
			return englishEditorService.getName() + " " + englishEditorService.composeEmail();
		}
		else if (language.equalsIgnoreCase("Spanish")) {
			return spanishEditorService.getName() + " " + spanishEditorService.composeEmail();
		}
		else {
			return "Language " + language + " not supported.";
		}
	}
	
	@ResponseBody
	@RequestMapping("/headers") 	
	public String getAllHeaders(@RequestHeader HttpHeaders headers)
	{
		Set<String> keys = headers.keySet();
		String response = "";
		Iterator<String> i = keys.iterator();
		while(i.hasNext()) {
			String key = i.next();
			List<String> value = headers.get(key);
			response += key + " " + value;
		}
		return response;
	}	
	
	@ResponseBody
	@RequestMapping("/userAgent") 	
	public String getContentType(@RequestHeader("User-Agent") String userAgent)
	{
		return "User Agent:" + userAgent;
	}	
	
	@ResponseBody
    @RequestMapping(value = "/", params = {"action"}, method=RequestMethod.GET)
    public String getGreeting(@RequestParam("action") String action)
    {
		String ret = "";
		if (action.equalsIgnoreCase("compose")) {
			ret = "Invoking editor service: " + englishEditorService.composeEmail();
		}
		return ret;
    }
	
	public void setSpanishEditorService(EditorService editorService) {
		this.spanishEditorService = editorService;
	}
}
