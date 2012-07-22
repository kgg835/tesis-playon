/**
 * 
 */
package tesis.playon.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Pablo
 *
 */
@Controller
@RequestMapping(value = "/searchplayas")
public class SearchPlayaController {

    @RequestMapping(method = RequestMethod.GET)
    public String getPlayas(Model model) {
	return "searchplayas";
    }
}
