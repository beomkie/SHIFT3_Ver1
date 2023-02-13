// window.initMap = function () {
//
//     let mapDiv = document.getElementById('map');
//     window.map = new naver.maps.Map(mapDiv, {
//         zoom: 19,
//         minZoom: 1,
//     });
// }

window.infowindow = [];

window.drawMap = function (shop) {
    let lat = shop.latitude;
    let lng = shop.longitude;
    let position = new naver.maps.LatLng(lat, lng);

    // naverMap infoWindow
    var contentString =
        `<div class="iw_inner" style="height:100px; padding: 20px;">
            <div class="row">
                <div class="col-4">
                    <img src="/file/${shop.imageSelectShops[0].imageName}" alt="편집샵 미리보기" class="thumbnail"/>
                </div>
                <div class="col-8" style="align-self: center;">
                    <span style="word-break: keep-all">${shop.name}</span>
                </div>
            </div>
        </div>`;


    window.infowindow = new naver.maps.InfoWindow({
        content: contentString,
        borderWidth: 0,
        anchorSkew: true,
        // disableAnchor: true,
        backgroundColor: 'white',
    });

    marker = new naver.maps.Marker({
        map: map,
        position: position
    });

    // 지도 Zoom 레벨에 따라 InfoWindow 크기 조절
    map.addListener('zoom_changed', function () {
        var zoomLevel = map.getZoom();
        var fontSize = 15;
        var heightSize = 6.5;

        if (zoomLevel > 15) {
            fontSize = 13;
            heightSize = 7;
        }
        console.log(zoomLevel + "., " + fontSize);
        $('.iw_inner').parent().parent().attr('style', `
        position: absolute; top: 0px; left: 0px; z-index: 0; margin: 0px; padding: 0px; border: 0px solid rgb(51, 51, 51); display: block; cursor: default; box-sizing: content-box !important; background: white; border-radius: 50px;`);
        $('.iw_inner').parent().attr('style', `
        margin: 0px; padding: 0px; border: 0px solid transparent; display: inline-block; box-sizing: content-box !important;
        font-size: ${fontSize}px; width:15em;  height: ${heightSize}em;`);
    });


    // change zoom level
    map.setZoom(map.getZoom());

    // all info window open
    infowindow.open(map, marker);
    $('.iw_inner').parent().parent().css('border-radius', '50px');

    naver.maps.Event.addListener(marker, 'click', function (e) {
        // info window open
        detailShop(shop);
    });

    // panto
    map.panTo(position);

    console.log(shop.name + " : " + shop.latitude + ", " + shop.longitude)
}
// initMap();