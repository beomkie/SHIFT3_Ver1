package com.sch.shift3.user.view;

import com.sch.shift3.user.service.FeedService;
import com.sch.shift3.user.service.ProductService;
import com.sch.shift3.utill.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserView {
    private final FeedService feedService;
    private final ProductService productService;

    @GetMapping("/")
    public String mainPage(Model model){
        model.addAttribute("disableLoading", true);

        // Recent Feed
        model.addAttribute("recentFeed", feedService.getRecentFeed());

        // category Feed
        model.addAttribute("clothes_category", feedService.getFeedByCategory("옷"));

        return "user/content/main";
    }

    @GetMapping("/file/{fileName}")
    public ResponseEntity<Resource> getImageFile(@PathVariable String fileName){
        return ImageUtil.display(fileName);
    }

    @GetMapping("/introduce")
    public String introducePage() {
        return "user/content/pages/introduce";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("disableLoading", true);
        return "user/content/login";
    }

    @GetMapping("/forgot")
    public String forgotPage(){
        return "user/content/forgot";
    }

    @GetMapping("/shop-list")
    public String shopListPage(Model model){
        model.addAttribute("disableLoading", true);
        return "user/content/pages/shop-list";
    }

    @GetMapping("/content-list")
    public String contentListPage(Model model){
        model.addAttribute("disableLoading", true);
        return "user/content/pages/content-list";
    }

    @GetMapping("/mypage")
    public String myPage(Model model){
        model.addAttribute("enable", "index");
        return "user/content/pages/my-page/index";
    }

    @GetMapping("/mypage/dips")
    public String myDipsPage(Model model){
        model.addAttribute("enable", "dips");
        return "user/content/pages/my-page/dips";
    }

    @GetMapping("/mypage/notice")
    public String myNoticePage(Model model){
        model.addAttribute("enable", "notice");
        return "user/content/pages/my-page/notice";
    }

    @GetMapping("/mypage/cs")
    public String myCsPage(Model model){
        model.addAttribute("enable", "cs");
        return "user/content/pages/my-page/cs";
    }

    @GetMapping("/feed/{id}")
    public String feedPage(Model model, @PathVariable Integer id){
        model.addAttribute("disableLoading", true);
        model.addAttribute("feed", feedService.getFeedById(id));
        model.addAttribute("products", productService.getProductsByFeed(id));
        productService.getProductsByFeed(id).get(0).getImages().forEach(
                image -> log.info("image: {}", image.getImageName())
        );
//        model.addAttribute("feed", feedService.getFeedById(id));

//        log.info("feed: {}", feedService.getFeedById(id));

        return "user/content/pages/feed/read";
    }

    @GetMapping("/product/{productId}")
    public String productPage(Model model, @PathVariable Integer productId){
        model.addAttribute("product", productService.getProductById(productId));
        model.addAttribute("disableLoading", true);
        return "user/content/pages/product/detail";
    }


}