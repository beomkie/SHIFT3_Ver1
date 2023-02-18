// window.initMap = function () {
//
//     let mapDiv = document.getElementById('map');
//     window.map = new naver.maps.Map(mapDiv, {
//         zoom: 19,
//         minZoom: 1,
//     });
// }

window.infowindow = [];

window.removeAllMarker = function () {
    infowindow.forEach(function (e) {
        let ele = e.getElement();
        $(ele).remove();
    });

    window.infowindow = [];
}

window.drawMap = function (shop) {
    let lat = shop.latitude;
    let lng = shop.longitude;
    let position = new naver.maps.LatLng(lat, lng);


    var markerContent =
            `<div style="position:absolute;">
            <div class="infowindow" style="
                    font-size: 0.6rem;
                    padding: 0.8rem; display:none;position:absolute;
                    width: 13rem;
                    height: 4rem;
                    top: -5rem;
                    left: -6rem;
                    background-color:white;z-index:1;
                    border-radius: 1.3em;">
                <a class="float-end btn-close"></a>
                <div class="row g-0">
                    <div class="col-4">
                    ${(shop.imageSelectShops.length !== 0) ?
                `<img src="/file/${shop.imageSelectShops[0].imageName}" alt="편집샵 미리보기" class="thumbnail"/>` :
                `<img src="https://icon-library.com/images/no-picture-available-icon/no-picture-available-icon-20.jpg" class="thumbnail" alt="no-image">`}
                    </div>
                    <div class="col-8" style="align-self: center;    padding-left: 0.2rem;">
                        <span style="word-break: keep-all">${shop.name}</span>
                    </div>
                </div>
            </div>
<!--            todo marker image change-->
            <div class="pin_s" style="cursor: pointer; width: 22px; height: 30px;">
                <img src="https://ssl.pstatic.net/static/maps/img/icons/pin_s_3.png" alt="" style="margin: 0px; padding: 0px; border: 0px solid transparent; display: block; max-width: none; max-height: none; -webkit-user-select: none; position: absolute; width: 22px; height: 30px; left: 0px; top: 0px;">
            </div> 
        </div>`,

        htmlMarker = new naver.maps.Marker({
            position: position,
            map: map,
            icon: {
                content: markerContent,
                size: new naver.maps.Size(22, 30),
                anchor: new naver.maps.Point(11, 30)
            }
        }), elHtmlMarker = htmlMarker.getElement();

    window.infowindow.push(htmlMarker);

    $(elHtmlMarker).on('click', 'img', function (e) {
        if ($("#shopDetail #shopId").val() != shop.id) {
            detailShop(shop)
        }
        $("#shopDetail").show();

        $(elHtmlMarker).find('.infowindow').show();
    });

    $(elHtmlMarker).on('click', '.btn-close', function (e) {
        $(elHtmlMarker).find('.infowindow').hide();
    });

    var overlay = new CustomOverlay({
        map: map,
        position: position,
        shop: shop,
    });

    // when marker click, toggle overlay
    naver.maps.Event.addListener(htmlMarker, 'click', function (e) {
        if (overlay.getMap()) {
            overlay.setMap(null);
        } else {
            overlay.setMap(map);
        }
    });


    // marker = new naver.maps.Marker({
    //     map: map,
    //     position: position
    // });

    // 지도 Zoom 레벨에 따라 InfoWindow 크기 조절
    /*    map.addListener('zoom_changed', function () {
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
        });*/

    // all info window open
    // iw.open(map, marker);
    // $('.iw_inner').parent().parent().css('border-radius', '50px');

    /*naver.maps.Event.addListener(marker, 'click', function (e) {
        // info window open
        iw.open(map, marker);
        detailShop(shop);
    });*/

    // infowindow.push(iw);

    // panto
    map.setZoom(13);
    map.panTo(position);
}
// initMap();