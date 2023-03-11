package com.sch.shift3.user.view;

import com.sch.shift3.admin.service.AdminNoticeService;
import com.sch.shift3.admin.service.PopupService;
import com.sch.shift3.config.CurrentUser;
import com.sch.shift3.user.dto.ShopRequest;
import com.sch.shift3.user.entity.Account;
import com.sch.shift3.user.entity.AccountPasswordChange;
import com.sch.shift3.user.entity.ContentFeed;
import com.sch.shift3.user.entity.Product;
import com.sch.shift3.user.repository.AccountPasswordChangeRepository;
import com.sch.shift3.user.repository.ShopRepository;
import com.sch.shift3.user.service.DibService;
import com.sch.shift3.user.service.FeedService;
import com.sch.shift3.user.service.ProductService;
import com.sch.shift3.user.service.QuestionService;
import com.sch.shift3.utill.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserView {
    private final FeedService feedService;
    private final ProductService productService;
    private final ShopRepository shopRepository;
    private final PopupService popupService;
    private final QuestionService questionService;
    private final DibService dibService;
    private final AdminNoticeService adminNoticeService;
    private final AccountPasswordChangeRepository accountPasswordChangeRepository;

    @GetMapping("/")
    public String mainPage(Model model){

        model.addAttribute("disableLoading", true);
        model.addAttribute("popupList", popupService.getAllPopupList());
        model.addAttribute("recentFeed", feedService.getRecentFeed());
        model.addAttribute("bannerFeed", feedService.getBannerFeed());
        // category Feed
        model.addAttribute("clothes_category", feedService.getFeedByCategory("옷"));
        model.addAttribute("ele_category", feedService.getFeedByCategory("전자기기"));

        return "user/content/main";
    }

    @GetMapping("/file/{fileName}")
    public ResponseEntity<Resource> getImageFile(@PathVariable String fileName){
        return ImageUtil.display(fileName);
    }

    @GetMapping("/introduce")
    public String introducePage(Model model) {
//        model.addAttribute("disableLoading", true);
        return "user/content/pages/introduce";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("disableLoading", true);
        return "user/content/login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("disableLoading", true);
        return "user/content/signup";
    }

    @GetMapping("/forgot")
    public String forgotPage(Model model){
        model.addAttribute("disableLoading", true);
        return "user/content/forgot";
    }

    @GetMapping("/shop-list")
    public String shopListPage(Model model, ShopRequest shopRequest){
        model.addAttribute("disableLoading", true);
        model.addAttribute("disableFooter", true);

        model.addAttribute("shopRequest", shopRequest);

        if (shopRequest.isEmpty()){
            model.addAttribute("shopList", shopRepository.getShopList(new ShopRequest()));
        } else {
            model.addAttribute("shopList", shopRepository.getShopList(shopRequest));
        }

        return "user/content/pages/shop-list";
    }

    @GetMapping("/shop-detail/{shopId}")
    public String shopDetailPage(Model model, @PathVariable Integer shopId){
        model.addAttribute("disableLoading", true);

        model.addAttribute("shop", shopRepository.getShopById(shopId));
        model.addAttribute("relatedFeed", feedService.getRelatedFeedByShopId(shopId));

        return "user/content/pages/shop-detail";
    }

    @GetMapping("/content-list")
    public String contentListPage(Model model, String category){
        model.addAttribute("disableLoading", true);
        if (category == null) {
            category = "전체";
        }

        model.addAttribute("category", category);
        return "user/content/pages/content-list";
    }

    @GetMapping("/mypage")
    public String myPage(Model model, @CurrentUser Account account){
        model.addAttribute("enable", "index");
        model.addAttribute("account", account);

        if (account == null)
            return "user/content/login";

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
        model.addAttribute("noticeList", adminNoticeService.getNotices());
        return "user/content/pages/my-page/notice";
    }

    @GetMapping("/mypage/cs")
    public String myCsPage(Model model){
        model.addAttribute("enable", "cs");
        return "user/content/pages/my-page/cs";
    }

    @GetMapping("/mypage/qna")
    public String qnaPage(Model model){
        model.addAttribute("enable", "cs");

        return "user/content/pages/my-page/qna";
    }

    @GetMapping("/mypage/qna/{qnaId}")
    public String qnaDetailPage(Model model, @CurrentUser Account account, @PathVariable Integer qnaId){
        model.addAttribute("enable", "cs");
        model.addAttribute("question", questionService.getMyQnaById(account, qnaId));

        return "user/content/pages/my-page/qna-detail";
    }

    @GetMapping("/mypage/qna-list")
    public String qnaListPage(Model model, @CurrentUser Account account){
        model.addAttribute("enable", "cs");
        model.addAttribute("questionList", questionService.getMyQnaList(account));
        return "user/content/pages/my-page/qna-list";
    }

    @GetMapping("/notice/{noticeId}")
    public String getNoticePage(Model model, @PathVariable Integer noticeId){
        model.addAttribute("notice", adminNoticeService.findNoticeById(noticeId));
        return "user/content/pages/my-page/notice-detail";
    }

    @GetMapping("/feed/{id}")
    public String feedPage(Model model, @PathVariable Integer id){
        model.addAttribute("disableLoading", true);
        ContentFeed feed = feedService.getFeedById(id);
        model.addAttribute("feed", feed);
        List<ContentFeed> resemblanceFeedList = feedService.getResemblanceFeed(feed, 3);
        log.info("resemblanceFeedList : {}", resemblanceFeedList);
        model.addAttribute("relatedFeedList", resemblanceFeedList);
        model.addAttribute("products", productService.getProductsByFeed(id));

        return "user/content/pages/feed/read";
    }

    @GetMapping("/product/{productId}")
    public String productPage(Model model, @PathVariable Integer productId, @CurrentUser Account account){
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        model.addAttribute("disableLoading", true);
        if (account != null){
            model.addAttribute("isDibs", dibService.isProductDib(account.getId(), productId));
        } else {
            model.addAttribute("isDibs", false);
        }
        model.addAttribute("relatedFeed", feedService.getRelatedFeed(productId));
        model.addAttribute("relatedProducts", productService.getRelatedProduct(product));


        return "user/content/pages/product/detail";
    }


    @GetMapping("/account/forgot/{hash}")
    public String forgotPassword(@PathVariable String hash, Model model){
        model.addAttribute("disableLoading", true);

        if (hash == null || hash.isEmpty())
            model.addAttribute("error", "잘못된 접근입니다.");

        AccountPasswordChange accountPasswordChange = accountPasswordChangeRepository.findByHash(hash)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다."));

        if (accountPasswordChange.getExpiredAt().isBefore(LocalDateTime.now())){
            model.addAttribute("error", "만료된 링크입니다.");
            return "user/content/forgot-change";
        }

        log.info("hash : {}", hash);
        model.addAttribute("hash", hash);
        return "user/content/forgot-change";
    }


}