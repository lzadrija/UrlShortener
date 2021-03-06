package com.lzadrija.help;

import static org.springframework.http.HttpStatus.FOUND;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/help")
public class HelpDocsController {

    @ResponseStatus(value = FOUND)
    @RequestMapping(method = GET)
    public ModelAndView redirectToHelpPage() {

        RedirectView rv = new RedirectView("/docs/index.html", true);
        return new ModelAndView(rv);

    }

}
