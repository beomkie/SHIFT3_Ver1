console.log("ASDASDASDASDS")

initMap = function () {

    let mapDiv = document.getElementById('map');
    window.map = new naver.maps.Map(mapDiv, {
        zoom: 19,
        minZoom: 1,
    });
    //
    // window.infoWindow = new naver.maps.InfoWindow({
    //     anchorSkew: true
    // });

    /*   //지도 클릭 이벤트 셋팅
       window.map.addListener('click', function(e) {
           mapUtil.searchCoordinateToAddress(e.coord);
       });*/

    //기본 좌표 셋팅
    // mapUtil.searchAddressToCoordinate('서울특별시 관악구 신림로53가길 12');
    //
    // let streetLayer = new naver.maps.StreetLayer();
    // let cadastralLayer = new naver.maps.CadastralLayer();
    //
    // naver.maps.Event.once(map, 'init', function() {
    //     const customControl = new naver.maps.CustomControl(customButtonHTML, {
    //         position: naver.maps.Position.TOP_RIGHT
    //     });
    //     customControl.setMap(map);
    // });

    //지적도 설정 끝
    console.log("asd");
}

initMap();