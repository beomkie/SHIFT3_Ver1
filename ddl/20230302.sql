create table account
(
    id                           int auto_increment
        primary key,
    phone_number                 varchar(15)                     null comment 'cellphone number',
    username                     varchar(50)                     null comment 'username',
    password                     varchar(80)                     null,
    role                         varchar(15) default 'ROLE_USER' null comment 'user role',
    ban                          tinyint(1)  default 0           null comment '밴 여부
false = no ban
true = yes ban',
    information_to_third_parties tinyint(1)  default 0           null comment '개인정보 3자 제공 및 위탁사항 체크 여부',
    name                         varchar(15)                     not null,
    interest                     json                            null,
    provider                     varchar(15)                     null
)
    comment '회원 테이블';

create table account_email_auth_log
(
    id         int auto_increment,
    email      varchar(50) null,
    code       varchar(10) null,
    created_at datetime    null,
    expired_at datetime    null,
    ip         varchar(30) null,
    constraint account_email_auth_log_id_uindex
        unique (id)
);

create table account_password_change
(
    id         int auto_increment
        primary key,
    hash       varchar(100) not null,
    username   varchar(50)  not null,
    expired_at datetime     not null,
    constraint account_password_change_pk
        unique (hash)
);

create table category
(
    id   int auto_increment
        primary key,
    name varchar(30) not null comment '카테고리 이름 (국문 )'
)
    comment '카테고리 테이블';

create table content_feed
(
    id                  int auto_increment
        primary key,
    thumbnail_file_name varchar(100)         not null,
    title               varchar(50)          not null comment '컨텐츠 제목',
    description         mediumtext           not null comment '내용',
    category            varchar(15)          not null,
    created_at          datetime             null,
    is_banner           tinyint(1) default 0 null,
    hit                 int        default 0 null
)
    comment '컨텐츠 피드 테이블';

create fulltext index title
    on content_feed (title, description);

create table content_feed_product
(
    id         int auto_increment,
    feed_id    int not null,
    product_id int null,
    constraint content_feed_product_id_uindex
        unique (id)
);

create table customer_service
(
    id          int auto_increment comment '인덱스'
        primary key,
    account_id  int         not null comment '작성자 id',
    cs_type     text        null comment '문의 유형',
    title       varchar(30) not null comment '문의글 제목',
    description mediumtext  not null comment '문의글 내용',
    created_at  datetime    null comment '작성일'
)
    comment '1대 1 고객센터 테이블';

create table customer_service_anwser
(
    id         int auto_increment comment '인덱스'
        primary key,
    cs_id      int        not null comment '문의 글 id',
    anwser     mediumtext not null comment '답장 내용',
    created_at datetime   null comment '답장시간'
)
    comment '1대1 문의 답장 테이블';

create table dibs
(
    id             int auto_increment comment '인덱스'
        primary key,
    account_id     int      not null comment '찜한 사용자 id',
    product_id     int      null comment '상품 id',
    select_shop_id int      null comment '편집샵 id',
    created_at     datetime null
)
    comment '찜 테이블';

create table email_verify
(
    id          int auto_increment comment '인덱스'
        primary key,
    username    varchar(30)                not null comment '가입 이메일',
    code        varchar(10)                not null comment '인증 코드',
    success     tinyint(1) default (false) null comment '인증 성공 여부',
    expired_at  datetime                   not null comment '인증 만료 시간 ( now + 10분 )',
    verify_type smallint                   not null comment '인증타입  (회원가입 0 or 비밀번호 찾기 1)'
)
    comment '이메일 인증';

create table image
(
    id             int auto_increment comment '인덱스'
        primary key,
    select_shop_id int          null comment '편집샵 이미지에 경우엔 not null',
    feed_id        int          null comment '피드 이미지에 경우엔 not null',
    product_id     int          null,
    image_name     varchar(100) not null,
    image_type     varchar(1)   not null comment '이미지 타입 (feed = f, shop = s, product = p)'
)
    comment '프로젝트 이미지 컬렉터';

create table notice
(
    id          int auto_increment comment '인덱스'
        primary key,
    title       varchar(100) null comment '공지사항 제목',
    description mediumtext   not null comment '공지사항 내용',
    created_at  datetime     null comment '작성일'
)
    comment '공지사항 테이블';

create table popup
(
    id          int auto_increment
        primary key,
    title       varchar(30) null,
    description mediumtext  null comment '팝업 내용',
    width       int         null,
    height      int         null,
    created_at  datetime    null
)
    comment '팝업 테이블';

create table qna
(
    id                   int auto_increment comment '인덱스'
        primary key,
    account_id           int           not null,
    question_title       varchar(100)  not null comment 'faq 질문',
    question_description varchar(1000) null,
    answer               varchar(1000) null comment 'faq 답변',
    question_type        varchar(10)   not null comment '질문 타입 (회원관련 ... 등 )',
    question_at          datetime      null,
    answer_at            datetime      null
)
    comment '고객센터';

create table select_shop
(
    id                    int auto_increment comment '인덱스'
        primary key,
    name                  varchar(30)   not null comment '편집샵 이름',
    introduce             varchar(70)   null comment '한줄 소개',
    latitude              double        not null comment '위도',
    longitude             double        not null comment '경도',
    street_address        varchar(100)  not null comment '도로명 주소',
    street_address_detail varchar(20)   null,
    contact_number        varchar(20)   not null comment '연락처 번호',
    operating_time        varchar(20)   null,
    hit_count             int default 0 null comment '편집샵 인기도 (구매하기 링크 클릭시 카운트)',
    created_at            datetime      null comment '생성일'
)
    comment '편집샵 테이블';

create table product
(
    id             int auto_increment comment '인덱스'
        primary key,
    name           varchar(100) not null comment '상품 이름',
    price          int          not null comment '상품 가격 (원화)',
    url            varchar(300) not null,
    select_shop_id int          null comment '편집샵 fk',
    description    mediumtext   not null comment '상품 설명',
    created_at     datetime     null,
    constraint fk_product_select_shop
        foreign key (select_shop_id) references select_shop (id)
)
    comment '상품 테이블';

create fulltext index searching
    on product (name, description);

create table select_shop_brand
(
    id             int auto_increment
        primary key,
    select_shop_id int         not null comment '편집샵 아이디',
    brand_name     varchar(50) not null comment '취급 브랜드 이름'
)
    comment '편집샵 취급 브랜드';


