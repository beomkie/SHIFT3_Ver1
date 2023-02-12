package com.sch.shift3.user.view;

import com.sch.shift3.admin.service.AdminNoticeService;
import com.sch.shift3.admin.service.PopupService;
import com.sch.shift3.config.CurrentUser;
import com.sch.shift3.user.dto.ShopRequest;
import com.sch.shift3.user.entity.Account;
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

    @GetMapping("/")
    public String mainPage(Model model){

        /*List<PopupDto> popupList = new ArrayList<>();
        List<ContentFeed> recentFeed = new ArrayList<>();
        List<ContentFeedProduct> clothes_category = new ArrayList<>();

        model.addAttribute("popupList", popupList);
        model.addAttribute("recentFeed", recentFeed);
        model.addAttribute("clothes_category", clothes_category);*/


        model.addAttribute("disableLoading", true);
        model.addAttribute("popupList", popupService.getAllPopupList());
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
        model.addAttribute("shopList", shopRepository.getShopList(new ShopRequest()));
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
        model.addAttribute("feed", feedService.getFeedById(id));
        model.addAttribute("products", productService.getProductsByFeed(id));

        return "user/content/pages/feed/read";
    }

    @GetMapping("/product/{productId}")
    public String productPage(Model model, @PathVariable Integer productId, @CurrentUser Account account){
        model.addAttribute("product", productService.getProductById(productId));
        model.addAttribute("disableLoading", true);
        if (account != null){
            model.addAttribute("isDibs", dibService.isProductDib(account.getId(), productId));
        } else {
            model.addAttribute("isDibs", false);
        }
        model.addAttribute("relatedFeed", feedService.getRelatedFeed(productId));

        return "user/content/pages/product/detail";
    }


}