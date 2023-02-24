window.loadingTag = function (ele) {
    new Tagify(ele, {
        whitelist: ["옷", "신발", "전자기기"], // 화이트리스트 배열
        maxTags: 10, // 최대 허용 태그 갯수
        userInput: false,

        dropdown: {
            maxItems: 20,           // 드롭다운 메뉴에서 몇개 정도 항목을 보여줄지
            classname: "tags-look", // 드롭다운 메뉴 엘리먼트 클래스 이름. 이걸로 css 선택자로 쓰면 된다.
            enabled: 0,             // 단어 몇글자 입력했을떄 추천 드롭다운 메뉴가 나타날지
            closeOnSelect: false    // 드롭다운 메뉴에서 태그 선택하면 자동으로 꺼지는지 안꺼지는지
        }
    });
}