package xyz.cupscoffee.imagecoffeeutils.ui.shared.adapter.in.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import xyz.cupscoffee.imagecoffeeutils.ui.shared.adapter.annotations.WebControllerAdapter;

/**
 * General controller.
 */
@WebControllerAdapter
@AllArgsConstructor
public class GeneralController {
    /**
     * Index page.
     *
     * @return the index page
     */
    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    /**
     * Health page.
     *
     * @param model the view.
     * @return the health page
     */
    @GetMapping("/health")
    public String getHealth(Model model) {
        model.addAttribute("jsf", "JSF Works!");
        model.addAttribute("jstl", "JSTL Works!");

        return "health";
    }

    /**
     * Sitemap.
     *
     * @return the sitemap
     */
    @GetMapping(value = "sitemap.xml")
    @ResponseBody
    public String getSitemap() {
        String sitemap = """
                <?xml version="1.0" encoding="UTF-8"?>
                <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
                    <url>
                        <loc>https://image-coffee-utils.cupscoffee.xyz/</loc>
                        <changefreq>monthly</changefreq>
                        <priority>1.0</priority>
                    </url>
                </urlset>""";

        return sitemap;
    }
}
