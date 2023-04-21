package acme.controllers;

import acme.entities.banner.Banner;
import acme.services.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class BannerAdvisor {

    @Autowired
    private BannerService repository;

    @ModelAttribute("banner")
    public Banner getBanner() {
        Banner result;

        result = repository.findRandomBanner();

        return result;
    }
}
