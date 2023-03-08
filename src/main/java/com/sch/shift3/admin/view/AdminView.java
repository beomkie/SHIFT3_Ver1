package com.sch.shift3.admin.view;

import com.sch.shift3.admin.service.*;
import com.sch.shift3.user.dto.*;
import com.sch.shift3.user.entity.Account;
import com.sch.shift3.user.entity.ContentFeed;
import com.sch.shift3.user.entity.Image;
import com.sch.shift3.user.entity.Product;
import com.sch.shift3.user.service.FeedService;
import com.sch.shift3.user.service.ProductService;
import com.sch.shift3.user.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminView {

    private final AdminShopService adminShopService;
    private final AdminProductService adminProductService;
    private final AdminContentFeedService adminContentFeedService;
    private final FeedService feedService;
    private final ProductService productService;
    private final PopupService popupService;
    private final QuestionService questionService;
    private final AdminNoticeService adminNoticeService;
    private final AdminAccountService adminAccountService;

    @GetMapping("")
    public String mainPage(Model model){
//        return "admin/content/main";
        model.addAttribute("questionList", questionService.getAllQuestionList());
        return "admin/content/pages/cs/list";
    }

    @GetMapping("/account/list")
    public String accountListPage(Model model){
        model.addAttribute("accounts", adminAccountService.getAllAccounts());
        return "admin/content/pages/account/list";
    }

    @GetMapping("/account/detail/{accountId}")
    public String accountDetailPage(Model model, @PathVariable Integer accountId){
        Account account = adminAccountService.getAccountById(accountId)
                        .orElse(new Account());
        model.addAttribute("account", account);
        return "admin/content/pages/account/view";
    }

    @GetMapping("/account/edit/{accountId}")
    public String accountEditPage(Model model, @PathVariable Integer accountId){
        Account account = adminAccountService.getAccountById(accountId)
                .orElse(new Account());
        model.addAttribute("account", account);
        return "admin/content/pages/account/edit";
    }

    @GetMapping("/contents/list")
    public String contentListPage(Model model){
        model.addAttribute("contents", adminContentFeedService.getAllContentList());

        return "admin/content/pages/contents/list";
    }

    @GetMapping("/contents/create")
    public String contentCreatePage(Model model){
        ContentFeedDto contentFeedDto = new ContentFeedDto();
//        contentFeedDto.setTitle("테스트");
//        contentFeedDto.setDescription("테스트");
//        contentFeedDto.setThumbnailFileName("eb2172fd-61b0-485d-826b-a65ca131074d2.png");

        model.addAttribute("ContentFeedDto", contentFeedDto);
        return "admin/content/pages/contents/create";
    }

    @GetMapping("/contents/edit/{feedId}")
    public String contentCreatePage(Model model, @PathVariable Integer feedId){
        ContentFeed contentFeed = feedService.getFeedById(feedId);
        ContentFeedDto dto = ContentFeedDto.builder()
                .id(contentFeed.getId())
                .description(contentFeed.getDescription())
                .title(contentFeed.getTitle())
                .category(contentFeed.getCategory())
                .thumbnailFileName(contentFeed.getThumbnailFileName())
                .createdAt(contentFeed.getCreatedAt())
                .isBanner(contentFeed.getIsBanner())
                .build();

        List<Product> products = productService.getProductsByFeed(feedId);
        if (!products.isEmpty())
            dto.setProducts(products);

        model.addAttribute("ContentFeedDto", dto);
        model.addAttribute("editMode", true);

        return "admin/content/pages/contents/create";
    }

    @GetMapping("/shop/list")
    public String shopListPage(Model model){
        model.addAttribute("shops", adminShopService.getAllShopList());
        return "admin/content/pages/shop/list";
    }

    @GetMapping("/shop/create")
    public String shopCreatePage(Model model){
        SelectShopDto selectShopDto = new SelectShopDto();
//        selectShopDto.setStreetAddress("서울특별시 강남구 역삼동 824-1");
//        selectShopDto.setName("테스트");
//        selectShopDto.setIntroduce("테스트");
//        selectShopDto.setStreetAddressDetail("테스트");
//        selectShopDto.setContactNumber("010-1234-5678");
        selectShopDto.setOperatingTime("09:00 ~ 18:00");

        model.addAttribute("SelectShopDto", selectShopDto);
        return "admin/content/pages/shop/create";
    }

    @GetMapping("/shop/edit/{id}")
    public String shopEditPage(Model model, @PathVariable Integer id){

        SelectShopDto selectShopDto = adminShopService.getShop(id);
        log.info("selectShopDto: {}", selectShopDto);

        if (selectShopDto.getImageList() != null) {
            // selectShopDto.getImageList() sort by id asc
            selectShopDto.getImageList().sort(Comparator.comparing(Image::getId));
        }

        model.addAttribute("editMode", true);
        model.addAttribute("SelectShopDto", selectShopDto);
        return "admin/content/pages/shop/create";
    }

    @GetMapping("/product/list")
    public String productListPage(Model model){
        log.info("product List {}",  adminProductService.getAllProductList());
        model.addAttribute("products", adminProductService.getAllProductList());
        return "admin/content/pages/product/list";
    }

    @GetMapping("/product/create")
    public String productCreatePage(Model model){
        ProductDto productDto = new ProductDto();
//        productDto.setName("상품");
//        productDto.setPrice(1000);
//        productDto.setUrl("https://www.naver.com");
//        productDto.setDescription("Lorem Blaaaaaa");

        model.addAttribute("ProductDto", productDto);

        return "admin/content/pages/product/create";
    }

    @GetMapping("/product/edit/{id}")
    public String productEditPage(Model model, @PathVariable Integer id){

        ProductDto productDto = adminProductService.getProduct(id);


        model.addAttribute("editMode", true);
        model.addAttribute("ProductDto", productDto);
        return "admin/content/pages/product/create";
    }

    @GetMapping("/popup/list")
    public String popupListPage(Model model){
        log.info("popup List {}",  popupService.getAllPopupList());
        model.addAttribute("popupList", popupService.getAllPopupList());
        return "admin/content/pages/popup/list";
    }

    @GetMapping("/popup/create")
    public String popupCreatePage(Model model){
        model.addAttribute("PopupDto", new PopupDto());
        return "admin/content/pages/popup/create";
    }

    @GetMapping("/popup/edit/{id}")
    public String popupEditPage(Model model, @PathVariable Integer id){
        model.addAttribute("editMode", true);
        model.addAttribute("PopupDto", popupService.getPopup(id));
        return "admin/content/pages/popup/create";
    }

    @GetMapping("/cs/list")
    public String csListPage(Model model){
        model.addAttribute("questionList", questionService.getAllQuestionList());
        return "admin/content/pages/cs/list";
    }

    @GetMapping("/notice/list")
    public String noticeListPage(Model model){
        model.addAttribute("noticeList", adminNoticeService.getNotices());
        return "admin/content/pages/notice/list";
    }

    @GetMapping("/notice/create")
    public String noticeCreatePage(Model model){
        NoticeDto notice = new NoticeDto();
        model.addAttribute("notice", notice);
        return "admin/content/pages/notice/create";
    }

    @GetMapping("/notice/edit/{id}")
    public String noticeEditPage(Model model, @PathVariable Integer id){
        model.addAttribute("notice", adminNoticeService.findNoticeById(id));
        model.addAttribute("editMode", true);
        return "admin/content/pages/notice/create";
    }

    @GetMapping("/banner/list")
    public String bannerListPage(Model model){
        model.addAttribute("bannerList", null);
        return "admin/content/pages/banner/list";
    }

}
