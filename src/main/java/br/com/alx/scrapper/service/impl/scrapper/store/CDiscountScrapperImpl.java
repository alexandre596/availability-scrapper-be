package br.com.alx.scrapper.service.impl.scrapper.store;

import br.com.alx.scrapper.service.scrapper.ScrapperService;
import br.com.alx.scrapper.service.impl.scrapper.base.BaseScrapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CDiscountScrapperImpl extends BaseScrapper implements ScrapperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CDiscountScrapperImpl.class);

    @Autowired
    public CDiscountScrapperImpl(WebDriver ghostDriver, @Value("${sites.cdiscount.url}") String url) {
        super(ghostDriver, url);
    }

    @Override
    public Optional<String> getPriceSection() {
        Document page = getFullPageContent(this.url);
        Elements repositories = page.select("#MainZone1 > div.carousel.carouselImage.jsCarouselImage > ul > li:nth-child(1)");

        if (repositories == null || !repositories.toString().contains("Console Xbox Series X")) {
            LOGGER.warn("The item could not be found. Maybe something has changed?");
            return Optional.of("Invalid selection " + RandomStringUtils.randomAlphanumeric(5));
        } else {
            return Optional.ofNullable(repositories.toString());
        }
    }

    @Override
    public String getStoreName() {
        return "CDiscount";
    }
}